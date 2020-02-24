package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/11/1.
 */

public interface ModifyVatmanInvoiceView {
    Context mContext();
    int eventId();
    String orderNumber();
    int obtainWay();
    int invoiceType();
    String receiver();
    String cellphone();
    String address();
    String invoiceTitle();
    String invoiceItem();
    String brief();
    String taxNum();
    int companyCodeType();
    String companyCode();
    int receiverType();
    String companyResAddr();
    String companyFinanceContact();
    String companyBank();
    String bankAccount();

    void showModifySuccess(String response);

    void showModifyFailed(String errInfo);
}
