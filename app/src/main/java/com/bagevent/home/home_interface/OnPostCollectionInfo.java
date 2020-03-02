package com.bagevent.home.home_interface;

import com.bagevent.home.data.ExportData;

/**
 * Created by zwj on 2016/7/14.
 */
public interface OnPostCollectionInfo {
    void postSuccess(ExportData response);
    void postFailed();
}
