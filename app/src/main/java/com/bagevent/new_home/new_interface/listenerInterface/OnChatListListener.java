package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.ChatData;

/**
 * Created by ZWJ on 2017/11/21 0021.
 */

public interface OnChatListListener {
    void showChatListSuccess(ChatData response);
    void showChatListFailed(String errInfo);
}
