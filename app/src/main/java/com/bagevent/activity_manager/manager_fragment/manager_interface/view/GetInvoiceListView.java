package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by ZWJ on 2017/12/27 0027.
 */

public interface GetInvoiceListView {

    Context mContext();
    String userId();
    int eventId();
    int page();
    String fromTiem();

    void showInvoiceListSuccess(String response);
    void showInvoiceListFailed(String errInfo);
}
