package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.AddFormResponse;
import com.bagevent.new_home.new_interface.AddFormInterface;
import com.bagevent.new_home.new_interface.GetLiveInterface;
import com.bagevent.new_home.new_interface.impl.AddCustomImpl;
import com.bagevent.new_home.new_interface.impl.GetLiveImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormListener;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetLiveUrlListener;
import com.bagevent.new_home.new_interface.new_view.AddFormView;
import com.bagevent.new_home.new_interface.new_view.GetLiveView;


public class GetLiveUrlPresenter {
    private GetLiveInterface getLiveInterface;
    private GetLiveView getLiveViewView;

    public GetLiveUrlPresenter(GetLiveView getLiveViewView) {
        this.getLiveInterface = new GetLiveImpl();
        this.getLiveViewView = getLiveViewView;
    }

    public void getEventLiveUrl() {
        getLiveInterface.getLiveUrl(getLiveViewView.mContext(), getLiveViewView.eventId(), new OnGetLiveUrlListener() {
            @Override
            public void getLiveUrlSuccess(String response) {
                getLiveViewView.eventGetLiveUrlSuccess(response);
            }

            @Override
            public void getLiveUrl(String errInfo) {
                getLiveViewView.eventGetLiveUrlFailed(errInfo);
            }
        });

    }


}
