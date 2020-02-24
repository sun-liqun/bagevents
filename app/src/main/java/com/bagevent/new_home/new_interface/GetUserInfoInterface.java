package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnGetUserInfoListener;

/**
 * Created by zwj on 2016/10/11.
 */
public interface GetUserInfoInterface {
    void userInfoo(Context mContext, String userId, OnGetUserInfoListener listener);
}
