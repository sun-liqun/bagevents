package com.bagevent;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.adapter.RecentContactAdapter;
import com.bagevent.new_home.new_interface.callback.RecentContactsCallback;
import com.bagevent.util.LogUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.module.MsgForwardFilter;
import com.netease.nim.uikit.business.session.module.MsgRevokeFilter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.zhy.autolayout.AutoLinearLayout;
//import com.raizlabs.android.dbflow.sql.language.Select;
//import com.zhy.autolayout.AutoLinearLayout;
//import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;


public class NewMessageFragment extends BaseFragment implements RecentContactAdapter.OnItemClickListener{

    Unbinder unbinder;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
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

    // 置顶功能可直接使用，也可作为思路，供开发者充分利用RecentContact的tag字段
    public static final long RECENT_TAG_STICKY = 0x0000000000000001; // 联系人置顶tag
    private List<RecentContact> items=new ArrayList<>();
    private RecentContactAdapter adapter;
    private RecentContactsCallback callback;
    private boolean msgLoaded = false;
    String content;
    int eventbus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> recentContacts) {
            onRecentContactChanged(recentContacts);
        }
    };
    Observer<RecentContact> deleteObserver = new Observer<RecentContact>() {
        @Override
        public void onEvent(RecentContact recentContact) {
            if (recentContact != null) {
                for (RecentContact item : items) {
                    if (TextUtils.equals(item.getContactId(), recentContact.getContactId())
                            && item.getSessionType() == recentContact.getSessionType()) {
                        items.remove(item);
                        refreshMessage(true);
                        break;
                    }
                }
            } else {
                items.clear();
                refreshMessage(true);
            }
        }
    };

    Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {
            int index = getItemIndex(message.getUuid());
            if (index >= 0 && index < items.size()) {
                RecentContact item = items.get(index);
                item.setMsgStatus(message.getStatus());
                refreshViewHolderByIndex(index);
            }
        }
    };


    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(MessageEvent event) {
        if ((event.getAction()==MessageAction.REFRESH_YUNXIN_MESSAGE)) {
            requestMessages(true);
            eventbus=1;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_new_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isLoading();
        initMessageList();
        registerObservers(true);
        requestMessages(false);

    }

    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);
        service.observeMsgStatus(statusObserver, register);
        service.observeRecentContactDeleted(deleteObserver, register);
    }

    private List<RecentContact> loadedRecents;

    private void requestMessages(boolean delay) {
        if (msgLoaded) {
            return;
        }
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                NIMClient.getService(MsgService.class).queryRecentContacts()
                        .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                            @Override
                            public void onResult(int code, List<RecentContact> result, Throwable exception) {
                                Log.i("-----slq","code:"+code+"result:"+result+"e"+exception);
                                Log.i("-------slqevent",eventbus+"");
                                if (code != ResponseCode.RES_SUCCESS || result == null) {
                                    if(eventbus==1){
                                        Toast.makeText(getContext(), "错误码:"+code+"结果:"+result, Toast.LENGTH_SHORT).show();
                                    }
                                    return;
                                }
                                loadedRecents = result;
                                loadFinished();
                                msgLoaded=true;
                                if (isAdded()){
                                    onRecentContactsLoaded();
                                }
                            }
                        });
            }
        }, delay ? 500 : 0);
    }

    private void onRecentContactsLoaded() {
        items.clear();
        if (loadedRecents!=null){
            items.addAll(loadedRecents);
            loadedRecents=null;
            refreshMessage(true);
        }
        if (callback != null) {
            callback.onRecentContactsLoaded();
        }
    }

    private void refreshMessage(boolean unreadChanged) {
        sortRecentContacts(items);
        notifyDataSetChanged();
        // 方式一：累加每个最近联系人的未读（快）
        int unreadNum = 0;
        for (RecentContact r : items) {
            unreadNum += r.getUnreadCount();
        }
        //
        if (unreadNum==0){
            EventBus.getDefault().post(new MsgEvent("Readed"));
        }else {
            EventBus.getDefault().post(new MsgEvent("UnRead"));
        }
        SharedPreferencesUtil.put(getActivity(), "UnRead",String.valueOf(unreadNum));
        // 方式二：直接从SDK读取（相对慢）
//        if (callback!=null){
//            callback.onUnreadCountChange(unreadNum);
//        }
//        Badger.updateBadgerCount(unreadNum);
    }

    private void initView() {
        tvTitle.setText(R.string.Messag_center);
        llRightClick.setVisibility(View.GONE);
        llTitleBack.setVisibility(View.GONE);
        eventbus=22;
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
    }

    private void loadFinished() {
        llLoading.setVisibility(View.GONE);
    }

    private void emptyView(){
        llPageStatus.setVisibility(View.VISIBLE);
        rvMessage.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.no_msg).into(ivPageStatus);
        tvPageStatus.setText(R.string.no_chatting);
    }

    private void initMessageList() {
        items=new ArrayList<>();
        adapter=new RecentContactAdapter(items);
        adapter.setItemClickListener(this);
        initCallBack();
        adapter.setCallback(callback);
        rvMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMessage.setAdapter(adapter);
    }

    private void initCallBack() {
        if (callback!=null){
            return;
        }
        callback=new RecentContactsCallback() {
            @Override
            public void onRecentContactsLoaded() {

            }

            @Override
            public void onUnreadCountChange(int unreadCount) {

            }

            @Override
            public void onItemClick(RecentContact recent) {

            }

            @Override
            public String getDigestOfAttachment(RecentContact recent, MsgAttachment attachment) {
                return null;
            }

            @Override
            public String getDigestOfTipMsg(RecentContact recent) {
                return null;
            }
        };
    }

    private void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
        boolean empty = items.isEmpty() && msgLoaded;
        if (empty){
            llPageStatus.setVisibility(View.VISIBLE);
            rvMessage.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_msg).into(ivPageStatus);
            tvPageStatus.setText(R.string.no_chatting);
        }else {
            llPageStatus.setVisibility(View.GONE);
            rvMessage.setVisibility(View.VISIBLE);
        }
    }

    /**
     * **************************** 排序 ***********************************
     */
    private void sortRecentContacts(List<RecentContact> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private static Comparator<RecentContact> comp = new Comparator<RecentContact>() {

        @Override
        public int compare(RecentContact o1, RecentContact o2) {
            // 先比较置顶tag
            long sticky = (o1.getTag() & RECENT_TAG_STICKY) - (o2.getTag() & RECENT_TAG_STICKY);
            if (sticky != 0) {
                return sticky > 0 ? -1 : 1;
            } else {
                long time = o1.getTime() - o2.getTime();
                return time == 0 ? 0 : (time > 0 ? -1 : 1);
            }
        }
    };

    private void onRecentContactChanged(List<RecentContact> recentContacts) {
        int index;
        for (RecentContact r : recentContacts) {
            index = -1;
            for (int i = 0; i < items.size(); i++) {
                if (r.getContactId().equals(items.get(i).getContactId())
                        && r.getSessionType() == (items.get(i).getSessionType())) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                items.remove(index);
            }
            items.add(r);
            refreshMessage(true);
        }
    }

    private int getItemIndex(String uuid) {
        for (int i = 0; i < items.size(); i++) {
            RecentContact item = items.get(i);
            if (TextUtils.equals(item.getRecentMessageId(), uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected void refreshViewHolderByIndex(final int index) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(index);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("--------------------msgFOnResume");
        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.i("--------------------msgFonPause");
        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().removeAllStickyEvents();
        registerObservers(false);
    }


    @Override
    public void onItemClick(View view, int position) {
        RecentContact recentContact=items.get(position);
        NimUIKit.startP2PSession(getActivity(), recentContact.getContactId());
        NimUIKit.setMsgForwardFilter(new MsgForwardFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                return true;
            }
        });
        NimUIKit.setMsgRevokeFilter(new MsgRevokeFilter() {
            @Override
            public boolean shouldIgnore(IMMessage message) {
                return false;
            }
        });
    }
}
