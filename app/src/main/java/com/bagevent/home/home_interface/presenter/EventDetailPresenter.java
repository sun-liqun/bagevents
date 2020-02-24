package com.bagevent.home.home_interface.presenter;

import com.bagevent.home.data.EventDetailData;
import com.bagevent.home.home_interface.GetEventDetailInterface;
import com.bagevent.home.home_interface.GetEventDetailListener;
import com.bagevent.home.home_interface.impls.GetEventDetailImpls;
import com.bagevent.home.home_interface.view.GetEventDetailView;

/**
 * Created by zwj on 2016/9/27.
 */
public class EventDetailPresenter {
    private GetEventDetailInterface getEventDetailInterface;
    private GetEventDetailView getEventDetailView;

    public EventDetailPresenter(GetEventDetailView getEventDetailView) {
        this.getEventDetailInterface = new GetEventDetailImpls();
        this.getEventDetailView = getEventDetailView;
    }

    public void getEventDetail() {
        getEventDetailInterface.eventDetailInterface(getEventDetailView.mContext(),getEventDetailView.getEventId(), getEventDetailView.userId(), new GetEventDetailListener() {
            @Override
            public void getEventDetailSuccess(EventDetailData response) {
                getEventDetailView.eventDetailSuccess(response);
            }

            @Override
            public void getEventDetailFailed(String errInfo) {
                getEventDetailView.eventDetailFailed(errInfo);
            }
        });
    }
}
