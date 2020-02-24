package com.bagevent.home.home_interface;

import com.bagevent.home.data.EventDetailData;

/**
 * Created by zwj on 2016/9/27.
 */
public interface GetEventDetailListener {
    void getEventDetailSuccess(EventDetailData response);
    void getEventDetailFailed(String errInfo);
}
