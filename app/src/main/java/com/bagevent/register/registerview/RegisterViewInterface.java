package com.bagevent.register.registerview;

import android.content.Context;

import com.bagevent.register.data.RegisterData;

/**
 * Created by zwj on 2016/5/27.
 */
public interface RegisterViewInterface {
    Context context();
    String getPhoneNum();
    String getPhonePassword();
    String getIdentifyCode();
    void showRegisterFail(String err);
    void toLoginActivity(RegisterData response);
}
