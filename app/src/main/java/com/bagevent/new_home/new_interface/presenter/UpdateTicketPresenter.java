package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.UpdateTicketInterface;
import com.bagevent.new_home.new_interface.impl.UpdateTicketImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateTicketListener;
import com.bagevent.new_home.new_interface.new_view.UpdateTicketView;

/**
 * Created by zwj on 2016/9/20.
 */
public class UpdateTicketPresenter {
    private UpdateTicketInterface updateTicketInterface;
    private UpdateTicketView updateTicketView;

    public UpdateTicketPresenter(UpdateTicketView updateTicketView) {
        this.updateTicketInterface = new UpdateTicketImpl();
        this.updateTicketView = updateTicketView;
    }

    public void updateTicket() {
        updateTicketInterface.updateTicket(updateTicketView.mContext(),updateTicketView.eventId(), updateTicketView.userId(), updateTicketView.ticketId(), updateTicketView.ticketName(),
                updateTicketView.ticketCount(), updateTicketView.ticketPrice(), updateTicketView.audit(), new OnUpdateTicketListener() {
                    @Override
                    public void updateTicketSuccess() {
                        updateTicketView.updateSuccess();
                    }

                    @Override
                    public void updateTicketFailed(String errInfo) {
                        updateTicketView.updateFailed(errInfo);
                    }
                });
    }
}
