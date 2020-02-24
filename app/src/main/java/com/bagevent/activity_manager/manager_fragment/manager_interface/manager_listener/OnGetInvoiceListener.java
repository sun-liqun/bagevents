package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.InvoiceBean;

/**
 * Created by zwj on 2017/10/26.
 */

public interface OnGetInvoiceListener {
    void showInvoiceSuccess(InvoiceBean response);
    void showInvoiceFailed(String errInfo);
}
