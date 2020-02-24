package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.GetAllFormData;

/**
 * Created by zwj on 2016/9/22.
 */
public interface OnGetAllFormListener {
    void getAllFormSuccess(GetAllFormData formType);
    void getAllFormFailed(String errInfo);
}
