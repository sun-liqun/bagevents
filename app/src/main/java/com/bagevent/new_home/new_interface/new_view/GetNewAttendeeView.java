package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.NewAttendeeData;

/**
 * Created by zwj on 2016/9/2.
 */
public interface GetNewAttendeeView {
    Context mContext();

    String userId();
    int size();

    void showGetNewAttendeeSuccess(NewAttendeeData response);
    void showGetNewAttendeeFailed();
}
