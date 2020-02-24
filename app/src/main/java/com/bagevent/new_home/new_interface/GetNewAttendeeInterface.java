package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnGetNewAttendeeListener;

/**
 * Created by zwj on 2016/9/2.
 */
public interface GetNewAttendeeInterface {
    void getNewAttendee(Context mContext, String userId, int size, OnGetNewAttendeeListener listener);
}
