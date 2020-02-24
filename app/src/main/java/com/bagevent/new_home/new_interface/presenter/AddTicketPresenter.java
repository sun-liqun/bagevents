package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.AddTicketInterface;
import com.bagevent.new_home.new_interface.impl.AddTicketImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddTicketListener;
import com.bagevent.new_home.new_interface.new_view.AddTicketView;

/**
 * Created by zwj on 2016/9/19.
 */
public class AddTicketPresenter {
    private AddTicketInterface addTicketInterface;
    private AddTicketView addTicketView;

    public AddTicketPresenter(AddTicketView addTicketView) {
        this.addTicketInterface = new AddTicketImpl();
        this.addTicketView = addTicketView;
    }

    public void addTicket() {
        addTicketInterface.addTicket(addTicketView.mContext(),addTicketView.userId(), addTicketView.eventId(), addTicketView.ticketName(), addTicketView.ticketCount(), addTicketView.ticketPrice(), addTicketView.audit(), new OnAddTicketListener() {
            @Override
            public void addSuccess() {
                addTicketView.addSuccess();
            }

            @Override
            public void addFalied(String errInfo) {
                addTicketView.addFailed(errInfo);
            }
        });
    }
}
