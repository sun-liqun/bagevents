package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.ModifyData;

/**
 * Created by zwj on 2016/7/1.
 */
public interface OnModifyUserInfoListener {
    void modifyUserInfoSuccess(ModifyData modifyData);
    void modifyUserInoFailed(ModifyData modifyData);
}
