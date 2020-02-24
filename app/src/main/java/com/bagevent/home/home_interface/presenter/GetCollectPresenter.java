package com.bagevent.home.home_interface.presenter;

import com.bagevent.home.data.CollectDetailData;
import com.bagevent.home.home_interface.GetCollectChildInterface;
import com.bagevent.home.home_interface.OnGetCollectChildInterface;
import com.bagevent.home.home_interface.impls.GetCollectChildImpls;
import com.bagevent.home.home_interface.view.GetCollectChildView;

/**
 * Created by zwj on 2016/7/13.
 */
public class GetCollectPresenter {
    private GetCollectChildInterface getCollectChild;
    private GetCollectChildView getCollectChildView;

    public GetCollectPresenter(GetCollectChildView getCollectChildView) {
        this.getCollectChildView = getCollectChildView;
        this.getCollectChild = new GetCollectChildImpls();
    }

    public void getCollectChild() {
        getCollectChild.getCollectChild(getCollectChildView.mContext(),getCollectChildView.collectionId(), getCollectChildView.eventId(), new OnGetCollectChildInterface() {
            @Override
            public void showSuccess(CollectDetailData data) {
                getCollectChildView.showGetSuccess(data);
            }

            @Override
            public void showFailed() {
                getCollectChildView.showGetFailed();
            }
        });
    }
}
