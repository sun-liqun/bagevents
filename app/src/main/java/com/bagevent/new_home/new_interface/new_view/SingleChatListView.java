package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.ChatData;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public interface SingleChatListView {
    Context mContext();
    String userId();
    int contactId();

    void showSingleChatSuccess(ChatData response);
    void showSingleChatFailed(String errInfo);
}
