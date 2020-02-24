package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnSaveSponsorListener;

/**
 * Created by zwj on 2016/9/20.
 */
public interface SaveSponsorInterface {
    void saveSponsor(Context mContext, int eventId, String userId, String textName, String organizerIds, OnSaveSponsorListener listener);
}
