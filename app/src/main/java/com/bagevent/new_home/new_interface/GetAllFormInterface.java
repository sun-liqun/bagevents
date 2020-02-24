package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnGetAllFormListener;

/**
 * Created by zwj on 2016/9/22.
 */
public interface GetAllFormInterface {
    void getAllForm(Context mContext, String eventId, String userId, OnGetAllFormListener listener);
}
