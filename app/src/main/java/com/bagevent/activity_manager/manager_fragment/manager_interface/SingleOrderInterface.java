package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnSingleOrderListener;

/**
 * Created by ZWJ on 2017/12/4 0004.
 */

public interface SingleOrderInterface {
    void getSingleOrder(Context mContext, int eventId, String userId,String orderNumber, int orderId,OnSingleOrderListener listener);
}
