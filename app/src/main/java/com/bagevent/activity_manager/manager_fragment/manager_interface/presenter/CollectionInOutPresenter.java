package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.CollectionInOutInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.CollectionInOutImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.CollectionInOutListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CollectionInOutView;

/**
 * Created by zwj on 2017/6/14.
 */

public class CollectionInOutPresenter {
    private CollectionInOutInterface collectionInOut;
    private CollectionInOutView collectionInOutView;

    public CollectionInOutPresenter(CollectionInOutView collectionInOutView) {
        this.collectionInOut = new CollectionInOutImpl();
        this.collectionInOutView = collectionInOutView;
    }

    public void setCollectionInOut() {
        collectionInOut.setCollectionInout(collectionInOutView.mContext(), collectionInOutView.collectionId(), collectionInOutView.barcode(), collectionInOutView.state(), collectionInOutView.checkInTime(), new CollectionInOutListener() {
            @Override
            public void setCollectionInOutSuccess(StringData stringData) {
                collectionInOutView.setCollectionSuccess(stringData);
            }

            @Override
            public void setCollectionInOutFailed(String errInfo) {
                collectionInOutView.setCollectionFailed(errInfo);
            }
        });
    }
}
