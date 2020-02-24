package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.CreateEventData;

/**
 * Created by zwj on 2016/9/19.
 */
public interface CreateEventView {
    Context mContext();

    String userIds();
    int type();

    void createSuccess(CreateEventData response);
    void createFailed(String errInfo);
}
