package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.NewAttendeeData;

/**
 * Created by zwj on 2016/9/2.
 */
public interface OnGetNewAttendeeListener {
    void showGetSuccess(NewAttendeeData response);
    void showGetFailed();
}
