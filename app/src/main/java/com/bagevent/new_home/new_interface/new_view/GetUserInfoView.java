package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.UserInfoData;

/**
 * Created by zwj on 2016/10/11.
 */
public interface GetUserInfoView {
    Context mContext();

    String userId();
    void getUserInfoSuccess(UserInfoData response);
    void getUserInfoFailed(String errInfo);
}
