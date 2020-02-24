package com.bagevent.new_home.new_interface;

import com.bagevent.new_home.new_interface.listenerInterface.OnWXGetAccessTokenListener;

/**
 * Created by zwj on 2016/12/30.
 */
public interface WXGetAccessTokenInterface {
    void wxLogin(String code, String appId, String secret,OnWXGetAccessTokenListener listener);
}
