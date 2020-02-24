package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

/**
 * Created by WenJie on 2017/11/1.
 */

public interface OnConfirmOrderListener {
    void showConfirmSuccess(String response);
    void showConfirmFailed(String errInfo);
}
