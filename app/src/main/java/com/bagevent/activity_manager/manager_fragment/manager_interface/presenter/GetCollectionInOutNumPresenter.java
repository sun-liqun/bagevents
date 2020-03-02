package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.GetCollectionInOutNumInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.GetCollectionInOutNumImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.GetCollectionInOutNumListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetCollectionInOutView;

/**
 * Created by zwj on 2017/6/15.
 */

public class GetCollectionInOutNumPresenter {
    private GetCollectionInOutNumInterface getCollectionInOutNum;
    private GetCollectionInOutView getCollectionInOutView;

    public GetCollectionInOutNumPresenter(GetCollectionInOutView getCollectionInOutView) {
        this.getCollectionInOutNum = new GetCollectionInOutNumImpl();
        this.getCollectionInOutView = getCollectionInOutView;
    }

    public void getCollectionInOutNum() {
        getCollectionInOutNum.getInOutNum(getCollectionInOutView.mContext(), getCollectionInOutView.collectionId(), new GetCollectionInOutNumListener() {
            @Override
            public void showCollectionInOutNum(String num) {
                getCollectionInOutView.showCollectionInOutNumSuccess(num);
            }

            @Override
            public void showCollectionInOutNumFailed(String errInfo) {
                getCollectionInOutView.showCollectionInOutNumFailed(errInfo);
            }
        });
    }
}
