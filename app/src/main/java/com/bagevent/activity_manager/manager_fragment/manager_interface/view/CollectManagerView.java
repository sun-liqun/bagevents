package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.CollectManagerData;

/**
 * Created by zwj on 2016/7/15.
 */
public interface CollectManagerView {
    Context mContext();

    String eventId();

    void showCollectListSuccess(CollectManagerData data);

    void showCollectListFailed();
}
