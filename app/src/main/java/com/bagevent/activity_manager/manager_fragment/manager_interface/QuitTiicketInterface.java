package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnQuickTicketListener;

/**
 * Created by WenJie on 2017/11/2.
 */

public interface QuitTiicketInterface {
    void quitTicketFromOrder(Context mContext, int eventId, int orderId, int isSendEmail, OnQuickTicketListener listener);

    void quitTicketFromAttnedee(Context mContext,int eventId,int orderId,int attendeeId,int isSendEmail,OnQuickTicketListener listener);
}
