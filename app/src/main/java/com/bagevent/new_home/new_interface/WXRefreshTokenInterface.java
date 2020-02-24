package com.bagevent.new_home.new_interface;

import com.bagevent.new_home.new_interface.listenerInterface.WXRefreshTokenListener;

/**
 * Created by zwj on 2017/1/3.
 */
public interface WXRefreshTokenInterface {
    void refreshToken(String appId, String refreshToken, WXRefreshTokenListener listener);
}
