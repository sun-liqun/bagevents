package com.bagevent.activity_manager.detail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.CollectionInfoData;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.CollectionInOutPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.CollectionPointInfoPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetAttendPeoplePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetAttendeeFilePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetCollectionInOutNumPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetFormTypePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CollectionInOutView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CollectionPointInfoView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendPeopleView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendeeJsonFileView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetCollectionInOutView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetFormTypeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetTicketInfoView;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.BackupCollection;
//import com.bagevent.db.BackupCollection_Table;
import com.bagevent.db.BackupCollection_Table;
import com.bagevent.db.CollectChild;
//import com.bagevent.db.CollectChild_Table;
import com.bagevent.db.CollectChild_Table;
import com.bagevent.db.CollectList;
//import com.bagevent.db.CollectList_Table;
import com.bagevent.db.CollectList_Table;
import com.bagevent.home.data.CollectDetailData;
import com.bagevent.home.data.EventDetailData;
import com.bagevent.home.data.ExportData;
import com.bagevent.home.home_interface.presenter.EventDetailPresenter;
import com.bagevent.home.home_interface.presenter.ExportCollectPresenter;
import com.bagevent.home.home_interface.presenter.GetCollectPresenter;
import com.bagevent.home.home_interface.presenter.PostCollectPresenter;
import com.bagevent.home.home_interface.view.ExportCollectView;
import com.bagevent.home.home_interface.view.GetCollectChildView;
import com.bagevent.home.home_interface.view.GetEventDetailView;
import com.bagevent.home.home_interface.view.PostCollectionView;
import com.bagevent.login.LoginActivity;
import com.bagevent.new_home.new_activity.AddTagsForAttendee;
import com.bagevent.synchro_data.NetWorkBroadcast;
import com.bagevent.synchro_data.StartSyncDataService;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.ExcelUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.OnMultipleClickListener;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.util.dbutil.DBUtil;
import com.bagevent.util.dbutil.SyncAttendeeUtil;
import com.bagevent.util.dbutil.SyncTicketUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.wevey.selector.dialog.DialogOnItemClickListener;
import com.wevey.selector.dialog.MDEditDialog;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.wevey.selector.dialog.NormalSelectionDialog;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.xiaopan.switchbutton.SwitchButton;
import name.gudong.loading.LoadingView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zwj on 2016/7/12.
 * 扫描前需要同步参会人员和采集人员
 */
public class CollectionBarcode extends Activity implements View.OnClickListener, GetAttendeeJsonFileView, ExportCollectView, GetEventDetailView,
        CollectionPointInfoView, GetCollectionInOutView, CollectionInOutView, GetFormTypeView, GetAttendPeopleView, GetCollectChildView, GetTicketInfoView, PostCollectionView,
        CompoundButton.OnCheckedChangeListener, OnScannerCompletionListener {
    private static final String TAG = "CollectionBarcode";
    private ScannerView mScannerView;
    private Result mLastResult;
    private TextView isAllowCamera;
    private int isRepeat = -1;
    private int collectionId = -1;
    private String barcode = "";
    private AutoLinearLayout ll_collect_barcode_back;
    private TextView tv_barcode_title;
    private AutoLinearLayout rl_collect_back_visible;
    private AutoLinearLayout ll_collect_num;
    private AutoLinearLayout ll_collect_updown;
    private TextView tv_in_out;
    private TextView tv_collect_num_mark;
    private AutoLinearLayout ll_collect_export;

    private PopupWindow mPopUpWindow;
    private View mPopUpView;
    private TextView tv_pop_title;
    private TextView tv_pop_name;
    private ImageView iv_pop_result;
    private TextView tv_pop_company;
    private ImageView iv_add_tag;
    private TextView show_tag;
    private AutoLinearLayout ll_from_exhibitor;
    private AutoLinearLayout ll_from_collection;
    private AutoLinearLayout ll_left_exhibitor;

    private TextView tv_pop_name1;
    private ImageView iv_pop_result1;
    private TextView tv_title_remind;
    private AutoRelativeLayout rl_switch_button;

    private PopupWindow mSwitchPopupWindow;
    private View mSwitchPopUpView;
    private SwitchButton switch_collect;
    private TextView collect_num;
    private LoadingView progerss_sync;
    private PostCollectPresenter presenter;
    private ExportCollectPresenter exportCollectPresenter;
    private String name;
    private String company = "";
    private String pinyinName;
    private String str = "";
    private int eventId = -1;
    private String startTime = "";
    private String endTime = "";
    private String collectionName = "";
    private String ticketStr = "";
    private String ticketIdStr = "";
    private String productIdStr = "";
    private short isAllProduct = 0;
    private int showNum = -1;
    private int limitType = 0;//默认为0:门票采集，1：商品采集
    private String ticketId = "";//胸卡二维码中的ticketId;
    private int collectionType = -1;//2为进出控制
    private String state = "1";//采集点进出口标识，0---出口，1----进口
    private boolean isExport = false;//导出采集人员
    private String email = "";//导出邮箱

    /**
     * 同步表单信息
     */
    private String mFormType = "";
    private FormType mType = null;

    /**
     * 同步参会人员
     */
    private int pageNum = 1;//当前页码
    private int pageCounts = -1;//总页数
    private int pageCount = -1;//总页数
    private String currentTime = "";//数据更新时间
    private int peopleCount = -1;
    private GetAttendPeoplePresenter getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);//获得参会人员
    private GetAttendeeFilePresenter getattendeeFile;
    private EventDetailPresenter eventDetailPresenter;

    /**
     * 同步采集人员信息
     */
    private GetCollectPresenter getCollectPresenter;
    private CollectionInOutPresenter collectionInOutPresenter;
    private GetCollectionInOutNumPresenter collectionInOutNumPresenter;
    /**
     * 使用单线程池,方便监听是否向数据库填写数据完成
     */
    private ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();
    private Timer timer;
    private long scanTime = 0;
    private String tempScanResult = "";

    private String loginType = "";

    private String isStr = "";//标识为门票限制

    private boolean isSetAttendeeToDB = false;

    /**
     * 获取门票信息
     */
    private GetTicketPresenter getTicketPresenter;

    /**
     * 发送广播
     */
    private NetWorkBroadcast broadcast;
    private NormalAlertDialog exitDialog;
    private MDEditDialog passwordDialog;
    private MDEditDialog exportDialog;
    private NormalSelectionDialog selectionDialog;
    private static final int REQUECT_CODE_CAMERA = 2;
    private static final int REQUECT_CODE_FILE = 3;
    private boolean isChecked;
    private int export;
    private int syncData = 0;

    boolean isExhibitor = false;
    boolean fromExhibitor = false;
    private int exhibitorId;
    private int exhibitorAttendeeId;
    private ArrayList<Integer> attendeeTags = new ArrayList<>();
    private ArrayList<String> attendeeTagsName = new ArrayList<>();
    private String tagNames = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_collect_barcode);
        Intent intent = getIntent();
        loginType = intent.getStringExtra(Common.COLLECT_LOGIN_TYPE);
        collectionId = intent.getIntExtra("collectionId", 0);
        eventId = intent.getIntExtra("eventId", 0);
        export = intent.getIntExtra("export", 0);
        fromExhibitor = intent.getBooleanExtra("isExhibitor", false);
        initView();
        MPermissions.requestPermissions(this, REQUECT_CODE_FILE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        setListener();
        if (fromExhibitor) {
            checkIsExhibitor();
            exhibitorId = intent.getIntExtra("exhibitorId", -1);
        }
        initPop();

//        initSwitchPop();
        passwordDialog();
        exportDialog();
        exitBarCodeCollect();
        sendNetWorkBroadcast();//启动广播
        if (loginType.equals(Common.COLLECT_LOGIN_TYPE_BARCODE) || loginType.equals(Common.COLLECT_LOGIN_TYPE_AUTOLOGIN)) {//扫描登录和自动登录隐藏返回键
            setBackVisible();
        }
        if (!loginType.equals(Common.COLLECT_LOGIN_TYPE_MANAGER)) {//非采集点管理界面进入，需同步数据
            getPeople();
        }
        countDownTimer.start();
        getCollectionInfo();
    }

    private void checkIsExhibitor() {
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }
        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.CHECK_IS_EXHIBITOR)
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        String result = response.body().string();
                        return result;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        parserError(action);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                isExhibitor = jsonObject.getJSONObject("respObject").getBoolean("isExhibitor");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
//                            parserError();
                        }

                    }
                });
    }

    @PermissionGrant(REQUECT_CODE_CAMERA)
    public void requestCameraSuccess() {
        isAllowCamera.setVisibility(View.GONE);
    }

    @PermissionDenied(REQUECT_CODE_CAMERA)
    public void requestCameraFailed() {
        isAllowCamera.setVisibility(View.VISIBLE);
    }

    @PermissionGrant(REQUECT_CODE_FILE)
    public void requestFileSuccess() {
    }

    @PermissionDenied(REQUECT_CODE_FILE)
    public void requestFileFailed() {
        TosUtil.show(getString(R.string.no_permission_read_write));
    }

    /**
     * 导出采集人员
     */
    private void exportDialog() {
        exportDialog = new MDEditDialog.Builder(this)
                .setTitleVisible(true)
                .setTitleText(getString(R.string.input_email))
                .setTitleTextSize(20)
                .setTitleTextColor(R.color.black_light)
                .setContentTextSize(14)
                .setHintText(getString(R.string.email))
                .setInputTpye(InputType.TYPE_CLASS_TEXT)
                .setMaxLines(1)
                .setContentText(email)
                .setContentTextColor(R.color.black)
                .setButtonTextSize(14)
                .setLeftButtonTextColor(R.color.grey)
                .setLeftButtonText(getString(R.string.cancel))
                .setRightButtonTextColor(R.color.fe6900)
                .setRightButtonText(getString(R.string.export))
                .setLineColor(R.color.black)
                .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                    @Override
                    public void clickLeftButton(View view, String text) {
                        exportDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view, String text) {
                        if (!TextUtils.isEmpty(text)) {
                            if (CompareRex.checkEmail(text)) {
                                email = text;
                                if (NetUtil.isConnected(CollectionBarcode.this)) {
                                    exportCollectPresenter = new ExportCollectPresenter(CollectionBarcode.this);
                                    exportCollectPresenter.export();
                                } else {
                                    Toast toast = Toast.makeText(CollectionBarcode.this, R.string.check_your_net, Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                                //inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                exportDialog.dismiss();
                            } else {
                                Toast toast = Toast.makeText(CollectionBarcode.this, R.string.emai_err, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        } else {
                            Toast toast = Toast.makeText(CollectionBarcode.this, R.string.please_input_eamil, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                })
                .setMinHeight(0.1f)
                .setWidth(0.8f)
                .build();
    }

    /**
     * 选择出入口
     */
    private void selectInOrOutDialog() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(getString(R.string.enter));
        list.add(getString(R.string.out));
        selectionDialog = new NormalSelectionDialog.Builder(this)
                .setlTitleVisible(true)   //设置是否显示标题
                .setTitleHeight(50)   //设置标题高度
                .setTitleText(getString(R.string.select_enter_out))  //设置标题提示文本
                .setTitleTextSize(14) //设置标题字体大小 sp
                .setTitleTextColor(R.color.black) //设置标题文本颜色
                .setItemHeight(45)  //设置item的高度
                .setItemWidth(0.9f)  //屏幕宽度*0.9
                .setItemTextColor(R.color.colorPrimaryDark)  //设置item字体颜色
                .setItemTextSize(16)  //设置item字体大小
                .setCancleButtonText(getString(R.string.cancel))  //设置最底部“取消”按钮文本
                .setOnItemListener(new DialogOnItemClickListener() {  //监听item点击事件
                    @Override
                    public void onItemClick(Button button, int position) {
                        if (position == 0) {
                            tv_in_out.setText(getString(R.string.go));
                            state = "1";
                            selectionDialog.dismiss();
                            tempScanResult = "";
                        } else {
                            tv_in_out.setText(getString(R.string.out1));
                            state = "0";
                            selectionDialog.dismiss();
                            tempScanResult = "";
                        }
                    }
                })
                .setCanceledOnTouchOutside(false)  //设置是否可点击其他地方取消dialog
                .build();
        selectionDialog.setDataList(list);
    }

    /**
     * 输入密码退出采集点扫描
     */
    private void passwordDialog() {
        passwordDialog = new MDEditDialog.Builder(this)
                .setTitleVisible(true)
                .setTitleText(getString(R.string.exit_password))
                .setTitleTextSize(20)
                .setTitleTextColor(R.color.black_light)
                .setContentTextSize(14)
                .setHintText(getString(R.string.please_input))
                .setInputTpye(InputType.TYPE_CLASS_NUMBER)
                .setMaxLength(6)
                .setMaxLines(1)
                .setContentTextColor(R.color.black)
                .setButtonTextSize(14)
                .setLeftButtonTextColor(R.color.grey)
                .setLeftButtonText(getString(R.string.cancel))
                .setRightButtonTextColor(R.color.fe6900)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setLineColor(R.color.black)
                .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                    @Override
                    public void clickLeftButton(View view, String editText) {
                        passwordDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view, String editText) {
                        if ("666888".equals(editText)) {
                            SharedPreferencesUtil.clear(CollectionBarcode.this);
                            passwordDialog.dismiss();
                            Intent intent = new Intent(CollectionBarcode.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            AppManager.getAppManager().finishActivity();
                        }
                    }
                })
                .setMinHeight(0.1f)
                .setWidth(0.8f)
                .build();
    }


    /**
     * 退出采集点扫描
     */
    private void exitBarCodeCollect() {
        exitDialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black)
                .setContentText(getString(R.string.check_network1))
                .setContentTextColor(R.color.black)
                .setSingleMode(true)
                .setSingleButtonText(getString(R.string.close))
                .setSingleButtonTextColor(R.color.black)
                .setCanceledOnTouchOutside(false)
                .setSingleListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        exitDialog.dismiss();
                        AppManager.getAppManager().finishActivity();
                    }
                })
                .build();

    }

    private void setTvValueAnimator(int showNum) {
        if (showNum == 0) {
            ll_collect_num.setVisibility(View.GONE);
        } else {
            ll_collect_num.setVisibility(View.VISIBLE);
            if (collectionType != 2) {
                List<CollectChild> tempCount = new Select().from(CollectChild.class).where(CollectChild_Table.eventId.is(eventId)).and(CollectChild_Table.collectionId.is(collectionId)).queryList();
                if (tempCount.size() > 0) {
                    collect_num.setText(tempCount.size() + "");
                } else {
                    collect_num.setText("0");
                }
            }
        }
    }

    private void eventDetailInfo() {
        if (NetUtil.isConnected(this)) {
            eventDetailPresenter = new EventDetailPresenter(this);
            eventDetailPresenter.getEventDetail();
        }
    }

    private void getCollectionInfo() {
//        CollectList collectList = new Select().from(CollectList.class).where(CollectList_Table.eventId.is(Integer.valueOf(eventId))).and(CollectList_Table.collectPointId.is(collectionId)).querySingle();
//        if (collectList != null) {
//            isRepeat = collectList.isRepeat;
//            startTime = collectList.startTime;
//            endTime = collectList.endTime;
//            collectionName = collectList.collectionName;
//            ticketStr = collectList.ticketStr;
//            showNum = collectList.showNum;
//            ticketIdStr = collectList.ticketIdStr;
//            collectionType = collectList.collectionType;
//            email = collectList.userEmail;
//            productIdStr = collectList.productIdStr;
//            limitType = collectList.limitType;
//            isAllProduct = (short) collectList.isAllProduct;
//
//            setTvValueAnimator(showNum);
//            tv_barcode_title.setText(collectionName);
//            if (collectionType == 2) {
//                rl_switch_button.setVisibility(View.GONE);
//                if (NetUtil.isConnected(this)) {
//                    collectionInOutNumPresenter = new GetCollectionInOutNumPresenter(this);
//                    collectionInOutNumPresenter.getCollectionInOutNum();
//                } else {
//                    collect_num.setText("0");
//                }
//                selectInOrOutDialog();
//                tv_in_out.setVisibility(View.VISIBLE);
//                tv_collect_num_mark.setText(R.string.present_people);
//            } else {
//                tv_in_out.setVisibility(View.GONE);
//                tv_collect_num_mark.setText(R.string.collect_num);
//            }
//        } else {
//            if (NetUtil.isConnected(this)) {//如果没有采集点信息
//                CollectionPointInfoPresenter presenter = new CollectionPointInfoPresenter(this);
//                presenter.getCollectionInfo();
//            } else {
//                Toast.makeText(this, R.string.handling_exceptions, Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "Collect is NULL!");
//            }
//        }

        //根据collectpointId获取采集点信息,来找到才采集点是否有限制
        if (NetUtil.isConnected(this)) {//如果没有采集点信息
            CollectionPointInfoPresenter presenter = new CollectionPointInfoPresenter(this);
            presenter.getCollectionInfo();
        } else {
            CollectList collectList = new Select().from(CollectList.class).where(CollectList_Table.eventId.is(Integer.valueOf(eventId))).and(CollectList_Table.collectPointId.is(collectionId)).querySingle();
            if (collectList != null) {
                isRepeat = collectList.isRepeat;
                startTime = collectList.startTime;
                endTime = collectList.endTime;
                collectionName = collectList.collectionName;
                ticketStr = collectList.ticketStr;
                showNum = collectList.showNum;
                ticketIdStr = collectList.ticketIdStr;
                collectionType = collectList.collectionType;
                email = collectList.userEmail;
                productIdStr = collectList.productIdStr;
                limitType = collectList.limitType;
                isAllProduct = (short) collectList.isAllProduct;

                setTvValueAnimator(showNum);
                tv_barcode_title.setText(collectionName);
                if (collectionType == 2) {
                    rl_switch_button.setVisibility(View.GONE);
                    if (NetUtil.isConnected(this)) {
                        collectionInOutNumPresenter = new GetCollectionInOutNumPresenter(this);
                        collectionInOutNumPresenter.getCollectionInOutNum();
                    } else {
                        collect_num.setText("0");
                    }
                    selectInOrOutDialog();
                    tv_in_out.setVisibility(View.VISIBLE);
                    tv_collect_num_mark.setText(R.string.present_people);
                } else {
                    tv_in_out.setVisibility(View.GONE);
                    tv_collect_num_mark.setText(R.string.collect_num);
                }
            } else {
                Toast.makeText(this, R.string.handling_exceptions, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Collect is NULL!");
            }
        }
    }

    private void getPeople() {
        // eventDetailInfo();//获得参会总人数
        getAttendPeopleInfo();
        getCollectionPeople();
        getTicketInfo();
        getFormType();
//        if (timer == null) {
//            timer = new Timer();
//            timer.schedule(task, 1000 * 60, 1000 * 60);
//        }

    }

    private void sendNetWorkBroadcast() {
        if (broadcast != null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            broadcast = new NetWorkBroadcast();
            registerReceiver(broadcast, filter);
        }
    }

    /**
     * 使用定时器 每一分钟向服务器同步一次数据
     */
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            //  if (isSetAttendeeToDB) {
            getAttendPeopleInfo();
            getCollectionPeople();
            //  }
        }
    };

    CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
//            if (mSwitchPopupWindow != null && mSwitchPopupWindow.isShowing()) {
//                mSwitchPopupWindow.dismiss();
//            }

            if (rl_switch_button != null) {
                rl_switch_button.setVisibility(View.GONE);
            }
            if (mPopUpWindow != null && mPopUpWindow.isShowing()) {
                mPopUpWindow.dismiss();
            }
            if (ll_collect_barcode_back.getVisibility() == View.VISIBLE && (loginType.equals(Common.COLLECT_LOGIN_TYPE_BARCODE) || loginType.equals(Common.COLLECT_LOGIN_TYPE_AUTOLOGIN))) {
                ll_collect_barcode_back.setVisibility(View.INVISIBLE);//扫描登录和自动登录进采集点需隐藏返回键
            }
        }
    };

    /**
     * 表单信息
     */

    private void getFormType() {
        if (NetUtil.isConnected(this)) {
            GetFormTypePresenter presenter = new GetFormTypePresenter(this);
            presenter.getFormType();
        }
    }

    /**
     * 门票信息
     */
    private void getTicketInfo() {
        if (NetUtil.isConnected(this)) {
            getTicketPresenter = new GetTicketPresenter(this);
            getTicketPresenter.getTicket();
        }
    }

    /**
     * 采集点人员信息
     */
    private void getCollectionPeople() {
        if (NetUtil.isConnected(this)) {
            getCollectPresenter = new GetCollectPresenter(this);
            getCollectPresenter.getCollectChild();
        }
    }

    /**
     * 获得参会人员信息
     */
    private void getAttendPeopleInfo() {
        if (NetUtil.isConnected(CollectionBarcode.this)) {
            progerss_sync.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(currentTime())) {
                //首次请求,直接下载参会人员文件
                syncData = 1;
                getattendeeFile = new GetAttendeeFilePresenter(this);
                getattendeeFile.attendeeJsonFile();
            } else {
                if (pageNum > 1) {
                    getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
                    getAttendPeoplePresenter.getAttendPeoples();
                } else {
                    getAttendPeoplePresenter = new GetAttendPeoplePresenter(this);
                    getAttendPeoplePresenter.getRefreshAttendPeople();
                }
            }
        } else {
            progerss_sync.setVisibility(View.GONE);
        }
    }

    private String currentTime() {
        //根据eventId将currentTime从sp中取出
        currentTime = SharedPreferencesUtil.get(this, "currentTime" + eventId, "");
        return currentTime;
    }

    /**
     * 开启在线控制
     */
    private void initSwitchPop() {
        mSwitchPopUpView = getLayoutInflater().inflate(R.layout.activity_collect_barcode_switchbtn, null);
        mSwitchPopupWindow = new PopupWindow(mSwitchPopUpView, LinearLayout.LayoutParams.MATCH_PARENT, 150, true);
        switch_collect = (SwitchButton) mSwitchPopUpView.findViewById(R.id.switch_collect);
        switch_collect.setOnCheckedChangeListener(this);

        mSwitchPopupWindow.setTouchable(true);
        mSwitchPopupWindow.setOutsideTouchable(true);

        mSwitchPopupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.a99323232)));
        mSwitchPopupWindow.getContentView().setFocusableInTouchMode(true);
        mSwitchPopupWindow.getContentView().setFocusable(true);
        mSwitchPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mSwitchPopupWindow != null && mSwitchPopupWindow.isShowing()) {
                        mSwitchPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initPop() {
        mPopUpView = getLayoutInflater().inflate(R.layout.activity_collect_pop, null);
        mPopUpWindow = new PopupWindow(mPopUpView, LinearLayout.LayoutParams.MATCH_PARENT, 400, true);
        tv_pop_title = (TextView) mPopUpView.findViewById(R.id.tv_pop_title);
        tv_pop_name = (TextView) mPopUpView.findViewById(R.id.tv_pop_name);
        iv_pop_result = (ImageView) mPopUpView.findViewById(R.id.iv_pop_result);
        tv_pop_company = (TextView) mPopUpView.findViewById(R.id.tv_pop_company);
        iv_add_tag = mPopUpView.findViewById(R.id.iv_add_tag);
        ll_from_collection = mPopUpView.findViewById(R.id.ll_from_collection);
        ll_left_exhibitor = mPopUpView.findViewById(R.id.ll_left_exhibitor);
        ll_from_exhibitor = mPopUpView.findViewById(R.id.ll_from_exhibitor);
        show_tag = mPopUpView.findViewById(R.id.show_tag);

        tv_pop_name1 = mPopUpView.findViewById(R.id.tv_pop_name1);
        tv_title_remind = mPopUpView.findViewById(R.id.tv_title_remind);
        iv_pop_result1 = mPopUpView.findViewById(R.id.iv_pop_result1);

        mPopUpWindow.setTouchable(true);
        mPopUpWindow.setOutsideTouchable(true);

        mPopUpWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.a99323232)));
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

        iv_add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectionBarcode.this, AddTagsForAttendee.class);
                intent.putExtra("exhibitorId", exhibitorId);
                intent.putExtra("eventId", eventId);
                intent.putExtra("tagIds", attendeeTags);
                intent.putExtra("tagNames", attendeeTagsName);
                intent.putExtra("exhibitorAttendeeId", exhibitorAttendeeId);
                intent.putExtra("isFromExhibitor", true);
                startActivity(intent);
            }
        });
    }

    private void setListener() {
        ll_collect_barcode_back.setOnClickListener(this);
        ll_collect_updown.setOnClickListener(this);
        ll_collect_export.setOnClickListener(this);
        mScannerView.setOnScannerCompletionListener(this);
        rl_collect_back_visible.setOnClickListener(new OnMultipleClickListener(3, 1000) {//连续点击3次出现返回按钮
            @Override
            public void onMultipleClick(View v) {
                if (loginType.equals(Common.COLLECT_LOGIN_TYPE_BARCODE) || loginType.equals(Common.COLLECT_LOGIN_TYPE_AUTOLOGIN)) {
                    if (ll_collect_barcode_back.getVisibility() == View.INVISIBLE) {
                        ll_collect_barcode_back.setVisibility(View.VISIBLE);
                        countDownTimer.start();
                    }
                }
            }
        });
        tv_barcode_title.setOnClickListener(new OnMultipleClickListener(3, 1000) {
            @Override
            public void onMultipleClick(View v) {
                if (collectionType == 2) {
                    selectionDialog.show();
                }
            }
        });
        mScannerView.setOnClickListener(new OnMultipleClickListener(7, 1000) {
            @Override
            public void onMultipleClick(View v) {
                List<CollectChild> mlist = new Select().from(CollectChild.class).where(CollectChild_Table.eventId.is(eventId)).and(CollectChild_Table.collectionId.is(collectionId)).queryList();
                List<BackupCollection> mBack = new Select().from(BackupCollection.class).where(BackupCollection_Table.eventId.is(eventId)).and(BackupCollection_Table.collectionId.is(collectionId)).queryList();
                try {
                    ExcelUtil.writeExcel(CollectionBarcode.this, mlist, null, "本地-" + collectionName);
                    ExcelUtil.writeExcel(CollectionBarcode.this, null, mBack, "备份-" + collectionName);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage() + "");
                }
            }
        });
    }

    private void initView() {
        mScannerView = (ScannerView) findViewById(R.id.collect_barcode);//二维码扫描控件
        isAllowCamera = (TextView) findViewById(R.id.isAllowCamera);
        progerss_sync = (LoadingView) findViewById(R.id.progerss_sync);
        ll_collect_num = (AutoLinearLayout) findViewById(R.id.ll_collect_num);
        ll_collect_barcode_back = (AutoLinearLayout) findViewById(R.id.ll_collect_barcode_back);
        tv_barcode_title = (TextView) findViewById(R.id.tv_barcode_title);
        collect_num = (TextView) findViewById(R.id.tv_collect_num);
        rl_collect_back_visible = (AutoLinearLayout) findViewById(R.id.rl_collect_back_visible);
        ll_collect_updown = (AutoLinearLayout) findViewById(R.id.ll_collect_updown);
        tv_in_out = (TextView) findViewById(R.id.tv_in_out);
        tv_collect_num_mark = (TextView) findViewById(R.id.tv_collect_num_mark);
        ll_collect_export = (AutoLinearLayout) findViewById(R.id.ll_collect_export);
        rl_switch_button = findViewById(R.id.rl_switch_button);
        SwitchButton switch_collect = findViewById(R.id.switch_collect);
        if (export == 0) {
            ll_collect_export.setVisibility(View.GONE);
        }
        switch_collect.setOnCheckedChangeListener(this);
        mScannerView.setLaserFrameBoundColor(0xfffe6900);
        mScannerView.setLaserLineHeight(1);
        mScannerView.setLaserColor(0xfffe6900);
        MPermissions.requestPermissions(this, REQUECT_CODE_CAMERA, Manifest.permission.CAMERA);
    }

    //采集界面隐藏返回键
    private void setBackVisible() {
        if (ll_collect_barcode_back.getVisibility() == View.VISIBLE) {
            ll_collect_barcode_back.setVisibility(View.INVISIBLE);
        } else {
            ll_collect_barcode_back.setVisibility(View.VISIBLE);
        }
    }

    //显示采集结果
    private void collectResultVisible() {
        if (isExhibitor) {
            ll_left_exhibitor.setVisibility(View.VISIBLE);
            tv_pop_name.setVisibility(View.VISIBLE);
            iv_add_tag.setVisibility(View.VISIBLE);
            show_tag.setVisibility(View.VISIBLE);
            ll_from_exhibitor.setVisibility(View.VISIBLE);
            ll_from_collection.setVisibility(View.GONE);
            tv_pop_company.setVisibility(View.VISIBLE);
        } else {
            ll_left_exhibitor.setVisibility(View.GONE);
            iv_add_tag.setVisibility(View.GONE);
            show_tag.setVisibility(View.GONE);
            ll_from_collection.setVisibility(View.VISIBLE);
            ll_from_exhibitor.setVisibility(View.GONE);
            tv_pop_name.setVisibility(View.GONE);
            tv_pop_company.setVisibility(View.GONE);
        }

        //iv_pop_result.setVisibility(View.VISIBLE);
    }

    //隐藏采集结果
    private void collectResultGone() {
        ll_from_collection.setVisibility(View.VISIBLE);
        ll_left_exhibitor.setVisibility(View.GONE);
        ll_from_exhibitor.setVisibility(View.GONE);
//        tv_pop_name.setVisibility(View.GONE);
//        tv_pop_company.setVisibility(View.GONE);
//        iv_add_tag.setVisibility(View.GONE);
//        show_tag.setVisibility(View.GONE);
        tv_pop_name1.setVisibility(View.GONE);

        //   iv_pop_result.setVisibility(View.GONE);
    }

    /**
     * 进出控制
     */
    private void limitEnterOut(String text) {
        collectResultGone();
        //  Log.e("aaa","xxx");

//        if (isExhibitor){
//            tv_pop_title.setText(text);
//            tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
//            Glide.with(this).load(R.drawable.collect_wrong).error(R.drawable.collect_wrong).into(iv_pop_result);
//        }else {
        ll_left_exhibitor.setVisibility(View.GONE);
        ll_from_exhibitor.setVisibility(View.GONE);
        ll_from_collection.setVisibility(View.VISIBLE);
        tv_title_remind.setText(text);
        RequestOptions options = new RequestOptions()
                .error(R.drawable.collect_wrong);
        Glide.with(this).load(R.drawable.collect_wrong).apply(options).into(iv_pop_result1);
        tv_title_remind.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
//        }

        mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
        countDownTimer.start();
    }

    /**
     * 不能进入
     */
    private void showLimitEnter() {
        collectResultGone();
        //  Log.e("aaa","xxx");
//        if (isExhibitor){
//            tv_pop_title.setText(R.string.not_design_ticket);
//            tv_pop_name.setVisibility(View.GONE);
//            tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
//            Glide.with(this).load(R.drawable.collect_wrong).error(R.drawable.collect_wrong).into(iv_pop_result);
//        }else {
        ll_left_exhibitor.setVisibility(View.GONE);
        ll_from_exhibitor.setVisibility(View.GONE);
        ll_from_collection.setVisibility(View.VISIBLE);
        tv_title_remind.setText(R.string.not_design_ticket);
        RequestOptions options = new RequestOptions()
                .error(R.drawable.collect_wrong);
        Glide.with(this).load(R.drawable.collect_wrong).apply(options).into(iv_pop_result1);
        tv_title_remind.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
//        }
        mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
        countDownTimer.start();
    }

    /**
     * 允许进入
     *
     * @param name
     * @param company
     * @param attendeeTagsName
     */
    private void showAllownEneter(String name, String company, ArrayList<String> attendeeTagsName) {
        if (isExhibitor) {
            ll_from_exhibitor.setVisibility(View.VISIBLE);
            ll_left_exhibitor.setVisibility(View.VISIBLE);
            ll_from_collection.setVisibility(View.GONE);
            tv_pop_name.setVisibility(View.VISIBLE);
            tv_pop_title.setText(R.string.collect_welcome);
            tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            ll_from_exhibitor.setVisibility(View.GONE);
            ll_left_exhibitor.setVisibility(View.GONE);
            ll_from_collection.setVisibility(View.VISIBLE);
            tv_pop_name.setVisibility(View.GONE);
            tv_title_remind.setText(R.string.collect_welcome);
            tv_title_remind.setTextColor(ContextCompat.getColor(this, R.color.white));
        }


        tagNames = "";
        if (isChecked) {
            int collected_num = Integer.parseInt(collect_num.getText().toString()) + 1;
            collect_num.setText(String.valueOf(collected_num));
        }

        if (name != null) {
            tv_pop_name.setText(name);
            tv_pop_name1.setText(name);
        } else {
            tv_pop_name.setText("");
            tv_pop_name1.setText("");
        }

        if (attendeeTagsName.size() == 1) {
            tagNames = "#" + attendeeTagsName.get(0) + "#";
        } else {
            for (int i = 0; i < attendeeTagsName.size(); i++) {
                String tagName = attendeeTagsName.get(i);
                tagNames = tagNames + tagName;
                if (i != attendeeTagsName.size() - 1) {
                    tagNames += ",";
                }
            }
        }

        if (tagNames.contains(",")) {
            tagNames = "#" + tagNames.replaceAll(",", "# #") + "#";
        }
        RequestOptions options = new RequestOptions()
                .error(R.drawable.collect_success);
        show_tag.setText(tagNames);
        tv_pop_company.setText(company);
        if (isExhibitor) {
            Glide.with(this).load(R.drawable.collect_success).apply(options).into(iv_pop_result);
        } else {
            Glide.with(this).load(R.drawable.collect_success).apply(options).into(iv_pop_result1);
        }
        mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
        countDownTimer.start();
    }

    /**
     * 该参会者已采集
     *
     * @param name
     * @param company
     */
    private void showHaveCollect(String name, String company) {

        if (isExhibitor) {
            ll_from_exhibitor.setVisibility(View.VISIBLE);
            ll_left_exhibitor.setVisibility(View.VISIBLE);
            ll_from_collection.setVisibility(View.GONE);
            tv_pop_name.setVisibility(View.VISIBLE);
        } else {
            ll_from_exhibitor.setVisibility(View.GONE);
            ll_left_exhibitor.setVisibility(View.GONE);
            ll_from_collection.setVisibility(View.VISIBLE);
            tv_pop_name.setVisibility(View.GONE);
        }
        if (name != null) {
            tv_pop_name.setText(name);
            tv_pop_name1.setText(name);
        } else {
            tv_pop_name.setText("");
            tv_pop_name1.setText("");
        }
        if (showNum == 0 || showNum == 1) {
            tv_pop_title.setText(R.string.collect_already_coming);
            tv_title_remind.setText(R.string.collect_already_coming);
        } else {
            tv_pop_title.setText(R.string.have_collect);
            tv_title_remind.setText(R.string.have_collect);
        }
        tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
        tv_title_remind.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
        tv_pop_company.setText(company);
        RequestOptions options = new RequestOptions()
                .error(R.drawable.collect_wrong);
        if (isExhibitor) {
            Glide.with(this).load(R.drawable.collect_wrong).apply(options).into(iv_pop_result);
        } else {
            Glide.with(this).load(R.drawable.collect_wrong).apply(options).into(iv_pop_result1);
        }
        mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
        countDownTimer.start();
    }

    private void searchDB(String result) {
        //参会人员
        Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(OperatorGroup.clause().and(Attends_Table.barcodes.is(result)).or(Attends_Table.refCodes.is(result)))
                .and(Attends_Table.payStatuss.is(1))
                .and(OperatorGroup.clause().and(Attends_Table.audits.is(0))
                        .or(Attends_Table.audits.is(2))).querySingle();
        if (!TextUtils.isEmpty(ticketIdStr) || isAllProduct == 1) {
            //ticketIdStr判断限制类型
            if (!TextUtils.isEmpty(ticketId)) {
                //根据胸卡上的ticketId判断是否可以采集
                if (limitType == 0) {//门票采集
                    if (ticketIdStr.contains(ticketId)) {
                        collect(attends, result);
                    } else {
                        showLimitEnter();
                    }
                } else {//商品采集人员一定要同步完成，确认本地有此人方可进行采集;
                    if (attends != null) {
                        String[] productIdStrArray = productIdStr.split("\\|\\|");
                        String[] productIdArray = attends.productIds.split(" ");
                        boolean limit = false;
                        for (int i = 0; i < productIdStrArray.length; i++) {
                            String idStr = productIdStrArray[i];
                            for (int j = 0; j < productIdArray.length; j++) {
                                if (productIdArray[j].equals(idStr)) {
                                    limit = true;
                                }
                            }
                        }
                        if (limit) {
                            collect(attends, result);
                        } else {
                            showLimitEnter();
                        }
                    } else {
                        showLimitEnter();
                    }
                }
            } else if (attends != null) {
                //根据数据库中的参会人员信息判断是否可以采集
                if (limitType == 0) {
                    if (ticketIdStr.contains(attends.ticketIds + "")) {
                        collect(attends, result);
                    } else {
                        showLimitEnter();
                    }
                } else {
                    String[] productIdStrArray = productIdStr.split("\\|\\|");
                    String[] productIdArray = attends.productIds.split(" ");
                    boolean limit = false;
                    for (int i = 0; i < productIdStrArray.length; i++) {
                        String idStr = productIdStrArray[i];
                        for (int j = 0; j < productIdArray.length; j++) {
                            if (productIdArray[j].equals(idStr)) {
                                limit = true;
                            }
                        }
                    }
                    if (limit) {
                        collect(attends, result);
                    } else {
                        showLimitEnter();
                    }
                }
            } else {
                showLimitEnter();
            }
        } else {//无门票和商品限制
            if (limitType == 0) {
                collect(attends, result);
            } else {
                if (attends != null) {
                    if (!TextUtils.isEmpty(attends.productIds)) {
                        collect(attends, result);
                    } else {
                        showLimitEnter();
                    }
                } else {
                    showLimitEnter();
                }
            }
        }
    }

    private void collect(Attends attends, String result) {
        collectResultVisible();
        str = time();
        name = "";
        pinyinName = "";
        company = "";
        if (attends != null) {
            try {
                name = attends.names;
                pinyinName = attends.pinyinNames;
                company = getFiled(attends.gsonUser);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (isRepeat == 0) {//不允许重复
            CollectChild collect = new Select().from(CollectChild.class).where(CollectChild_Table.attendeeBarcode.is(result)).and(CollectChild_Table.collectionId.is(collectionId)).querySingle();
            if (collect == null) {
                setDataToBackupDB(str);
                setDataToDB(Constants.COLLECT_NOT_SYNV, str);
                if (NetUtil.isConnected(this)) {
                    presenter = new PostCollectPresenter(this);
                    presenter.postCollection();
                } else {
                    showAllownEneter(name, company, attendeeTagsName);
                }
            } else {
                //   Log.e("aaa",collect.attendeeBarcode);
                showHaveCollect(name, company);
            }
        } else if (isRepeat == 1) {//允许重复
            setDataToBackupDB(str);
            setDataToDB(Constants.COLLECT_NOT_SYNV, str);
            if (NetUtil.isConnected(this)) {
                presenter = new PostCollectPresenter(this);
                presenter.postCollection();
            } else {
                showAllownEneter(name, company, attendeeTagsName);
            }
//            showAllownEneter(name, company);
        }
    }

    private void resetStatusView() {
        mLastResult = null;
    }

    private void restartPreviewAfterDelay(long delayMS) {
        mScannerView.restartPreviewAfterDelay(delayMS);
        resetStatusView();
    }

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onResume() {
        mScannerView.onResume();
        resetStatusView();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }


    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().removeAllStickyEvents();
        if (mLastResult != null) {
            restartPreviewAfterDelay(0L);
        }
//        if (timer != null) {
//            timer.cancel();
//        }
        if (broadcast != null) {
            unregisterReceiver(broadcast);
        } else {
            EventBus.getDefault().postSticky(new MsgEvent("fromCollectionBarcode"));
        }
        countDownTimer.cancel();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals("success")) {
            progerss_sync.setVisibility(View.GONE);
            Log.e("CollectionBarcode", "参会人员同步成功");
        } else if (event.mMsg.contains(Common.ATTENDEE_PAGE)) {
            pageNum = event.pageNumber;
            getAttendPeopleInfo();
        } else if (event.mMsg.equals("SyncDataService")) {//service数据上传完成发出此消息
            if (isExport) {
                isExport = false;
                exportDialog.show();
            } else {
                Toast toast = Toast.makeText(this, R.string.sys_complete, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } else if (event.mMsg.equals("setTvValueAnimator")) {
            setTvValueAnimator(showNum);
        } else if (event.mMsg.equals(Common.SYNC_ATTEND_INFO_SUCCESS)) {
            syncData = 2;
        }
    }

    private boolean isNum(String num) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) num);
        return matcher.matches();
    }

    private void startCollect(String result) {

        if (!isChecked) {
            //没有开启在线控制
            searchDB(result);
        } else {
            //开启在线控制
            barcode = result;
            str = time();
            if (NetUtil.isConnected(this)) {
                presenter = new PostCollectPresenter(this);
                presenter.postCollection();
            } else {
                TosUtil.show(getString(R.string.check_your_net));
            }
        }
    }

    private void scanBarcode(String result) {
        vibrate();
        barcode = result;
        boolean num = isNum(barcode);
        RequestOptions options = new RequestOptions()
                .error(R.drawable.collect_wrong);
        if (((barcode.length() == 25 && barcode.substring(8, 9).contains("-"))) || (num && barcode.length() == 19) || (num && barcode.length() == 20) || barcode.equals("www.bagevent.com") || ticketId.length() > 0) {
            if (CompareRex.dateCompare(startTime, endTime).contains(R.string.start + "")) {
                startCollect(result);
            } else if (CompareRex.dateCompare(startTime, endTime).contains(R.string.not_start + "")) {
                collectResultGone();
                tv_pop_title.setText(R.string.not_start_collect);
                tv_title_remind.setText(R.string.not_start_collect);
                tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
                tv_title_remind.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
                Glide.with(this).load(R.drawable.collect_wrong).apply(options).into(iv_pop_result1);
                tv_pop_name.setText("");
                tv_pop_name1.setText("");
                mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
                countDownTimer.start();
            } else if (CompareRex.dateCompare(startTime, endTime).contains(R.string.stop + "")) {
                collectResultGone();
                tv_pop_title.setText(R.string.finish_collect);
                tv_title_remind.setText(R.string.finish_collect);
                Glide.with(this).load(R.drawable.collect_wrong).apply(options).into(iv_pop_result1);
                tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
                tv_title_remind.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
                tv_pop_name.setText("");
                tv_pop_name1.setText("");
                mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
                countDownTimer.start();
            }
        } else {
            collectResultGone();
            tv_pop_title.setText(R.string.error_barcode);
            tv_title_remind.setText(R.string.error_barcode);
            Glide.with(this).load(R.drawable.collect_wrong).apply(options).into(iv_pop_result1);
            tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
            tv_title_remind.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
            mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
            countDownTimer.start();
        }

//        if (CompareRex.dateCompare(startTime, endTime).contains(R.string.start + "")) {
//            CollectChild collect = new Select().from(CollectChild.class).where(CollectChild_Table.attendeeBarcode.is(result)).and(CollectChild_Table.collectionId.is(collectionId)).querySingle();
//            if (collect == null) {
//                if (((barcode.length() == 25 && barcode.substring(8, 9).contains("-"))) || (num && barcode.length() == 19) || (num && barcode.length() == 20) || barcode.equals("www.bagevent.com") || ticketId.length() > 0) {
//                    startCollect(result);
//                } else {
//                    collectResultGone();
//                    tv_pop_title.setText("该二维码不是正确的参会二维码");
//                    tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
//                    mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
//                    countDownTimer.start();
//                }
//            } else {
//                startCollect(result);
//            }
//        } else if (CompareRex.dateCompare(startTime, endTime).contains(R.string.not_start + "")) {
//            collectResultGone();
//            tv_pop_title.setText(R.string.not_start_collect);
//            tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
//            tv_pop_name.setText("");
//            mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
//            countDownTimer.start();
//        } else if (CompareRex.dateCompare(startTime, endTime).contains(R.string.stop + "")) {
//            collectResultGone();
//            tv_pop_title.setText(R.string.finish_collect);
//            tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
//            tv_pop_name.setText("");
//            mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
//            countDownTimer.start();
//        } else {
//            Toast toast = Toast.makeText(this, "该二维码不是正确的参会二维码", Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//        }
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String detailEventId() {
        return eventId + "";
    }

    @Override
    public int sType() {
        return 0;
    }

    @Override
    public String eventId() {
        return eventId + "";
    }

    @Override
    public String userEmail() {
        return email;
    }

    @Override
    public void showExportSuccess() {
        Toast toast = Toast.makeText(this, R.string.daochu_success, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showExportFailed() {

    }

    @Override
    public String collectionId() {
        return collectionId + "";
    }

    @Override
    public void showCollectionInfoSuccess(CollectionInfoData response) {
        CollectionInfoData.RespObjectBean bean = response.getRespObject();
        SQLite.insert(CollectList.class)
                .columns(CollectList_Table.eventId, CollectList_Table.collectPointId, CollectList_Table.collectionName, CollectList_Table.userEmail,
                        CollectList_Table.collectionType, CollectList_Table.isAllTicket, CollectList_Table.availableDateType, CollectList_Table.startTime,
                        CollectList_Table.endTime, CollectList_Table.isBegin, CollectList_Table.isRepeat, CollectList_Table.export,
                        CollectList_Table.checkinCount, CollectList_Table.ticketStr, CollectList_Table.ticketIdStr, CollectList_Table.showNum)
                .values(eventId, collectionId, bean.getCollectionName(), bean.getUserEmail(),
                        bean.getCollectionType(), bean.getIsAllTicket(), bean.getAvailableDateType(), bean.getStartTime(),
                        bean.getEndTime(), bean.getIsBegin(), bean.getIsRepeat(), bean.getExport(),
                        bean.getCheckinCount(), bean.getTicketStr(), bean.getTicketIdStr(), bean.getShowNum())
                .execute();
        CollectList collectList = new Select().from(CollectList.class).where(CollectList_Table.eventId.is(eventId)).and(CollectList_Table.collectPointId.is(collectionId)).querySingle();
        if (collectList != null) {
            isRepeat = collectList.isRepeat;
            startTime = response.getRespObject().getStartTime();
            endTime = response.getRespObject().getEndTime();
            collectionName = collectList.collectionName;
            ticketStr = collectList.ticketStr;
            showNum = collectList.showNum;
            ticketIdStr = collectList.ticketIdStr;
            collectionType = collectList.collectionType;
            setTvValueAnimator(showNum);
            tv_barcode_title.setText(collectionName);
            if (collectionType == 2) {
                if (NetUtil.isConnected(this)) {
                    collectionInOutNumPresenter = new GetCollectionInOutNumPresenter(this);
                    collectionInOutNumPresenter.getCollectionInOutNum();
                } else {
                    collect_num.setText("0");
                }
                selectInOrOutDialog();
                tv_in_out.setVisibility(View.VISIBLE);
                tv_collect_num_mark.setText(R.string.present_people);
            } else {
                tv_in_out.setVisibility(View.GONE);
                tv_collect_num_mark.setText(R.string.collect_num);
            }
        }
    }

    @Override
    public void showCollectionInfoFailed(String errInfo) {

    }

    @Override
    public void showCollectionInOutNumSuccess(String num) {
        collect_num.setText(num);
    }

    @Override
    public void showCollectionInOutNumFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGetSuccess(CollectDetailData data) {
        List<CollectChild> collect = new ArrayList<CollectChild>();//将数据存到list中，然后插入数据库
        Delete.table(CollectChild.class, OperatorGroup.clause().and(CollectChild_Table.eventId.is(eventId))
                .and(CollectChild_Table.collectionId.is(collectionId)).and(CollectChild_Table.collectIsSync.is(Constants.COLLECT_SYNV)));
        for (int i = 0; i < data.getRespObject().getCheckInList().size(); i++) {
            CollectChild child = new CollectChild();
            CollectDetailData.RespObjectBean.CheckInListBean bean = data.getRespObject().getCheckInList().get(i);
            child.eventId = eventId;
            child.collectionId = collectionId;
            child.attendeeBarcode = bean.getAttendeeBarcode();
            child.attendeeCheckInTime = bean.getAttendeeCheckInTime();
            child.collectIsSync = Constants.COLLECT_SYNV;
            collect.add(child);
        }
        if (collect.size() > 0) {
            setListToDB(collect);
        }
    }

    private void setListToDB(final List<CollectChild> collect) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                FlowManager.getDatabase(AppDatabase.class)
                        .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                new ProcessModelTransaction.ProcessModel<CollectChild>() {
                                    @Override
                                    public void processModel(CollectChild model, DatabaseWrapper wrapper) {
                                        model.save();
                                    }
                                }).addAll(collect).build())
                        .error(new Transaction.Error() {
                            @Override
                            public void onError(Transaction transaction, Throwable error) {
                                //Log.e("list", "Database transaction failed.", error);
                            }
                        })
                        .success(new Transaction.Success() {
                            @Override
                            public void onSuccess(Transaction transaction) {
                                EventBus.getDefault().post(new MsgEvent("setTvValueAnimator"));
                            }
                        }).build().execute();
            }
        };
        runnable.run();

    }

    @Override
    public void showGetFailed() {

    }

    @Override
    public String barcode() {
        return barcode;
    }

    @Override
    public String state() {
        return state;
    }

    @Override
    public void setCollectionSuccess(StringData data) {
        if (data.getRetStatus() == 200) {
            ll_left_exhibitor.setVisibility(View.GONE);
            ll_from_exhibitor.setVisibility(View.GONE);
            ll_from_collection.setVisibility(View.VISIBLE);
            collect_num.setText(data.getRespObject());
//            tv_pop_title.setText(R.string.collect_welcome);
            tv_title_remind.setText(R.string.collect_welcome);
            tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.white));
            tv_title_remind.setTextColor(ContextCompat.getColor(this, R.color.white));
//            tv_pop_name.setText(R.string.collect_welcome);
            tv_pop_name1.setText(name);
            Glide.with(this).load(R.drawable.collect_success).into(iv_pop_result1);
            mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
            countDownTimer.start();
        } else if (data.getRetStatus() == -1005 || data.getRetStatus() == -1003 || data.getRetStatus() == -1004) {
            showLimitEnter();
        } else if (data.getRetStatus() == 301) {
            limitEnterOut(getString(R.string.not_repeat_enter));
        } else if (data.getRetStatus() == 302) {
            limitEnterOut(getString(R.string.not_repeat_go));
        } else if (data.getRetStatus() == -1) {
            limitEnterOut(getString(R.string.people_not_exist));
        }

    }

    @Override
    public void setCollectionFailed(String errInfo) {
        limitEnterOut(errInfo);
    }

    @Override
    public String checkInTime() {
        return str;
    }


    @Override
    public void showPostSuccess(ExportData response) {
        name = null;
        attendeeTags.clear();
        attendeeTagsName.clear();
        if (response.getRetStatus() == 200) {
            Gson gson = new Gson();
            String s = gson.toJson(response);
            try {
                JSONObject jsonObject = new JSONObject(s);
                exhibitorAttendeeId = jsonObject.getJSONObject("respObject").getJSONObject("attendeeDTO").getInt("exhibitorAttendeeId");
                JSONArray jsonArray = jsonObject.getJSONObject("respObject").getJSONArray("tagList");
                if (jsonArray.length() == 1) {
                    attendeeTagsName.add(jsonObject.getJSONObject("respObject").getJSONArray("tagList").getJSONObject(0).getString("tagName"));
                    attendeeTags.add(jsonObject.getJSONObject("respObject").getJSONArray("tagList").getJSONObject(0).getInt("exhibitorAttendeeTagId"));
                } else if ((jsonArray.length() > 1)) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        attendeeTagsName.add(jsonObject.getJSONObject("respObject").getJSONArray("tagList").getJSONObject(i).getString("tagName"));
                        attendeeTags.add(jsonObject.getJSONObject("respObject").getJSONArray("tagList").getJSONObject(i).getInt("exhibitorAttendeeTagId"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if (response.getRetStatus() == -1004 || response.getRetStatus() == -1005) {
            showLimitEnter();
            return;
        }

        if (response.getRetStatus() == -1) {
            collectResultGone();

//            tv_pop_title.setText(R.string.not_design_ticket);
            tv_title_remind.setText(R.string.not_design_ticket);
//            tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
            tv_title_remind.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
            mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
            countDownTimer.start();
            return;
        }


        LogUtil.e("barcode=" + barcode);
        DBUtil.collect(eventId, collectionId, barcode, Constants.COLLECT_SYNV);
        if (TextUtils.isEmpty(name)) {
            Attends attends = new Select().from(Attends.class).where(Attends_Table.barcodes.is(barcode)).querySingle();
            if (attends != null) {
                name = attends.names;
                LogUtil.e("name=" + name);
            }
        }
        if (isRepeat == 0) {
            if (response.getRetStatus() == 200) {
                showAllownEneter(name, company, attendeeTagsName);
            } else if (response.getRetStatus() == 300) {
                showHaveCollect(name, company);
            }
        } else {
            showAllownEneter(name, company, attendeeTagsName);
        }
    }

    @Override
    public void showPostFailed() {
        //   Log.e("aaa","ssss");

        if (!isChecked) {
            SQLite.update(CollectChild.class).set(CollectChild_Table.isLegal.is(1))
                    .where(CollectChild_Table.collectionId.is(collectionId))
                    .and(CollectChild_Table.attendeeBarcode.is(barcode)).execute();
            showLimitEnter();
        } else {
            TosUtil.show(getString(R.string.error_collect));
        }
    }

    /**
     * 保存数据到数据库
     *
     * @param isSync
     * @param time
     */
    private void setDataToDB(final int isSync, final String time) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<CollectChild> list = new ArrayList<CollectChild>();
                CollectChild child = new CollectChild();
                child.attendeeBarcode = barcode;
                child.attendeeCheckInTime = time;
                child.eventId = eventId;
                child.collectionId = collectionId;
                child.collectIsSync = isSync;
                list.add(child);
                FlowManager.getDatabase(AppDatabase.class)
                        .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                new ProcessModelTransaction.ProcessModel<CollectChild>() {
                                    @Override
                                    public void processModel(CollectChild model, DatabaseWrapper wrapper) {
                                        model.save();
                                    }
                                }).addAll(list).build())
                        .error(new Transaction.Error() {
                            @Override
                            public void onError(Transaction transaction, Throwable error) {
                                //Log.e("lists", "Database transaction failed.", error);
                            }
                        })
                        .success(new Transaction.Success() {
                            @Override
                            public void onSuccess(Transaction transaction) {
                                EventBus.getDefault().post(new MsgEvent("setTvValueAnimator"));
                            }
                        }).build().execute();
            }
        };
        runnable.run();
    }


    /**
     * 保存数据到备份数据库
     */
    private void setDataToBackupDB(final String time) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<BackupCollection> list = new ArrayList<BackupCollection>();
                BackupCollection backupCollection = new BackupCollection();
                backupCollection.attendeeName = name;
                backupCollection.attendeePinyinName = pinyinName;
                backupCollection.attendeeBarcode = barcode;
                backupCollection.attendeeCheckInTime = time;
                backupCollection.eventId = eventId;
                backupCollection.collectionId = collectionId;
                list.add(backupCollection);
                FlowManager.getDatabase(AppDatabase.class)
                        .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                new ProcessModelTransaction.ProcessModel<BackupCollection>() {
                                    @Override
                                    public void processModel(BackupCollection model, DatabaseWrapper wrapper) {
                                        model.save();
                                    }
                                }).addAll(list).build())
                        .error(new Transaction.Error() {
                            @Override
                            public void onError(Transaction transaction, Throwable error) {
                                //Log.e("lists", "Database transaction failed.", error);
                            }
                        })
                        .success(new Transaction.Success() {
                            @Override
                            public void onSuccess(Transaction transaction) {
                                //    Log.e("list", "Database transaction success");
                            }
                        }).build().execute();
            }
        };
        runnable.run();
    }


    @Override
    public String getEventId() {
        return eventId + "";
    }

    @Override
    public String userId() {
        String userId = SharedPreferencesUtil.get(this, "userId", "");
        return SharedPreferencesUtil.get(this, "userId", "");
    }

    @Override
    public void eventDetailSuccess(EventDetailData response) {
        peopleCount = response.getRespObject().getAttendeeCount();
    }

    @Override
    public void eventDetailFailed(String errInfo) {

    }

    @Override
    public void attendeeJsonFile(String jsonFile) {
        SyncAttendeeUtil util = new SyncAttendeeUtil(this, jsonFile, eventId, 0);
        util.syncAttendeeFile();
    }

    @Override
    public void attendeeJsonFileFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getPageNum() {
        return pageNum + "";
    }

    @Override
    public String getUpdateTime() {
        return currentTime;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_collect_barcode_back:
                if (loginType.equals(Common.COLLECT_LOGIN_TYPE_BARCODE) || loginType.equals(Common.COLLECT_LOGIN_TYPE_AUTOLOGIN)) {
                    passwordDialog.show();
                } else if (loginType.equals(Common.COLLECT_LOGIN_TYPE_HOMEPAGE) || loginType.equals(Common.COLLECT_LOGIN_TYPE_MANAGER)) {
                    AppManager.getAppManager().finishActivity();
                }
                break;
            case R.id.ll_collect_updown:
                if (NetUtil.isConnected(this)) {
                    Intent intent = new Intent(this, StartSyncDataService.class);
                    startService(intent);
                } else {
                    Toast toast = Toast.makeText(this, R.string.check_network2, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                break;
            case R.id.ll_collect_export:
                //Log.e(TAG,"CLICK!");
                if (NetUtil.isConnected(this)) {
                    List<CollectChild> collect = new Select().from(CollectChild.class).where(CollectChild_Table.collectIsSync.is(Constants.COLLECT_NOT_SYNV)).queryList();
                    if (collect.size() > 0) {
                        isExport = true;
                        Intent intent = new Intent(this, StartSyncDataService.class);
                        startService(intent);
                        Toast toast = Toast.makeText(this, R.string.is_sys_data, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        exportDialog.show();
                    }

                } else {
                    Toast toast = Toast.makeText(this, R.string.check_network2, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.isChecked = isChecked;
        if (isChecked) {
            Toast toast = Toast.makeText(this, R.string.open_switch, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private String time() {
        Long time = System.currentTimeMillis();
        //   String currentTime = time.toString();
        return time.toString();
    }

//    private String time() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        Date curDate = new Date(System.currentTimeMillis());
//        return format.format(curDate);
//    }

    /**
     * 采集人员
     */

    private void collectPeople(String result) {
        if (loginType.equals(Common.COLLECT_LOGIN_TYPE_MANAGER)) {//采集点管理界面进入
            rl_switch_button.setVisibility(View.GONE);
        }
        LogUtil.i("---------------------------------++++++++");
        if (tempScanResult.equals(result)) {
            if (System.currentTimeMillis() - scanTime > 5000) {
                scanTime = System.currentTimeMillis();

                if (syncData == 1) {
                    TosUtil.showCenter(getString(R.string.sys_data_remind));
                    return;
                }

                if (collectionType == 2) {
                    setCollectionInOut(result);
                } else {
                    scanBarcode(result);
                }
            }
            LogUtil.i("---------------------------------"+(System.currentTimeMillis() - scanTime)+"");
        } else {
            tempScanResult = result;
            scanTime = System.currentTimeMillis();

            if (syncData == 1) {
                TosUtil.showCenter(getString(R.string.sys_data_remind));
                return;
            }

            if (collectionType == 2) {
                setCollectionInOut(result);
            } else {
                scanBarcode(result);
            }
        }
    }

    /**
     * 设置采集点进出口
     */
    private void setCollectionInOut(String result) {
        vibrate();
        boolean num = isNum(result);
        if (((result.length() == 25 && result.substring(8, 9).contains("-"))) || (num && result.length() == 19) || (num && result.length() == 20) || result.equals("www.bagevent.com")) {
            if (NetUtil.isConnected(this)) {
                str = time();
                barcode = result;
                collectionInOutPresenter = new CollectionInOutPresenter(this);
                collectionInOutPresenter.setCollectionInOut();
            } else {
                Toast.makeText(this, R.string.check_network2, Toast.LENGTH_SHORT).show();
            }
        } else {
            collectResultGone();

            tv_pop_title.setText(R.string.error_barcode);
            tv_title_remind.setText(R.string.error_barcode);
            tv_pop_title.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
            tv_title_remind.setTextColor(ContextCompat.getColor(this, R.color.aFF1D00));
            mPopUpWindow.showAtLocation(this.findViewById(R.id.ll_barcode_title), Gravity.BOTTOM, 0, 0);
            countDownTimer.start();
        }
    }

    /**
     * 如果扫描采集点进入则需要同步该活动的门票信息
     */
    @Override
    public void showTicketInfo(final TicketInfo ticketInfo) {
        SyncTicketUtil util = new SyncTicketUtil(this, eventId, ticketInfo, false);
        util.syncTicket();
    }

    @Override
    public void showErrInfo(String errInfo) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showDetailInfo(FormType formType) {

    }

    @Override
    public void showDetailErrInfo(String errInfo) {

    }

    @Override
    public void showBadgeFormInfo(FormType formType) {

    }

    @Override
    public void showBadgeFormErrInfo(String errInfo) {

    }

    /**
     * 获取表单中的公司和职位的filedId
     */
    private String getFiled(String gsonUser) throws JSONException {
        mFormType = (String) NetUtil.readObject(this, Constants.FORM_FILE_NAME + eventId + "");//从文件中获得表单类型
        mType = new Gson().fromJson(mFormType, FormType.class);//解析表单数据
        JSONObject obj = new JSONObject(gsonUser);
        int count = mType.getRespObject().size();
        for (int i = 0; i < count; i++) {
            FormType.RespObjectBean type = mType.getRespObject().get(i);
            if (type.getFieldTypeName().contains("company")) {
                String company = obj.getString(type.getFormFieldId() + "");
                return company;
            }
        }
        return "";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            if (loginType.equals(Common.COLLECT_LOGIN_TYPE_BARCODE) || loginType.equals(Common.COLLECT_LOGIN_TYPE_AUTOLOGIN)) {
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 扫描结果
     *
     * @param rawResult
     * @param parsedResult
     * @param barcode
     */
    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        restartPreviewAfterDelay(0L);
        String result = rawResult.toString();
        Log.i("-------------------,", result);
//        LogUtil.e("采集点扫描结果为" + result);
        if (result.contains("$")) {
            String[] s = result.split("\\$");
            String code = s[0];
            ticketId = s[1];
            collectPeople(code);
        } else if (result.startsWith("http://bagevent.com/i/")) {
            String[] s = result.split("http://bagevent.com/i/");
            if (s.length >= 2) {
                String code = s[1];
                collectPeople(code);
            }
        } else {
            collectPeople(result);
        }
    }
}
