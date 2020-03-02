package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetAttendeeJsonFile;

/**
 * Created by zwj on 2017/9/7.
 */

public interface GetAttendeeJsonFileInterface {
    void getAttendeeJsonFile(Context mContext,String eventId,OnGetAttendeeJsonFile listener);


}
