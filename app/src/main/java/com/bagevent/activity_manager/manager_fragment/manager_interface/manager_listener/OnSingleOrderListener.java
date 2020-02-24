package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.SingleOrderData;

/**
 * Created by ZWJ on 2017/12/4 0004.
 */

public interface OnSingleOrderListener {
    void showSingleOrderSuccess(SingleOrderData response);
    void showSingleOrderFailed(String errInfo);
}
