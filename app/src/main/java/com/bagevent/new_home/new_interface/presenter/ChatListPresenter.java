package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.ChatData;
import com.bagevent.new_home.new_interface.ChatListInterface;
import com.bagevent.new_home.new_interface.impl.ChatListImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnChatListListener;
import com.bagevent.new_home.new_interface.new_view.ChatListView;
import com.bagevent.new_home.new_interface.new_view.SingleChatListView;

/**
 * Created by ZWJ on 2017/11/21 0021.
 */

public class ChatListPresenter {
    private ChatListInterface chatList;
    private ChatListView chatListView;

    public ChatListPresenter(ChatListView chatListView) {
        this.chatList = new ChatListImpl();
        this.chatListView = chatListView;
    }



    public void chatList() {
        chatList.chat(chatListView.mContext(), chatListView.userId(), new OnChatListListener() {
            @Override
            public void showChatListSuccess(ChatData response) {
                chatListView.showChatListSuccess(response);
            }

            @Override
            public void showChatListFailed(String errInfo) {
                chatListView.showChatListFailed(errInfo);
            }
        });
    }
}
