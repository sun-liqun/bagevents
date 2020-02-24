package com.bagevent.home.home_interface;

import android.content.Context;

/**
 * Created by zwj on 2016/9/27.
 */
public interface GetEventDetailInterface {
    void eventDetailInterface(Context mContext, String getEventId, String userId, GetEventDetailListener listener);
}
