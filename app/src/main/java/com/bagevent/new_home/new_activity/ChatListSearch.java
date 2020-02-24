package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.db.Chat;
import com.bagevent.db.ChatMessage;
//import com.bagevent.db.ChatMessage_Table;
//import com.bagevent.db.Chat_Table;
import com.bagevent.db.ChatMessage_Table;
import com.bagevent.db.Chat_Table;
import com.bagevent.new_home.adapter.ChatListSearchAdpater;
import com.bagevent.new_home.data.ChatSearchData;
import com.bagevent.util.AppManager;
import com.bagevent.util.InputMethodUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZWJ on 2017/11/23 0023.
 */

public class ChatListSearch extends BaseActivity implements TextWatcher, ChatListSearchAdpater.OnItemClickListener {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @BindView(R.id.btn_search_cancle)
    Button btnSearchCancle;
    @BindView(R.id.rv_chatlist_search)
    RecyclerView rvChatlistSearch;
    @BindView(R.id.iv_page_status)
    ImageView ivPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;

    private List<ChatSearchData> searchResult = new ArrayList<ChatSearchData>();
    private ChatListSearchAdpater searchAdapter;
    private String userId;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_chatlist_search);
        ButterKnife.bind(this);
        initView();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals("searchResult")) {
            if (searchResult.size() > 0) {
                rvChatlistSearch.setVisibility(View.VISIBLE);
                if (searchAdapter == null) {
                    initAdapter();
                } else {
                    searchAdapter.replaceData(searchResult);
                }
            } else {
                etSearch.setText("");
                rvChatlistSearch.setVisibility(View.GONE);
                llPageStatus.setVisibility(View.VISIBLE);
            }

        }
    }

//    @Override
//    public boolean isSupportSwipeBack() {
//        return false;
//    }

    private void initAdapter() {
        searchAdapter = new ChatListSearchAdpater(searchResult);
        searchAdapter.openLoadAnimation();
        searchAdapter.setItemClickListener(this);
        rvChatlistSearch.setAdapter(searchAdapter);
    }

    private void initView() {
        userId = SharedPreferencesUtil.get(this, "userId", "");
        rvChatlistSearch.setLayoutManager(new LinearLayoutManager(this));
//        etSearch.addTextChangedListener(this);
//        etSearch.setFocusable(true);
//        etSearch.requestFocus();
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchData(etSearch.getText().toString().trim());
                    InputMethodUtil.close(ChatListSearch.this,etSearch);
                }
                return false;
            }
        });

        Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
        tvPageStatus.setText(R.string.no_search_result);
    }


    private void searchData(final String keyword) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<ChatSearchData> tempChatMsg = new ArrayList<ChatSearchData>();
                List<ChatMessage> search = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                        .and(ChatMessage_Table.content.like("%" + keyword + "%")).queryList();
                for (int i = 0; i < search.size(); i++) {
                    ChatSearchData bean = new ChatSearchData();
                    ChatMessage msg = search.get(i);
                    bean.setContactId(msg.contactId + "");
                    tempChatMsg.add(bean);
                }

                tempChatMsg = removeDuplicteContacId(tempChatMsg);

                for (int i = 0; i < tempChatMsg.size(); i++) {
                    Chat chat = new Select().from(Chat.class).where(Chat_Table.userId.is(Long.parseLong(userId)))
                            .and(Chat_Table.contactId.is(Integer.parseInt(tempChatMsg.get(i).getContactId()))).querySingle();
                    if (chat != null) {
                        //  Log.e("fdsaf", chat.sys + " " + chat.isRemove);
                        if (!chat.sys && chat.isRemove) {
                            List<ChatMessage> chatMessage = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                                    .and(ChatMessage_Table.contactId.is(Integer.parseInt(tempChatMsg.get(i).getContactId()))).and(ChatMessage_Table.content.like("%" + keyword + "%")).queryList();
                            ChatSearchData bean = new ChatSearchData();
                            bean.setContactId(chat.contactId + "");
                            bean.setSort(chat.sort);
                            bean.setCreateTime(TimeUtil.timedate(chat.createTime));
                            bean.setUpdateTime(TimeUtil.timedate(chat.updateTime));
                            bean.setUnReadCount(chat.unReadCount);
                            bean.setPeopleId(chat.peopleId);
                            bean.setToken(chat.token);
                            bean.setType(chat.type);
                            bean.setEventId(chat.eventId);
                            bean.setShowName(chat.showName);
                            bean.setAvatar(chat.avatar);
                            bean.setOrganizer(chat.organizer);
                            bean.setSys(chat.sys);
                            if (chatMessage.size() > 0) {
                                if (chatMessage.size() == 1) {
                                    bean.setLastMessage(chatMessage.get(0).content);
                                } else {
                                    bean.setLastMessage(chatMessage.size() + getString(R.string.related_records));
                                }
                            }
                            searchResult.add(bean);
                        }

                    }
                }
                EventBus.getDefault().post(new MsgEvent("searchResult"));
            }
        });
        thread.start();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        final String result = s.toString();
        searchResult.clear();
        if (TextUtils.isEmpty(result)) {
            ivSearchClear.setVisibility(View.GONE);
            if (searchAdapter != null) {
                searchAdapter.setNewData(searchResult);
            }
        } else {
            ivSearchClear.setVisibility(View.VISIBLE);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<ChatSearchData> tempChatMsg = new ArrayList<ChatSearchData>();
                    List<ChatMessage> search = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                            .and(ChatMessage_Table.content.like("%" + result + "%")).queryList();
                    for (int i = 0; i < search.size(); i++) {
                        ChatSearchData bean = new ChatSearchData();
                        ChatMessage msg = search.get(i);
                        bean.setContactId(msg.contactId + "");
                        tempChatMsg.add(bean);
                    }

                    tempChatMsg = removeDuplicteContacId(tempChatMsg);

                    for (int i = 0; i < tempChatMsg.size(); i++) {
                        Chat chat = new Select().from(Chat.class).where(Chat_Table.userId.is(Long.parseLong(userId)))
                                .and(Chat_Table.contactId.is(Integer.parseInt(tempChatMsg.get(i).getContactId()))).querySingle();
                        if (chat != null) {
                            //  Log.e("fdsaf", chat.sys + " " + chat.isRemove);
                            if (!chat.sys && chat.isRemove) {
                                List<ChatMessage> chatMessage = new Select().from(ChatMessage.class).where(ChatMessage_Table.userId.is(Long.parseLong(userId)))
                                        .and(ChatMessage_Table.contactId.is(Integer.parseInt(tempChatMsg.get(i).getContactId()))).and(ChatMessage_Table.content.like("%" + result + "%")).queryList();
                                ChatSearchData bean = new ChatSearchData();
                                bean.setContactId(chat.contactId + "");
                                bean.setSort(chat.sort);
                                bean.setCreateTime(TimeUtil.timedate(chat.createTime));
                                bean.setUpdateTime(TimeUtil.timedate(chat.updateTime));
                                bean.setUnReadCount(chat.unReadCount);
                                bean.setPeopleId(chat.peopleId);
                                bean.setToken(chat.token);
                                bean.setType(chat.type);
                                bean.setEventId(chat.eventId);
                                bean.setShowName(chat.showName);
                                bean.setAvatar(chat.avatar);
                                bean.setOrganizer(chat.organizer);
                                bean.setSys(chat.sys);
                                if (chatMessage.size() > 0) {
                                    if (chatMessage.size() == 1) {
                                        bean.setLastMessage(chatMessage.get(0).content);
                                    } else {
                                        bean.setLastMessage(chatMessage.size() + getString(R.string.related_records));
                                    }
                                }
                                searchResult.add(bean);
                            }

                        }
                    }
                    EventBus.getDefault().post(new MsgEvent("searchResult"));
                }
            });
            thread.start();
        }

    }

    private List<ChatSearchData> removeDuplicteContacId(List<ChatSearchData> orderInfos) {
        Set<ChatSearchData> s = new TreeSet<ChatSearchData>(new Comparator<ChatSearchData>() {
            @Override
            public int compare(ChatSearchData o1, ChatSearchData o2) {

                return o1.getContactId().compareTo(o2.getContactId());
            }
        });
        s.addAll(orderInfos);
        return new ArrayList<ChatSearchData>(s);
    }


    @OnClick({R.id.iv_search_clear, R.id.btn_search_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search_clear:
                etSearch.setText("");
                searchResult.clear();
                if (searchAdapter != null) {
                    searchAdapter.notifyDataSetChanged();
                }
//                searchAdapter.notifyDataSetChanged();
                //searchAdapter.setNewData(searchResult);
                break;
            case R.id.btn_search_cancle:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (searchResult.size() > 0) {
            Intent intent = new Intent(this, ChattingActivity.class);
            intent.putExtra("sendToken", searchResult.get(position).getToken());
            intent.putExtra("eventId", searchResult.get(position).getEventId());
            intent.putExtra("chatName", searchResult.get(position).getShowName());
            intent.putExtra("avatar", searchResult.get(position).getAvatar());
            intent.putExtra("peopleId", searchResult.get(position).getPeopleId());
            intent.putExtra("contactId", Integer.parseInt(searchResult.get(position).getContactId()));
            startActivity(intent);
        }

    }
}
