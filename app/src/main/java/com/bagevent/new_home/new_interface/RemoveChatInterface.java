package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnRemoveChatListener;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public interface RemoveChatInterface {
    void removeChat(Context mContext, String userId, int contactId, OnRemoveChatListener listener);
}
