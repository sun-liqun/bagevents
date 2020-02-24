package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.CheckIn;

/**
 * Created by zwj on 2016/7/6.
 */
public interface AddPeopleCheckInView {
    Context mContext();

    int eventId();
    String checkInStatus();
    String checkInupdateTime();

    void showCheckInSuccess(CheckIn checkIn);
    void showCheckInFailed(String errInfo);
}
