package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by zwj on 2017/6/15.
 */

public interface GetCollectionInOutView {
    Context mContext();
    String collectionId();
    void showCollectionInOutNumSuccess(String num);
    void showCollectionInOutNumFailed(String errInfo);
}
