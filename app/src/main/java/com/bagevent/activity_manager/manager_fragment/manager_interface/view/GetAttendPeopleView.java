package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.AttendPeople;

/**
 * Created by zwj on 2016/6/6.
 */
public interface GetAttendPeopleView {
    Context mContext();

    String getEventId();
    String getPageNum();
    String getUpdateTime();
    void showSuccessInfo(String people);
    void showFailInfo(String errInfo);

}
