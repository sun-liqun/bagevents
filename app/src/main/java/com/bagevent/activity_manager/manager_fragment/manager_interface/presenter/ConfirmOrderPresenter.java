package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.ConfirmOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.ConfirmOrderImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnConfirmOrderListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ConfirmOrderView;

/**
 * Created by WenJie on 2017/11/1.
 */

public class ConfirmOrderPresenter {
    private ConfirmOrderInterface confirmOrder;
    private ConfirmOrderView confirmOrderView;

    public ConfirmOrderPresenter(ConfirmOrderView confirmOrderView) {
        this.confirmOrder = new ConfirmOrderImpl();
        this.confirmOrderView = confirmOrderView;
    }

    public void confirm() {
        confirmOrder.confirmOrder(confirmOrderView.mContext(), confirmOrderView.eventId(), confirmOrderView.orderId(), new OnConfirmOrderListener() {
            @Override
            public void showConfirmSuccess(String response) {
                confirmOrderView.showConfirmSuccess(response);
            }

            @Override
            public void showConfirmFailed(String errInfo) {
                confirmOrderView.showConfirmFailed(errInfo);
            }
        });
    }
}
