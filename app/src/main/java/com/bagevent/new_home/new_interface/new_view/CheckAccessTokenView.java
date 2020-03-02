package com.bagevent.new_home.new_interface.new_view;

import com.bagevent.new_home.data.CheckAccessTokenData;

/**
 * Created by zwj on 2017/1/3.
 */
public interface CheckAccessTokenView {
    String accessToken();
    String openId();
    void accessTokenEnable(CheckAccessTokenData response);
    void accessTokenUnable();
}
