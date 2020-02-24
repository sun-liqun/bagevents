package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.UserInfoData;

/**
 * Created by zwj on 2016/10/11.
 */
public interface OnGetUserInfoListener {
    void userInfoSuccess(UserInfoData response);
    void userInfoFailed(String errInfo);
}
