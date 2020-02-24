package com.bagevent.home.home_interface;

import android.content.Context;

/**
 * Created by zwj on 2016/7/13.
 */
public interface GetCollectChildInterface {
    void getCollectChild(Context mContext,String collectionId, String eventId, OnGetCollectChildInterface listener);
}
