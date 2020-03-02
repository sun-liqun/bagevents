package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.StringData;

/**
 * Created by zwj on 2017/6/14.
 */

public interface CollectionInOutView {
    Context mContext();
    String collectionId();
    String checkInTime();
    String barcode();
    String state();
    void setCollectionSuccess(StringData stringData);
    void setCollectionFailed(String errInfo);
}
