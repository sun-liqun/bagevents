package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.ChatData;
import com.bagevent.new_home.new_interface.ChatListInterface;
import com.bagevent.new_home.new_interface.impl.ChatListImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnChatListListener;
import com.bagevent.new_home.new_interface.new_view.AttendeeChatView;

/**
 * Created by ZWJ on 2017/12/7 0007.
 */

public class AttendeeSingleChatPresenter {
    private ChatListInterface chatList;
    private AttendeeChatView attendeeChatView;

    public AttendeeSingleChatPresenter(AttendeeChatView attendeeChatView) {
        this.chatList = new ChatListImpl();
        this.attendeeChatView = attendeeChatView;
    }

    public void getAttendeeSingleChat() {
        chatList.attendeeSingleChat(attendeeChatView.mContext(), attendeeChatView.userId(), attendeeChatView.eventId(), Integer.parseInt(attendeeChatView.attendeeId()), new OnChatListListener() {
            @Override
            public void showChatListSuccess(ChatData response) {
                attendeeChatView.showAttendeeSingleChatSuccess(response);
            }

            @Override
            public void showChatListFailed(String errInfo) {
                attendeeChatView.showAttendeeSingleChatFailed(errInfo);
            }
        });
    }
}
