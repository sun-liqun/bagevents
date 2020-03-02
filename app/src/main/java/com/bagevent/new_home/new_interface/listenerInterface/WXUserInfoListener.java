package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.WXUserInfoData;

/**
 * Created by zwj on 2017/1/3.
 */
public interface WXUserInfoListener {
    void userInfo(WXUserInfoData response);
    void userInfoErr();
}
