package com.bagevent.new_home.new_interface.new_view;

import com.bagevent.new_home.data.WXAccessTokenData;

/**
 * Created by zwj on 2016/12/30.
 */
public interface WXGetAccessTokenView {
    String appid();
    String code();
    String secret();

    void GetAccessTokenOk(WXAccessTokenData response);
    void GetAccessTokenErr();
}
