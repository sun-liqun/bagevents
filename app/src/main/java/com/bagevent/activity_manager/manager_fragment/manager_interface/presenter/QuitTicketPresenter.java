package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.QuitTiicketInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.QuitTicketImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnQuickTicketListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.QuitTicketFromAttendeeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.QuitTicketFromOrderView;

/**
 * Created by WenJie on 2017/11/2.
 */

public class QuitTicketPresenter {
    private QuitTiicketInterface quitTiicket;
    private QuitTicketFromOrderView orderView;
    private QuitTicketFromAttendeeView attendeeView;

    public QuitTicketPresenter(QuitTicketFromOrderView orderView) {
        this.quitTiicket = new QuitTicketImpl();
        this.orderView = orderView;
    }

    public QuitTicketPresenter(QuitTicketFromAttendeeView attendeeView) {
        this.quitTiicket = new QuitTicketImpl();
        this.attendeeView = attendeeView;
    }

    public void quitTicketFromOrderView() {
        quitTiicket.quitTicketFromOrder(orderView.mContext(), orderView.eventId(), orderView.orderId(), orderView.isSendEmail(), new OnQuickTicketListener() {
            @Override
            public void showQuitSuccess(String response) {
                orderView.showQuitSuccess(response);
            }

            @Override
            public void showQuitFailed(String errInfo) {
                orderView.showQuitFailed(errInfo);
            }
        });
    }

    public void quitTicketFromAttendeeView() {
        quitTiicket.quitTicketFromAttnedee(attendeeView.mContext(), attendeeView.eventId(), attendeeView.orderId(), Integer.parseInt(attendeeView.attendeeId()), attendeeView.isSendEmail(), new OnQuickTicketListener() {
            @Override
            public void showQuitSuccess(String response) {
                attendeeView.showQuitSuccess(response);
            }

            @Override
            public void showQuitFailed(String errInfo) {
                attendeeView.showQuitFailed(errInfo);
            }
        });
    }
}
