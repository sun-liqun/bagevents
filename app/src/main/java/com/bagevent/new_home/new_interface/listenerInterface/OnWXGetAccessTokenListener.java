package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.WXAccessTokenData;

/**
 * Created by zwj on 2016/12/30.
 */
public interface OnWXGetAccessTokenListener {
    void getAccessTokenSuccess(WXAccessTokenData response);
    void getAccessTokenFailed();
}
