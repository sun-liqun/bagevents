package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.ChatMessageData;

/**
 * Created by ZWJ on 2017/11/28 0028.
 */

public interface UpFetchChatMessageView {
    Context mContext();
    String userId();
    String sendToken();
    int eventId();
    String sendTime();
    void upFetchChatMessageSuccess(ChatMessageData response);
    void upFetchChatMessageFailed(String errInfo);

}
