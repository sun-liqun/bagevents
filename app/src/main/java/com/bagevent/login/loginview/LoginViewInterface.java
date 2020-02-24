package com.bagevent.login.loginview;

import android.content.Context;

import com.bagevent.login.model.UserInfo;

/**
 * Created by zwj on 2016/5/25.
 */
public interface LoginViewInterface {
    Context mContext();
    String getUserName();
    String getPassword();
    void clearUserName();
    void clearPassword();
    void toMainActivity(UserInfo userInfo);
    void showFailedErr(String errInfo);
}
