package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/21.
 */
public interface DeleteTicketView {
    Context mContext();
    int eventId();
    String userId();
    int ticketId();

    void deleteSuccess();
    void deleteFailed(String errInfo);
}
