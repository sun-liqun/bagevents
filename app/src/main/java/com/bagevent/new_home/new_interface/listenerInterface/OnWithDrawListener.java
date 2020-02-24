package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.WithDrawAccountData;

/**
 * Created by WenJie on 2017/11/16.
 */

public interface OnWithDrawListener {
    void withdrawSuccess(WithDrawAccountData response);
    void withdrawFailed(String errInfo);
}