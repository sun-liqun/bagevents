package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.CheckIn;

/**
 * Created by zwj on 2016/6/16.
 */
public interface OnCheckInListener {
    void checkInSuccess(CheckIn checkIn);
    void checkInFailed(String errInfo);
}
