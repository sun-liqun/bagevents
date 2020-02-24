package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.CollectManagerData;
import com.bagevent.activity_manager.manager_fragment.data.CollectionInfoData;

/**
 * Created by zwj on 2017/6/13.
 */

public interface CollectionPointInfoView {
    Context mContext();
    String getEventId();
    String collectionId();
    void showCollectionInfoSuccess(CollectionInfoData response);
    void showCollectionInfoFailed(String errInfo);
}
