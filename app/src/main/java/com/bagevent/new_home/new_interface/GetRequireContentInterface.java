package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnGetRequireContentListener;

/**
 * Created by zwj on 2016/9/12.
 */
public interface GetRequireContentInterface {
    void getRequireContent(Context mContext, String userId, OnGetRequireContentListener listener);
}
