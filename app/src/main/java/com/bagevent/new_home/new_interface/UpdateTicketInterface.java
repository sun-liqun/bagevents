package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateTicketListener;

/**
 * Created by zwj on 2016/9/19.
 */
public interface UpdateTicketInterface {
    void updateTicket(Context mContext,int eventId, String userId, int ticketId, String ticketName, String ticketCount, String ticketPrice, int audit, OnUpdateTicketListener listener);
}
