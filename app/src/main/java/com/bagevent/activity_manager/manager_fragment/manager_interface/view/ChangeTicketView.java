package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/11/13.
 */

public interface ChangeTicketView {
    Context mContext();
    int userId();
    int eventId();
    int attendeeId();
    int ticketId();
    int notice();

    void changeTicketSuccess();
    void changeTicketFailed(String errInfo);
}
