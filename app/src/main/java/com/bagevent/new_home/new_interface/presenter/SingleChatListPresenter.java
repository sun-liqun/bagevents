package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.ChatData;
import com.bagevent.new_home.new_interface.ChatListInterface;
import com.bagevent.new_home.new_interface.impl.ChatListImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnChatListListener;
import com.bagevent.new_home.new_interface.new_view.SingleChatListView;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public class SingleChatListPresenter {
    private ChatListInterface chatList;
    private SingleChatListView singleChatListView;

    public SingleChatListPresenter(SingleChatListView singleChatListView) {
        this.chatList = new ChatListImpl();
        this.singleChatListView = singleChatListView;
    }

    public void singleChatList() {
        chatList.singleChat(singleChatListView.mContext(), singleChatListView.userId(), singleChatListView.contactId(), new OnChatListListener() {
            @Override
            public void showChatListSuccess(ChatData response) {
                singleChatListView.showSingleChatSuccess(response);
            }

            @Override
            public void showChatListFailed(String errInfo) {
                singleChatListView.showSingleChatFailed(errInfo);
            }
        });
    }
}
