package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/19.
 */
public interface UpdateTicketView {
    Context mContext();
    int eventId();
    String userId();
    int ticketId();
    String ticketName();
    String ticketCount();
    String ticketPrice();
    int audit();

    void updateSuccess();
    void updateFailed(String errInfo);
}
