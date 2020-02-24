package com.bagevent.login.loginview;

import android.content.Context;

/**
 * Created by ZWJ on 2018/1/11 0011.
 */

public interface ModifyPwView {
    Context mContext();
    String phoneNum();
    String smsCode();
    String password();
    String passwordAgaing();

    void showModifySuccess(String response);
    void showModifyFailed(String errInfo);
}
