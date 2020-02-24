package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.ChatMessageData;

/**
 * Created by ZWJ on 2017/11/24 0024.
 */

public interface OnGetChatMessageListener {
    void showChatMessageSuccess(ChatMessageData response);
    void showChatMessageFailed(String errInfo);
}

