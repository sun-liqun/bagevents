package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnSubmitOrderListener;

/**
 * Created by zwj on 2016/7/5.
 */
public interface SubmitOrderInterface {
    void submitOrder(Context mContext, String eventId, String submitInfo, String buyWay, OnSubmitOrderListener listener);
}
