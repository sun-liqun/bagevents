package com.bagevent.home.home_interface;

import android.content.Context;

/**
 * Created by zwj on 2016/7/14.
 */
public interface PostCollectionInfoInterface {
    void postCollectionInfo(Context mContext,String collectionId, String barcode, String checkinTime, OnPostCollectionInfo listener);
}
