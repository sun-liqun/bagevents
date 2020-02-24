package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.ChatData;

/**
 * Created by ZWJ on 2017/11/21 0021.
 */

public interface ChatListView {
    Context mContext();
    String userId();
    void showChatListSuccess(ChatData response);
    void showChatListFailed(String errInfo);
}
