package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnActivityOrderListener;

/**
 * Created by zwj on 2017/10/24.
 */

public interface ActivityOrderInterface {
    void getActivityOrderFirst(Context mContext, String userId,String eventId, int page, OnActivityOrderListener listener);

    void getActivityOrderFromTime(Context mContext,String userId,String eventId,String updateTime,OnActivityOrderListener listener);

}
