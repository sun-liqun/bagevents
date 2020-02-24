package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnReSendListener;

/**
 * Created by WenJie on 2017/10/31.
 */

public interface ReSendTicketInterface {
    void reSendTicketFromOrder(Context mContext, int eventId, int orderId, OnReSendListener listener);
    void reSendTicketFromAttendee(Context mContext,int eventId,int orderId,String attendeeId,int sendToAttendee,OnReSendListener listener);
}
