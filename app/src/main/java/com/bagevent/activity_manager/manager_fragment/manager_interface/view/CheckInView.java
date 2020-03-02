package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.CheckIn;

/**
 * Created by zwj on 2016/6/16.
 */
public interface CheckInView {
    Context mContext();

    String checkEventId();
    String checkAttendId();
    String checkStatus();
    String checkUpdateTime();

    void showCheckInSuccessInfo(CheckIn checkIn);
    void showCheckInFailed(String errInfo);
}
