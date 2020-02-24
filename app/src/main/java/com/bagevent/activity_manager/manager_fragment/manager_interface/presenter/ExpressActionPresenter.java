package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;


import com.bagevent.activity_manager.manager_fragment.data.OrderHandleData;
import com.bagevent.activity_manager.manager_fragment.data.OrderServiceData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.ExpressActionInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.ExPressActionImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnExpressHandleListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnOrderHandleListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnOrderServiceListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.OrderHandleView;

/**
 * Created by ZWJ on 2017/12/26 0026.
 */

public class ExpressActionPresenter {
    private ExpressActionInterface exPressAction;
    private OrderHandleView orderHandleView;

    public ExpressActionPresenter(OrderHandleView orderHandleView) {
        this.exPressAction = new ExPressActionImpl();
        this.orderHandleView = orderHandleView;
    }

    public void orderHandle() {
        exPressAction.orderHandle(orderHandleView.mContext(),orderHandleView.requestData(), orderHandleView.eBusinessID(), orderHandleView.requestType(),
                orderHandleView.dataSign(), orderHandleView.dataType(), new OnOrderHandleListener() {
            @Override
            public void orderHandleSuccess(OrderHandleData response) {
                orderHandleView.orderHandleSuccess(response);
            }

            @Override
            public void orderHandleFailed(String errCode) {
                orderHandleView.orderHandleFailed(errCode);
            }
        });
    }

    public void queryExpress() {
        exPressAction.queryAction(orderHandleView.mContext(),orderHandleView.requestData(), orderHandleView.eBusinessID(), orderHandleView.requestType(),
                orderHandleView.dataSign(),  new OnExpressHandleListener() {
            @Override
            public void expressHandleListenerSuccess() {
                orderHandleView.querySuccess();
            }

            @Override
            public void expressHandleListenerFailed() {
                orderHandleView.queryFailed();
            }
        });
    }

    public void orderService() {
        exPressAction.eOrderService(orderHandleView.mContext(),orderHandleView.requestData(), orderHandleView.eBusinessID(), orderHandleView.requestType(),
                orderHandleView.dataSign(), orderHandleView.dataType(), new OnOrderServiceListener() {
            @Override
            public void orderServiceSuccess(OrderServiceData response) {
                orderHandleView.orderServiceSuccess(response);
            }

            @Override
            public void orderServiceFailed() {
                orderHandleView.orderServiceFailed();
            }
        });
    }
}
