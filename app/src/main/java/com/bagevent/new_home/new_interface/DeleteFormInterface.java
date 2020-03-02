package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnDeleteFormFieldIdListener;

/**
 * Created by zwj on 2016/9/22.
 */
public interface DeleteFormInterface {
    void deleteForm(Context mContext, String eventId, String userId, int formFieldId, OnDeleteFormFieldIdListener listener);
}
