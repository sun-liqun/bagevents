package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.ModifyOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.ModifyOrderImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnModifyOrderListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AuditOrderView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ModifyOrderView;

/**
 * Created by WenJie on 2017/10/30.
 */

public class ModifyOrderPresenter {
    private ModifyOrderInterface modifyOrder;
    private ModifyOrderView modifyOrderView;
    private AuditOrderView auditOrderView;

    public ModifyOrderPresenter(ModifyOrderView modifyOrderView) {
        this.modifyOrder = new ModifyOrderImpl();
        this.modifyOrderView = modifyOrderView;
    }

    public ModifyOrderPresenter(AuditOrderView auditOrderView) {
        this.modifyOrder = new ModifyOrderImpl();
        this.auditOrderView = auditOrderView;
    }

    public void modifyOrder() {
        modifyOrder.modifyOrderInfo(modifyOrderView.mContext(), modifyOrderView.eventId(), modifyOrderView.orderNumber(), modifyOrderView.buyerName(),
                modifyOrderView.buyerEmail(), modifyOrderView.buyerCellphone(), modifyOrderView.areaCode(), new OnModifyOrderListener() {
                    @Override
                    public void showModifySuccess(String response) {
                        modifyOrderView.showModifySuccess(response);
                    }

                    @Override
                    public void showModifyFailed(String errInfo) {
                        modifyOrderView.showModifyFailed(errInfo);
                    }
                });
    }

    public void auditOrder() {
        modifyOrder.modifyOrderAuditStatus(auditOrderView.mContext(), auditOrderView.eventId(), auditOrderView.orderNumber(), auditOrderView.audit(), new OnModifyOrderListener() {
            @Override
            public void showModifySuccess(String response) {
                auditOrderView.showAuditSuccess(response);
            }

            @Override
            public void showModifyFailed(String errInfo) {
                auditOrderView.showAuditFailed(errInfo);
            }
        });
    }
}
