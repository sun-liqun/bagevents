package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.StringData;

/**
 * Created by zwj on 2017/6/14.
 */

public interface CollectionInOutListener {
    void setCollectionInOutSuccess(StringData StringData);
    void setCollectionInOutFailed(String errInfo);
}
