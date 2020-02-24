package com.bagevent.home.home_interface;

import android.content.Context;

/**
 * Created by zwj on 2016/7/14.
 */
public interface ExportCollectionInterface {
    void exportCollect(Context mContext,String collectionId, String eventId, String email, OnExportCollection listener );
}
