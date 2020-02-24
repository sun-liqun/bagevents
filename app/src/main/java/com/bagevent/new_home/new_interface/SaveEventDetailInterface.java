package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnSaveEventDetailListener;

/**
 * Created by zwj on 2016/10/10.
 */
public interface SaveEventDetailInterface {
    void saveEventDetail(Context mContext, int eventId, String userId, String textName, String eventContent, String eventBrief, OnSaveEventDetailListener listener);
}
