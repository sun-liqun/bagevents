package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.CreateEventData;

/**
 * Created by zwj on 2016/9/19.
 */
public interface OnCreateEventListener {
    void createSuccess(CreateEventData response);
    void createFailed(String errInfo);
}
