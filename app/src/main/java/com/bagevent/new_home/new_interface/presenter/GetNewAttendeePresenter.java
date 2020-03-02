package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.NewAttendeeData;
import com.bagevent.new_home.new_interface.GetNewAttendeeInterface;
import com.bagevent.new_home.new_interface.impl.GetNewAttendeeImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetNewAttendeeListener;
import com.bagevent.new_home.new_interface.new_view.GetNewAttendeeView;

/**
 * Created by zwj on 2016/9/2.
 */
public class GetNewAttendeePresenter {
    private GetNewAttendeeInterface getNewAttendee;
    private GetNewAttendeeView getNewAttendeeView;

    public GetNewAttendeePresenter(GetNewAttendeeView getAttendPeopleView) {
        this.getNewAttendeeView = getAttendPeopleView;
        this.getNewAttendee = new GetNewAttendeeImpl();
    }

    public void newAttendee() {
        getNewAttendee.getNewAttendee(getNewAttendeeView.mContext(),getNewAttendeeView.userId(), getNewAttendeeView.size(), new OnGetNewAttendeeListener() {
            @Override
            public void showGetSuccess(NewAttendeeData response) {
                getNewAttendeeView.showGetNewAttendeeSuccess(response);
            }

            @Override
            public void showGetFailed() {
                getNewAttendeeView.showGetNewAttendeeFailed();
            }
        });
    }
}
