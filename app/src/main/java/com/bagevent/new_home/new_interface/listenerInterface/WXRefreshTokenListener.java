package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.WXAccessTokenData;

/**
 * Created by zwj on 2017/1/3.
 */
public interface WXRefreshTokenListener {
    void refreshOK(WXAccessTokenData response);
    void refreshErr();
}
