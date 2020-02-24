package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.CollectionInfoData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.CollectPointInfoInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.CollectionPointInfoImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.CollectPointInfoListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CollectionPointInfoView;

/**
 * Created by zwj on 2017/6/13.
 */

public class CollectionPointInfoPresenter {
    private CollectPointInfoInterface collectPointInfoInterface;
    private CollectionPointInfoView collectionPointInfoView;

    public CollectionPointInfoPresenter(CollectionPointInfoView collectionPointInfoView) {
        this.collectPointInfoInterface = new CollectionPointInfoImpl();
        this.collectionPointInfoView = collectionPointInfoView;
    }

    public void getCollectionInfo() {
        collectPointInfoInterface.getCollectPointInfo(collectionPointInfoView.mContext(), collectionPointInfoView.getEventId(), collectionPointInfoView.collectionId(), new CollectPointInfoListener() {
            @Override
            public void showCollectPointInfo(CollectionInfoData response) {
                collectionPointInfoView.showCollectionInfoSuccess(response);
            }

            @Override
            public void showCollectPointInfoFailed(String errInfo) {
                collectionPointInfoView.showCollectionInfoFailed(errInfo);
            }
        });
    }
}
