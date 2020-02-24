package com.bagevent.home.home_interface.view;

import android.content.Context;

import com.bagevent.home.data.ExportData;

/**
 * Created by zwj on 2016/7/14.
 */
public interface PostCollectionView {
    Context mContext();
    String collectionId();
    String barcode();
    String checkInTime();

    void showPostSuccess(ExportData response);
    void showPostFailed();
}
