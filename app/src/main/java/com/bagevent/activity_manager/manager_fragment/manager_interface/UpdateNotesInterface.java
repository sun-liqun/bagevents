package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnEditNotesListener;

/**
 * Created by WenJie on 2017/11/2.
 */

public interface UpdateNotesInterface {
    void editAttendeeNotes(Context mContext, int eventId, int attnedId, String notes, OnEditNotesListener listener);
    void editOrderNotes(Context mContext,int eventId,String orderNumber,String notes,OnEditNotesListener listener);
}
