package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.ActivityOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.ActivityOrderImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnActivityOrderListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ActivityOrderView;

/**
 * Created by zwj on 2017/10/24.
 */

public class ActivityOrderPresenter {
    private ActivityOrderInterface activityOrder;
    private ActivityOrderView activityOrderView;

    public ActivityOrderPresenter(ActivityOrderView activityOrderView) {
        this.activityOrder = new ActivityOrderImpl();
        this.activityOrderView = activityOrderView;
    }

    /**
     * 第一次加载订单数据
     */
    public void getOrderFirsst() {
        activityOrder.getActivityOrderFirst(activityOrderView.mContext(), activityOrderView.userId(), activityOrderView.eventId(), activityOrderView.page(), new OnActivityOrderListener() {
            @Override
            public void showOrderSuccess(String response) {
                activityOrderView.showOrderSuccess(response);
            }

            @Override
            public void showOrderFailed(String errInfo) {
                activityOrderView.showOrderFailed(errInfo);
            }
        });
    }

    public void getOrderFromPage() {
        activityOrder.getActivityOrderFirst(activityOrderView.mContext(), activityOrderView.userId(), activityOrderView.eventId(), activityOrderView.page(), new OnActivityOrderListener() {
            @Override
            public void showOrderSuccess(String response) {
                activityOrderView.showOrderSuccess2(response);
            }

            @Override
            public void showOrderFailed(String errInfo) {
                activityOrderView.showOrderFailed(errInfo);
            }
        });
    }

    public void getOrderFromTime() {
        activityOrder.getActivityOrderFromTime(activityOrderView.mContext(), activityOrderView.userId(), activityOrderView.eventId(), activityOrderView.fromTime(), new OnActivityOrderListener() {
            @Override
            public void showOrderSuccess(String response) {
                activityOrderView.showOrderSuccess(response);
            }

            @Override
            public void showOrderFailed(String errInfo) {
                activityOrderView.showOrderFailed(errInfo);
            }
        });
    }
}
