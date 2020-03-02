package com.bagevent.new_home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.BaseFragment;
import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.db.Chat;
//import com.bagevent.db.Chat_Table;
import com.bagevent.db.Chat_Table;
import com.bagevent.new_home.adapter.ChatListAdapter;
import com.bagevent.new_home.data.ChatData;
import com.bagevent.new_home.data.FindUnreadData;
import com.bagevent.new_home.new_activity.ChatListSearch;
import com.bagevent.new_home.new_activity.ChattingActivity;
import com.bagevent.new_home.new_activity.ChattingSysActivity;
import com.bagevent.new_home.new_interface.new_view.ChatListView;
import com.bagevent.new_home.new_interface.new_view.FindExistUnReadView;
import com.bagevent.new_home.new_interface.new_view.RemoveChatView;
import com.bagevent.new_home.new_interface.new_view.SingleChatListView;
import com.bagevent.new_home.new_interface.presenter.ChatListPresenter;
import com.bagevent.new_home.new_interface.presenter.FindExistUnReadPresenter;
import com.bagevent.new_home.new_interface.presenter.RemoveChatPresenter;
import com.bagevent.new_home.new_interface.presenter.SingleChatListPresenter;
import com.bagevent.new_home.new_interface.presenter.UpdateChatTimePresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.LocalBroadcastManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TimeUtil;
import com.bagevent.util.dbutil.SyncChatListUtil;
import com.bumptech.glide.Glide;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by ZWJ on 2017/11/21 0021.
 */

public class MessageFragment extends BaseFragment implements ChatListView, SingleChatListView, ChatListAdapter.OnTopAndRemoveListener, SwipeRefreshLayout.OnRefreshListener,
        ChatListAdapter.OnItemClickListener, FindExistUnReadView, RemoveChatView {
    Unbinder unbinder;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.sfl_message)
    SwipeRefreshLayout sflMessage;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.iv_page_status)
    ImageView ivPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;

    private ChatListAdapter messageListAdapter;
    private ChatListPresenter listPresenter;
    private SingleChatListPresenter singleChatList;
    private UpdateChatTimePresenter chatTimePresenter;
    private FindExistUnReadPresenter findExistUnReadPresenter;
    private RemoveChatPresenter removeChatPresenter;
    private String userId;
    private List<Chat> chatList = new ArrayList<Chat>();
    private boolean isSwipe = false;
    private int contactId;
    private String upDateChatTime;
    public static final String HOME_MESSAGE_RECEIVED_ACTION = "com.bagevent.MESSAGE_RECEIVED";
    private ChatMessageReciver chatMessageReciver;
    private Map<String, Integer> pushValue = new HashMap<String, Integer>();
    private int num = 1;//接收到的未读消息的key值变量
    private int removePosition;
    private int removeContactId;

    // data
    private List<RecentContact> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isLoading();
        initData();
        registerReceiver();
//        NIMClient.getService(MsgServiceObserve .class)
//                .observeReceiveMessage(incomingMessageObserver, true);
    }


//    Observer<List<IMMessage>> incomingMessageObserver =
//            new Observer<List<IMMessage>>() {
//                @Override
//                public void onEvent(List<IMMessage> messages) {
//                    for (IMMessage message:messages){
//                        String uuid = message.getUuid();//获取消息的uuid, 该域在生成消息时即会填上
//                        String sessionId = message.getSessionId();//聊天对象id
//                        MsgDirectionEnum direct = message.getDirect();//获取消息方向，
//                        long time = message.getTime();
//                        String data=TimeUtil.timedate(time);//获取消息时间
//                        LogUtil.e("云信收到的消息："+message.getContent()+"消息Id："+"+sessionId"+sessionId+"方向："+direct
//                                +"时间："+data+"uuid："+uuid);
//                    }
//                }
//            };

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        getExistUnReadMsg();
        getNewMsg();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
//        NIMClient.getService(MsgServiceObserve.class)
//                .observeReceiveMessage(incomingMessageObserver, false);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(chatMessageReciver);
    }


    private void getNewMsg() {
        if (messageListAdapter != null) {
            if (!pushValue.isEmpty()) {
                final List<Chat> isTopCount = new Select().from(Chat.class).where(Chat_Table.userId.is(Long.parseLong(userId))).and(Chat_Table.isTop.is(true)).queryList();
                Set<Map.Entry<String, Integer>> entries = pushValue.entrySet();
                for (Map.Entry<String, Integer> entry : entries) {
                    if (NetUtil.isConnected(getActivity())) {
                        contactId = entry.getValue();
                        Chat singleChat = new Select().from(Chat.class).where(Chat_Table.userId.is(Long.parseLong(userId))).and(Chat_Table.contactId.is(contactId)).and(Chat_Table.isRemove.is(true)).querySingle();
                        if (singleChat != null) {
                            //Log.e("singleChat",singleChat.unReadCount+"");
                            for (int i = 0; i < chatList.size(); i++) {
                                if (contactId == chatList.get(i).contactId) {
                                    if (chatList.get(i).sys) {
                                        chatList.remove(0);
                                        messageListAdapter.notifyItemRemoved(1);
                                    } else {
                                        chatList.remove(i);
                                        messageListAdapter.notifyItemRemoved(i);
                                    }
                                }
                            }
                            if (singleChat.sys) {
                                if (AppManager.getAppManager().currentActivity() instanceof ChattingSysActivity) {
                                    messageListAdapter.setUnReadView(false);
                                } else {
                                    messageListAdapter.setUnReadView(true);
                                }
                                messageListAdapter.addData(0, singleChat);
                            } else {
                                messageListAdapter.addData(isTopCount.size() + 1, singleChat);
                            }
                        }
                    }
                    pushValue.remove(entry.getKey());
                }
            }
        }
    }


    @OnClick({R.id.ll_search, R.id.ll_page_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                Intent intent = new Intent(getActivity(), ChatListSearch.class);
                startActivity(intent);
                break;
            case R.id.ll_page_status:
                isLoading();
                initData();
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals(Common.HOME_SYNC_SUCCESS)) {
            chatList.clear();
            getChatList();
            sflMessage.setRefreshing(false);
        } else if (event.mMsg.equals(Common.POST_MESSAGE)) {
            Chat c = new Select().from(Chat.class).where(Chat_Table.userId.is(Long.parseLong(userId))).and(Chat_Table.peopleId.is(event.pageNumber)).querySingle();
            if (c != null && event.pageCount != -1) {
                Chat a = chatList.get(event.pageCount);
                a.lastMessage = c.lastMessage;
                a.updateTime = c.updateTime;
                a.unReadCount = c.unReadCount;
                messageListAdapter.notifyDataSetChanged();
            }
        } else if (event.mMsg.equals(Common.CURRENT_CHAT_LIST)) {
            getNewMsg();
        }
    }


    @Override
    public void onRefresh() {
        if (sflMessage.isRefreshing()) {
            isSwipe = true;
            sflMessage.setRefreshing(true);
            initData();
        }
    }

    public class ChatMessageReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Common.CHATTING_MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                int notificationId = intent.getIntExtra("notificationId", -1);
                String extras = intent.getStringExtra("extras");
                try {
                    JSONObject extrasJson = new JSONObject(extras);
                    String contactIds = extrasJson.getString("contactId");
                    //Chat tempChat = new Select().from(Chat.class).where(Chat_Table.userId.is(Long.parseLong(userId))).and(Chat_Table.sys.is(true)).and(Chat_Table.contactId.is(Integer.parseInt(contactIds))).querySingle();
                    getSingleChat(contactIds);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JPushInterface.clearNotificationById(context, notificationId);
            }
        }
    }

    private void getSingleChat(String keyContactId) {
        contactId = Integer.parseInt(keyContactId);
        singleChatList = new SingleChatListPresenter(this);
        singleChatList.singleChatList();
    }

    private void getChatList() {
        loadFinished();
        List<Chat> chat = new Select().from(Chat.class)
                .where(Chat_Table.isRemove.is(true))
                .and(Chat_Table.userId.is(Long.parseLong(userId)))
                .orderBy(Chat_Table.sys, false)
                .orderBy(Chat_Table.isTop, false)
                .orderBy(Chat_Table.updateTime, false)
                .queryList();
        for (int i = 0; i < chat.size(); i++) {
            Chat tempChat = chat.get(i);
            if (tempChat != null) {
//                List<ChatMessage> msg = new Select().from(ChatMessage.class).where(ChatMessage_Table.contactId.is(tempChat.contactId)).and(ChatMessage_Table.userId.is(Long.parseLong(userId))).queryList();
//                if(msg.size() > 0) {
                chatList.add(tempChat);
//                }
            }
        }
        if (messageListAdapter == null) {
            initAdapter();
        } else {
            messageListAdapter.setNewData(chatList);
        }
    }

    private void registerReceiver() {
        chatMessageReciver = new ChatMessageReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Common.CHATTING_MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(chatMessageReciver, filter);
    }

    private void noRecord() {
        if (chatList.size() > 0) {
            llPageStatus.setVisibility(View.GONE);
            rvMessage.setVisibility(View.VISIBLE);
        } else {
            llPageStatus.setVisibility(View.VISIBLE);
            rvMessage.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_msg).into(ivPageStatus);
            tvPageStatus.setText(R.string.no_chatting);
        }
    }

    private void initAdapter() {
        noRecord();
        messageListAdapter = new ChatListAdapter(chatList);
        // messageListAdapter.openLoadAnimation();
        //  messageListAdapter.addHeaderView(view);
        messageListAdapter.setTopAndRemoveListener(this);
        messageListAdapter.setItemClickListener(this);
        // messageListAdapter.addHeaderView(view);
        rvMessage.setAdapter(messageListAdapter);
    }

    private void initData() {
        if (NetUtil.isConnected(getActivity())) {
            listPresenter = new ChatListPresenter(this);
            listPresenter.chatList();
        } else {
            getChatList();
            sflMessage.setRefreshing(false);
        }
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        sflMessage.setVisibility(View.GONE);
    }

    private void loadFinished() {
        llLoading.setVisibility(View.GONE);
        sflMessage.setVisibility(View.VISIBLE);
    }

    private void initView() {
        userId = SharedPreferencesUtil.get(getActivity(), "userId", "");
        sflMessage.setOnRefreshListener(this);
        rvMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvTitle.setText(R.string.Messag_center);
        llRightClick.setVisibility(View.GONE);
        llTitleBack.setVisibility(View.GONE);
    }

    private void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(500);
        }
    }

    @Override
    public void top(int postion) {
        if (chatList.get(postion).isTop) {//取消置顶
            SQLite.update(Chat.class).set(Chat_Table.isTop.is(false)).where(Chat_Table.contactId.is(chatList.get(postion).contactId)).execute();
            chatList.clear();
            getChatList();
        } else {
            List<Chat> list = new Select().from(Chat.class).where(Chat_Table.isTop.is(true)).queryList();
            if (list.size() < 10) {
                SQLite.update(Chat.class).set(Chat_Table.isTop.is(true)).where(Chat_Table.contactId.is(chatList.get(postion).contactId)).execute();
                chatList.clear();
                getChatList();
            } else {//最多置顶10人
                Toast.makeText(getActivity(), R.string.ten_person, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void remove(int position) {
        if (NetUtil.isConnected(getActivity())) {
            contactId = chatList.get(position).contactId;
            removePosition = position;
            removeContactId = contactId;
            removeChatPresenter = new RemoveChatPresenter(this);
            removeChatPresenter.removeChat();
        }
    }

    @Override
    public Context mContext() {
        return getActivity();
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public void findExistUnReadMsg(FindUnreadData response) {
//        if (response.isRespObject()) {
//            EventBus.getDefault().postSticky(new MsgEvent("UnRead"));
//        } else {
//            EventBus.getDefault().postSticky(new MsgEvent("FinishRead"));
//        }
    }

    @Override
    public void findExistUnReadMsgFailed(String errInfo) {


    }

    @Override
    public int contactId() {
        return contactId;
    }

    @Override
    public void removeChatSuccess() {
        SQLite.update(Chat.class).set(Chat_Table.isRemove.is(false), Chat_Table.isTop.is(false)).where(Chat_Table.contactId.is(removeContactId)).execute();
        chatList.remove(removePosition);
        messageListAdapter.notifyItemRemoved(removePosition);
    }

    @Override
    public void removeChatFailed(String errInfo) {

    }

    @Override
    public void showSingleChatSuccess(ChatData response) {
        vibrate(getActivity());
        pushValue.put("contactId" + num, contactId);
        num = num + 1;
        //Log.e("fds",response.getRespObject().get(0).getUnReadCount()+"");
        if (getUserVisibleHint()) {
            SyncChatListUtil util = new SyncChatListUtil(getActivity(), response, true, true);
            util.startSyncChatList();
        } else {
            SyncChatListUtil util = new SyncChatListUtil(getActivity(), response, true, false);
            util.startSyncChatList();
        }
    }

    @Override
    public void showSingleChatFailed(String errInfo) {

    }


    @Override
    public void showChatListSuccess(ChatData response) {
        SyncChatListUtil util = new SyncChatListUtil(getActivity(), response, false, false);
        util.startSyncChatList();
    }

    @Override
    public void showChatListFailed(String errInfo) {
        //Toast.makeText(getActivity(), errInfo, Toast.LENGTH_SHORT).show();
        getChatList();
        sflMessage.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == 0) {
            messageListAdapter.setUnReadView(false);
            messageListAdapter.notifyItemChanged(0);
            //   Toast.makeText(getActivity(), "系统消息", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), ChattingSysActivity.class);
            intent.putExtra("token", chatList.get(position).token);
            intent.putExtra("contactId", chatList.get(position).contactId);
            startActivity(intent);
        } else {
            //    Toast.makeText(getActivity(),"普通",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), ChattingActivity.class);
            Chat chat = chatList.get(position);
            intent.putExtra("sendToken", chat.token);
            intent.putExtra("eventId", chat.eventId);
            intent.putExtra("chatName", chat.showName);
            intent.putExtra("avatar", chat.avatar);
            intent.putExtra("peopleId", chat.peopleId);
            intent.putExtra("contactId", chat.contactId);
            intent.putExtra("position", position);
            EventBus.getDefault().post(new MsgEvent("Readed"));
            view.findViewById(R.id.tv_unread_count).setVisibility(View.GONE);
            startActivity(intent);
        }

    }
}
