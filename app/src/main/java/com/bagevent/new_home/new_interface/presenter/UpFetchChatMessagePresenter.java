package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.ChatMessageData;
import com.bagevent.new_home.new_interface.ChatMessageInterface;
import com.bagevent.new_home.new_interface.impl.ChatMessageInterfaceImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetChatMessageListener;
import com.bagevent.new_home.new_interface.new_view.UpFetchChatMessageView;

/**
 * Created by ZWJ on 2017/11/28 0028.
 */

public class UpFetchChatMessagePresenter {
    private ChatMessageInterface chatMessage;
    private UpFetchChatMessageView upFetchChatMessageView;

    public UpFetchChatMessagePresenter(UpFetchChatMessageView upFetchChatMessageView) {
        this.chatMessage = new ChatMessageInterfaceImpl();
        this.upFetchChatMessageView = upFetchChatMessageView;
    }

    public void upFetchMessage() {
        chatMessage.upFetchChatMessage(upFetchChatMessageView.mContext(), upFetchChatMessageView.userId(), upFetchChatMessageView.sendToken(), upFetchChatMessageView.eventId(), upFetchChatMessageView.sendTime(), new OnGetChatMessageListener() {
            @Override
            public void showChatMessageSuccess(ChatMessageData response) {
                upFetchChatMessageView.upFetchChatMessageSuccess(response);
            }

            @Override
            public void showChatMessageFailed(String errInfo) {
                upFetchChatMessageView.upFetchChatMessageFailed(errInfo);
            }
        });
    }
}
