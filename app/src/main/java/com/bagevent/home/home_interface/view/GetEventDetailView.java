package com.bagevent.home.home_interface.view;

import android.content.Context;

import com.bagevent.home.data.EventDetailData;

/**
 * Created by zwj on 2016/9/27.
 */
public interface GetEventDetailView {
    Context mContext();
    String getEventId();
    String userId();
    void eventDetailSuccess(EventDetailData response);
    void eventDetailFailed(String errInfo);

}
