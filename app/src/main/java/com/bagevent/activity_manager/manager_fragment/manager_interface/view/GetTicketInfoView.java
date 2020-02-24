package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;

/**
 * Created by zwj on 2016/6/2.
 */
public interface GetTicketInfoView {
    Context mContext();
    void showProgress();
    void hideProgress();
    String getEventId();
    Context getContext();
    void showTicketInfo(TicketInfo ticketInfo);
    void showErrInfo(String errInfo);
}
