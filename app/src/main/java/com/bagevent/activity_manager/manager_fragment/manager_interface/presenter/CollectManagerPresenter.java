package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.CollectManagerData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.CollectManagerInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.CollectManagerImpls;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnCollectManagerListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CollectManagerView;

/**
 * Created by zwj on 2016/7/15.
 */
public class CollectManagerPresenter {
    private CollectManagerInterface collectManager;
    private CollectManagerView collectManagerView;

    public CollectManagerPresenter(CollectManagerView collectManagerView) {
        this.collectManagerView = collectManagerView;
        this.collectManager = new CollectManagerImpls();
    }

    public void getCollectList() {
        collectManager.getCollectList(collectManagerView.mContext(),collectManagerView.eventId(), new OnCollectManagerListener() {
            @Override
            public void showCollectList(CollectManagerData data) {
                collectManagerView.showCollectListSuccess(data);
            }

            @Override
            public void showFailed() {
                collectManagerView.showCollectListFailed();
            }
        });
    }
}
