package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetFormTypeListener;

/**
 * Created by zwj on 2016/6/27.
 */
public interface GetFormTypeInterface {
    void getFormType(Context context,String eventId, OnGetFormTypeListener onGetFormTypeListener);
    void getBadgeFormType(Context context,String eventId,int sType,OnGetFormTypeListener listener);
}
