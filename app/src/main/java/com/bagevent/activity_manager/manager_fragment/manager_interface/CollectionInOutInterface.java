package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.CollectionInOutListener;

/**
 * Created by zwj on 2017/6/14.
 */

public interface CollectionInOutInterface {
    void setCollectionInout(Context mContext,String collectionId, String barcode,String state, String checkinTime, CollectionInOutListener listener);
}
