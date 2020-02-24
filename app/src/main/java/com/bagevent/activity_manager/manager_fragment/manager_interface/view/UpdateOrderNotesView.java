package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/11/2.
 */

public interface UpdateOrderNotesView {
    Context mContext();
    int eventId();
    String orderNumber();
    String notes();

    void editNotesSuccess(String response);
    void editNotesFailed(String errInfo);
}
