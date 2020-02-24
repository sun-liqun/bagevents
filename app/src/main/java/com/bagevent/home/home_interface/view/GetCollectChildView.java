package com.bagevent.home.home_interface.view;

import android.content.Context;

import com.bagevent.home.data.CollectDetailData;

/**
 * Created by zwj on 2016/7/13.
 */
public interface GetCollectChildView {
    Context mContext();
    String eventId();
    String collectionId();

    void showGetSuccess(CollectDetailData data);
    void showGetFailed();
}
