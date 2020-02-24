package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.SubmitDelegateOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.SubmitDelegateOrderImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.SubmitDelegateOrderListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SubmitDelegateOrderView;

/**
 * Created by zwj on 2017/6/20.
 */

public class SubmitDelegateOrderPresenter {
    private SubmitDelegateOrderInterface submitDelegateOrder;
    private SubmitDelegateOrderView submitDelegateOrderView;

    public SubmitDelegateOrderPresenter(SubmitDelegateOrderView submitDelegateOrderView) {
        this.submitDelegateOrder = new SubmitDelegateOrderImpl();
        this.submitDelegateOrderView = submitDelegateOrderView;
    }

    public void submitDelegate(String refcode) {
        submitDelegateOrder.submitDelegate(submitDelegateOrderView.mContext(), submitDelegateOrderView.eventId() +"", submitDelegateOrderView.ticketId(),submitDelegateOrderView.buyWay(), submitDelegateOrderView.payType(),
                submitDelegateOrderView.attendeeMap(), submitDelegateOrderView.badgeMap(), new SubmitDelegateOrderListener() {
                    @Override
                    public void submitDelegateSuccess() {
                        submitDelegateOrderView.submitDelegateOrderSuccess();
                    }

                    @Override
                    public void submitDelegateFailed() {
                        submitDelegateOrderView.submitDelegateOrderFailed();
                    }
                });
    }
}
