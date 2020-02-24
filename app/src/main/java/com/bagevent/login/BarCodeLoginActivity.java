package com.bagevent.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.AcManager;
import com.bagevent.activity_manager.manager_fragment.data.CollectionInfoData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.CollectionPointInfoPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CollectionPointInfoView;
import com.bagevent.common.Common;
import com.bagevent.db.CollectList;
//import com.bagevent.db.CollectList_Table;
import com.bagevent.activity_manager.detail.CollectionBarcode;
import com.bagevent.db.CollectList_Table;
import com.bagevent.login.loginview.LoginViewInterface;
import com.bagevent.login.model.UserInfo;
import com.bagevent.login.presenter.LoginPresenter;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

/**
 * Created by zwj on 2016/7/19.
 */
public class BarCodeLoginActivity extends BaseActivity implements LoginViewInterface, CollectionPointInfoView, View.OnClickListener, OnScannerCompletionListener {
    private static final String TAG = "BarcodeLoginActivity";
    private ScannerView scannerLogin;
    private AutoLinearLayout ll_login_back;
    private LoginPresenter presenter;
    private TextView isAllowCamera;
    private String autoToken = "";
    private int eventId = -1;
    private String collectionId = "";
    private String userId = "";
    private String qrcode = "";//采集点登录码
    private long scanTime = 0L;//扫描间隔时间
    private String tempScanResult = "";//扫描间隔时间
    private static final int REQUECT_CODE_CAMERA = 2;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
    @Override
    protected void onResume() {
        scannerLogin.onResume();
        super.onResume();
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
            AppManager.getAppManager().addActivity(this);
            setContentView(R.layout.activity_barcode_login);
            initView();
            MPermissions.requestPermissions(this, REQUECT_CODE_CAMERA, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            setListener();

    }

    @Override
    protected void onPause() {
        scannerLogin.onPause();
        super.onPause();
    }

    @PermissionGrant(REQUECT_CODE_CAMERA)
    public void requestCameraSuccess() {
        isAllowCamera.setVisibility(View.GONE);
    }

    @PermissionDenied(REQUECT_CODE_CAMERA)
    public void requestCameraFailed() {
        isAllowCamera.setVisibility(View.VISIBLE);
    }


    private void setListener() {
        ll_login_back.setOnClickListener(this);
        scannerLogin.setOnScannerCompletionListener(this);
    }

    private void initView() {
        scannerLogin = (ScannerView) findViewById(R.id.scanner_login);
        ll_login_back = (AutoLinearLayout) findViewById(R.id.ll_login_back);
        isAllowCamera = (TextView) findViewById(R.id.isAllowCamera);
        scannerLogin.setLaserFrameBoundColor(0xfffe6900);
        scannerLogin.setLaserLineHeight(1);
        scannerLogin.setLaserColor(0xfffe6900);
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    /**
     * 解析采集点登录码
     *
     * @param collectInfo
     */
    private void resolveCollectQrcode(String collectInfo) {
        vibrate();
        qrcode = collectInfo;
        String[] s1 = collectInfo.split(":");
        eventId = Integer.parseInt(s1[1]);
        SharedPreferencesUtil.put(this, "eventId", String.valueOf(eventId));
        collectionId = s1[2];
        userId = s1[3];
        autoToken = s1[4];
        if (NetUtil.isConnected(this)) {
            CollectionPointInfoPresenter presenter = new CollectionPointInfoPresenter(this);
            presenter.getCollectionInfo();
        } else {
            TosUtil.showCenter(getString(R.string.check_network1));
        }

    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String getEventId() {
        return eventId + "";
    }

    @Override
    public String collectionId() {
        return collectionId;
    }

    @Override
    public void showCollectionInfoSuccess(CollectionInfoData response) {
        // CollectList list = new Select().from(CollectList.class).where(CollectList_Table.eventId.is(33615)).and(CollectList_Table.collectPointId.is(60)).querySingle();
        SQLite.delete().from(CollectList.class).where(CollectList_Table.eventId.is(eventId)).and(CollectList_Table.collectPointId.is(Integer.parseInt(collectionId))).execute();
        CollectionInfoData.RespObjectBean bean = response.getRespObject();
        SQLite.insert(CollectList.class)
                .columns(CollectList_Table.eventId, CollectList_Table.collectPointId, CollectList_Table.collectionName, CollectList_Table.userEmail,
                        CollectList_Table.collectionType, CollectList_Table.isAllTicket, CollectList_Table.availableDateType, CollectList_Table.startTime,
                        CollectList_Table.endTime, CollectList_Table.isBegin, CollectList_Table.isRepeat, CollectList_Table.export,
                        CollectList_Table.checkinCount, CollectList_Table.ticketStr, CollectList_Table.ticketIdStr, CollectList_Table.showNum, CollectList_Table.isAllProduct, CollectList_Table.limitType,
                        CollectList_Table.productStr, CollectList_Table.productIdStr)
                .values(eventId, Integer.parseInt(collectionId), bean.getCollectionName(), bean.getUserEmail(),
                        bean.getCollectionType(), bean.getIsAllTicket(), bean.getAvailableDateType(), bean.getStartTime(),
                        bean.getEndTime(), bean.getIsBegin(), bean.getIsRepeat(), bean.getExport(),
                        bean.getCheckinCount(), bean.getTicketStr(), bean.getTicketIdStr(), bean.getShowNum(), bean.getIsAllProduct(), bean.getLimitType(), bean.getProductStr(), bean.getProductIdStr())
                .execute();
        SharedPreferencesUtil.put(this, KeyUtil.KEY_AUTO_COLLECT, qrcode);
        SharedPreferencesUtil.put(this, KeyUtil.KEY_AUTO_LOGIN_TOKEN, autoToken);
        CollectList tempList = new Select().from(CollectList.class).where(CollectList_Table.eventId.is(eventId)).and(CollectList_Table.collectPointId.is(Integer.parseInt(collectionId))).querySingle();
        if (tempList != null) {
            Intent intent = new Intent(this, CollectionBarcode.class);
            intent.putExtra(KeyUtil.KEY_EVENT_ID, eventId);
            intent.putExtra("export",bean.getExport());
            intent.putExtra(KeyUtil.KEY_COLLECTION_ID, Integer.parseInt(collectionId));
            intent.putExtra(KeyUtil.KEY_COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_BARCODE);
            startActivity(intent);
            AppManager.getAppManager().finishActivity();
        } else {
            Log.e(TAG, "Collect is Null");
        }
    }

    @Override
    public void showCollectionInfoFailed(String errInfo) {
        TosUtil.show(errInfo);
    }

    /**
     * 扫描登录
     *
     * @param result
     */
    private void autoLogin(String result) {
        vibrate();
        String[] s = result.split("eventId:");
        autoToken = s[0];
        eventId = Integer.parseInt(s[1]);
        SharedPreferencesUtil.put(this, "eventId", String.valueOf(eventId));
        if (NetUtil.isConnected(this)) {
            presenter = new LoginPresenter(this);
            presenter.autoLogin(this,autoToken);
        } else {
            Intent intent = new Intent(this, AcManager.class);
            Bundle bundle = new Bundle();
            bundle.putInt(KeyUtil.KEY_EVENT_ID, eventId);
            bundle.putString(Common.BARCODE_LOGIN, Common.BARCODE_LOGIN);
            intent.putExtras(bundle);
            startActivity(intent);
            AppManager.getAppManager().finishActivity();
        }
    }


    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearPassword(){

    }

    @Override
    public void toMainActivity(UserInfo userInfo) {
        //清除sharePreference中的内容
        SharedPreferencesUtil.clear(this);
        //使用Sharepreference保存用户信息
        SharedPreferencesUtil.put(this, "eventId", String.valueOf(eventId));
        SharedPreferencesUtil.put(this,"loginType",Common.BARCODE_LOGIN);
        SharedPreferencesUtil.put(this, KeyUtil.KEY_USER_ID, userInfo.getReturnObj().getUserId() + "");
        SharedPreferencesUtil.put(this, KeyUtil.KEY_EMAIL, userInfo.getReturnObj().getEmail());
        SharedPreferencesUtil.put(this, KeyUtil.KEY_CELL_PHONE, userInfo.getReturnObj().getCellphone());
        SharedPreferencesUtil.put(this, KeyUtil.KEY_USER_NAME, userInfo.getReturnObj().getUserName());
        SharedPreferencesUtil.put(this, KeyUtil.KEY_AVATAR, userInfo.getReturnObj().getAvatar());
        SharedPreferencesUtil.put(this, KeyUtil.KEY_SOURCE, userInfo.getReturnObj().getSource() + "");
        SharedPreferencesUtil.put(this, KeyUtil.KEY_TOKEN, userInfo.getReturnObj().getToken());
        SharedPreferencesUtil.put(this, KeyUtil.KEY_STATE, userInfo.getReturnObj().getState() + "");
        SharedPreferencesUtil.put(this, KeyUtil.KEY_AUTO_LOGIN_TOKEN, userInfo.getReturnObj().getAutoLoginToken());
        SharedPreferencesUtil.put(this, KeyUtil.KEY_AUTO_LOGIN_EXPIRETIME, userInfo.getReturnObj().getAutoLoginExpireTime()+"");

        Intent intent = new Intent(this, AcManager.class);
        Bundle bundle = new Bundle();
        bundle.putInt(KeyUtil.KEY_EVENT_ID, eventId);
        bundle.putString(Common.BARCODE_LOGIN, Common.BARCODE_LOGIN);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void showFailedErr(String errInfo) {
      TosUtil.show(errInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_login_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }


    private void scanTime(String result) {
        if (tempScanResult.equals(result)) {
            if (System.currentTimeMillis() - scanTime > 5000) {
                if (result.contains("eventId:")) {
                    autoLogin(result);
                } else if (result.contains("collection:")) {
                    resolveCollectQrcode(result);//解析采集点登录二维码
                } else {
                    Toast toast = Toast.makeText(this, R.string.check_barcode, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                scanTime = System.currentTimeMillis();
            }
        } else {
            if (result.contains("eventId:")) {
                autoLogin(result);
            } else if (result.contains("collection:")) {
                resolveCollectQrcode(result);//解析采集点登录二维码
            } else {
                Toast toast = Toast.makeText(this, R.string.check_barcode, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            scanTime = System.currentTimeMillis();
        }
        tempScanResult = result;
    }


    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        scannerLogin.restartPreviewAfterDelay(0L);
        scanTime(rawResult.toString());
    }
}
