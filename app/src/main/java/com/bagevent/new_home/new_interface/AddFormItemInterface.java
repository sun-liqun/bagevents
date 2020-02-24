package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormItemListener;

/**
 * Created by zwj on 2016/9/26.
 */
public interface AddFormItemInterface {
    void addFormItem(Context mContext, String detailEventId, String userId, int fieldId, OnAddFormItemListener listener);
}
