package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/11/1.
 */

public interface ConfirmOrderView {
    Context mContext();
    int eventId();
    int orderId();

    void showConfirmSuccess(String response);
    void showConfirmFailed(String errInfo);
}
