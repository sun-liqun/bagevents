package com.bagevent.new_home.new_interface;

import com.bagevent.new_home.new_interface.listenerInterface.WXUserInfoListener;

/**
 * Created by zwj on 2017/1/3.
 */
public interface WXUserInfoInterface {
    void getWXUserInfo(String accessToken, String openId, WXUserInfoListener listener);
}
