package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetAttendPeopleInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.GetAttendPeopleIms;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnCheckInListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AddPeopleCheckInView;

/**
 * Created by zwj on 2016/7/6.
 */
public class AddPeopleCheckInPresenter {
    private GetAttendPeopleInterface checkIn;
    private AddPeopleCheckInView checkInView;

    public AddPeopleCheckInPresenter(AddPeopleCheckInView checkInView) {
        this.checkInView = checkInView;
        this.checkIn = new GetAttendPeopleIms();
    }

    public void addPeopleCheckIn(String ref_code){
        checkIn.AddAttendeeCheckIn(checkInView.mContext(),checkInView.eventId() +"", ref_code, checkInView.checkInStatus(), checkInView.checkInupdateTime(), new OnCheckInListener() {
            @Override
            public void checkInSuccess(CheckIn checkIn) {
                checkInView.showCheckInSuccess(checkIn);
            }

            @Override
            public void checkInFailed(String errInfo) {
                checkInView.showCheckInFailed(errInfo);
            }
        });
    }

}
