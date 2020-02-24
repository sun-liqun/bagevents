package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnAddTicketListener;

/**
 * Created by zwj on 2016/9/19.
 */
public interface AddTicketInterface {
    void addTicket(Context mContext, String userId, int eventId, String ticketName, String ticketCount, String ticketPrice, int audit, OnAddTicketListener listener);
}
