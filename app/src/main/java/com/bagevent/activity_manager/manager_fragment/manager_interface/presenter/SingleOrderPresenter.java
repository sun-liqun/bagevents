package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.SingleOrderData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.SingleOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.SingleOrderImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnSingleOrderListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SingOrderView;

/**
 * Created by ZWJ on 2017/12/4 0004.
 */

public class SingleOrderPresenter {
    private SingleOrderInterface singleOrder;
    private SingOrderView singOrderView;

    public SingleOrderPresenter(SingOrderView singOrderView) {
        this.singleOrder = new SingleOrderImpl();
        this.singOrderView = singOrderView;
    }

    public void getSingleOrder() {
        singleOrder.getSingleOrder(singOrderView.mContext(), singOrderView.eventId(),singOrderView.userId(), singOrderView.orderNumber(), singOrderView.orderId(),new OnSingleOrderListener() {
            @Override
            public void showSingleOrderSuccess(SingleOrderData response) {
                singOrderView.showSingleOrderSuccess(response);
            }

            @Override
            public void showSingleOrderFailed(String errInfo) {
                singOrderView.showSingleOrderFailed(errInfo);
            }
        });
    }
}
