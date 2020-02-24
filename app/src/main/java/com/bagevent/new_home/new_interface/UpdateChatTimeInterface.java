package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateChatTimeListener;

/**
 * Created by ZWJ on 2017/11/30 0030.
 */

public interface UpdateChatTimeInterface {
    void updateChatTime(Context mContext, String userId, int contactId, String updateTime, OnUpdateChatTimeListener listener);
}
