package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/10/31.
 */

public interface ReSendTicketFromAttendeeView {
    Context mContext();
    int eventId();
    int orderId();
    String attendeeId();
    int sendToAttendee();
    void showSendSuccess(String response);
    void showSendFailed(String errInfo);
}
