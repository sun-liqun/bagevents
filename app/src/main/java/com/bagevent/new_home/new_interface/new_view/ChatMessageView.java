package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.ChatMessageData;

/**
 * Created by ZWJ on 2017/11/24 0024.
 */

public interface ChatMessageView {
    Context mContext();
    String userId();
    String sendToken();
    int eventId();
    int page();
    void showChatMessageSuccess(ChatMessageData response);
    void showChatMessageFailed(String errInfo);
}
