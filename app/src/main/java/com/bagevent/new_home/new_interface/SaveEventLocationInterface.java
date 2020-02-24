package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnSaveEventLocation;

/**
 * Created by zwj on 2016/9/20.
 */
public interface SaveEventLocationInterface {
    void saveLocation(Context mContext, int eventId, String userId, String textName , int addrType, String address, OnSaveEventLocation listener);
}
