package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnSaveEventTimeListener;

/**
 * Created by zwj on 2016/9/20.
 */
public interface SaveEventTimeInterface {
    void saveEventInfo(Context mContext, int eventId, String userId, String textName, String startTime, String endTime, OnSaveEventTimeListener listener);
}
