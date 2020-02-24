package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnConfirmOrderListener;

/**
 * Created by WenJie on 2017/11/1.
 */

public interface ConfirmOrderInterface {
    void confirmOrder(Context mContext, int eventId, int orderId, OnConfirmOrderListener listener);
}
