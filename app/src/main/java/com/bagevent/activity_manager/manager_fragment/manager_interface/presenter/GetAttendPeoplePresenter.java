package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.GetAttendPeopleInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.GetAttendPeopleIms;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetAttendPeopleListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendPeopleView;

/**
 * Created by zwj on 2016/6/6.
 */
public class GetAttendPeoplePresenter {
    private GetAttendPeopleInterface getAttendPeople;
    private GetAttendPeopleView getAttendPeopleView;
    public GetAttendPeoplePresenter(GetAttendPeopleView getAttendPeopleView) {
        this.getAttendPeople = new GetAttendPeopleIms();
        this.getAttendPeopleView = getAttendPeopleView;
    }

    public void getAttendPeoples(){
        getAttendPeople.getAttendPeople(getAttendPeopleView.mContext(),getAttendPeopleView.getEventId(), getAttendPeopleView.getPageNum(),new OnGetAttendPeopleListener() {
            @Override
            public void getAttendPeopleSuccess(String name) {
                getAttendPeopleView.showSuccessInfo(name);
            }

            @Override
            public void getAttendPeopleFailed(String errInfo) {
                getAttendPeopleView.showFailInfo(errInfo);
            }
        });
    }

    public void getRefreshAttendPeople(){
        getAttendPeople.getRefreshAttendPeople(getAttendPeopleView.mContext(),getAttendPeopleView.getEventId(), getAttendPeopleView.getUpdateTime(),getAttendPeopleView.getPageNum() ,new OnGetAttendPeopleListener() {
            @Override
            public void getAttendPeopleSuccess(String name) {
             //   Log.e("","success...."+name.getRespObject().getObjects().size());
                getAttendPeopleView.showSuccessInfo(name);
            }

            @Override
            public void getAttendPeopleFailed(String errInfo) {
                //Log.e("","failed...");
                getAttendPeopleView.showFailInfo(errInfo);
            }
        });
    }

}
