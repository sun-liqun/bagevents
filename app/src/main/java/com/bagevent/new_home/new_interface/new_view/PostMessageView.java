package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.PostChatMessageData;

/**
 * Created by ZWJ on 2017/11/29 0029.
 */

public interface PostMessageView {
    Context mContext();
    String userId();
    int eventId();
    String attendeeId();
    String postSendToken();
    String receiverToken();
    String content();

    void postMessageSuccess(PostChatMessageData response);
    void postMessageFailed(String errInfo);
}
