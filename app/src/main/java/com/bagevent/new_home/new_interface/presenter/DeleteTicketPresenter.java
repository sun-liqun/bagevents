package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.DeleteTicketInterface;
import com.bagevent.new_home.new_interface.impl.DeleteTicketImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnDeleteTicketListener;
import com.bagevent.new_home.new_interface.new_view.DeleteTicketView;

/**
 * Created by zwj on 2016/9/21.
 */
public class DeleteTicketPresenter {
    private DeleteTicketInterface deleteTicketInterface;
    private DeleteTicketView deleteTicketView;

    public DeleteTicketPresenter(DeleteTicketView deleteTicketView) {
        this.deleteTicketInterface = new DeleteTicketImpl();
        this.deleteTicketView = deleteTicketView;
    }

    public void delete() {
        deleteTicketInterface.deleteTikcet(deleteTicketView.mContext(),deleteTicketView.eventId(), deleteTicketView.userId(), deleteTicketView.ticketId(), new OnDeleteTicketListener() {
            @Override
            public void deleteTicketSuccess() {
                deleteTicketView.deleteSuccess();
            }

            @Override
            public void deleteTicketFailed(String errInfo) {
                deleteTicketView.deleteFailed(errInfo);
            }
        });
    }
}
