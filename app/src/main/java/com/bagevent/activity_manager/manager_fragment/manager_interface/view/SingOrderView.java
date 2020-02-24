package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.SingleOrderData;

/**
 * Created by ZWJ on 2017/12/4 0004.
 */

public interface SingOrderView {
    Context mContext();
    String userId();
    int eventId();
    String orderNumber();
    int orderId();
    void showSingleOrderSuccess(SingleOrderData response);
    void showSingleOrderFailed(String errInfo);
}
