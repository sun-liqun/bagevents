package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.PostChatMessageData;

/**
 * Created by ZWJ on 2017/11/29 0029.
 */

public interface OnPostMessageListener {
    void postMessageSuccess(PostChatMessageData response);
    void postMessageFailed(String errInfo);
}
