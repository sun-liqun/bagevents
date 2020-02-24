package com.bagevent.new_home.new_activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.detail.ActivityOrderDetail;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.db.ChatMessage;
//import com.bagevent.db.ChatMessage_Table;
import com.bagevent.db.ChatMessage_Table;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.adapter.ChatSysMessageAdapter;
import com.bagevent.new_home.data.ChatMessageData;
import com.bagevent.new_home.new_interface.new_view.ChatMessageView;
import com.bagevent.new_home.new_interface.new_view.UpFetchChatMessageView;
import com.bagevent.new_home.new_interface.presenter.ChatMessagePresenter;
import com.bagevent.new_home.new_interface.presenter.UpFetchChatMessagePresenter;
import com.bagevent.util.AppManager;
//import com.bagevent.util.LocalBroadcastManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TimeUtil;
import com.bagevent.util.dbutil.SyncChatMessageUtil;
import com.bagevent.view.ScrollSpeedLinearLayoutManger;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by ZWJ on 2017/12/4 0004.
 */

public class ChattingSysActivity extends BaseActivity implements ChatMessageView,BaseQuickAdapter.UpFetchListener,UpFetchChatMessageView,BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_no_record)
    TextView tv_no_record;
    @BindView(R.id.rv_sys_chat)
    RecyclerView rvSysChat;

    private ChatMessagePresenter chatMessagePresenter;
    private UpFetchChatMessagePresenter upFetchChatMessagePresenter;
    private String userId;
    private String token;
    private int contactId;
    private int limit = 20;
    private int offset = 0;
    private String sendTime;
    private static int MESSAGE_LIMIT_COUNT = 4;//当加载的消息超过8条，更改recyclerview加载方式
    private ChatSysMessageAdapter sysMessageAdapter;
    private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
//    private SysChatReceiver sysChatReceiver;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_chat_sys);
        ButterKnife.bind(this);
        initView();
        getIntentValue();
//        registerChattingReceiver();
        initData();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(sysChatReceiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals(Common.SYNC_CHAT_MESSAGE)) {
            setData();
        } else if (event.mMsg.equals(Common.UPFETCH_MESSAGE)) {
            offset = offset + limit;
            List<ChatMessage> messages = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId))).and(ChatMessage_Table.contactId.is(contactId)).orderBy(ChatMessage_Table.sendTime, false).offset(offset).limit(limit).queryList();
            Collections.reverse(messages);
            sysMessageAdapter.addData(0, messages);
        }
    }

//    private void registerChattingReceiver() {
//        sysChatReceiver = new SysChatReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Common.CHATTING_MESSAGE_RECEIVED_ACTION);
//        LocalBroadcastManager.getInstance(this).registerReceiver(sysChatReceiver,filter);
//    }

    public class SysChatReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Common.CHATTING_MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                int notificationId = intent.getIntExtra("notificationId",-1);
                String extras = intent.getStringExtra("extras");
                try {
                    JSONObject extrasJson = new JSONObject(extras);
                    String contactIds = extrasJson.optString("contactId");
                    String eventId = extrasJson.optString("eventId");
                    String sendTime = extrasJson.optString("sendTime");
                    String chatId = extrasJson.optString("chatId");
                    if(Integer.parseInt(contactIds) == contactId) {//同一个会话直接展示接收到的消息
                     //   Log.e("fdsfs",contactIds);
                        insertData(Long.parseLong(userId),eventId,Integer.parseInt(contactIds),chatId,TimeUtil.timeStmp(sendTime),extras);
                        ChatMessage message1 = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                                .and(ChatMessage_Table.contactId.is(Integer.parseInt(contactIds)))
                                .and(ChatMessage_Table.sendTime.is(TimeUtil.timeStmp(sendTime)))
                                .querySingle();
                        if(message1 != null) {
                            sysMessageAdapter.addData(sysMessageAdapter.getData().size(),message1);
                            rvSysChat.smoothScrollToPosition(sysMessageAdapter.getData().size());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JPushInterface.clearNotificationById(context,notificationId);
            }
        }
    }

    private void insertData(Long userId,String eventId,int contactId,String chatId,Long sendTime,String content) {
        SQLite.insert(ChatMessage.class).columns(ChatMessage_Table.userId,ChatMessage_Table.eventId,ChatMessage_Table.contactId,ChatMessage_Table.chatId,ChatMessage_Table.sendTime,ChatMessage_Table.content)
                .values(userId,eventId,contactId,Integer.parseInt(chatId),sendTime,content)
                .execute();
    }

    @OnClick(R.id.ll_title_back)
    public void onViewClicked() {
        if(AppManager.getAppManager().activityStackCount() > 1) {
            AppManager.getAppManager().finishActivity();
        }else {
            Intent intent = new Intent(this,HomePage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void setData() {
        List<ChatMessage> messages = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                .and(ChatMessage_Table.contactId.is(contactId))
                .orderBy(ChatMessage_Table.sendTime, false).offset(0).limit(20).queryList();
        chatMessages.addAll(messages);

        Collections.reverse(chatMessages);
        if (chatMessages.size() > MESSAGE_LIMIT_COUNT) {
            ScrollSpeedLinearLayoutManger layoutManager = new ScrollSpeedLinearLayoutManger(this);
            layoutManager.setSpeedSlow();
            layoutManager.setStackFromEnd(true);
            rvSysChat.setLayoutManager(layoutManager);
        } else {
            rvSysChat.setLayoutManager(new ScrollSpeedLinearLayoutManger(this));
        }
        if (sysMessageAdapter == null) {
            initAdapter();
        }
    }

    private void initData() {
        if (NetUtil.isConnected(this)) {
            chatMessagePresenter = new ChatMessagePresenter(this);
            chatMessagePresenter.chatMessage();
        }
    }

    private void initAdapter() {
        if (chatMessages.size() > 0) {
            sysMessageAdapter = new ChatSysMessageAdapter(chatMessages);
            sysMessageAdapter.openLoadAnimation();
            sysMessageAdapter.setOnItemClickListener(this);
            rvSysChat.setAdapter(sysMessageAdapter);
            sysMessageAdapter.setUpFetchEnable(true);
            sysMessageAdapter.setStartUpFetchPosition(1);
            sysMessageAdapter.setUpFetchListener(this);
        }else {
            rvSysChat.setVisibility(View.GONE);
            tv_no_record.setVisibility(View.VISIBLE);
        }
    }

    private void getIntentValue() {
        userId = SharedPreferencesUtil.get(this, "userId", "");
        token = getIntent().getStringExtra("token");
        contactId = getIntent().getIntExtra("contactId", -1);
    }

    private void initView() {
        tvTitle.setText(R.string.system_message);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
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
    public String sendToken() {
        return token;
    }

    @Override
    public int eventId() {
        return 0;
    }

    @Override
    public String sendTime() {
        return sendTime;
    }

    @Override
    public void upFetchChatMessageSuccess(ChatMessageData response) {
        sysMessageAdapter.setUpFetching(false);
        SyncChatMessageUtil util = new SyncChatMessageUtil(this, response, contactId, true);
        util.startSyncChatListMessage();
    }

    @Override
    public void upFetchChatMessageFailed(String errInfo) {

    }

    @Override
    public int page() {
        return 1;
    }

    @Override
    public void showChatMessageSuccess(ChatMessageData response) {
        SyncChatMessageUtil util = new SyncChatMessageUtil(this, response, contactId, false);
        util.startSyncChatListMessage();
    }

    @Override
    public void showChatMessageFailed(String errInfo) {

    }

    @Override
    public void onUpFetch() {
        sysMessageAdapter.setUpFetching(true);
        if (NetUtil.isConnected(this)) {
            sendTime = TimeUtil.timedate(sysMessageAdapter.getData().get(0).sendTime);
            upFetchChatMessagePresenter = new UpFetchChatMessagePresenter(this);
            upFetchChatMessagePresenter.upFetchMessage();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String jsonOrder = chatMessages.get(position).content;
        try {
            JSONObject object = new JSONObject(jsonOrder);
            String eventId = object.getString("eventId");
            String orderId = object.getString("orderId");
            String orderNumber = object.getString("orderNumber");
            Intent intent = new Intent(this, ActivityOrderDetail.class);
            intent.putExtra("position",position);
            intent.putExtra("eventId",Integer.parseInt(eventId));
            intent.putExtra("orderId",Integer.parseInt(orderId));
            intent.putExtra("orderNumber",orderNumber);
            intent.putExtra("enterStatus",3);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(AppManager.getAppManager().activityStackCount() > 1) {
                AppManager.getAppManager().finishActivity();
            }else {
                Intent intent = new Intent(this,HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
