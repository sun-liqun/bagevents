package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/11/1.
 */

public interface ModifyCommonInvoiceView {
    Context mContext();
    int eventId();
    String orderNumber();
    int obtainWay();
    int invoiceType();
    String receiver();
    String cellphone();
    String address(); String invoiceTitle();
    String invoiceItem();
    String brief();
    String taxNum();
    int companyCodeType();
    String companyCode();
    int receiverType();

    void showModifySuccess(String response);
    void showmodifyFailed(String errInfo);
}
