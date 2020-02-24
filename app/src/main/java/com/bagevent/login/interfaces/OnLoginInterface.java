package com.bagevent.login.interfaces;

import com.bagevent.login.model.UserInfo;

/**
 * Created by zwj on 2016/5/25.
 */
public interface OnLoginInterface {
    void loginSuccess(UserInfo userInfo);
    void loginFailed(String errInfo);
}
