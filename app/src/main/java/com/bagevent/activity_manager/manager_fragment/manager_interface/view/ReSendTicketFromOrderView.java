package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/10/31.
 */

public interface ReSendTicketFromOrderView {
    Context mContext();
    int eventId();
    int orderId();
    void resendSuccess(String response);
    void resendFailed(String errInfo);
}
