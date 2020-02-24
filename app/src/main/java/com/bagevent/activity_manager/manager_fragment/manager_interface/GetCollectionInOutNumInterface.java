package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.GetCollectionInOutNumListener;

/**
 * Created by zwj on 2017/6/15.
 */

public interface GetCollectionInOutNumInterface {
    void getInOutNum(Context mContext, String collectionId, GetCollectionInOutNumListener listener);
}
