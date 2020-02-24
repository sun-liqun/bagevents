package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetAttendPeopleInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.GetAttendPeopleIms;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnCheckInListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.CheckInView;

/**
 * Created by zwj on 2016/6/16.
 */
public class CheckInPresenter {
    private GetAttendPeopleInterface getAttendPeople;
    private CheckInView checkInView;

    public CheckInPresenter(CheckInView checkInView) {
        this.getAttendPeople = new GetAttendPeopleIms();
        this.checkInView = checkInView;
    }

    public void attendCheckIn() {
        getAttendPeople.AttendeeCheckIn(checkInView.mContext(), checkInView.checkEventId(), checkInView.checkAttendId(), checkInView.checkStatus(), checkInView.checkUpdateTime(), new OnCheckInListener() {
            @Override
            public void checkInSuccess(CheckIn checkIn) {
                checkInView.showCheckInSuccessInfo(checkIn);
            }

            @Override
            public void checkInFailed(String errInfo) {
                checkInView.showCheckInFailed(errInfo);
            }
        });
    }

    public void checkIn() {
        getAttendPeople.AddAttendeeCheckIn(checkInView.mContext(), checkInView.checkEventId(), checkInView.checkAttendId(), checkInView.checkStatus(), checkInView.checkUpdateTime(), new OnCheckInListener() {
            @Override
            public void checkInSuccess(CheckIn checkIn) {
                checkInView.showCheckInSuccessInfo(checkIn);
            }

            @Override
            public void checkInFailed(String errInfo) {
                checkInView.showCheckInFailed(errInfo);
            }
        });
    }
}
