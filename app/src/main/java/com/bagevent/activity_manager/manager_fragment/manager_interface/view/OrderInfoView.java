package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;

/**
 * Created by zwj on 2017/4/14.
 */

public interface OrderInfoView {
    Context context();
    String eventId();
    long fromTime();
    String syncAllStat();
    void orderInfoSuccess(OrderInfo info);
    void orderInfoFailed(String errInfo);
}
