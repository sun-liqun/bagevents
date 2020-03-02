package com.bagevent.login.loginview;

import com.bagevent.login.model.UserInfo;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/8
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public interface PhoneLoginViewInterface {
    String getUserName();
    String getAuthCode();
    void updateCountDown(String msg);
    void setAuthCodeEnable(boolean enable);
    void clearUserName();
    void clearAuthCode();
    void toMainActivity(UserInfo userInfo);
    void showFailedErr(String errInfo);
}
