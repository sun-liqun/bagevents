package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnCollectManagerListener;

/**
 * Created by zwj on 2016/7/15.
 */
public interface CollectManagerInterface {
    void getCollectList(Context mContext, String eventId, OnCollectManagerListener listener);
}
