package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;

/**
 * Created by zwj on 2017/4/14.
 */

public interface OnOrderInfoListener {
    void getOrderInfoSuccess(OrderInfo orderInfo);
    void getOrderInfoFailed(String errInfo);
}
