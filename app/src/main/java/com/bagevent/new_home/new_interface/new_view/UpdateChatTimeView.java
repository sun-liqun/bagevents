package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by ZWJ on 2017/11/30 0030.
 */

public interface UpdateChatTimeView {
    Context mContext();
    String userId();
    int contactId();
    String upDateChatTime();

    void showUpdateChatTimeSuccess();
    void showUpdateChatTimeFailed(String errInfo);

}
