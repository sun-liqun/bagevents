package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.CollectManagerData;

/**
 * Created by zwj on 2016/7/15.
 */
public interface OnCollectManagerListener {
    void showCollectList(CollectManagerData data);
    void showFailed();
}
