package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateFormListener;

/**
 * Created by zwj on 2016/9/26.
 */
public interface UpdateFormInterface {
    void updateForm(Context mContext,String detailEventId, String userId, int formFieldId, int type, String formFieldValue, OnUpdateFormListener listener);
}
