package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.CollectionInfoData;

/**
 * Created by zwj on 2017/6/13.
 */

public interface CollectPointInfoListener {
    void showCollectPointInfo(CollectionInfoData response);
    void showCollectPointInfoFailed(String errInfo);
}
