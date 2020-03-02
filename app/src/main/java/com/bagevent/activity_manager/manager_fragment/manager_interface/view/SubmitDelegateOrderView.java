package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by zwj on 2017/6/20.
 */

public interface SubmitDelegateOrderView {
    Context mContext();
    String buyWay();
    int eventId();
    String payType();
    String attendeeMap();
    String badgeMap();
    String ticketId();
    void submitDelegateOrderSuccess();
    void submitDelegateOrderFailed();
}
