package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/11/2.
 */

public interface QuitTicketFromOrderView {
    Context mContext();
    int eventId();
    int orderId();
    int isSendEmail();
    void showQuitSuccess(String response);
    void showQuitFailed(String errInfo);
}
