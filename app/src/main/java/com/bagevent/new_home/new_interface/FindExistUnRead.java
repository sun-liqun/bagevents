package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnFindExistUnReadListener;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public interface FindExistUnRead {
    void findExistUnRead(Context mContext, String userId, OnFindExistUnReadListener listener);
}
