package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/20.
 */
public interface SaveEventTimeView {
    Context mContext();

    int eventId();
    String userId();
    String textName();
    String startTime();
    String endTime();

    void showSaveTimeSuccess();
    void showSaveTimeFailed(String errInfo);
}
