package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnCreateEventListener;

/**
 * Created by zwj on 2016/9/19.
 */
public interface CreateEventInterface {
    void create(Context mContext, String userId, int type, OnCreateEventListener listener);
}
