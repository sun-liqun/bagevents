package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnDeleteFormItemListener;

/**
 * Created by zwj on 2016/9/26.
 */
public interface DeleteFormItemInterface {
    void deleteFormItem(Context mContext, String detailEventId, String userId, int formFieldId, String itemName, OnDeleteFormItemListener listener);
}
