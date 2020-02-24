package com.bagevent.new_home;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.bagevent.NewMessageFragment;
import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.EventList;
//import com.bagevent.db.EventList_Table;
import com.bagevent.db.EventList_Table;
import com.bagevent.dialog.LDialog;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.home.home_interface.presenter.GetExercisingPresenter;
import com.bagevent.home.home_interface.view.GetExercisingView;
import com.bagevent.jpush.JpushReceiver;
import com.bagevent.new_home.adapter.DynamicEventAdapter;
import com.bagevent.new_home.data.FindUnreadData;
import com.bagevent.new_home.fragment.DynamicFragment;
import com.bagevent.new_home.fragment.EventPandectFragment;
import com.bagevent.new_home.fragment.MessageFragment;
import com.bagevent.new_home.fragment.UserSetFragment;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.new_home.new_interface.new_view.FindExistUnReadView;
import com.bagevent.new_home.new_interface.presenter.FindExistUnReadPresenter;
import com.bagevent.synchro_data.NetWorkBroadcast;
import com.bagevent.util.AppManager;
import com.bagevent.util.DxPxUtils;
import com.bagevent.util.InputMethodUtil;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.LocalBroadcastManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.view.MyLinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zwj on 2016/8/25.
 */
public class HomePage extends BaseActivity implements FindExistUnReadView {

    @BindView(R.id.iv_event_pandect)
    ImageView ivEventPandect;
    @BindView(R.id.tv_event_pandect)
    TextView tvEventPandect;
    @BindView(R.id.ll_event_pandect)
    AutoLinearLayout llEventPandect;

    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.ll_message)
    AutoLinearLayout llMessage;

    @BindView(R.id.iv_user_set)
    ImageView ivUserSet;
    @BindView(R.id.tv_user_set)
    TextView tvUserSet;
    @BindView(R.id.ll_user_set)
    AutoLinearLayout llUserSet;

    @BindView(R.id.v_unread)
    View vUnread;
    @BindView(R.id.iv_dynamic)
    ImageView ivDynamic;
    @BindView(R.id.tv_dynamic)
    TextView tvDynamic;
    @BindView(R.id.ll_dynamic)
    AutoLinearLayout llDynamic;
    @BindView(R.id.left_menu_recycler)
    RecyclerView left_menu_recycler;
    @BindView(R.id.tv_dynamic_event_num)
    TextView tv_dynamic_event_num;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tv_all_event)
    TextView tv_all_event;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.myLinearLayout)
    MyLinearLayout myLinearLayout;
    @BindView(R.id.fl_guide_sign)
    AutoFrameLayout fl_guide_sign;
    @BindView(R.id.fl_guide_sign_dynamic)
    AutoFrameLayout fl_guide_sign_dynamic;
    @BindView(R.id.ll_home_bottom)
    LinearLayout ll_home_bottom;

    private LDialog lDialog;

    private InputMethodManager inputMethodManager;


    private PopupWindow mPopUpWindow;
    private View mPopUpView;
    private int popupWidth;
    private int popupHeight;
    private EditText et_comment_dynamic;
    private TextView tv_comment_dynamic;
    /**
     * fragment
     */
    private static final int EVENTPANDECT = 0x11;
    private static final int USERSET = 0x12;
    private static final int MESSAGE = 0x13;
    private static final int DYNAMIC = 0X14;
    @BindView(R.id.tv_filter_all)
    TextView tvFilterAll;
    @BindView(R.id.tv_filter_question)
    TextView tvFilterQuestion;
    @BindView(R.id.tv_filter_publish)
    TextView tvFilterPublish;
    @BindView(R.id.tv_filter_realy)
    TextView tvFilterRealy;
    @BindView(R.id.tv_filter_answer)
    TextView tvFilterAnswer;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;

    //活动总览
    private EventPandectFragment EPFragment;
    //账户设置
    private UserSetFragment userSetFragment;
    //消息中心
//    private MessageFragment messageFragment;
    private NewMessageFragment messageFragment;
//    private RecentContactsFragment messageFragment;

    //动态
    private DynamicFragment dynamicFragment;

    private FragmentManager fragmentManager;

    private WeakHandler weakHandler;

    private ArrayList<String> lastSelcetEventId = new ArrayList<>();
    private ArrayList<String> currentSelectEventId = new ArrayList<>();
    private int currentSelctIndex = 0;
    private int lastSelctIndex = 0;

    private boolean isLeftSelect;
    private boolean isRightSelect;
    private boolean isSelectAll;

    private int event_id;
    private int comment_id;

    /**
     * 标识连续按两次返回键退出
     */
    private long exitTime = 0;

    /**
     * 发送监听网络状态的广播
     */
    private NetWorkBroadcast broadcast;
    private JpushReceiver jpushReceiver;
    private static final int REQUECT_CODE_SDCARD = 1;
    private ChatMessageReciver chatMessageReciver;

    private String userId;
    private FindExistUnReadPresenter findExistUnReadPresenter;
    private List<EventList> eventLists;
    private DynamicEventAdapter dynamicEventAdapter=new DynamicEventAdapter(eventLists,currentSelectEventId);

    List<String> allEventList;
    String nickName;

    private GetExercisingPresenter getExPresenter;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_homepage);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), Constants.STATUS_ALPHA);
        weakHandler = new WeakHandler();
//        //不保留后台活动，从厂商推送进聊天页面，会无法退出聊天页面
//        if (savedInstanceState == null && parseIntent()) {
//            return;
//        }
        initView();
        setDefaultPage(EVENTPANDECT);
        initData();
        sendNetWorkBroadcast();
        registerChatReceiver();
        initLeftMenu();
        initPop();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
        MPermissions.requestPermissions(this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
    }
    Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    vUnread.setVisibility(View.VISIBLE);
                }
            };
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent event){
        if(event.getAction() == MessageAction.ACTION_DYNAMIC_EVENT_ID){
            event_id= (int) event.getValue();
        }else if(event.getAction() == MessageAction.ACTION_DYNAMIC_COMMENT_ID) {
            comment_id= (int) event.getValue();
        }else if (event.getAction()==MessageAction.ACTION_GET_NICKNAME){
            nickName=(String) event.getValue();
        }else if (event.getAction()==MessageAction.ACTION_GET_NICKNAME_TWO){
            nickName=(String)event.getValue();
        }
    }

    public void showLeftMenu() {
        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    public void showRightMenu() {
        if (!drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    @Override
    protected void onResume() {
        setAlias();
        super.onResume();
        if (getIntent().getExtras()!=null){
            parseIntent();
//            Bundle extras = getIntent().getExtras();
//            extras.get(NimIntent.EXTRA_NOTIFY_CONTENT);
//            setDefaultPage(MESSAGE);
        }
    }


    /**
     * 获取已经选择的活动id
     *
     * @return
     */
    public  String getSelectEventId() {
        String selectEventId = "";
        for (int i = 0; i < currentSelectEventId.size(); i++) {
            if (i == currentSelectEventId.size() - 1) {
                selectEventId = selectEventId + currentSelectEventId.get(i);
            } else {
                selectEventId = selectEventId + currentSelectEventId.get(i) + ",";
            }
        }
        return selectEventId;
    }

    public String getCurrentSelctIndex() {
        return String.valueOf(currentSelctIndex);
    }

    public String getSelectEventName() {
        if (currentSelectEventId.size() == 0) {
            return getString(R.string.no_activity);
        } else if (currentSelectEventId.size() == eventLists.size()) {
            return getString(R.string.all_event);
        } else {
            String eventName = "";
            for (int i = 0; i < eventLists.size(); i++) {
                if (eventLists.get(i).eventId== Integer.parseInt(currentSelectEventId.get(0))) {
                    eventName = eventLists.get(i).eventName;
                    break;
                }
            }
            if (currentSelectEventId.size() == 1) {
                return eventName;
            } else {
                return eventName + getString(R.string.wait) + currentSelectEventId.size() + getString(R.string.activity_num);
            }
        }

    }


    /**
     * 初始化左侧活动菜单
     */
    private void initLeftMenu() {
        allEventList=new ArrayList<>();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        eventLists = new Select().from(EventList.class).where(EventList_Table.userId.is(Integer.parseInt(userId))).queryList();
        if (eventLists.size()>0){
            String allEventId="";
            StringBuilder builder=new StringBuilder();
            for (int i = 0; i <eventLists.size() ; i++) {
                if (builder.length()>0){
                    builder.append(",");
                }
                builder.append(eventLists.get(i).eventId);
            }
            allEventId=builder.toString();
            SharedPreferencesUtil.put(this,"eventListId",allEventId);
        }

        if (eventLists.size() == 0) {
            return;
        }
        String selectEvents = SharedPreferencesUtil.get(this, KeyUtil.KEY_SELECT_EVENT_ID, "");
        tv_dynamic_event_num.setText(String.valueOf(eventLists.size()));
        lastSelcetEventId.clear();
        currentSelectEventId.clear();
        if (TextUtils.isEmpty(selectEvents)) {
            //如果本地没有存储选择活动的id，那就是初次进入程序，那就选择所有活动
            for (int i = 0; i < eventLists.size(); i++) {
                lastSelcetEventId.add(String.valueOf(eventLists.get(i).eventId));
                currentSelectEventId.add(String.valueOf(eventLists.get(i).eventId));
            }
            isSelectAll = true;
            selectAll();
        } else {
            //有存储活动的id，就进行分割获取
            String[] events = selectEvents.split(",");
            for (int i = 0; i < events.length; i++) {
                lastSelcetEventId.add(events[i]);
                currentSelectEventId.add(events[i]);
            }

            noSelectAll();

            if (events.length == eventLists.size()) {
                isSelectAll = true;
                selectAll();
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        dynamicEventAdapter = new DynamicEventAdapter(eventLists, currentSelectEventId);
        left_menu_recycler.setLayoutManager(linearLayoutManager);
        left_menu_recycler.setAdapter(dynamicEventAdapter);
        dynamicEventAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (currentSelectEventId.contains(String.valueOf(eventLists.get(position).eventId))) {
                    currentSelectEventId.remove(String.valueOf(eventLists.get(position).eventId));
                    view.findViewById(R.id.iv_select).setVisibility(View.GONE);
                } else {
                    currentSelectEventId.add(String.valueOf(eventLists.get(position).eventId));
                    view.findViewById(R.id.iv_select).setVisibility(View.VISIBLE);
                }
                if (currentSelectEventId.size()==eventLists.size()){
                    selectAll();
                    isSelectAll=true;
                }else {
                    isSelectAll=false;
                    noSelectAll();
                }
//                dynamicEventAdapter.notifyItemChanged(position);
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                if (drawerView.getId() == R.id.ll_left_menu) {
                    if (isLeftSelect) {
                        lastSelcetEventId.clear();
                        for (int i = 0; i < currentSelectEventId.size(); i++) {
                            lastSelcetEventId.add(currentSelectEventId.get(i));
                        }
                    } else {
                        currentSelectEventId.clear();
                        for (int i = 0; i < lastSelcetEventId.size(); i++) {
                            currentSelectEventId.add(lastSelcetEventId.get(i));
                        }
                        if (dynamicEventAdapter!=null){
                            dynamicEventAdapter.notifyDataSetChanged();
                        }
                    }
                    isLeftSelect = false;
                    if (currentSelectEventId.size()==eventLists.size()){
                        selectAll();
                        isSelectAll=true;
                    }else {
                        isSelectAll=false;
                        noSelectAll();
                    }
                } else {
                    if (isRightSelect) {
                        lastSelctIndex = currentSelctIndex;
                    } else {
                        currentSelctIndex = lastSelctIndex;
                        cancelSelectFilter(currentSelctIndex);
                    }
                    isRightSelect = false;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals("UnRead")) {
            vUnread.setVisibility(View.VISIBLE);
        } else if (event.mMsg.equals("Readed")) {
            vUnread.setVisibility(View.GONE);
        } else if (event.mMsg.contains("newEventList")) {
            initLeftMenu();
        }
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

//    private TagAliasCallback mAliasCallback = new TagAliasCallback() {
//        @Override
//        public void gotResult(int code, final String alias, Set<String> set) {
//            switch (code) {
//                case 0:
//                    // Toast.makeText(HomePage.this, "Set Alias Success!", Toast.LENGTH_SHORT).show();
//                    SharedPreferencesUtil.put(HomePage.this, "alias" + alias, alias);
//                    break;
//                case 6002:
//                    weakHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            JPushInterface.setAlias(getApplicationContext(), alias, mAliasCallback);
//                        }
//                    }, 1000 * 60);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    private void setAlias() {
        String userId = SharedPreferencesUtil.get(this, "userId", "");
        String alias = SharedPreferencesUtil.get(this, "alias" + userId, "");

        if (!TextUtils.isEmpty(userId) && TextUtils.isEmpty(alias)) {
            JPushInterface.setAlias(HomePage.this, 0, userId);
        }
    }


    private void selectAll() {
        tv_all_event.setTextColor(0xfffe9300);
//        Drawable drawable = getResources().getDrawable(R.drawable.icon_gou);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.icon_gou);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_all_event.setCompoundDrawables(drawable, null, null, null);

    }

    private void noSelectAll() {
        tv_all_event.setTextColor(0xff898989);
        tv_all_event.setCompoundDrawables(null, null, null, null);
    }

    private void registerChatReceiver() {
        chatMessageReciver = new ChatMessageReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Common.CHATTING_MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(chatMessageReciver, filter);
    }

    private void sendNetWorkBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadcast = new NetWorkBroadcast();
        registerReceiver(broadcast, filter);
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public void findExistUnReadMsg(FindUnreadData res) {
//        if (res.isRespObject()) {
//            vUnread.setVisibility(View.VISIBLE);
//        } else {
//            vUnread.setVisibility(View.GONE);
//        }
    }

    @Override
    public void findExistUnReadMsgFailed(String errInfo) {

    }


    public class ChatMessageReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Common.CHATTING_MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                vUnread.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(jpushReceiver);

        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess() {
        //Toast.makeText(this, "GRANT ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed() {
        //Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

    private void setDefaultPage(int index) {
        InputMethodUtil.close(this, et_comment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideTransaction(transaction);
        setDefaultImgAndColor();
        switch (index) {
            case EVENTPANDECT:
                llEventPandect.setSelected(true);
                if (EPFragment == null) {
                    EPFragment = new EventPandectFragment();
                    transaction.add(R.id.fm_new_home, EPFragment);
                } else {
                    transaction.show(EPFragment);
                }
                setEventPandectCheck();
                break;
            case USERSET:
                llUserSet.setSelected(true);
                if (userSetFragment == null) {
                    userSetFragment = new UserSetFragment();
                    transaction.add(R.id.fm_new_home, userSetFragment);
                } else {
                    transaction.show(userSetFragment);
                }
                setUserSetCheck();
                break;
            case MESSAGE:
                llMessage.setSelected(true);
                if (messageFragment == null) {
//                    messageFragment = new MessageFragment();
                    messageFragment = new NewMessageFragment();
//                    messageFragment=new RecentContactsFragment();
                    transaction.add(R.id.fm_new_home, messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                setMessageSetCheck();
                break;
            case DYNAMIC:
                llDynamic.setSelected(true);
                if (dynamicFragment == null) {
                    dynamicFragment = new DynamicFragment();
                    transaction.add(R.id.fm_new_home, dynamicFragment);
                } else {
                    transaction.show(dynamicFragment);
                }
                if (!SharedPreferencesUtil.getSettingBoolean(this, KeyUtil.KEY_GUIDE_SIGN_ONE, false)) {
                    fl_guide_sign_dynamic.setVisibility(View.VISIBLE);
                }
                if (EPFragment==null){
                    EPFragment.hideBarcode();
                }
                setDynamicCheck();
                break;
        }
        transaction.commit();
//        transaction.commitAllowingStateLoss();
    }

    private void hideTransaction(FragmentTransaction transaction) {
        if (EPFragment != null) {
            transaction.hide(EPFragment);
        }
        if (userSetFragment != null) {
            transaction.hide(userSetFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (dynamicFragment != null) {
            transaction.hide(dynamicFragment);
        }

        llEventPandect.setSelected(false);
        llUserSet.setSelected(false);
        llMessage.setSelected(false);
    }

    private void setMessageSetCheck() {
        ivEventPandect.setImageResource(R.drawable.myevent_uncheck);
        ivUserSet.setImageResource(R.drawable.usermanager_uncheck);
        ivDynamic.setImageResource(R.drawable.dynamic_uncheck);

        tvEventPandect.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvUserSet.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvDynamic.setTextColor(ContextCompat.getColor(this, R.color.grey));

        ivMessage.setImageResource(R.drawable.xiaoxi);
        tvMessage.setTextColor(ContextCompat.getColor(this, R.color.ff9a3b));
    }

    private void setUserSetCheck() {
        ivEventPandect.setImageResource(R.drawable.myevent_uncheck);
        ivMessage.setImageResource(R.drawable.xiaoxihei);
        ivDynamic.setImageResource(R.drawable.dynamic_uncheck);

        tvEventPandect.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvMessage.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvDynamic.setTextColor(ContextCompat.getColor(this, R.color.grey));


        ivUserSet.setImageResource(R.drawable.usermanager_check);
        tvUserSet.setTextColor(ContextCompat.getColor(this, R.color.ff9a3b));
    }

    private void setEventPandectCheck() {
        ivUserSet.setImageResource(R.drawable.usermanager_uncheck);
        ivMessage.setImageResource(R.drawable.xiaoxihei);
        ivDynamic.setImageResource(R.drawable.dynamic_uncheck);

        tvUserSet.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvMessage.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvDynamic.setTextColor(ContextCompat.getColor(this, R.color.grey));

        ivEventPandect.setImageResource(R.drawable.myevent_check);
        tvEventPandect.setTextColor(ContextCompat.getColor(this, R.color.ff9a3b));
    }

    private void setDynamicCheck() {
        ivUserSet.setImageResource(R.drawable.usermanager_uncheck);
        ivMessage.setImageResource(R.drawable.xiaoxihei);
        ivEventPandect.setImageResource(R.drawable.myevent_uncheck);

        tvUserSet.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvMessage.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvEventPandect.setTextColor(ContextCompat.getColor(this, R.color.grey));

        ivDynamic.setImageResource(R.drawable.dynamic_check);
        ivDynamic.setImageResource(R.drawable.dynamic_check);
        tvDynamic.setTextColor(ContextCompat.getColor(this, R.color.ff9a3b));
    }

    private void setDefaultImgAndColor() {
        ivEventPandect.setImageResource(R.drawable.myevent_uncheck);
        ivUserSet.setImageResource(R.drawable.usermanager_uncheck);
        ivMessage.setImageResource(R.drawable.xiaoxihei);
        ivDynamic.setImageResource(R.drawable.myevent_uncheck);
        tvEventPandect.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvUserSet.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvMessage.setTextColor(ContextCompat.getColor(this, R.color.grey));
        tvDynamic.setTextColor(ContextCompat.getColor(this, R.color.grey));
    }

    private void initData() {
        myLinearLayout.setOnResizeListener(new MyLinearLayout.OnResizeListener() {
            @Override
            public void onResize(int w, int h, int oldw, int oldh) {
                //如果第一次初始化
                if (oldh == 0) {
                    return;
                }
                if (h > oldh) {
                    //输入法关闭
                    ll_comment.setVisibility(View.GONE);
                }
            }
        });

        if (!SharedPreferencesUtil.getSettingBoolean(this, KeyUtil.KEY_GUIDE_SIGN, false)) {
            fl_guide_sign.setVisibility(View.VISIBLE);
        }
    }
    public void hideGuide(){
        fl_guide_sign.setVisibility(View.GONE);
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        userId = SharedPreferencesUtil.get(this, "userId", "");
        if (!InputMethodUtil.isShowing(this,getWindow().getDecorView())){
           if (dynamicFragment!=null){
               dynamicFragment.showPublishButton();
           }
        }
    }

    @OnClick({R.id.ll_event_pandect, R.id.ll_message, R.id.ll_user_set, R.id.ll_dynamic,
            R.id.tv_left_menu_cancel, R.id.tv_left_menu_ok, R.id.tv_right_menu_canel, R.id.tv_right_menu_ok,
            R.id.tv_filter_all, R.id.tv_filter_question, R.id.tv_filter_publish, R.id.tv_filter_realy,
            R.id.tv_filter_answer, R.id.tv_all_event,R.id.iv_guide_sign,R.id.iv_guide_sign_dynamic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_event_pandect:
                setDefaultPage(EVENTPANDECT);
                break;
            case R.id.ll_message:
                setDefaultPage(MESSAGE);
                break;
            case R.id.ll_user_set:
                setDefaultPage(USERSET);
                break;
            case R.id.ll_dynamic:
                setDefaultPage(DYNAMIC);
                break;
            case R.id.tv_left_menu_cancel:
                isLeftSelect = false;
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.tv_left_menu_ok:
                isLeftSelect = true;
                if (currentSelectEventId.size() <= 0) {
                    TosUtil.show("请至少选择一个活动");
                    return;
                }
                if (currentSelctIndex != 0) {
                    currentSelctIndex = lastSelctIndex = 0;
                    cancelSelectFilter(0);
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                dynamicFragment.lazyLoad();
                break;
            case R.id.tv_right_menu_canel:
                isRightSelect = false;
                drawerLayout.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.tv_right_menu_ok:
                isRightSelect = true;
                drawerLayout.closeDrawer(Gravity.RIGHT);
                dynamicFragment.lazyLoad();
                break;
            case R.id.tv_filter_all:
                currentSelctIndex = 0;
                selectFilterAll();
                break;
            case R.id.tv_filter_question:
                currentSelctIndex = 1;
                selectFilterQuestion();
                break;
            case R.id.tv_filter_publish:
                currentSelctIndex = 2;
                selectFilterPublish();
                break;
            case R.id.tv_filter_realy:
                currentSelctIndex = 3;
                selectFilterRealy();
                break;
            case R.id.tv_filter_answer:
                currentSelctIndex = 5;
                selectFilterAnswer();
                break;
            case R.id.tv_all_event:
                if (isSelectAll) {
                    isSelectAll = false;
                    noSelectAll();
                    currentSelectEventId.clear();
                } else {
                    isSelectAll = true;
                    selectAll();
                    lastSelcetEventId.clear();
                    currentSelectEventId.clear();
                    for (int i = 0; i < eventLists.size(); i++) {
                        lastSelcetEventId.add(String.valueOf(eventLists.get(i).eventId));
                        currentSelectEventId.add(String.valueOf(eventLists.get(i).eventId));
                    }
                }
                if (dynamicEventAdapter!=null){
                    dynamicEventAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.iv_guide_sign:
                SharedPreferencesUtil.putSettingBoolean(this, KeyUtil.KEY_GUIDE_SIGN, true);
                fl_guide_sign.setVisibility(View.GONE);
                if (EPFragment != null) {
                    EPFragment.showBarcodeCheckin();
                }
                break;
            case R.id.iv_guide_sign_dynamic:
                SharedPreferencesUtil.putSettingBoolean(this, KeyUtil.KEY_GUIDE_SIGN_ONE, true);
                fl_guide_sign_dynamic.setVisibility(View.GONE);
                if(dynamicFragment!=null){
                    dynamicFragment.showPublishButton();
                }
                break;
        }
    }

    private void initPop() {
        mPopUpView=getLayoutInflater().inflate(R.layout.activity_new_comment_pop,null);
        tv_comment_dynamic=mPopUpView.findViewById(R.id.tv_comment);
        et_comment_dynamic=mPopUpView.findViewById(R.id.et_comment);
        mPopUpWindow=new PopupWindow(mPopUpView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopUpView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = mPopUpView.getMeasuredHeight();
        mPopUpWindow.setHeight(popupHeight+10);
        mPopUpWindow.setTouchable(true);
        mPopUpWindow.setOutsideTouchable(true);
        mPopUpWindow.setFocusable(true);
        mPopUpWindow.setOutsideTouchable(true);
        mPopUpWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dynamicFragment!=null){
                    dynamicFragment.showPublishButton();
                }
            }
        });
    }

    public void showCommentLayout() {
        mPopUpWindow.showAtLocation(getWindow().getDecorView(),Gravity.BOTTOM,0,0);
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
        et_comment_dynamic.setFocusable(true);
        et_comment_dynamic.requestFocus();
        et_comment_dynamic.setHint(getString(R.string.reply1)+nickName);
        InputMethodUtil.showOrHide(getApplication());
        mPopUpWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopUpWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        tv_comment_dynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment();

            }
        });
        if (dynamicFragment!=null){
            dynamicFragment.hidePublishButton();
        }

    }

    private void sendComment() {
        String comment=et_comment_dynamic.getText().toString().trim();
        if (TextUtils.isEmpty(comment)){
            TosUtil.show(getString(R.string.send_message_not_null));
        }else {
            showLoading();
            OkHttpUtil.Post(this)
                    .url(Constants.NEW_URL + Constants.ORGANIZER_REPLY_COMMENT)
                    .addParams("eventId", String.valueOf(event_id))
                    .addParams("commentId", String.valueOf(comment_id))
                    .addParams("commentText", comment)
                    .build()
                    .execute(new Callback<String>() {
                        @Override
                        public String parseNetworkResponse(Response response, int id) throws Exception {
                            return response.body().string();
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            hideLoading();
                            TosUtil.show(getString(R.string.send_error));
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            hideLoading();
                            if (response.contains("\"retStatus\":200")) {
                                EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                                InputMethodUtil.close(getApplication(),getWindow().getDecorView());
                                dynamicFragment.showPublishButton();
                            } else {
                                TosUtil.show(getString(R.string.send_error));
                            }
                        }
                    });
            mPopUpWindow.dismiss();
        }

}

    private void showOrHide() {
        InputMethodUtil.showOrHide(this);
    }


    private void showLoading() {
        if (lDialog == null) {
            lDialog = new LDialog.Builder(getFragmentManager())
                    .setLayoutId(R.layout.view_loading)
                    .setWidth(DxPxUtils.dip2px(this, 150))
                    .setHeight(DxPxUtils.dip2px(this, 150))
                    .build()
                    .show();
        } else {
            lDialog.show();
        }
    }

    private void hideLoading() {
        lDialog.dismiss();
    }

    private void cancelSelectFilter(int lastSelctIndex) {
        switch (lastSelctIndex) {
            case 0:
                selectFilterAll();
                break;
            case 1:
                selectFilterQuestion();
                break;
            case 2:
                selectFilterPublish();
                break;
            case 3:
                selectFilterRealy();
                break;
            case 5:
                selectFilterAnswer();
                break;
        }
    }

    private void selectFilterAll() {
        tvFilterAll.setTextColor(0xffff9000);
        tvFilterQuestion.setTextColor(0xff898989);
        tvFilterAnswer.setTextColor(0xff898989);
        tvFilterPublish.setTextColor(0xff898989);
        tvFilterRealy.setTextColor(0xff898989);

        tvFilterAll.setBackgroundResource(R.drawable.dynamic_filter_select);
        tvFilterQuestion.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterAnswer.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterPublish.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterRealy.setBackgroundResource(R.drawable.dynamic_filter_unselect);

//        Drawable drawable = getResources().getDrawable(R.drawable.icon_gou);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.icon_gou);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvFilterAll.setCompoundDrawables(null, null, drawable, null);
        tvFilterQuestion.setCompoundDrawables(null, null, null, null);
        tvFilterAnswer.setCompoundDrawables(null, null, null, null);
        tvFilterPublish.setCompoundDrawables(null, null, null, null);
        tvFilterRealy.setCompoundDrawables(null, null, null, null);

    }

    private void selectFilterQuestion() {
        tvFilterAll.setTextColor(0xff898989);
        tvFilterQuestion.setTextColor(0xffff9000);
        tvFilterAnswer.setTextColor(0xff898989);
        tvFilterPublish.setTextColor(0xff898989);
        tvFilterRealy.setTextColor(0xff898989);

        tvFilterAll.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterQuestion.setBackgroundResource(R.drawable.dynamic_filter_select);
        tvFilterAnswer.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterPublish.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterRealy.setBackgroundResource(R.drawable.dynamic_filter_unselect);

//        Drawable drawable = getResources().getDrawable(R.drawable.icon_gou);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.icon_gou);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvFilterAll.setCompoundDrawables(null, null, null, null);
        tvFilterQuestion.setCompoundDrawables(null, null, drawable, null);
        tvFilterAnswer.setCompoundDrawables(null, null, null, null);
        tvFilterPublish.setCompoundDrawables(null, null, null, null);
        tvFilterRealy.setCompoundDrawables(null, null, null, null);
    }

    private void selectFilterPublish() {
        tvFilterAll.setTextColor(0xff898989);
        tvFilterQuestion.setTextColor(0xff898989);
        tvFilterAnswer.setTextColor(0xff898989);
        tvFilterPublish.setTextColor(0xffff9000);
        tvFilterRealy.setTextColor(0xff898989);

        tvFilterAll.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterPublish.setBackgroundResource(R.drawable.dynamic_filter_select);
        tvFilterAnswer.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterRealy.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterQuestion.setBackgroundResource(R.drawable.dynamic_filter_unselect);

//        Drawable drawable = getResources().getDrawable(R.drawable.icon_gou);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.icon_gou);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvFilterAll.setCompoundDrawables(null, null, null, null);
        tvFilterQuestion.setCompoundDrawables(null, null, null, null);
        tvFilterAnswer.setCompoundDrawables(null, null, null, null);
        tvFilterPublish.setCompoundDrawables(null, null, drawable, null);
        tvFilterRealy.setCompoundDrawables(null, null, null, null);
    }

    private void selectFilterRealy() {
        tvFilterAll.setTextColor(0xff898989);
        tvFilterQuestion.setTextColor(0xff898989);
        tvFilterAnswer.setTextColor(0xff898989);
        tvFilterPublish.setTextColor(0xff898989);
        tvFilterRealy.setTextColor(0xffff9000);

        tvFilterAll.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterRealy.setBackgroundResource(R.drawable.dynamic_filter_select);
        tvFilterAnswer.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterPublish.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterQuestion.setBackgroundResource(R.drawable.dynamic_filter_unselect);

//        Drawable drawable = getResources().getDrawable(R.drawable.icon_gou);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.icon_gou);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvFilterAll.setCompoundDrawables(null, null, null, null);
        tvFilterQuestion.setCompoundDrawables(null, null, null, null);
        tvFilterAnswer.setCompoundDrawables(null, null, null, null);
        tvFilterPublish.setCompoundDrawables(null, null, null, null);
        tvFilterRealy.setCompoundDrawables(null, null, drawable, null);
    }

    private void selectFilterAnswer() {
        tvFilterAll.setTextColor(0xff898989);
        tvFilterQuestion.setTextColor(0xff898989);
        tvFilterAnswer.setTextColor(0xffff9000);
        tvFilterPublish.setTextColor(0xff898989);
        tvFilterRealy.setTextColor(0xff898989);

        tvFilterAll.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterAnswer.setBackgroundResource(R.drawable.dynamic_filter_select);
        tvFilterQuestion.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterPublish.setBackgroundResource(R.drawable.dynamic_filter_unselect);
        tvFilterRealy.setBackgroundResource(R.drawable.dynamic_filter_unselect);

//        Drawable drawable = getResources().getDrawable(R.drawable.icon_gou);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.icon_gou);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvFilterAll.setCompoundDrawables(null, null, null, null);
        tvFilterQuestion.setCompoundDrawables(null, null, null, null);
        tvFilterAnswer.setCompoundDrawables(null, null, drawable, null);
        tvFilterPublish.setCompoundDrawables(null, null, null, null);
        tvFilterRealy.setCompoundDrawables(null, null, null, null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //    /**
//     * 连续按两次返回键退出
//     *
//     * @param keyCode
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

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
         //必不可少，否则所有的组件都不会有TouchEvent了
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

            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom ) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if(event.getAction() == KeyEvent.ACTION_DOWN){
                exit();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void exit() {

        if(ll_comment.getVisibility() == View.VISIBLE){
            ll_comment.setVisibility(View.GONE);
            dynamicFragment.showPublishButton();
            return;
        }

        if (drawerLayout!=null&&drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        if (drawerLayout!=null&&drawerLayout.isDrawerOpen(Gravity.RIGHT)){
            drawerLayout.closeDrawer(Gravity.RIGHT);
            return;
        }
        if (dynamicFragment != null && dynamicFragment.isPublishLayoutShow()) {
            dynamicFragment.closePublishDynamicLaout();
            return;
        }
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(getApplicationContext(), R.string.exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
//            LogUtil.d("System.currentTimeMillis()");
        } else {
//            AppManager.getAppManager().AppExit(this);
//            System.exit(0);
//            LogUtil.d("AppManager");
            AppManager.getAppManager().finishActivity(this);
            SharedPreferencesUtil.put(getApplication(),"select_event_id","");
    }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        parseIntent();
    }
    private boolean parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            IMMessage message = (IMMessage) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            intent.removeExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            switch (message.getSessionType()) {
                case P2P:
                    NimUIKit.startP2PSession(this, message.getSessionId());
//                    SessionHelper.startP2PSession(this, message.getSessionId());
                    break;
            }
            return true;
        }
        return false;
    }

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
}
