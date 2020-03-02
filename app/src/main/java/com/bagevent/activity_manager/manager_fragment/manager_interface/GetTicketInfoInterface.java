package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetTicketListener;

/**
 * Created by zwj on 2016/6/2.
 */
public interface GetTicketInfoInterface {
    void getTicket(Context context, String eventId, OnGetTicketListener ticketListener);
}
