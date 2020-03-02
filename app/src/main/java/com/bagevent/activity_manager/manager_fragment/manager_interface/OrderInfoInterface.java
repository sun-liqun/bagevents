package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnOrderInfoListener;

/**
 * Created by zwj on 2017/4/14.
 */

public interface OrderInfoInterface {
    void getOrderInfo(Context mContext, String eventId, long fromTime, String syncAllStat, OnOrderInfoListener listener);
}
