package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by ZWJ on 2017/12/19 0019.
 */

public interface SingleAttendeeFromIdView {
    Context mContext();
    String userId();
    int eventId();
    String attendeeId();
    void showSingleAttendeeFromId(String response);
    void showSingleAttendeeFailedFromId(String errInfo);
}
