package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.ChangeTicketInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.ChangeTicketImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.ChangeTicketListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ChangeTicketView;

/**
 * Created by WenJie on 2017/11/13.
 */

public class ChangeTicketPresenter {
    private ChangeTicketInterface changeTicket;
    private ChangeTicketView changeTicketView;

    public ChangeTicketPresenter(ChangeTicketView changeTicketView) {
        this.changeTicket = new ChangeTicketImpl();
        this.changeTicketView = changeTicketView;
    }

    public void changeTicket() {
        changeTicket.changeTicket(changeTicketView.mContext(), changeTicketView.userId(), changeTicketView.eventId(), changeTicketView.attendeeId(), changeTicketView.ticketId(), changeTicketView.notice(), new ChangeTicketListener() {
            @Override
            public void showChangeTicketSuccess() {
                changeTicketView.changeTicketSuccess();
            }

            @Override
            public void showChangeTicketFailed(String errInfo) {
                changeTicketView.changeTicketFailed(errInfo);
            }
        });
    }
}
