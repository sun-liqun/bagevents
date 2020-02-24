package com.bagevent.home.home_interface.view;

import android.content.Context;

/**
 * Created by zwj on 2016/7/14.
 */
public interface ExportCollectView {
    Context mContext();
    String collectionId();
    String eventId();
    String userEmail();
    void showExportSuccess();
    void showExportFailed();
}
