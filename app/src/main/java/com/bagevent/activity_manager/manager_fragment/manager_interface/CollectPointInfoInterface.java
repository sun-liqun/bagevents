package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.CollectPointInfoListener;

/**
 * Created by zwj on 2017/6/13.
 */

public interface CollectPointInfoInterface {
    void getCollectPointInfo(Context mContext, String eventId, String collectId, CollectPointInfoListener listener);
}
