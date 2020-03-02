package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnWithDrawListener;

/**
 * Created by WenJie on 2017/11/16.
 */

public interface WithdrawAccountInterface {
    void withdrawAccount(Context mContext, String userId, OnWithDrawListener listener);
}
