package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnSingleAttendeeListener;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public interface SingleAttendeeInterface {
    void singleAttendee(Context mContext, int eventId, int orderId, String userId, OnSingleAttendeeListener listener);

    void singleAttendeeFromAttendeeId(Context mContext,int eventId,int attendeeId,String userId,OnSingleAttendeeListener listener);
}
