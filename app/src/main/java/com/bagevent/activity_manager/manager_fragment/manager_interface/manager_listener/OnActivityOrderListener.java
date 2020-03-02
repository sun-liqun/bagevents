package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

/**
 * Created by zwj on 2017/10/24.
 */

public interface OnActivityOrderListener {
    void showOrderSuccess(String response);
    void showOrderFailed(String errInfo);
}
