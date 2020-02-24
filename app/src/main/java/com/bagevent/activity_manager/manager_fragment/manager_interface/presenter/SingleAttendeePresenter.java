package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.SingleAttendeeInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.SingleAttendeeImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnSingleAttendeeListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SingleAttendeeFromIdView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SingleAttendeeView;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public class SingleAttendeePresenter {
    private SingleAttendeeInterface singleAttendee;
    private SingleAttendeeView singleAttendeeView;
    private SingleAttendeeFromIdView singleAttendeeFromIdView;

    public SingleAttendeePresenter(SingleAttendeeView singleAttendeeView) {
        this.singleAttendee = new SingleAttendeeImpl();
        this.singleAttendeeView = singleAttendeeView;
    }

    public SingleAttendeePresenter(SingleAttendeeFromIdView singleAttendeeFromIdView) {
        this.singleAttendee = new SingleAttendeeImpl();
        this.singleAttendeeFromIdView = singleAttendeeFromIdView;
    }

    public void singleAttendeeFromId() {
        singleAttendee.singleAttendeeFromAttendeeId(singleAttendeeFromIdView.mContext(), singleAttendeeFromIdView.eventId(), Integer.parseInt(singleAttendeeFromIdView.attendeeId()), singleAttendeeFromIdView.userId(), new OnSingleAttendeeListener() {
            @Override
            public void showSingleAttendeeSuccess(String response) {
                singleAttendeeFromIdView.showSingleAttendeeFromId(response);
            }

            @Override
            public void showSingleAttendeeFailed(String errInfo) {
                singleAttendeeFromIdView.showSingleAttendeeFromId(errInfo);
            }
        });
    }

    public void singleAttendee() {
        singleAttendee.singleAttendee(singleAttendeeView.mContext(), singleAttendeeView.eventId(), singleAttendeeView.orderId(), singleAttendeeView.userId(), new OnSingleAttendeeListener() {
            @Override
            public void showSingleAttendeeSuccess(String response) {
                singleAttendeeView.showSingleAttendee(response);
            }

            @Override
            public void showSingleAttendeeFailed(String errInfo) {
                singleAttendeeView.showSingleAttendeeFailed(errInfo);
            }
        });
    }
}
