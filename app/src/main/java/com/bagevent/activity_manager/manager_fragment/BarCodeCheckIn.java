package com.bagevent.activity_manager.manager_fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.detail.SingleSelectCheckin;
import com.bagevent.activity_manager.manager_fragment.adapter.BarCodeShowInfoAdapter;
import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.AddPeopleCheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.CheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetAttendPeoplePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetAttendeeFilePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AddPeopleCheckInView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CheckInView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendPeopleView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendeeJsonFileView;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.EventList;
import com.bagevent.db.EventList_Table;
import com.bagevent.home.data.EventDetailData;
import com.bagevent.home.home_interface.presenter.EventDetailPresenter;
import com.bagevent.home.home_interface.view.GetEventDetailView;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.util.dbutil.DBUtil;
import com.bagevent.util.dbutil.SyncAttendeeUtil;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by zwj on 2016/7/11.
 */
public class BarCodeCheckIn extends BaseActivity implements OnScannerCompletionListener, CheckInView,
        AddPeopleCheckInView, View.OnClickListener, TextWatcher, GetAttendPeopleView, GetAttendeeJsonFileView {

    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    // private QRCodeView mQRCodeView;
    private ScannerView mScannerView;
    private TextView checkin_isAllowCamera;

    private AutoLinearLayout ll_barcode_checkin_back;//返回
    private AutoLinearLayout ll_barcode_search;//根据用户输入的数据,从数据库中查找
    private AutoLinearLayout ll_node;
    //   private AutoLinearLayout ll_btn_input;//点击显示隐藏的控件
    //   private TextView tv_btn_input;
    private Button btn_barcode_checkin;//签到
    private EditText et_barcode;
    private TextView tv_company_title;
    private TextView tv_job_title;
    private TextView tv_barcode_name;
    private TextView tv_nodes;

    private Attends attends;
    private int eventId = -1;
    private int checkStatus = 1; //初始化未签到
    private int attendId = -1;
    private String ref_code = "";
    private String key = "";
//    private String hasEdit="";


    private RecyclerView rvShowInfo;
    private PopupWindow mPopUpWindow;
    private View mPopUpView;
    private TextView tv_barcode_checkIn;
    private TextView tv_company_name;
    private TextView tv_job;
    private ImageView iv_barcode_clear;
    private InputMethodManager inputMethodManager;//软键盘
    private int heightDifference = -1;

    private CheckInPresenter checkInPresenter;
    private AddPeopleCheckInPresenter addPeopleCheckInPresenter;
    private static final String checkinStr_attendId = "barcode";

    private GetAttendPeoplePresenter getAttendPeoplePresenter;
    private GetAttendeeFilePresenter getattendeeFile;
    private int pageNum = -1;
    /**
     * 表单信息
     */
    private String mFormType = "";
    private FormType mType = null;
    private String company = "";
    private String job = "";
    private String name = "";
    private String nodes = "";

    private long scanTime = 0;//扫描同一个人的间隔时间
    private String tempScanResult = "";//用于判断是否连续扫描了同一个人
    private String checkinTime = "";
    private static final int REQUECT_CODE_CAMERA = 2;
    private boolean isSigin;
    private boolean hasEdit;
    private String attendCode;
    private String userId;
    private ArrayList<String> showInfo = new ArrayList<>();
    private ArrayList<String> barcodeInfo = new ArrayList<>();

    private ArrayList<String> selectShowInfo = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    private int type;
    private int barcodeLogin;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.activity_barcode_checkin);
//        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
//        eventId = getIntent().getIntExtra("eventId", -1);
//        barcodeLogin=getIntent().getIntExtra("isBarcodeLogin",-1);
//
//        isSigin = (eventId == -1);
//        hasEdit = (eventId == 0);
//        userId = SharedPreferencesUtil.get(this, "userId", "");
//        initView();
//        initPop();
//        setListener();
//        type = getIntent().getIntExtra(KeyUtil.KEY_GO_SIGN, -1);
//    }

    public ArrayList<String> getShowInfo() {
        sharedPreferences = getSharedPreferences("SignList", MODE_PRIVATE);
        int infoNum = sharedPreferences.getInt(String.valueOf(eventId), 0);
        for (int i = 0; i < infoNum; i++) {
            String selectText = sharedPreferences.getString(String.valueOf(eventId) + i + "name", null);
            if (selectText != null) {
                showInfo.add(selectText);
            }
        }
        return showInfo;
    }

    @PermissionGrant(REQUECT_CODE_CAMERA)
    public void requestCameraSuccess() {
        checkin_isAllowCamera.setVisibility(View.GONE);
    }

    @PermissionDenied(REQUECT_CODE_CAMERA)
    public void requestCameraFailed() {
        checkin_isAllowCamera.setVisibility(View.VISIBLE);
    }

    private void initPop() {
        mPopUpView = getLayoutInflater().inflate(R.layout.activity_barcode_pop, null);
        mPopUpWindow = new PopupWindow(mPopUpView, LinearLayout.LayoutParams.MATCH_PARENT, AutoLinearLayout.LayoutParams.WRAP_CONTENT, true);
        tv_barcode_checkIn =  mPopUpView.findViewById(R.id.tv_barcode_checkin);
        tv_company_name =  mPopUpView.findViewById(R.id.tv_company_name);
        tv_job = mPopUpView.findViewById(R.id.tv_job);
        tv_company_title =  mPopUpView.findViewById(R.id.tv_company_title);
        tv_job_title =  mPopUpView.findViewById(R.id.tv_job_title);
        tv_barcode_name =  mPopUpView.findViewById(R.id.tv_barcode_name);
        ll_node =  mPopUpView.findViewById(R.id.ll_node);
        tv_nodes =  mPopUpView.findViewById(R.id.tv_nodes);

        rvShowInfo = mPopUpView.findViewById(R.id.rv_show_info);

        mPopUpWindow.setTouchable(true);
        mPopUpWindow.setOutsideTouchable(true);
        mPopUpWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.a99323232)));
        //mPopUpWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopUpWindow.getContentView().setFocusableInTouchMode(true);
        mPopUpWindow.getContentView().setFocusable(true);
        mPopUpWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopUpWindow != null && mPopUpWindow.isShowing()) {
                        mPopUpWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setListener() {
        et_barcode.setOnClickListener(this);
        ll_barcode_checkin_back.setOnClickListener(this);
        btn_barcode_checkin.setOnClickListener(this);
        et_barcode.addTextChangedListener(this);
        iv_barcode_clear.setOnClickListener(this);
        mScannerView.setOnScannerCompletionListener(this);
    }

    private void initView() {
        mScannerView = (ScannerView) findViewById(R.id.barcode_checkin);//二维码扫描控件
        checkin_isAllowCamera = (TextView) findViewById(R.id.checkin_isAllowCamera);
        ll_barcode_checkin_back = (AutoLinearLayout) findViewById(R.id.ll_barcode_checkin_back);
        ll_barcode_search = (AutoLinearLayout) findViewById(R.id.ll_barcode_search);
        btn_barcode_checkin = (Button) findViewById(R.id.btn_barcode_checkin);
        et_barcode = (EditText) findViewById(R.id.et_barcode);
        iv_barcode_clear = (ImageView) findViewById(R.id.iv_barcode_clear);
        mScannerView.setLaserFrameBoundColor(0xfffe6900);
        mScannerView.setLaserLineHeight(1);
        mScannerView.setLaserColor(0xfffe6900);
        if (eventId == -1) {
            ll_barcode_search.setVisibility(View.GONE);
        }
        MPermissions.requestPermissions(this, REQUECT_CODE_CAMERA, Manifest.permission.CAMERA);
    }

    /**
     * 签到成功通知fragment更新签到状态
     */
    private void updateCheckinStatus(int eventId, String attendId) {
        EventBus.getDefault().postSticky(new MsgEvent(eventId, attendId, checkinStr_attendId));
    }


    /**
     * 获取表单中的公司和职位的filedId
     */
    private void getFiled(int eventId, String gsonUser) throws JSONException {
        mFormType = (String) NetUtil.readObject(this, Constants.FORM_FILE_NAME + eventId + "");//从文件中获得表单类型
        mType = new Gson().fromJson(mFormType, FormType.class);//解析表单数据
        JSONObject obj = new JSONObject(gsonUser);
        int count = mType.getRespObject().size();
        for (int j = 0; j < showInfo.size(); j++) {
            for (int i = 0; i < count; i++) {
                FormType.RespObjectBean type = mType.getRespObject().get(i);
                if (showInfo.get(j).contains(type.getShowName())) {
                    barcodeInfo.add(obj.getString(type.getFormFieldId() + ""));
                }
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals(Common.SIGN_FAILED)) {
            tv_company_title.setVisibility(View.GONE);
            tv_job_title.setVisibility(View.GONE);
            ll_node.setVisibility(View.GONE);
            tv_barcode_name.setVisibility(View.GONE);
            tv_barcode_checkIn.setText(R.string.error_barcode);
            rvShowInfo.setVisibility(View.GONE);
            tv_barcode_checkIn.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
            if (!BarCodeCheckIn.this.isFinishing()) {
                mPopUpWindow.showAtLocation(this.findViewById(R.id.barcode_title), Gravity.BOTTOM, 0, 0);
                vibrate();
            }
        } else if (event.mMsg.equals("newEventList")) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 根据扫描结果从数据库查询
     *
     * @param result
     */
    private void matchWithDB(String result) {
        showInfo.clear();
        barcodeInfo.clear();
        if (result.contains("$")) {
            String[] s = result.split("\\$");
            result = s[0];
        }

        if (!NetUtil.isConnected(BarCodeCheckIn.this)) {
            TosUtil.show(getString(R.string.check_network2));
            return;
        }

        if(barcodeLogin!=1){
            EventList event = new Select().from(EventList.class).where(EventList_Table.userId.is(Integer.parseInt(userId))).and(EventList_Table.eventId.is(eventId)).querySingle();
            if (event == null) {
                tv_company_title.setVisibility(View.GONE);
                tv_job_title.setVisibility(View.GONE);
                tv_barcode_name.setVisibility(View.GONE);
                ll_node.setVisibility(View.GONE);
                tv_barcode_checkIn.setText(R.string.error_barcode);
                tv_barcode_checkIn.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
                if (!BarCodeCheckIn.this.isFinishing()) {
                    mPopUpWindow.showAtLocation(this.findViewById(R.id.barcode_title), Gravity.BOTTOM, 0, 0);
                    vibrate();
                }
                return;
            }
        }

        if (result.length() > 4) {
            if (type == 1) {
                Attends attend = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.barcodes.is(result)).or(Attends_Table.checkinCodes.is(result)).or(Attends_Table.refCodes.is(result)).querySingle();
                if (attend == null) {
                    syncAttendsData();
                    return;
                }
            }
//            Attends attend = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.barcodes.is(result)).or(Attends_Table.checkinCodes.is(result)).or(Attends_Table.refCodes.is(result)).querySingle();
//            if (attend == null) {
//                //同步参会者信息
//                syncAttendsData();
//            } else {
            //进行线上签到
            attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.barcodes.is(result)).or(Attends_Table.checkinCodes.is(result)).or(Attends_Table.refCodes.is(result)).querySingle();
            attendCode = result;
            checkInPresenter = new CheckInPresenter(this);
            if (result.length() == 6) {
                //签到码签到
                if (attends == null) {
                    tv_company_title.setVisibility(View.GONE);
                    tv_job_title.setVisibility(View.GONE);
                    tv_barcode_name.setVisibility(View.GONE);
                    ll_node.setVisibility(View.GONE);
                    tv_barcode_checkIn.setText(R.string.people_not_exist);
                    rvShowInfo.setVisibility(View.GONE);
                    tv_barcode_checkIn.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
                    if (!BarCodeCheckIn.this.isFinishing()) {
                        mPopUpWindow.showAtLocation(this.findViewById(R.id.barcode_title), Gravity.BOTTOM, 0, 0);
                        vibrate();
                    }
                } else {
                    attendCode = String.valueOf(attends.attendId);
                    checkInPresenter.attendCheckIn();
                }
            } else {
                //二维码签到
                if (attends != null) {
                    name = attends.names;
                    nodes = attends.notes;
                    if (TextUtils.isEmpty(nodes)) {
                        ll_node.setVisibility(View.GONE);
                    }

                }
                checkInPresenter.checkIn();
            }
//            }
        } else if (result.length() == 4) {
            List<Attends> data = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.cellphones.like("%" + key)).queryList();
            if (data.size() > 0) {
                if (data.size() == 1) {
//                    Attends attends = data.get(0);
                    attends = data.get(0);
                    attendId = attends.attendId;
                    name = attends.names;
                    nodes = attends.notes;
                    attendCode = String.valueOf(attendId);
                    if(attends.buyingGroupId!=0){
                        if(attends.bgState==1){
                            checkInPresenter = new CheckInPresenter(this);
                            checkInPresenter.attendCheckIn();
                        }else{
                            TosUtil.show("该参会人员拼团还未成功！");
                        }
                    }else{
                        checkInPresenter = new CheckInPresenter(this);
                        checkInPresenter.attendCheckIn();
                    }
                } else {
                    Intent intent = new Intent(BarCodeCheckIn.this, SingleSelectCheckin.class);
                    intent.putExtra("key", result);
                    intent.putExtra("eventId", eventId);
                    startActivityForResult(intent, 66);
                    btn_barcode_checkin.setVisibility(View.GONE);
                    et_barcode.setCursorVisible(false);
                    et_barcode.setText("");
                }
            } else {
                tv_company_title.setVisibility(View.GONE);
                tv_job_title.setVisibility(View.GONE);
                tv_barcode_name.setVisibility(View.GONE);
                ll_node.setVisibility(View.GONE);
                tv_barcode_checkIn.setText(R.string.people_not_exist);
                rvShowInfo.setVisibility(View.GONE);
                tv_barcode_checkIn.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
                if (!BarCodeCheckIn.this.isFinishing()) {
                    mPopUpWindow.showAtLocation(this.findViewById(R.id.barcode_title), Gravity.BOTTOM, 0, 0);
                    vibrate();
                }
            }
        } else {
            Toast toast = Toast.makeText(this, R.string.empty_barcode, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 进行参会者信息的同步
     */
    private void syncAttendsData() {
        if (TextUtils.isEmpty(currentTime())) {
            getattendeeFile = new GetAttendeeFilePresenter(this);
            getattendeeFile.attendeeJsonFile();
        } else {
            getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
            getAttendPeoplePresenter.getRefreshAttendPeople();
        }
    }

    /**
     * 获取活动的id
     *
     * @param barCodes
     * @return
     */
    private int getEventId(String barCodes) {
        char[] charCode = barCodes.toCharArray();
        String eventId = "";
        for (int i = 4; i < charCode.length; i++) {
            if (i % 2 != 0) {
                eventId += charCode[i];
            }
        }
        if (eventId.length() >= 7) {
            try {
                return Integer.parseInt(eventId.substring(0, 7).trim());
            } catch (NumberFormatException e) {
                CrashReport.postCatchedException(new Throwable("获取扫描码长度不正确1" + barCodes + "length:" + eventId.length()));
                return -1;
            }
        } else {
            CrashReport.postCatchedException(new Throwable("获取扫描码长度不正确2" + barCodes + "length:" + eventId.length()));
            return -1;
        }

//        return Integer.parseInt(eventId.substring(0, 7).trim());
    }



    @Override
    protected void onResume() {
        mScannerView.onResume();
        super.onResume();
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_barcode_checkin);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
        eventId = getIntent().getIntExtra("eventId", -1);
        barcodeLogin=getIntent().getIntExtra("isBarcodeLogin",-1);

        isSigin = (eventId == -1);
        hasEdit = (eventId == 0);
        userId = SharedPreferencesUtil.get(this, "userId", "");
        initView();
        initPop();
        setListener();
        type = getIntent().getIntExtra(KeyUtil.KEY_GO_SIGN, -1);
    }

    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().post(new MsgEvent("refreshAttend"));
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    private String checkinTime() {
        Long time = System.currentTimeMillis();
        //  String currentTime = time.toString();
        return time.toString();
    }

    private String currentTime() {
        return SharedPreferencesUtil.get(this, "currentTime" + eventId, "");
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String getEventId() {
        LogUtil.e("getEventId"+eventId);
        return eventId + "";
    }

    @Override
    public void attendeeJsonFile(String jsonFile) {
        SyncAttendeeUtil util = new SyncAttendeeUtil(this, jsonFile, eventId, 0);
        util.syncAttendeeFile();
    }

    @Override
    public void attendeeJsonFileFailed(String errInfo) {

    }


    @Override
    public String getPageNum() {
        return -1 + "";
    }

    @Override
    public String getUpdateTime() {
        return currentTime();
    }

    @Override
    public void showSuccessInfo(final String people) {
        SyncAttendeeUtil util = new SyncAttendeeUtil(this, people, eventId, 0);
        util.syncAttendees();
    }

    @Override
    public void showFailInfo(String errInfo) {

    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public String checkInStatus() {
        return "1";
    }

    @Override
    public String checkInupdateTime() {
        return checkinTime();
    }

    @Override
    public void showCheckInSuccess(CheckIn checkIn) {//根据barcode签到成功的返回结果
        if (checkIn.getRetStatus() == 1) {
            tv_barcode_checkIn.setText(R.string.checkIn_success);
            tv_barcode_name.setText(name);
            if (!TextUtils.isEmpty(nodes)) {
                ll_node.setVisibility(View.VISIBLE);
                tv_nodes.setText(nodes);
            } else {
                ll_node.setVisibility(View.GONE);
            }
            tv_barcode_name.setVisibility(View.VISIBLE);
            tv_barcode_checkIn.setTextColor(ContextCompat.getColor(this, R.color.a59c709));
            tv_company_title.setVisibility(View.GONE);
            tv_job_title.setVisibility(View.GONE);
//            tv_barcode_name.setVisibility(View.GONE);
            if (!BarCodeCheckIn.this.isFinishing()) {
                mPopUpWindow.showAtLocation(this.findViewById(R.id.barcode_title), Gravity.BOTTOM, 0, 0);
                vibrate();
            }
        } else {
            tv_company_title.setVisibility(View.GONE);
            tv_job_title.setVisibility(View.GONE);
            tv_barcode_name.setText(name);
            tv_barcode_name.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(nodes)) {
                ll_node.setVisibility(View.VISIBLE);
                tv_nodes.setText(nodes);
            } else {
                ll_node.setVisibility(View.GONE);
            }
//            tv_barcode_name.setVisibility(View.GONE);
            tv_barcode_checkIn.setText(R.string.have_checkin);
            tv_barcode_checkIn.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
            if (!BarCodeCheckIn.this.isFinishing()) {
                mPopUpWindow.showAtLocation(this.findViewById(R.id.barcode_title), Gravity.BOTTOM, 0, 0);
                vibrate();
            }
        }
        if (attends != null) {
            name = attends.names;
            nodes = attends.notes;
            attendId = attends.attendId;
            checkStatus = attends.checkins;
            ref_code = attends.refCodes;
            checkinTime = checkinTime();
            getShowInfo();
            try {
                if (showInfo.size() > 0) {
                    getFiled(eventId, attends.gsonUser);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String checkEventId() {
        return eventId + "";
    }

    @Override
    public String checkAttendId() {
        return attendCode;
    }

    @Override
    public String checkStatus() {
//        return checkStatus + "";
        return "1";
    }

    @Override
    public String checkUpdateTime() {
        return checkinTime();
    }

    @Override
    public void showCheckInSuccessInfo(CheckIn checkIn) {
        if (attends != null) {
            name = attends.names;
            nodes = attends.notes;
            attendId = attends.attendId;
            checkStatus = attends.checkins;
            ref_code = attends.refCodes;
            checkinTime = checkinTime();
            getShowInfo();
            try {
                if (showInfo.size() > 0) {
                    getFiled(eventId, attends.gsonUser);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (checkIn.getRetStatus() == 1) {
            et_barcode.setText("");
            iv_barcode_clear.setVisibility(View.GONE);
            btn_barcode_checkin.setVisibility(View.GONE);
            tv_company_title.setVisibility(View.GONE);
            tv_job_title.setVisibility(View.GONE);
            tv_barcode_name.setVisibility(View.VISIBLE);
            tv_barcode_name.setText(name);
            if (!TextUtils.isEmpty(nodes)) {
                ll_node.setVisibility(View.VISIBLE);
                tv_nodes.setText(nodes);
            } else {
                ll_node.setVisibility(View.GONE);
            }
            showRecycler();
            tv_barcode_checkIn.setText(R.string.checkIn_success);
            tv_barcode_checkIn.setTextColor(ContextCompat.getColor(this, R.color.a59c709));
            if (!BarCodeCheckIn.this.isFinishing()) {
                mPopUpWindow.showAtLocation(BarCodeCheckIn.this.findViewById(R.id.barcode_title), Gravity.BOTTOM, 0, 0);
                vibrate();
            }
            DBUtil.updateAttendId(checkStatus, eventId, attendId, ref_code, checkinTime, Constants.SYNC);
            updateCheckinStatus(eventId, attendId + "");
        } else if (checkIn.getRetStatus() == 2) {
            et_barcode.setText("");
            iv_barcode_clear.setVisibility(View.GONE);
            btn_barcode_checkin.setVisibility(View.GONE);
            et_barcode.setCursorVisible(false);
            inputMethodManager.hideSoftInputFromWindow(et_barcode.getWindowToken(), 0);
            tv_barcode_checkIn.setText(R.string.have_checkin);
            showRecycler();
            if (!TextUtils.isEmpty(nodes)) {
                ll_node.setVisibility(View.VISIBLE);
                tv_nodes.setText(nodes);
            } else {
                ll_node.setVisibility(View.GONE);
            }
            tv_barcode_checkIn.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
            tv_barcode_name.setVisibility(View.VISIBLE);
            tv_barcode_name.setText(name);
            if (!BarCodeCheckIn.this.isFinishing()) {
                vibrate();
                mPopUpWindow.showAtLocation(BarCodeCheckIn.this.findViewById(R.id.barcode_title), Gravity.BOTTOM, 0, 0);
            }

        } else if (checkIn.getRetStatus() == -1 || checkIn.getRetStatus() == 5) {
            tv_company_title.setVisibility(View.GONE);
            tv_job_title.setVisibility(View.GONE);
            tv_barcode_name.setVisibility(View.GONE);
            ll_node.setVisibility(View.GONE);
            tv_barcode_checkIn.setText(R.string.error_barcode);
            rvShowInfo.setVisibility(View.GONE);
            tv_barcode_checkIn.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
            if (!BarCodeCheckIn.this.isFinishing()) {
                vibrate();
                mPopUpWindow.showAtLocation(BarCodeCheckIn.this.findViewById(R.id.barcode_title), Gravity.BOTTOM, 0, 0);
            }

        }
    }

    private void showRecycler() {
        if (showInfo.size() > 0) {
            rvShowInfo.setVisibility(View.VISIBLE);
            BarCodeShowInfoAdapter adapter = new BarCodeShowInfoAdapter(showInfo, barcodeInfo);
            rvShowInfo.setAdapter(adapter);
            rvShowInfo.setLayoutManager(new LinearLayoutManager(BarCodeCheckIn.this, LinearLayoutManager.VERTICAL, false));
            rvShowInfo.setHasFixedSize(true);
        } else {
            rvShowInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCheckInFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_barcode:
                et_barcode.setFocusable(true);
                et_barcode.requestFocus();
                et_barcode.setCursorVisible(true);
                inputMethodManager.showSoftInput(et_barcode, 0);
                btn_barcode_checkin.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_barcode_checkin:
                LogUtil.e("111"+eventId);
                if (!TextUtils.isEmpty(key)) {
                    hasEdit = true;
                    inputMethodManager.hideSoftInputFromWindow(et_barcode.getWindowToken(), 0);
                    matchWithDB(key);
                } else {
                    Toast toast = Toast.makeText(this, R.string.empty_barcode, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                break;
            case R.id.iv_barcode_clear:
                et_barcode.setText("");
                break;
            case R.id.ll_barcode_checkin_back:
                AppManager.getAppManager().finishActivity();
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        key = s.toString();
        if (!TextUtils.isEmpty(key)) {
            iv_barcode_clear.setVisibility(View.VISIBLE);
        } else {
            iv_barcode_clear.setVisibility(View.GONE);
        }
    }

    /**
     * 监听软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v != null) {
                if (isShouldHideInput(v, ev)) {
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);

            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();

            int[] rightTop = {bottom, right};
            btn_barcode_checkin.getLocationInWindow(rightTop);
            int left1 = rightTop[0];
            int top1 = rightTop[1];
            int btnbottom = top1 + btn_barcode_checkin.getHeight();
            int btnright = left1 + btn_barcode_checkin.getWidth();

            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom || event.getRawX() > left1 && event.getRawX() < btnright
                    && event.getRawY() > top1 && event.getRawY() < btnbottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                btn_barcode_checkin.setVisibility(View.GONE);
                et_barcode.setText("");
                iv_barcode_clear.setVisibility(View.GONE);
                return true;
            }
        }
        return false;
    }

    private void restartPreviewAfterDelay(long delayMS) {
        mScannerView.restartPreviewAfterDelay(delayMS);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 66 && resultCode == -66 && data != null) {
            if (!NetUtil.isConnected(this)) {
                TosUtil.show(getString(R.string.check_network2));
            } else {
                attendId = data.getIntExtra("attendId", -1);
                attendCode = String.valueOf(attendId);
                checkInPresenter = new CheckInPresenter(this);
                checkInPresenter.attendCheckIn();
            }
        }
    }

    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        showInfo.clear();
        barcodeInfo.clear();
        LogUtil.d("扫到" + rawResult.toString());
        hasEdit = false;
        restartPreviewAfterDelay(0L);
        tempScanResult = rawResult.toString();
//        scanTime = System.currentTimeMillis();
        if (tempScanResult.equals(rawResult.toString())) {
            if (System.currentTimeMillis() - scanTime > 5000) {
                scanTime = System.currentTimeMillis();
                if (isSigin) {
                    eventId = getEventId(rawResult.toString());
                }
                matchWithDB(rawResult.toString());
//                vibrate();
            }
        } else {
            tempScanResult = rawResult.toString();
            scanTime = System.currentTimeMillis();
            if (isSigin) {
                eventId = getEventId(rawResult.toString());
            }
            matchWithDB(rawResult.toString());
//            vibrate();
        }
    }

}
