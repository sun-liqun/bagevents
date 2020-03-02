package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.ChatMessageData;
import com.bagevent.new_home.new_interface.ChatMessageInterface;
import com.bagevent.new_home.new_interface.impl.ChatMessageInterfaceImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetChatMessageListener;
import com.bagevent.new_home.new_interface.new_view.ChatMessageView;
import com.bagevent.new_home.new_interface.new_view.UpFetchChatMessageView;

/**
 * Created by ZWJ on 2017/11/24 0024.
 */

public class ChatMessagePresenter {
    private ChatMessageInterface chatMessage;
    private ChatMessageView chatMessageView;


    public ChatMessagePresenter(ChatMessageView chatMessageView) {
        this.chatMessage = new ChatMessageInterfaceImpl();
        this.chatMessageView = chatMessageView;
    }



    public void chatMessage() {
        chatMessage.chatMessage(chatMessageView.mContext(), chatMessageView.userId(), chatMessageView.sendToken(), chatMessageView.eventId(), chatMessageView.page(), new OnGetChatMessageListener() {
            @Override
            public void showChatMessageSuccess(ChatMessageData response) {
                chatMessageView.showChatMessageSuccess(response);
            }

            @Override
            public void showChatMessageFailed(String errInfo) {
                chatMessageView.showChatMessageFailed(errInfo);
            }
        });
    }
}
