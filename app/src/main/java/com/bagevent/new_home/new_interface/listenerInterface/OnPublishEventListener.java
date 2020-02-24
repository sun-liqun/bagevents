package com.bagevent.new_home.new_interface.listenerInterface;

/**
 * Created by zwj on 2016/10/10.
 */
public interface OnPublishEventListener {
    void publishSuccess();
    void publishFailed(String errInfo);
}
