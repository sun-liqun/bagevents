package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnBindUserCellPhoneListener;

/**
 * Created by zwj on 2016/9/12.
 */
public interface BindUserCellPhoneInterface {
    void bindUserCellPhone(Context mContext, String cellphone, String randomCode, String userId, OnBindUserCellPhoneListener listener);
}
