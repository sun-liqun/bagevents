package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.ChangeTicketListener;

/**
 * Created by WenJie on 2017/11/13.
 */

public interface ChangeTicketInterface {
    void changeTicket(Context mContext, int userId, int eventId,int attendeeId ,int ticketId, int notice, ChangeTicketListener listener);
}
