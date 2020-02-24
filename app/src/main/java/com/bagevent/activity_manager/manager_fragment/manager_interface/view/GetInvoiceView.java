package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.InvoiceBean;

/**
 * Created by zwj on 2017/10/26.
 */

public interface GetInvoiceView {
    Context mContext();
    int eventId();
    String orderNumber();

    void showInvoiceSuccess(InvoiceBean response);
    void showInvoiceFailed(String errInfo);
}
