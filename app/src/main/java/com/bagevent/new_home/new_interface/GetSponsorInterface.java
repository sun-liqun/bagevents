package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnGetSponsorListener;

/**
 * Created by zwj on 2016/9/18.
 */
public interface GetSponsorInterface {
    void getSponsor(Context mContext, String userId, OnGetSponsorListener listener);
}
