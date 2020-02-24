package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.AddFormItemData;

/**
 * Created by zwj on 2016/9/26.
 */
public interface OnAddFormItemListener {
    void addItemSuccess(AddFormItemData response);
    void addItemFailed(String errInfo);
}
