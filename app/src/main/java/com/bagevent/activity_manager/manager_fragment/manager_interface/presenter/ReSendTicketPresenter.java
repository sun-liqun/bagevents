package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.ReSendTicketInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.ReSendTicketImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnReSendListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ReSendTicketFromAttendeeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ReSendTicketFromOrderView;

/**
 * Created by WenJie on 2017/10/31.
 */

public class ReSendTicketPresenter {
    private ReSendTicketInterface reSendTicket;
    private ReSendTicketFromOrderView sendTicketFromOrder;
    private ReSendTicketFromAttendeeView sendTicketFromAttendee;

    public ReSendTicketPresenter(ReSendTicketFromOrderView sendTicketFromOrder) {
        this.reSendTicket = new ReSendTicketImpl();
        this.sendTicketFromOrder = sendTicketFromOrder;
    }

    public ReSendTicketPresenter(ReSendTicketFromAttendeeView sendTicketFromAttendee) {
        this.reSendTicket = new ReSendTicketImpl();
        this.sendTicketFromAttendee = sendTicketFromAttendee;
    }

    public void resendTicketFromOrder() {
        reSendTicket.reSendTicketFromOrder(sendTicketFromOrder.mContext(), sendTicketFromOrder.eventId(), sendTicketFromOrder.orderId(), new OnReSendListener() {
            @Override
            public void showSendSuccess(String response) {
                sendTicketFromOrder.resendSuccess(response);
            }

            @Override
            public void showSendFailed(String errInfo) {
                sendTicketFromOrder.resendFailed(errInfo);
            }
        });
    }

    public void resendTicketFromAttendee() {
        reSendTicket.reSendTicketFromAttendee(sendTicketFromAttendee.mContext(), sendTicketFromAttendee.eventId(), sendTicketFromAttendee.orderId(),
                sendTicketFromAttendee.attendeeId(), sendTicketFromAttendee.sendToAttendee(), new OnReSendListener() {
                    @Override
                    public void showSendSuccess(String response) {
                        sendTicketFromAttendee.showSendSuccess(response);
                    }

                    @Override
                    public void showSendFailed(String errInfo) {
                        sendTicketFromAttendee.showSendFailed(errInfo);
                    }
                });
    }
}
