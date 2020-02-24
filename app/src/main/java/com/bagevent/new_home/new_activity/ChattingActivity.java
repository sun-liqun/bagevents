package com.bagevent.new_home.new_activity;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.detail.AttendPeopleDetailInfo;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetFormTypePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ReSendTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.SingleAttendeePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetFormTypeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetTicketInfoView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ReSendTicketFromAttendeeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SingleAttendeeFromIdView;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.Chat;
import com.bagevent.db.ChatMessage;
//import com.bagevent.db.ChatMessage_Table;
//import com.bagevent.db.Chat_Table;
import com.bagevent.db.ChatMessage_Table;
import com.bagevent.db.Chat_Table;
import com.bagevent.db.EventList;
//import com.bagevent.db.EventList_Table;
import com.bagevent.db.EventList_Table;
import com.bagevent.db.EventTicket;
//import com.bagevent.db.EventTicket_Table;
import com.bagevent.db.EventTicket_Table;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.adapter.ChatMessageAdapter;
import com.bagevent.new_home.data.ChatMessageData;
import com.bagevent.new_home.data.PostChatMessageData;
import com.bagevent.new_home.data.YunXinToken;
import com.bagevent.new_home.new_interface.new_view.ChatMessageView;
import com.bagevent.new_home.new_interface.new_view.PostMessageView;
import com.bagevent.new_home.new_interface.new_view.UpFetchChatMessageView;
import com.bagevent.new_home.new_interface.new_view.UpdateChatTimeView;
import com.bagevent.new_home.new_interface.presenter.ChatMessagePresenter;
import com.bagevent.new_home.new_interface.presenter.PostMessagePresenter;
import com.bagevent.new_home.new_interface.presenter.UpFetchChatMessagePresenter;
import com.bagevent.new_home.new_interface.presenter.UpdateChatTimePresenter;
import com.bagevent.util.AppManager;
//import com.bagevent.util.LocalBroadcastManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TimeUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.util.dbutil.SyncChatMessageUtil;
import com.bagevent.util.dbutil.SyncSingleAttendeeUtil;
import com.bagevent.util.dbutil.SyncTicketUtil;
import com.bagevent.view.ScrollSpeedLinearLayoutManger;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.MessageReceipt;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import name.gudong.loading.LoadingView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ZWJ on 2017/11/23 0023.
 */

public class ChattingActivity extends BaseActivity implements ChatMessageView, BaseQuickAdapter.UpFetchListener, UpFetchChatMessageView,
        PostMessageView, BaseQuickAdapter.OnItemChildClickListener, UpdateChatTimeView, ReSendTicketFromAttendeeView, SingleAttendeeFromIdView, GetTicketInfoView, GetFormTypeView {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_chat)
    RecyclerView rvChat;
    @BindView(R.id.et_chat_content)
    EditText etChatContent;
    @BindView(R.id.loading)
    LoadingView loading;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.tv_no_record)
    TextView tvNoRecord;
    @BindView(R.id.tv_current_event_status)
    TextView tvCurrentEventStatus;
    @BindView(R.id.tv_current_event_name)
    TextView tvCurrentEventName;
    @BindView(R.id.rl_current_event)
    AutoRelativeLayout rlCurrentEvent;
    private String sendToken;
    private String eventId;
    private String userId;
    private int peopleId;
    private int position;
    private int contactId;
    private int page;
    private String chatName;
    private String avatar;
    private String sendTime;
    private int limit = 20;
    private int offset = 0;
    private static int MESSAGE_LIMIT_COUNT = 12;//当加载的消息超过8条，更改recyclerview加载方式
    private ChatMessageAdapter messageAdapter;
    private ChatMessagePresenter chatMessagePresenter;
    private UpFetchChatMessagePresenter upFetchMessagePresenter;
    private PostMessagePresenter postMessagePresenter;
    private ReSendTicketPresenter reSendTicketPresenter;
    private UpdateChatTimePresenter updateChatTimePresenter;
    private SingleAttendeePresenter singleAttendeePresenter;
    private GetTicketPresenter getTicketPresenter;
    private GetFormTypePresenter getFormTypePresenter;
    private List<ChatMessage> messageList = new ArrayList<ChatMessage>();

    private int attendeeId;
    private String ticketAttendeeId;
    private String receiverToken;
    private String content;
    private long orgSendTime;
    private String orgSenderToken;
    private String orgReceiverToken;
    private int chatId;
    private int orderId;
    private String upDateChatTime;
//    private ChattingReceiver chattingReceiver;
    private NormalAlertDialog sendTicketDialog;
    private boolean isResendTicket = false;

    private Attends attends;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_chatting_ui);
        ButterKnife.bind(this);
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
        getIntentValue();
        initView();
        isLoading();
        initData();
//        registerChattingReceiver();
    }

    Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    for (IMMessage message:messages){
                        String uuid = message.getUuid();//获取消息的uuid, 该域在生成消息时即会填上
                        String sessionId = message.getSessionId();//聊天对象id
                        MsgDirectionEnum direct = message.getDirect();//获取消息方向，out发，in收
                        long time = message.getTime();//获取消息时间
                        String data=TimeUtil.timedate(time);
                        LogUtil.e("云信收到的消息："+message.getContent()+"消息Id："+"+sessionId："+sessionId+"方向："+direct
                                +"时间："+data+"uuid："+uuid);
                    }
                }
            };

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
        updateChatTime();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(chattingReceiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals(Common.SYNC_CHAT_MESSAGE)) {
            setData();
        } else if (event.mMsg.equals(Common.UPFETCH_MESSAGE)) {
            offset = offset + limit;
            List<ChatMessage> messages = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                    .and(ChatMessage_Table.contactId.is(contactId)).and(ChatMessage_Table.isSending.is(false)).orderBy(ChatMessage_Table.sendTime, false).offset(offset).limit(limit).queryList();
            //  Log.e("fdsa",messages.size()+" " + offset);
            Collections.reverse(messages);
            messageAdapter.addData(0, messages);
        } else if (event.mMsg.equals(Common.SINGLE_DATA)) {
            Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(Integer.parseInt(eventId))).and(Attends_Table.attendId.is(attendeeId)).querySingle();
            if (attends != null) {
                if (isResendTicket) {
                    eventId = attends.eventId + "";
                    attendeeId = attends.attendId;
                    orderId = attends.orderIds;
                    showResendTicketDialog(eventId, attendeeId, orderId);
                } else {
                    EventList eventList = new Select().from(EventList.class).where(EventList_Table.userId.is(Integer.parseInt(userId))).and(EventList_Table.eventId.is(attends.eventId)).querySingle();
                    Intent intent = new Intent(this, AttendPeopleDetailInfo.class);
                    intent.putExtra("eventId", attends.eventId);
                    intent.putExtra("attendId", attends.attendId);
                    intent.putExtra("ref_code", attends.refCodes);
                    intent.putExtra("remark", attends.notes);
                    intent.putExtra("detailType", 0);
                    intent.putExtra("orderId", attends.orderIds);
                    if (eventList != null) {
                        intent.putExtra("stType", eventList.sType);
                    }
                    startActivity(intent);
                }
            }
        }
    }

    @OnClick({R.id.ll_title_back, R.id.tv_send_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                if (AppManager.getAppManager().activityStackCount() > 1) {
                    AppManager.getAppManager().finishActivity();
                } else {
                    Intent intent = new Intent(this, HomePage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                break;
            case R.id.tv_send_message: //发送消息
//                if(messageAdapter != null) {
                if (NetUtil.isConnected(this)) {
                    ChatMessage message = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                            .and(ChatMessage_Table.contactId.is(contactId))
                            .orderBy(ChatMessage_Table.sendTime, false)
                            .querySingle();
                    if (message != null) {
                        if (!TextUtils.isEmpty(message.attendeeId)) {
                            attendeeId = Integer.parseInt(message.attendeeId);
                        }
                        chatId = message.chatId;
                        eventId = message.eventId;
                        orgSenderToken = message.senderToken;
                        orgReceiverToken = message.receiverToken;
                        if (message.org) {
                            receiverToken = message.receiverToken;
                        } else {
                            receiverToken = message.senderToken;
                        }
                    } else {
                        //Log.e("sendToken", sendToken);
                        receiverToken = sendToken;
                    }
                    Log.i("--------eventId",eventId+"==");
                    Log.i("--------attendeeId",attendeeId+"==");
                    orgSendTime = System.currentTimeMillis();
                    content = etChatContent.getText().toString().trim();
                    if (!TextUtils.isEmpty(content)) {
                        OkHttpUtil.Get(this)
                                .url(Constants.NEW_URL + Constants.YUNXIN_ACCID+attendeeId+Constants.YUNXIN_ACCID2)
                                .addParams("action","")
                                .addParams("eventId",eventId)
                                .build()
                                .execute(new Callback<String>() {
                                    @Override
                                    public String parseNetworkResponse(Response response, int id) throws Exception {
                                        return response.body().string();
                                    }

                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        LogUtil.e("云信获取参会者accid失败"+e.toString());
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        if (response.contains("\"retStatus\":200")) {
                                            YunXinToken yunXin=new Gson().fromJson(response,YunXinToken.class);
                                            String attendeeAccid =yunXin.getRespObject().getAccid();
                                            if (!TextUtils.isEmpty(attendeeAccid)){
                                                LogUtil.e("云信获取参会者accid成功"+attendeeAccid);
                                                SessionTypeEnum sessionType=SessionTypeEnum.P2P;
                                                IMMessage textMessage = MessageBuilder.createTextMessage(attendeeAccid, sessionType, content);
                                                NIMClient.getService(MsgService.class).sendMessage(textMessage, false).setCallback(new RequestCallback<Void>() {
                                                    @Override
                                                    public void onSuccess(Void param) {
                                                        LogUtil.e("云信发送聊天成功");
                                                    }

                                                    @Override
                                                    public void onFailed(int code) {
                                                        LogUtil.e("云信发送聊天失败"+code);
                                                    }

                                                    @Override
                                                    public void onException(Throwable exception) {
                                                        LogUtil.e(exception.toString());
                                                    }
                                                });
                                            }
                                        } else {
                                            LogUtil.e("云信获取参会者accid失败");
                                        }
                                    }
                                });
                        insertData(Long.parseLong(userId), eventId, attendeeId + "", true, contactId, chatId + "", orgSendTime, true, content, orgSenderToken, orgReceiverToken);
                        ChatMessage message1 = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                                .and(ChatMessage_Table.contactId.is(contactId))
                                .and(ChatMessage_Table.sendTime.is(orgSendTime))
                                .querySingle();
                        if (message1 != null) {
                            tvNoRecord.setVisibility(View.GONE);
                            rvChat.setVisibility(View.VISIBLE);
                            messageAdapter.addData(messageAdapter.getData().size(), message1);
                            etChatContent.setText("");
                            rvChat.smoothScrollToPosition(messageAdapter.getData().size());
                            postMessagePresenter = new PostMessagePresenter(this);
                            postMessagePresenter.postMessage();
                        }
                    } else {
                        TosUtil.show(getString(R.string.is_not_null));
                    }
                } else {
                    TosUtil.show(getString(R.string.check_your_net));
                }
//                }

                break;
        }
    }

    private void getAccid(String response) {

    }

    private void updateChatTime() {
        upDateChatTime = TimeUtil.timedate(System.currentTimeMillis());
        if (NetUtil.isConnected(this)) {
            updateChatTimePresenter = new UpdateChatTimePresenter(this);
            updateChatTimePresenter.updateChatTime();
        }
    }

    private void updateData(PostChatMessageData.RespObjectBean res) {
        SQLite.update(ChatMessage.class)
                .set(ChatMessage_Table.chatId.is(res.getChatId()), ChatMessage_Table.status.is(res.getStatus()), ChatMessage_Table.senderToken.is(res.getSenderToken()),
                        ChatMessage_Table.receiverToken.is(res.getReceiverToken()), ChatMessage_Table.sendSeconds.is(res.getSendSeconds()),
                        ChatMessage_Table.sendDay.is(res.getSendDay()), ChatMessage_Table.todaySend.is(res.isTodaySend()), ChatMessage_Table.isSending.is(false))
                .where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                .and(ChatMessage_Table.contactId.is(contactId))
                .and(ChatMessage_Table.sendTime.is(orgSendTime))
                .execute();

        SQLite.update(Chat.class).set(Chat_Table.updateTime.is(orgSendTime), Chat_Table.lastMessage.is(content), Chat_Table.unReadCount.is(0))
                .where(Chat_Table.userId.is(Long.parseLong(userId)))
                .and(Chat_Table.contactId.is(contactId))
                .execute();

        ChatMessage ms = messageAdapter.getData().get(messageAdapter.getData().size() - 1);
        if (ms != null) {
            ms.isSending = false;
            messageAdapter.notifyItemChanged(messageAdapter.getData().size() - 1);
        }
        EventBus.getDefault().postSticky(new MsgEvent(position, peopleId, Common.POST_MESSAGE));
    }

    private void insertData(Long userId, String eventId, String attendeeId, boolean isOrg, int contactId, String chatId, long sendTime, boolean isSending, String content, String senderToken, String receiverToken) {
        SQLite.insert(ChatMessage.class).columns(ChatMessage_Table.userId, ChatMessage_Table.eventId, ChatMessage_Table.attendeeId, ChatMessage_Table.org, ChatMessage_Table.contactId, ChatMessage_Table.chatId, ChatMessage_Table.sendTime, ChatMessage_Table.isSending, ChatMessage_Table.content, ChatMessage_Table.senderToken, ChatMessage_Table.receiverToken)
                .values(userId, eventId, attendeeId, isOrg, contactId, chatId, sendTime, isSending, content, senderToken, receiverToken)
                .execute();
    }

    private void setData() {
        loadingFinished();
        offset = 0;
        List<ChatMessage> messages = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                .and(ChatMessage_Table.contactId.is(contactId))
                .and(ChatMessage_Table.isSending.is(false))
                .orderBy(ChatMessage_Table.sendTime, false).offset(offset).limit(limit).queryList();
        messageList.addAll(messages);

        Collections.reverse(messageList);
        if (messageList.size() > MESSAGE_LIMIT_COUNT || messageList.size() > 16) {
            ScrollSpeedLinearLayoutManger layoutManager = new ScrollSpeedLinearLayoutManger(this);
            layoutManager.setSpeedSlow();
            layoutManager.setStackFromEnd(true);
            rvChat.setLayoutManager(layoutManager);
        } else {
            rvChat.setLayoutManager(new ScrollSpeedLinearLayoutManger(this));
        }
        if (messageAdapter == null) {
            initAdapter();
        }

        if(messages == null || messages.size() == 0){
            rlCurrentEvent.setVisibility(View.GONE);
            return;
        }

        ChatMessage lastMessage = messages.get(0);
        EventList eventList = new Select().from(EventList.class).where(EventList_Table.eventId.is(Integer.parseInt(lastMessage.eventId))).querySingle();
        if(eventList == null){
            rlCurrentEvent.setVisibility(View.GONE);
            return;
        }
        tvCurrentEventName.setText(eventList.eventName);

        if(!TextUtils.isEmpty(lastMessage.attendeeId)){
            attends = new Select().from(Attends.class).where(Attends_Table.attendId.is(Integer.parseInt(lastMessage.attendeeId))).querySingle();
        }else {
            tvCurrentEventStatus.setVisibility(View.GONE);
            return;
        }
        //Attends attends = new Select().from(Attends.class).where(Attends_Table.attendId.is(Integer.parseInt(lastMessage.attendeeId))).querySingle();

        if(attends == null){
            tvCurrentEventStatus.setVisibility(View.GONE);
            return;
        }

        setCurrentEventStatus(attends.payStatuss);
    }

    private void setCurrentEventStatus(int status){
        switch (status) {
            case 0:
                tvCurrentEventStatus.setText(R.string.pay_wait1);
                break;
            case 1:
                tvCurrentEventStatus.setText(R.string.order_complete);
                break;
            case 2:
                tvCurrentEventStatus.setText(R.string.overtime);
                break;
            case 3:
                tvCurrentEventStatus.setText(R.string.pay_atypism);
                break;
            case 4:
                tvCurrentEventStatus.setText(R.string.pay_doing);
                break;
            case 5:
                tvCurrentEventStatus.setText(R.string.cancelPaid);
                break;
            case 6:
                tvCurrentEventStatus.setText(R.string.offline_order);
                break;
            case 10:
                tvCurrentEventStatus.setText(R.string.back_order_already);
                break;
            case 12:
                tvCurrentEventStatus.setText(R.string.order_wait);
                break;
            case 13:
                tvCurrentEventStatus.setText(R.string.audit_refuse);
                break;
            case 14:
                tvCurrentEventStatus.setText(R.string.success_wait_confirm);
                break;
            default:
                tvCurrentEventStatus.setText(R.string.unknown_state);
                break;

        }
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        rvChat.setVisibility(View.GONE);
    }

    private void loadingFinished() {
        llLoading.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        rvChat.setVisibility(View.VISIBLE);
    }

    private void initAdapter() {
        if (messageList.size() == 0) {
            tvNoRecord.setVisibility(View.VISIBLE);
            rvChat.setVisibility(View.GONE);
        } else {
            tvNoRecord.setVisibility(View.GONE);
            rvChat.setVisibility(View.VISIBLE);
        }
        messageAdapter = new ChatMessageAdapter(messageList, avatar);
        //  messageAdapter.openLoadAnimation();
        rvChat.setAdapter(messageAdapter);
        messageAdapter.setUpFetchEnable(true);
        messageAdapter.setStartUpFetchPosition(1);
        messageAdapter.setUpFetchListener(this);
        messageAdapter.setOnItemChildClickListener(this);
    }

    private void initData() {
        if (NetUtil.isConnected(this)) {
            //聊天消息
            chatMessagePresenter = new ChatMessagePresenter(this);
            chatMessagePresenter.chatMessage();
        } else {
            setData();
        }
    }

    private void showResendTicketDialog(String eventId, int attendeeId, int orderId) {
        sendTicketDialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black_light)
                .setContentText(getString(R.string.weather_ticket_again))
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        sendTicketDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        if (NetUtil.isConnected(ChattingActivity.this)) {
//                            Toast toast = Toast.makeText(ChattingActivity.this, "重发中...", Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
                            reSendTicketPresenter = new ReSendTicketPresenter(ChattingActivity.this);
                            reSendTicketPresenter.resendTicketFromAttendee();
                        } else {
                            Toast.makeText(ChattingActivity.this, R.string.check_network, Toast.LENGTH_SHORT).show();
                        }
                        sendTicketDialog.dismiss();
                    }
                })
                .build();
        sendTicketDialog.show();
    }


    private void getIntentValue() {
        Intent intent = getIntent();
        sendToken = intent.getStringExtra("sendToken");
        userId = SharedPreferencesUtil.get(this, "userId", "");
        peopleId = intent.getIntExtra("peopleId", -1);
        chatName = intent.getStringExtra("chatName");
        avatar = intent.getStringExtra("avatar");
        contactId = intent.getIntExtra("contactId", -1);
        position = intent.getIntExtra("position", -1);
        eventId = intent.getStringExtra("eventId");
        attendeeId = intent.getIntExtra("attendeeId", 0);
        page = 1;
    }


    private void initView() {
        tvTitle.setText(chatName);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
    }

//    private void registerChattingReceiver() {
//        chattingReceiver = new ChattingReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Common.CHATTING_MESSAGE_RECEIVED_ACTION);
//        LocalBroadcastManager.getInstance(this).registerReceiver(chattingReceiver, filter);
//    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (!TextUtils.isEmpty(messageList.get(position).attendeeId)) {
            if (messageList.get(position).content.contains(getString(R.string.e_ticket)) || messageList.get(position).content.contains(getString(R.string.admission_ticket)) || messageList.get(position).content.contains(getString(R.string.participants))) {
                if (NetUtil.isConnected(this)) {
                    isResendTicket = true;
                    eventId = messageList.get(position).eventId;
                    attendeeId = Integer.parseInt(messageList.get(position).attendeeId);
                    //Log.e("fds",attendeeId+"");
                    Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(Integer.parseInt(eventId))).and(Attends_Table.attendId.is(attendeeId)).querySingle();
                    if (attends != null) {
                        orderId = attends.orderIds;
                        showResendTicketDialog(eventId, attendeeId, orderId);
                    } else {
                        if (NetUtil.isConnected(this)) {
                            singleAttendeePresenter = new SingleAttendeePresenter(this);
                            singleAttendeePresenter.singleAttendeeFromId();
                        } else {
                            Toast.makeText(this, R.string.check_your_net, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, R.string.check_your_net, Toast.LENGTH_SHORT).show();
                }
            } else if (messageList.get(position).content.contains(getString(R.string.change))) {
                isResendTicket = false;
                eventId = messageList.get(position).eventId;
                attendeeId = Integer.parseInt(messageList.get(position).attendeeId);
                List<EventTicket> ticketCount = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(Integer.parseInt(eventId))).queryList();
                if (ticketCount.size() == 0) {
                    if (NetUtil.isConnected(this)) {
                        getTicketPresenter = new GetTicketPresenter(this);
                        getTicketPresenter.getTicket();
                    }
                }

                if (NetUtil.isConnected(this)) {
                    getFormTypePresenter = new GetFormTypePresenter(this);
                    getFormTypePresenter.getFormType();
                }
                Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(Integer.parseInt(eventId))).and(Attends_Table.attendId.is(attendeeId)).querySingle();
                if (attends != null) {
                    EventList eventList = new Select().from(EventList.class).where(EventList_Table.userId.is(Integer.parseInt(userId))).and(EventList_Table.eventId.is(attends.eventId)).querySingle();
                    Intent intent = new Intent(this, AttendPeopleDetailInfo.class);
                    intent.putExtra("eventId", attends.eventId);
                    intent.putExtra("attendId", attends.attendId);
                    intent.putExtra("ref_code", attends.refCodes);
                    intent.putExtra("remark", attends.notes);
                    intent.putExtra("detailType", 0);
                    intent.putExtra("orderId", attends.orderIds);
                    if (eventList != null) {
                        intent.putExtra("stType", eventList.sType);
                    }
                    startActivity(intent);
                } else {
                    if (NetUtil.isConnected(this)) {
                        singleAttendeePresenter = new SingleAttendeePresenter(this);
                        singleAttendeePresenter.singleAttendeeFromId();
                    } else {
                        Toast.makeText(this, R.string.check_your_net, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


//    public class ChattingReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (Common.CHATTING_MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
//                int notificationId = intent.getIntExtra("notificationId", -1);
//                String message = intent.getStringExtra("content");
//                String extras = intent.getStringExtra("extras");
//                try {
//                    JSONObject extrasJson = new JSONObject(extras);
//                    String contactIds = extrasJson.optString("contactId");
//                    String eventId = extrasJson.optString("eventId");
//                    String senderToken = extrasJson.optString("senderToken");
//                    String attendeeId = extrasJson.optString("attendeeId");
//                    String sendTime = extrasJson.optString("sendTime");
//                    String receiverToken = extrasJson.optString("receiverToken");
//                    String chatIds = extrasJson.optString("chatId");
//                    if (Integer.parseInt(contactIds) == contactId) {//同一个会话直接展示接收到的消息
//                        insertData(Long.parseLong(userId), eventId, attendeeId, false, Integer.parseInt(contactIds), chatIds, TimeUtil.timeStmp(sendTime), false, message, senderToken, receiverToken);
//                        ChatMessage message1 = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
//                                .and(ChatMessage_Table.contactId.is(Integer.parseInt(contactIds)))
//                                .and(ChatMessage_Table.sendTime.is(TimeUtil.timeStmp(sendTime)))
//                                .querySingle();
//                        if (message1 != null) {
//                            messageAdapter.addData(messageAdapter.getData().size(), message1);
//                            rvChat.smoothScrollToPosition(messageAdapter.getData().size());
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                JPushInterface.clearNotificationById(context, notificationId);
//            }
//        }
//    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showTicketInfo(TicketInfo ticketInfo) {
        SyncTicketUtil util = new SyncTicketUtil(this, Integer.parseInt(eventId), ticketInfo, false);
        util.syncTicket();
    }

    @Override
    public void showErrInfo(String errInfo) {

    }

    @Override
    public String detailEventId() {
        return eventId;
    }

    @Override
    public int sType() {
        return 0;
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

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public int contactId() {
        return contactId;
    }

    @Override
    public String upDateChatTime() {
        return upDateChatTime;
    }

    @Override
    public void showUpdateChatTimeSuccess() {

    }

    @Override
    public void showUpdateChatTimeFailed(String errInfo) {

    }

    @Override
    public String sendToken() {
        return sendToken;
    }

    @Override
    public int eventId() {
        if (!TextUtils.isEmpty(eventId)) {
            return Integer.parseInt(eventId);
        } else {
            return 0;
        }
    }

    @Override
    public String attendeeId() {
        return attendeeId + "";
    }

    @Override
    public void showSingleAttendeeFromId(String response) {
        SyncSingleAttendeeUtil util = new SyncSingleAttendeeUtil(this, Integer.parseInt(eventId), response, true);
        util.syncSingleAttendeeUtil();
    }

    @Override
    public void showSingleAttendeeFailedFromId(String errInfo) {

    }


    @Override
    public int orderId() {
        return orderId;
    }


    @Override
    public int sendToAttendee() {
        return 1;
    }

    @Override
    public void showSendSuccess(String response) {
        Toast toast = Toast.makeText(this, response, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showSendFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();

    }

    @Override
    public String postSendToken() {
        return null;
    }

    @Override
    public String receiverToken() {
        return receiverToken;
    }

    @Override
    public String content() {
        return content;
    }

    @Override
    public void postMessageSuccess(PostChatMessageData response) {
        updateData(response.getRespObject());
    }

    @Override
    public void postMessageFailed(String errInfo) {

    }

    @Override
    public String sendTime() {
        return sendTime;
    }

    @Override
    public void upFetchChatMessageSuccess(ChatMessageData response) {
        messageAdapter.setUpFetching(false);
        SyncChatMessageUtil util = new SyncChatMessageUtil(this, response, contactId, true);
        util.startSyncChatListMessage();
    }

    @Override
    public void upFetchChatMessageFailed(String errInfo) {
        messageAdapter.setUpFetching(false);
    }

    @Override
    public int page() {
        return page;
    }

    @Override
    public void showChatMessageSuccess(ChatMessageData response) {
        SyncChatMessageUtil util = new SyncChatMessageUtil(this, response, contactId, false);
        util.startSyncChatListMessage();
    }

    @Override
    public void showChatMessageFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
        setData();
    }

    @Override
    public void onUpFetch() {
        messageAdapter.setUpFetching(true);
        if (NetUtil.isConnected(this)) {
            sendTime = TimeUtil.timedate(messageAdapter.getData().get(0).sendTime);
            upFetchMessagePresenter = new UpFetchChatMessagePresenter(this);
            upFetchMessagePresenter.upFetchMessage();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if(AppManager.getAppManager().activityStackCount() <= 1){
                Intent intent = new Intent(this, HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            AppManager.getAppManager().finishActivity(this);
//            if (AppManager.getAppManager().activityStackCount() > 1) {
//                AppManager.getAppManager().finishActivity();
//            } else {
//                Intent intent = new Intent(this, HomePage.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
