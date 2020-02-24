package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.CheckAccessTokenData;

/**
 * Created by zwj on 2017/1/3.
 */
public interface CheckAccessTokenlistener {
    void accessTokenEnable(CheckAccessTokenData response);
    void accessTokenUnable();
}
