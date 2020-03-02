package com.bagevent.new_home.new_interface;

import com.bagevent.new_home.new_interface.listenerInterface.CheckAccessTokenlistener;

/**
 * Created by zwj on 2017/1/3.
 */
public interface CheckAccessTokenInterface {
    void checkAccessToken(String accessToken, String openId, CheckAccessTokenlistener listener);
}
