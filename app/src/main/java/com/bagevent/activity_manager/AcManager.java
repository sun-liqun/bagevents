package com.bagevent.activity_manager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.detail.LoginQrCode;
import com.bagevent.activity_manager.manager_fragment.ActivityOrderFragment;
import com.bagevent.activity_manager.manager_fragment.BarCodeCheckIn;
import com.bagevent.activity_manager.manager_fragment.CollectManagerFragment;
import com.bagevent.activity_manager.manager_fragment.ExhibitorManageFragment;
import com.bagevent.activity_manager.manager_fragment.ManagerCenterFragment;
import com.bagevent.activity_manager.manager_fragment.MeetingPersonFragment;
import com.bagevent.activity_manager.manager_fragment.SignInFragment;
import com.bagevent.activity_manager.manager_fragment.adapter.ExhibitorStatusAdapter;
//import com.bagevent.activity_manager.manager_fragment.data.ExhibitorData;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.login.LoginActivity;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.new_home.new_activity.MoreActivity;
import com.bagevent.synchro_data.NetWorkBroadcast;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.PageTool;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.JsonParser;
import com.jaeger.library.StatusBarUtil;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by zwj on 2016/5/24.
 */
public class AcManager extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rv_status)
    RecyclerView rvStatus;

    private static final int ACTIVITYORDER = 0X3;
    private static final int COLLECTMANAGER = 0X4;
    private static final int MANAGERCENTER = 0X5;
    private static final int MEETINGPERSON = 0X6;
    private static final int EXHIBITORMANAGER = 0X7;

    private ActivityOrderFragment activityOrder;
    private CollectManagerFragment collectManager;
    private ManagerCenterFragment managerCenter;
    private MeetingPersonFragment meetingPerson;
    private ExhibitorManageFragment exhibitorManage;
    private SignInFragment signIn;
    private FrameLayout activity_manager;

    private FragmentManager fragmentManager;

    private AutoLinearLayout ll_managerCenter, ll_meetingPerson, ll_collectManager, ll_signIn, ll_order, ll_exhibitorManager,ll_barcode;// ll_activityNotification;
    private TextView text_managerCenter, text_meetingPerson, text_collectManager, text_signIn, text_order, text_exhibitorManager,text_barceode;// text_activityNotification;
    private ImageView img_managerCenter, img_meetingPerson, img_collectManager, img_signIn, img_order, img_exhibitorManager,img_barcode;// img_activityNotification;

    private Bundle bundle = null;
    private int eventId = -1;
    private int exType = -1;
    /**
     * 发送监听网络状态的广播
     */
    private NetWorkBroadcast broadcast;
    private String loginType = "";
    private NormalAlertDialog exitDialog;

    private ArrayList<String> exhibitorStatus = new ArrayList<>();
    private ArrayList<String> pos = new ArrayList<>();
    private ArrayList<String> selectedStatus=new ArrayList<>();

    private ExhibitorStatusAdapter statusAdapter;

    private int auditCount=0;
    private final byte ACTION_LAOD = 0;
    private final byte ACTION_REFRESH = 1;
    private final byte ACTION_LOADMORE = 2;

    @OnClick({R.id.tv_cancel,R.id.tv_ok})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv_cancel:
                pos.removeAll(pos);
                selectedStatus.removeAll(selectedStatus);
                statusAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_ok:
                exhibitorManage.initData(1, false);
                exhibitorManage.initAuditData(ACTION_LAOD);
                drawerLayout.closeDrawer(Gravity.RIGHT);
                EventBus.getDefault().post(new MessageEvent(MessageAction.REGRESH_EXHIBITOR_AUDIT));
                EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_STATUS,selectedStatus));
                EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_STATUS_ID,pos));
//                EventBus.getDefault().post(new MessageEvent(MessageAction.REFRESH_TYPE));
//                EventBus.getDefault().post(new MessageEvent(MessageAction.FILTER_DATA,auditCount));
                break;
        }
    }
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_manager);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), Constants.STATUS_ALPHA);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        loginType = bundle.getString(Common.BARCODE_LOGIN);
        eventId = bundle.getInt("eventId");
        exType = bundle.getInt("exType");
        if (loginType != null && !loginType.contains(Common.BARCODE_LOGIN)) {
            initView();
            setClickListener();
            ll_managerCenter.setVisibility(View.VISIBLE);
//            ll_collectManager.setVisibility(View.VISIBLE);
            setDefaultTab(MANAGERCENTER);
        } else if (loginType != null && loginType.contains(Common.BARCODE_LOGIN)) {
            initView();
            exitAccount();
            setClickListener();
            ll_managerCenter.setVisibility(View.GONE);
            ll_collectManager.setVisibility(View.GONE);
            ll_order.setVisibility(View.GONE);
            ll_signIn.setVisibility(View.GONE);
            ll_barcode.setVisibility(View.VISIBLE);
            setDefaultTab(MEETINGPERSON);
            sendNetWorkBroadcast();
        }
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (exType==1){
            initDrawer();
//            loadAuditExhibitor();
        }
    }

//    private void loadAuditExhibitor() {
//        if (!NetUtil.isConnected(this)) {
//            TosUtil.show(getString(R.string.check_your_net));
//            return;
//        }
//
//        OkHttpUtil.Get(this)
//                .url(Constants.NEW_URL + Constants.QUERY_EXHIBITORS)
//                .addParams("eventId", String.valueOf(eventId))
//                .addParams("pagingPage", "1")
//                .addParams("audit", "0")
//                .addParams("condition", "")
//                .addParams("startTime", "")
//                .addParams("search", "")
//                .build()
//                .execute(new Callback<String>() {
//                    @Override
//                    public String parseNetworkResponse(Response response, int id) throws Exception {
//                        return response.body().string();
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        auditCount=0;
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        if (response.contains("\"retStatus\":200")) {
//                            ArrayList<ExhibitorData.ExhibitorList> exhibitorData = getListData(response);
//                            auditCount = exhibitorData.size();
//                        } else {
//                            auditCount=0;
//                        }
//                    }
//                });
//    }
//
//    private ArrayList<ExhibitorData.ExhibitorList> getListData(String response) {
//        ExhibitorData exhibitorData =
//                new ExhibitorData(new JsonParser().parse(response).getAsJsonObject());
//        ArrayList<ExhibitorData.ExhibitorList> exhibitorList = exhibitorData.getRespObject().getExhibitorList();
//        if (exhibitorData.getRespObject() == null || exhibitorList == null) {
//            return new ArrayList<>();
//        } else {
//            return exhibitorList;
//        }
//    }

    private void initDrawer() {
        exhibitorStatus.add(getString(R.string.no_status));
        exhibitorStatus.add(getString(R.string.in_contract));
        exhibitorStatus.add(getString(R.string.fees_to_be_paid));
        exhibitorStatus.add(getString(R.string.expenses_paid));
        exhibitorStatus.add(getString(R.string.confirmation_of_participation));
        exhibitorStatus.add(getString(R.string.confirmation_not_to_participation));
        statusAdapter = new ExhibitorStatusAdapter(exhibitorStatus, pos);
        statusAdapter.notifyDataSetChanged();
        statusAdapter.setHasStableIds(true);
        rvStatus.setAdapter(statusAdapter);
        rvStatus.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false));
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this);
        rvStatus.setLayoutManager(manager);
        statusAdapter.setListener(new ExhibitorStatusAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (pos.contains(String.valueOf(position))){
                    pos.remove(String.valueOf(position));
                } else {
                    pos.add(String.valueOf(position));
                }

                if (selectedStatus.contains(exhibitorStatus.get(position))){
                    selectedStatus.remove(exhibitorStatus.get(position));
                }else {
                    selectedStatus.add(exhibitorStatus.get(position));
                }
            }
        });
    }

    public String getSelectStatus() {
        String status="";
        if (pos.size()>0){
            if (pos.size()==1){
                status=pos.get(0);
            }else {
                for (int i = 0; i < pos.size() ; i++) {
                    status=status+pos.get(i);
                    if (i!=pos.size()-1){
                        status+=",";
                    }
                }
            }
        }else {
            status="";
        }
        return String.valueOf(status);
    }


    public void showRightMenu() {
        if (!drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
        selectedStatus.removeAll(selectedStatus);
        pos.removeAll(pos);
        statusAdapter.notifyDataSetChanged();
    }

    private void sendNetWorkBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadcast = new NetWorkBroadcast();
        registerReceiver(broadcast, filter);
    }

    private void setDefaultTab(int i) {
        FragmentTransaction transactions = fragmentManager.beginTransaction();
        hideTab(transactions);
        setDefaultImgOrColor();
        switch (i) {
            case MANAGERCENTER:
                if (managerCenter == null) {
                    managerCenter = new ManagerCenterFragment();
                    managerCenter.setArguments(bundle);
                    transactions.add(R.id.activity_manager, managerCenter);
                } else {
                    transactions.show(managerCenter);
                }
                img_managerCenter.setImageResource(R.drawable.tabbar_manage_select);
                text_managerCenter.setTextColor(ContextCompat.getColor(this, R.color.fe6900));
                break;
            case MEETINGPERSON:
                if (meetingPerson == null) {
                    meetingPerson = new MeetingPersonFragment();
                    meetingPerson.setArguments(bundle);
                    transactions.add(R.id.activity_manager, meetingPerson);
                } else {
                    transactions.show(meetingPerson);
                }
                img_meetingPerson.setImageResource(R.drawable.tabbar_attendee_selected);
                text_meetingPerson.setTextColor(ContextCompat.getColor(this, R.color.fe6900));
                break;
            case COLLECTMANAGER:
                if (collectManager == null) {
                    collectManager = new CollectManagerFragment();
                    collectManager.setArguments(bundle);
                    transactions.add(R.id.activity_manager, collectManager);
                } else {
                    transactions.show(collectManager);
                }
                img_collectManager.setImageResource(R.drawable.collect);
//                text_collectManager.setTextColor(getResources().getColor(R.color.fe6900));
                text_collectManager.setTextColor(ContextCompat.getColor(this,R.color.fe6900));
                break;
            case ACTIVITYORDER:
                if (activityOrder == null) {
                    activityOrder = new ActivityOrderFragment();
                    activityOrder.setArguments(bundle);
                    transactions.add(R.id.activity_manager, activityOrder);
                } else {
                    transactions.show(activityOrder);
                }
                img_order.setImageResource(R.drawable.tabbar_order_selected);
//                text_order.setTextColor(getResources().getColor(R.color.fe6900));
                text_order.setTextColor(ContextCompat.getColor(this,R.color.fe6900));
                break;
            case EXHIBITORMANAGER:
                if (exhibitorManage == null) {
                    exhibitorManage = new ExhibitorManageFragment();
                    exhibitorManage.setArguments(bundle);
                    transactions.add(R.id.activity_manager, exhibitorManage);
                } else {
                    transactions.show(exhibitorManage);
                }
                img_exhibitorManager.setImageResource(R.drawable.exhibitor);
//                text_exhibitorManager.setTextColor(getResources().getColor(R.color.fe6900));
                text_exhibitorManager.setTextColor(ContextCompat.getColor(this,R.color.fe6900));
                break;

//            case SIGNIN:
//                if (signIn == null) {
//                    signIn = new SignInFragment();
//                    transactions.add(R.id.activity_manager, signIn);
//                } else {
//                    transactions.show(signIn);
//                }
//                img_signIn.setImageResource(R.drawable.sign);
//                text_signIn.setTextColor(getResources().getColor(R.color.fe6900));
//                break;

        }
        transactions.commit();
    }

    private void hideTab(FragmentTransaction transaction) {
        if (managerCenter != null) {
            transaction.hide(managerCenter);
        }
        if (meetingPerson != null) {
            transaction.hide(meetingPerson);
        }
        if (collectManager != null) {
            transaction.hide(collectManager);
        }
        if (activityOrder != null) {
            transaction.hide(activityOrder);
        }
        if (exhibitorManage != null) {
            transaction.hide(exhibitorManage);
        }
        if (signIn != null) {
            transaction.hide(signIn);
        }
        ll_managerCenter.setSelected(false);
        ll_meetingPerson.setSelected(false);
        ll_collectManager.setSelected(false);
        ll_order.setSelected(false);
        ll_exhibitorManager.setSelected(false);
        ll_signIn.setSelected(false);
    }

    private void setDefaultImgOrColor() {
        img_managerCenter.setImageResource(R.drawable.tabbar_manage_unselected);
        img_meetingPerson.setImageResource(R.drawable.tabbat_attendee_unselect);
        img_collectManager.setImageResource(R.drawable.collect_click);
        img_order.setImageResource(R.drawable.tabbar_order_unselect);
        img_exhibitorManager.setImageResource(R.drawable.exhibitor_grey);
        img_signIn.setImageResource(R.drawable.tab_more);

        text_managerCenter.setTextColor(ContextCompat.getColor(this, R.color.grey));
        text_meetingPerson.setTextColor(ContextCompat.getColor(this, R.color.grey));
        text_collectManager.setTextColor(ContextCompat.getColor(this, R.color.grey));
        text_order.setTextColor(getResources().getColor(R.color.grey));
        text_exhibitorManager.setTextColor(ContextCompat.getColor(this,R.color.grey));
        text_signIn.setTextColor(ContextCompat.getColor(this, R.color.grey));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_manager_center:
                setDefaultTab(MANAGERCENTER);
                break;
            case R.id.ll_meeting_person:
                setDefaultTab(MEETINGPERSON);
                break;
            case R.id.ll_collect_manager_tab:
                setDefaultTab(COLLECTMANAGER);
                break;
            case R.id.ll_activity_order:
                setDefaultTab(ACTIVITYORDER);
                break;
            case R.id.ll_exhibitor_manager:
                setDefaultTab(EXHIBITORMANAGER);
                break;
            case R.id.ll_sign_in:
                PageTool.go(this, MoreActivity.class,bundle);
                break;
            case R.id.ll_barcode:
                Intent intent=new Intent(this,BarCodeCheckIn.class);
                intent.putExtra("eventId",eventId);
                intent.putExtra("isBarcodeLogin", 1);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void setClickListener() {
        ll_managerCenter.setOnClickListener(this);
        ll_meetingPerson.setOnClickListener(this);
        ll_collectManager.setOnClickListener(this);
        ll_signIn.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        ll_exhibitorManager.setOnClickListener(this);
        ll_barcode.setOnClickListener(this);
    }

    private void initView() {

        activity_manager = (FrameLayout) findViewById(R.id.activity_manager);
        ll_managerCenter = (AutoLinearLayout) findViewById(R.id.ll_manager_center);
        ll_meetingPerson = (AutoLinearLayout) findViewById(R.id.ll_meeting_person);
        ll_signIn = (AutoLinearLayout) findViewById(R.id.ll_sign_in);
        ll_order = (AutoLinearLayout) findViewById(R.id.ll_activity_order);
        ll_exhibitorManager = findViewById(R.id.ll_exhibitor_manager);
        ll_collectManager = (AutoLinearLayout) findViewById(R.id.ll_collect_manager_tab);
        ll_barcode=findViewById(R.id.ll_barcode);

        text_managerCenter = (TextView) findViewById(R.id.text_manager_center);
        text_meetingPerson = (TextView) findViewById(R.id.text_meeting_person);
        text_collectManager = (TextView) findViewById(R.id.text_collect_manager);
        text_signIn = (TextView) findViewById(R.id.text_sign_in);
        text_order = (TextView) findViewById(R.id.text_activity_order);
        text_exhibitorManager = findViewById(R.id.text_exhibitor_manager);
        text_barceode=findViewById(R.id.text_barcode);

        img_managerCenter = (ImageView) findViewById(R.id.img_manager_center);
        img_meetingPerson = (ImageView) findViewById(R.id.img_meeting_person);
        img_collectManager = (ImageView) findViewById(R.id.img_collect_manager);
        img_signIn = (ImageView) findViewById(R.id.img_sign_in);
        img_order = (ImageView) findViewById(R.id.img_activity_order);
        img_exhibitorManager = findViewById(R.id.img_exhibitor_manager);
        img_barcode=findViewById(R.id.img_barcode);

        if (exType==0){
                ll_exhibitorManager.setVisibility(View.GONE);
            ll_collectManager.setVisibility(View.VISIBLE);
        }else {
            ll_collectManager.setVisibility(View.GONE);
            ll_exhibitorManager.setVisibility(View.VISIBLE);
        }
        fragmentManager = getSupportFragmentManager();
    }

    private void exitAccount() {
        exitDialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black_light)
                .setContentText(getString(R.string.autologin_exit))
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        exitDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        exitDialog.dismiss();
                        SharedPreferencesUtil.clear(AcManager.this);
                        Intent intent = new Intent(AcManager.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
//                        finish();
                        AppManager.getAppManager().finishAllActivity();
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            if (loginType != null && loginType.contains(Common.BARCODE_LOGIN)) {
                exitDialog.show();
            } else {
                super.onBackPressed();
            }
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if(event.getAction() == KeyEvent.ACTION_DOWN){
                if (loginType != null && loginType.contains(Common.BARCODE_LOGIN)) {
                     exitDialog.show();
                }else {
                    closeDrawer();
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
    private void closeDrawer() {
        if (drawerLayout!=null&&drawerLayout.isDrawerOpen(Gravity.RIGHT)){
            drawerLayout.closeDrawer(Gravity.RIGHT);
            return;
        }
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcast != null) {
            unregisterReceiver(broadcast);
        }
        EventBus.getDefault().removeAllStickyEvents();
    }
}
