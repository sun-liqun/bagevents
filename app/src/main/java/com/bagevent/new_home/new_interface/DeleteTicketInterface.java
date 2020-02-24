package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnDeleteTicketListener;

/**
 * Created by zwj on 2016/9/21.
 */
public interface DeleteTicketInterface {
    void deleteTikcet(Context mContext, int eventId, String userId, int ticketId, OnDeleteTicketListener listener);
}
