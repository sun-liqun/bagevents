package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.AddFormResponse;

/**
 * Created by zwj on 2016/9/23.
 */
public interface OnAddFormListener {
    void addFormSuccess(AddFormResponse response);
    void addFormFailed(String errInfo);
}
