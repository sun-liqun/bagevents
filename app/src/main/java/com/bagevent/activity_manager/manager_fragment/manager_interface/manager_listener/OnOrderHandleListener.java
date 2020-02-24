package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.OrderHandleData;

/**
 * Created by ZWJ on 2018/1/2 0002.
 */

public interface OnOrderHandleListener {
    void orderHandleSuccess(OrderHandleData response);
    void orderHandleFailed(String response);
}
