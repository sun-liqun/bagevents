package com.bagevent.synchro_data;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bagevent.db.Attends_Table;
import com.bagevent.db.CollectChild_Table;
import com.bagevent.util.dbutil.DBUtil;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.data.ModifyData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.AddPeopleCheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.AuditPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.CheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ModifyUserInfoPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.SubmitOrderPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.UpLoadImagePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AddPeopleCheckInView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AuditView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CheckInView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ModifyUserInfoView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SubmitOrderView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpLoadImageView;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.CollectChild;
//import com.bagevent.db.CollectChild_Table;
import com.bagevent.home.data.ExportData;
import com.bagevent.home.home_interface.presenter.PostCollectPresenter;
import com.bagevent.home.home_interface.view.PostCollectionView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zwj on 2016/7/21.
 */
public class StartSyncDataService extends Service implements SubmitOrderView, CheckInView, AddPeopleCheckInView, AuditView, ModifyUserInfoView, UpLoadImageView, PostCollectionView {

    private static final String TAG = "StartSyncDataService";
    private int eventId = -1;
    private int attendId = -1;


    /**
     * 添加参会人员信息
     */
    private String map = "";
    private int buyWay = -1;
    private List<Attends> addAttend;
    private int addIndex = -1;
    private SubmitOrderPresenter submitOrderPresenter;

    /**
     * 签到
     */
    private int checkStatus = -1;
    private String ref_code = "";
    private List<Attends> checkinAttendee;
    private int checkinIndex = -1;
    private CheckInPresenter checkInPresenter;
    private AddPeopleCheckInPresenter addPeopleCheckInPresenter;

    /**
     * 审核
     */
    private int audit = -1;
    private List<Attends> auditAttend;
    private int auditIndex = -1;
    private AuditPresenter auditPresenter;

    /**
     * 修改
     */
    private String gsonUser = "";//所有表单信息的map
    private String modifyInfo = "";//修改的map
    private List<Attends> modifyAttend;
    private int modifyIndex = -1;
    private ModifyUserInfoPresenter modifyPresenter;
    private UpLoadImagePresenter upLoadImagePresenter = new UpLoadImagePresenter(this);

    //表单中包含图像采集
    private boolean isContains = false;
    private String imgPath = "";
    private String userId = "";
    private File file = null;
    private int imgFiledId = -1;

    private boolean isAddOrModify = false;//true标识为添加中的图像采集,false标识为修改中的图像采集

    /**
     * 采集点
     */
    private List<CollectChild> collect;
    private int collectIndex = -1;
    private int collectionId = -1;
    private String barcode = "";
    private PostCollectPresenter postPresenter;

    private String collectTime = "";
    private String auditTime = "";
    private String checkInTime = "";
    private boolean isRunService = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "start sync data~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isRunService) {
            isRunService = false;
            addAttend = new Select().from(Attends.class).where(Attends_Table.addSync.is(Constants.ADD_NOT_SYNC)).queryList();
            checkinAttendee = new Select().from(Attends.class).where(Attends_Table.isSync.is(Constants.NOTSYNC)).queryList();
            auditAttend = new Select().from(Attends.class).where(Attends_Table.auditSync.is(Constants.AUDIT_NOT_SYNC)).queryList();
            modifyAttend = new Select().from(Attends.class).where(Attends_Table.modifyIsSync.is(Constants.MODIFY_NOT_SYNC)).queryList();
            collect = new Select().from(CollectChild.class).where(CollectChild_Table.collectIsSync.is(Constants.COLLECT_NOT_SYNV)).queryList();
            Log.e(TAG, "start sync data~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Log.e(TAG, checkinAttendee.size() + "F");

            if (addAttend.size() != 0) {//添加
                addIndex = 0;
                syncAdd(addIndex);
            } else if (auditAttend.size() != 0) {//审核
                //  Log.e("fdf","fdfdf");
                auditIndex = 0;
                syncAudit(auditIndex);
            } else if (modifyAttend.size() != 0) {//修改
                // Log.e("size",""+modifyAttend.size());
                modifyIndex = 0;
                syncModify(modifyIndex);
            } else if (collect.size() != 0) {//采集点
                //Log.e("dffa","collect");
                collectIndex = 0;
                syncCollect(collectIndex);
            } else if (checkinAttendee.size() != 0) {//签到
                //   Log.e("fdf","fdfds");
                checkinIndex = 0;
                syncCheckin(checkinIndex);
            } else {
                //   Log.e(TAG,"stop the service");
                isRunService = true;
                stopSelf();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 同步参会人员
     *
     * @param addIndex
     */
    private void syncAdd(int addIndex) {
        //Log.e("fa","add");
        // if(TextUtils.isEmpty(addAttend.get(addIndex).imgPath)) {
        eventId = addAttend.get(addIndex).eventId;
        buyWay = addAttend.get(addIndex).buyWays;
        map = addAttend.get(addIndex).addMap;
        ref_code = addAttend.get(addIndex).refCodes;
        gsonUser = addAttend.get(addIndex).gsonUser;
        submitOrderPresenter = new SubmitOrderPresenter(this);
        submitOrderPresenter.submitOrder(ref_code);
        //    Log.e("name-->", addAttend.get(addIndex).names);
        /*}else {
            userId = addAttend.get(addIndex).userId;
            imgPath = addAttend.get(addIndex).imgPath;

            eventId = addAttend.get(addIndex).eventId;
            buyWay = addAttend.get(addIndex).buyWays;
            map = addAttend.get(addIndex).addMap;
            ref_code = addAttend.get(addIndex).refCodes;
            gsonUser = addAttend.get(addIndex).gsonUser;

            if(imgPath.contains("luban_disk_cache")) {
                isAddOrModify = true;
                file = new File(imgPath);
                upLoadImagePresenter.upLoadImageFile();
            }else {
                submitOrderPresenter.submitOrder();
            }
        }*/

    }

    /**
     * 同步审核
     *
     * @param auditIndex
     */
    private void syncAudit(int auditIndex) {
        //Log.e("audit","audit");
        eventId = auditAttend.get(auditIndex).eventId;
        attendId = auditAttend.get(auditIndex).attendId;
        audit = auditAttend.get(auditIndex).audits;
        auditTime = auditAttend.get(auditIndex).auditTimes;
        auditPresenter = new AuditPresenter(this);
        auditPresenter.getAudit(audit + "");
    }

    /**
     * 同步修改
     *
     * @param modifyIndex
     */
    private void syncModify(int modifyIndex) {
        //Log.e("modify","modify");
        //   if(TextUtils.isEmpty(modifyAttend.get(modifyIndex).imgPath)) {//如果图片路径为空,则说明无需改动图片
        eventId = modifyAttend.get(modifyIndex).eventId;
        attendId = modifyAttend.get(modifyIndex).attendId;
        gsonUser = modifyAttend.get(modifyIndex).gsonUser;
        ref_code = modifyAttend.get(modifyIndex).refCodes;

        modifyPresenter = new ModifyUserInfoPresenter(this);
        modifyPresenter.modifyInfo();
        /*}else {
            imgPath = modifyAttend.get(modifyIndex).imgPath;
            userId = modifyAttend.get(modifyIndex).userId;

            eventId = modifyAttend.get(modifyIndex).eventId;
            attendId = modifyAttend.get(modifyIndex).attendId;
            gsonUser = modifyAttend.get(modifyIndex).gsonUser;
            modifyInfo = modifyAttend.get(modifyIndex).modifyMap;
            ref_code = modifyAttend.get(modifyIndex).refCodes;

            if(imgPath.contains("luban_disk_cache")) {
                isAddOrModify = false;
                file = new File(imgPath);
                upLoadImagePresenter.upLoadImageFile();
            }else {
                modifyPresenter.modifyInfo();
            }
        }*/

    }

    /**
     * 同步采集人员
     *
     * @param collectIndex
     */
    private void syncCollect(int collectIndex) {
        eventId = collect.get(collectIndex).eventId;
        collectionId = collect.get(collectIndex).collectionId;
        barcode = collect.get(collectIndex).attendeeBarcode;
        collectTime = collect.get(collectIndex).attendeeCheckInTime;
        postPresenter = new PostCollectPresenter(this);
        postPresenter.postCollection();
    }

    /**
     * 同步签到
     *
     * @param checkinIndex
     */
    private void syncCheckin(int checkinIndex) {
        eventId = checkinAttendee.get(checkinIndex).eventId;
        attendId = checkinAttendee.get(checkinIndex).attendId;
        checkStatus = checkinAttendee.get(checkinIndex).checkins;
        ref_code = checkinAttendee.get(checkinIndex).refCodes;
        checkInTime = checkinAttendee.get(checkinIndex).checkinTimes;
        if (attendId == 0) {
            addPeopleCheckInPresenter = new AddPeopleCheckInPresenter(this);
            addPeopleCheckInPresenter.addPeopleCheckIn(ref_code);
        } else {
            checkInPresenter = new CheckInPresenter(this);
            checkInPresenter.attendCheckIn();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().postSticky(new MsgEvent("SyncDataService"));
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 获得当前时间
     *
     * @return
     */
    private String currentTime() {
        Long time = System.currentTimeMillis();
        //     String currentTime = time.toString();
        return time.toString();
    }

    /**
     * 添加参会人员
     */
    @Override
    public String map() {
        // Log.e("add",map);
        return map;
    }

    @Override
    public String buyWay() {
        return buyWay + "";
    }

    @Override
    public void showSuccess(ModifyData submitData, String ref_code) {
        DBUtil.updateAddPeople(eventId, map, gsonUser, ref_code, Constants.ADD_SYNC);
        Delete.table(Attends.class, OperatorGroup.clause().and(Attends_Table.eventId.is(eventId)).and(Attends_Table.refCodes.is(ref_code)).and(Attends_Table.gsonUser.is(gsonUser)).and(Attends_Table.attendId.is(0)).and(Attends_Table.addMap.is(map)));
        ++addIndex;
        //    Log.d("addIndex-->", addIndex + "");
        if (addIndex < addAttend.size()) {//添加
            syncAdd(addIndex);
        } else if (auditAttend.size() != 0) {//审核
            auditIndex = 0;
            syncAudit(auditIndex);
        } else if (modifyAttend.size() != 0) {//修改
            modifyIndex = 0;
            syncModify(modifyIndex);
        } else if (collect.size() != 0) {//采集点
            // Log.e("add success","collect");
            collectIndex = 0;
            syncCollect(collectIndex);
        } else if (checkinAttendee.size() != 0) {//签到
            checkinIndex = 0;
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service");
            stopSelf();
        }
    }

    @Override
    public void showFailed(ModifyData submitData) {
        ++addIndex;
        //Log.d("addIndex-->", submitData.getRespObject()+"fds");
        if (addIndex < addAttend.size()) {//添加
            syncAdd(addIndex);
        } else if (auditAttend.size() != 0) {//审核
            auditIndex = 0;
            syncAudit(auditIndex);
        } else if (modifyAttend.size() != 0) {//修改
            modifyIndex = 0;
            syncModify(modifyIndex);
            //    Log.d("addIndex-->", addIndex + "");
        } else if (collect.size() != 0) {//采集点
            // Log.e("Fd","Fd");
            //  Log.e("add failed","collect");
            collectIndex = 0;
            syncCollect(collectIndex);
        } else if (checkinAttendee.size() != 0) {//签到
            checkinIndex = 0;
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service");
            stopSelf();
        }
    }

    /**
     * 审核
     */
    @Override
    public void showAuditSuccess() {
        DBUtil.updateAudit(audit, eventId, attendId, auditTime, Constants.AUDIT_SYNC);
        ++auditIndex;
        if (auditIndex < auditAttend.size()) {
            syncAudit(auditIndex);
        } else if (modifyAttend.size() != 0) {//修改
            modifyIndex = 0;
            syncModify(modifyIndex);
        } else if (collect.size() != 0) {
            // Log.e("audit success","collect");
            collectIndex = 0;
            syncCollect(collectIndex);
        } else if (checkinAttendee.size() != 0) {
            checkinIndex = 0;
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service");
            stopSelf();
        }
    }

    @Override
    public void showAuditFailed() {
        ++auditIndex;
        if (auditIndex < auditAttend.size()) {
            syncAudit(auditIndex);
        } else if (modifyAttend.size() != 0) {//修改
            modifyIndex = 0;
            syncModify(modifyIndex);
        } else if (collect.size() != 0) {
            //  Log.e("audit failed","collect");
            collectIndex = 0;
            syncCollect(collectIndex);
        } else if (checkinAttendee.size() != 0) {
            checkinIndex = 0;
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service");
            stopSelf();
        }
    }

    /**
     * 修改
     */

    @Override
    public String attendId() {
        return attendId + "";
    }

    @Override
    public String infoMap() {
        return gsonUser;
    }

    @Override
    public void showModifySuccess(ModifyData modifyData) {
        DBUtil.updateModify(eventId, attendId, gsonUser, modifyInfo, isContains, imgPath, ref_code, Constants.MODIFY_SYNC);
        ++modifyIndex;
        if (modifyIndex < modifyAttend.size()) {
            syncModify(modifyIndex);
        } else if (collect.size() != 0) {//采集点
            //  Log.e("modify success","collect");

            collectIndex = 0;
            syncCollect(collectIndex);
        } else if (checkinAttendee.size() != 0) {
            checkinIndex = 0;
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service");
            stopSelf();
        }
    }

    @Override
    public void showModifyFailed(ModifyData modifyData) {
        ++modifyIndex;
        if (modifyIndex < modifyAttend.size()) {
            syncModify(modifyIndex);
        } else if (collect.size() != 0) {//采集点
            //  Log.e("modify failed","collect");
            collectIndex = 0;
            syncCollect(collectIndex);
        } else if (checkinAttendee.size() != 0) {
            checkinIndex = 0;
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service");
            stopSelf();
        }
    }


    /**
     * ref_code签到
     *
     * @return
     */

    @Override
    public int eventId() {
        return eventId;
    }


    @Override
    public String attendeeId() {
        return attendId + "";
    }

    @Override
    public String upDateTime() {
        return auditTime;
    }


    @Override
    public String checkInStatus() {
        return checkStatus + "";
    }

    @Override
    public String checkInupdateTime() {
        return checkInTime;
    }

    @Override
    public void showCheckInSuccess(CheckIn checkIn) {
        DBUtil.updateRef_code(checkStatus, eventId, ref_code, checkInTime, Constants.SYNC);
        ++checkinIndex;
        if (checkinIndex < checkinAttendee.size()) {
            //      Log.e("checkinIndex--->", checkinIndex + "");
            //    Log.e("ref--->", ref_code);
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service");
            stopSelf();
        }
    }

    @Override
    public String checkEventId() {
        return eventId + "";
    }

    @Override
    public String checkAttendId() {
        return attendId + "";
    }

    @Override
    public String checkStatus() {
        return checkStatus + "";
    }

    @Override
    public String checkUpdateTime() {
        return collectTime;
    }

    @Override
    public void showCheckInSuccessInfo(CheckIn checkIn) {
        DBUtil.updateAttendId(checkStatus, eventId, attendId, ref_code, checkInTime, Constants.SYNC);
        ++checkinIndex;
        if (checkinIndex < checkinAttendee.size()) {
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service");
            stopSelf();
        }
    }

    @Override
    public void showCheckInFailed(String errInfo) {
        ++checkinIndex;
        if (checkinIndex < checkinAttendee.size()) {
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service");
            stopSelf();
        }
    }

    @Override
    public Context mContext() {
        return this;
    }

    /**
     * 采集人员
     *
     * @return
     */

    @Override
    public String collectionId() {
        return collectionId + "";
    }

    @Override
    public String barcode() {
        return barcode;
    }

    @Override
    public String checkInTime() {
        return collectTime;
    }

    @Override
    public void showPostSuccess(ExportData response) {
        //   Log.e("", "collect success status is--->" + response.getRetStatus());
        DBUtil.collect(eventId, collectionId, barcode, Constants.COLLECT_SYNV);
        ++collectIndex;
        if (collectIndex < collect.size()) {
            syncCollect(collectIndex);
        } else if (checkinAttendee.size() != 0) {//签到
            checkinIndex = 0;
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service!");
            stopSelf();
        }
    }

    @Override
    public void showPostFailed() {
        //Log.e("Fd",barcode);
        //    Log.e("", "collect status is failed");
        SQLite.update(CollectChild.class).set(CollectChild_Table.isLegal.is(1))
                .where(CollectChild_Table.collectionId.is(collectionId))
                .and(CollectChild_Table.attendeeBarcode.is(barcode)).execute();
        ++collectIndex;
        if (collectIndex < collect.size()) {
            syncCollect(collectIndex);
        } else if (checkinAttendee.size() != 0) {//签到
            checkinIndex = 0;
            syncCheckin(checkinIndex);
        } else {
            isRunService = true;
            Log.e(TAG, "stop the service!");
            stopSelf();
        }
    }

    @Override
    public File upLoadFile() {
        return file;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public void uploadSuccess(String response) {
        Gson gson = new Gson();
        imgPath = response;
        if (isAddOrModify) {
            Map<String, Map<String, String>> addMap = gson.fromJson(map, new TypeToken<Map<String, Map<String, String>>>() {
            }.getType());
            Set<String> keySet = addMap.keySet();
            for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
                String key = it.next();
                Map<String, String> map = addMap.get(key);
                Set<Map.Entry<String, String>> entrySet = map.entrySet();
                for (Iterator<Map.Entry<String, String>> it2 = entrySet.iterator(); it2.hasNext(); ) {
                    Map.Entry<String, String> me = it2.next();
                    if (me.getValue().contains("luban_disk_cache")) {
                        map.put(me.getKey(), response);
                    }
                }
            }

            Map<String, String> gsonMap = gson.fromJson(gsonUser, new TypeToken<Map<String, String>>() {
            }.getType());
            for (String key : gsonMap.keySet()) {
                if (gsonMap.get(key).contains("luban_disk_cache")) {
                    gsonMap.put(key, response);
                }
            }
            //  Log.e("key-->",addMap.toString());
            //      Log.e("gson-->",gsonMap.toString());
            map = gson.toJson(addMap);
            gsonUser = gson.toJson(gsonMap);
            submitOrderPresenter = new SubmitOrderPresenter(this);
            submitOrderPresenter.submitOrder(ref_code);

        } else {
            Map<String, String> modifyMap = gson.fromJson(modifyInfo, new TypeToken<Map<String, String>>() {
            }.getType());
            for (String key : modifyMap.keySet()) {
                if (modifyMap.get(key).contains("luban_disk_cache")) {
                    imgFiledId = Integer.parseInt(key);
                }
            }
            modifyMap.put(imgFiledId + "", response);
            modifyInfo = gson.toJson(modifyMap);

            Map<String, String> gsonMap = gson.fromJson(gsonUser, new TypeToken<Map<String, String>>() {
            }.getType());
            for (String key : gsonMap.keySet()) {
                if (gsonMap.get(key).contains("luban_disk_cache")) {
                    imgFiledId = Integer.parseInt(key);
                }
            }
            gsonMap.put(imgFiledId + "", response);
            gsonUser = gson.toJson(gsonMap);
            modifyPresenter = new ModifyUserInfoPresenter(this);
            modifyPresenter.modifyInfo();
        }

        // Log.e("modify-->",modifyInfo);

    }

    @Override
    public void uploadFailed(String response) {
        if (isAddOrModify) {
            ++addIndex;
            if (addIndex < addAttend.size()) {//添加
                syncAdd(addIndex);
            } else if (auditAttend.size() != 0) {//审核
                auditIndex = 0;
                syncAudit(auditIndex);
            } else if (modifyAttend.size() != 0) {//修改
                modifyIndex = 0;
                syncModify(modifyIndex);
                //    Log.d("addIndex-->", addIndex + "");
            } else if (collect.size() != 0) {//采集点
                collectIndex = 0;
                syncCollect(collectIndex);
            } else if (checkinAttendee.size() != 0) {//签到
                checkinIndex = 0;
                syncCheckin(checkinIndex);
            } else {
                isRunService = true;
                Log.e(TAG, "stop the service");
                stopSelf();
            }
        } else {
            ++modifyIndex;
            if (modifyIndex < modifyAttend.size()) {
                syncModify(modifyIndex);
            } else if (collect.size() != 0) {//采集点
                collectIndex = 0;
                syncCollect(collectIndex);
            } else if (checkinAttendee.size() != 0) {
                checkinIndex = 0;
                syncCheckin(checkinIndex);
            } else {
                isRunService = true;
                Log.e(TAG, "stop the service");
                stopSelf();
            }
        }
    }
}
