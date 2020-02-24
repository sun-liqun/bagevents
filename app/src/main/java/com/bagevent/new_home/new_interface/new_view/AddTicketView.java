package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/19.
 */
public interface AddTicketView {
    Context mContext();

    String userId();
    int eventId();
    String ticketName();
    String ticketCount();
    String ticketPrice();
    int audit();

    void addSuccess();
    void addFailed(String errInfo);
}
