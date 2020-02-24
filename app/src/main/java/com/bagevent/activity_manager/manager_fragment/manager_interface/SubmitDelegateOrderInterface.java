package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.SubmitDelegateOrderListener;

/**
 * Created by zwj on 2017/6/20.
 */

public interface SubmitDelegateOrderInterface {
    void submitDelegate(Context mContext, String eventId,String ticketId ,String buyway, String payType, String attendeeMap, String badgeMap, SubmitDelegateOrderListener listener);
}
