package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnGetWithdrawValidCodeListener;
import com.bagevent.new_home.new_interface.listenerInterface.OnSetWithdrawpasswordListener;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public interface SetWithdrawPasswordInterface {
    void setWithdrawPassword(Context mContext, String userId, String password, String confirmPassword, String validCode, OnSetWithdrawpasswordListener listener);
    void getWithdrawValidCode(Context mContext, String userId, int type, OnGetWithdrawValidCodeListener listener);
}
