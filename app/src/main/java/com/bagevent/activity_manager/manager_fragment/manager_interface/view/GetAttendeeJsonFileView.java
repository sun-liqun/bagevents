package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.AttendPeople;

/**
 * Created by zwj on 2017/9/7.
 */

public interface GetAttendeeJsonFileView {
    Context mContext();
    String getEventId();

    void attendeeJsonFile(String jsonFile);
    void attendeeJsonFileFailed(String errInfo);
}
