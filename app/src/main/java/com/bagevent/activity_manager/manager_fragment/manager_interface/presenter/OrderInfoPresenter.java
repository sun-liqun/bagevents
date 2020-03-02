package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.OrderInfoInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.OrderInfoImpls;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnOrderInfoListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.OrderInfoView;

/**
 * Created by zwj on 2017/4/14.
 */

public class OrderInfoPresenter {
    private OrderInfoInterface orderInfo;
    private OrderInfoView orderInfoView;

    public OrderInfoPresenter(OrderInfoView orderInfoView) {
        this.orderInfoView = orderInfoView;
        this.orderInfo = new OrderInfoImpls();
    }

    public void orderInfo() {
        orderInfo.getOrderInfo(orderInfoView.context(), orderInfoView.eventId(), orderInfoView.fromTime(), orderInfoView.syncAllStat(), new OnOrderInfoListener() {
            @Override
            public void getOrderInfoSuccess(OrderInfo orderInfo) {
                orderInfoView.orderInfoSuccess(orderInfo);
            }

            @Override
            public void getOrderInfoFailed(String errInfo) {
                orderInfoView.orderInfoFailed(errInfo);
            }
        });
    }
}
