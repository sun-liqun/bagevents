package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormListener;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetLiveUrlListener;

/**
 * Created by zwj on 2016/9/23.
 */
public interface GetLiveInterface {
    void getLiveUrl(Context mContext, int eventId, OnGetLiveUrlListener listener);
}
