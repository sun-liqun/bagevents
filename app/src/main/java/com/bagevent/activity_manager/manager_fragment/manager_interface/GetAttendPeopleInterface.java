package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnCheckInListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetAttendPeopleListener;

/**
 * Created by zwj on 2016/6/6.
 */
public interface GetAttendPeopleInterface {
    void getAttendPeople(Context mContext, String eventId, String page, OnGetAttendPeopleListener onGetAttendPeopleListener);

    void getRefreshAttendPeople(Context mContext,String eventId,String from_time,String page,OnGetAttendPeopleListener onGetAttendPeopleListener);

    void AttendeeCheckIn(Context mContext,String eventId,String attendId,String checkInStatus,String upDateTime,OnCheckInListener onCheckInListener);//列表签到

    void AddAttendeeCheckIn(Context mContext,String eventId,String ref_code,String checkIn,String upDateTime,OnCheckInListener onCheckInListener);//添加参会人员签到
}
