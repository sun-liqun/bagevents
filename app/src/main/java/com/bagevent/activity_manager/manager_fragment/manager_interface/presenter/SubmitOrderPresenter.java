package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.ModifyData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.SubmitOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.SubmitOrderimps;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnSubmitOrderListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SubmitOrderView;

/**
 * Created by zwj on 2016/7/5.
 */
public class SubmitOrderPresenter {
    private SubmitOrderInterface submitOrder;
    private SubmitOrderView submitOrderView;

    public SubmitOrderPresenter(SubmitOrderView submitOrderView) {
        this.submitOrderView = submitOrderView;
        this.submitOrder = new SubmitOrderimps();
    }

    public void submitOrder(final String ref_code){
        submitOrder.submitOrder(submitOrderView.mContext(),submitOrderView.eventId() +"", submitOrderView.map(), submitOrderView.buyWay(), new OnSubmitOrderListener() {
            @Override
            public void submitSuccess(ModifyData submitData) {
                submitOrderView.showSuccess(submitData,ref_code);
            }

            @Override
            public void submitFailed(ModifyData submitData) {
                submitOrderView.showFailed(submitData);
            }
        });
    }
}
