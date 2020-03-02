package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;

/**
 * Created by zwj on 2016/6/2.
 */
public interface OnGetTicketListener {
    void getTicketSuccess(TicketInfo ticketInfo);
    void getTicketFailed(String errInfo);
}
