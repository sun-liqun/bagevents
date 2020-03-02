package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.ModifyInvoiceInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.ModifyInvoiceImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnModifyInvoiceListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ModifyCommonInvoiceView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ModifyVatmanInvoiceView;

/**
 * Created by WenJie on 2017/11/1.
 */

public class ModifyInvoicePresenter {
    private ModifyInvoiceInterface modifyInvoice;
    private ModifyVatmanInvoiceView vatmanInvoiceView;
    private ModifyCommonInvoiceView commonInvoiceView;

    public ModifyInvoicePresenter(ModifyVatmanInvoiceView vatmanInvoiceView) {
        this.modifyInvoice = new ModifyInvoiceImpl();
        this.vatmanInvoiceView = vatmanInvoiceView;
    }

    public ModifyInvoicePresenter(ModifyCommonInvoiceView commonInvoiceView) {
        this.modifyInvoice = new ModifyInvoiceImpl();
        this.commonInvoiceView = commonInvoiceView;
    }

    public void modifyCommonInvoice() {
        modifyInvoice.modifyCommonInvoice(commonInvoiceView.mContext(), commonInvoiceView.eventId(), commonInvoiceView.orderNumber(), commonInvoiceView.obtainWay(),
                commonInvoiceView.invoiceType(), commonInvoiceView.receiver(), commonInvoiceView.cellphone(), commonInvoiceView.address(), commonInvoiceView.invoiceTitle(), commonInvoiceView.invoiceItem(),
                commonInvoiceView.brief(), commonInvoiceView.taxNum(), commonInvoiceView.companyCodeType(), commonInvoiceView.companyCode(), commonInvoiceView.receiverType(),
                new OnModifyInvoiceListener() {
                    @Override
                    public void showModifyInvoiceSuccess(String response) {
                        commonInvoiceView.showModifySuccess(response);
                    }

                    @Override
                    public void showModifyInvoiceFailed(String errInfo) {
                        commonInvoiceView.showmodifyFailed(errInfo);
                    }
                });
    }

    public void modifyVatmanInvoice() {
        modifyInvoice.modifyVatmanInvoice(vatmanInvoiceView.mContext(), vatmanInvoiceView.eventId(), vatmanInvoiceView.orderNumber(), vatmanInvoiceView.obtainWay(), vatmanInvoiceView.invoiceType(),
                vatmanInvoiceView.receiver(), vatmanInvoiceView.cellphone(), vatmanInvoiceView.address(), vatmanInvoiceView.invoiceTitle(), vatmanInvoiceView.invoiceItem(),
                vatmanInvoiceView.brief(), vatmanInvoiceView.taxNum(), vatmanInvoiceView.companyCodeType(), vatmanInvoiceView.companyCode(), vatmanInvoiceView.receiverType(),
                vatmanInvoiceView.companyResAddr(), vatmanInvoiceView.companyFinanceContact(), vatmanInvoiceView.companyBank(), vatmanInvoiceView.bankAccount(),
                new OnModifyInvoiceListener() {
                    @Override
                    public void showModifyInvoiceSuccess(String response) {
                        vatmanInvoiceView.showModifySuccess(response);
                    }

                    @Override
                    public void showModifyInvoiceFailed(String errInfo) {
                        vatmanInvoiceView.showModifyFailed(errInfo);
                    }
                });
    }
}
