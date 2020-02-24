package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnPostMessageListener;

/**
 * Created by ZWJ on 2017/11/29 0029.
 */

public interface PostChatMessageInterface {
    void postMessage(Context mContext, String userId, int eventId, int attendeeId, String sendToken, String receiverToken, String content, OnPostMessageListener listener);
}
