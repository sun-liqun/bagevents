package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateUserInfoListener;

/**
 * Created by zwj on 2016/10/13.
 */
public interface UpdateUserInfoInterface {
    void updateUserInfo(Context mContext, String userId, String formName, String formValue, OnUpdateUserInfoListener listener);
}
