package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.SingleAttendeeData;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public interface SingleAttendeeView {
    Context mContext();
    String userId();
    int eventId();
    int orderId();
    void showSingleAttendee(String response);
    void showSingleAttendeeFailed(String errInfo);
}
