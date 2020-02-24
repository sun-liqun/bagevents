package com.bagevent.new_home.new_interface.new_view;

import com.bagevent.new_home.data.WXUserInfoData;

/**
 * Created by zwj on 2017/1/3.
 */
public interface WXUserInfoView {
    String accessToken();
    String openId();

    void getUserInfoOK(WXUserInfoData response);
    void getUserInfoErr();
}
