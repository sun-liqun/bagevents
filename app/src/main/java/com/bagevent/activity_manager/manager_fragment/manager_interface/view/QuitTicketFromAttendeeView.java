package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/11/2.
 */

public interface QuitTicketFromAttendeeView {
    Context mContext();
    int eventId();
    int orderId();
    String attendeeId();
    int isSendEmail();
    void showQuitSuccess(String response);
    void showQuitFailed(String errInfo);
}
