package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetShareInfoListener;

/**
 * Created by zwj on 2016/12/5.
 */
public interface GetShareInfoInterface {
    void shareInfo(Context mContext,String eventId, String userId, OnGetShareInfoListener listener);
}
