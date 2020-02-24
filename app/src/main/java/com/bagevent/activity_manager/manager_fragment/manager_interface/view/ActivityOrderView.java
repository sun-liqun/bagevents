package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by zwj on 2017/10/24.
 */

public interface ActivityOrderView {
    Context mContext();
    String eventId();
    String userId();
    String fromTime();
    int page();

    void showOrderSuccess(String response);
    void showOrderSuccess2(String response);
    void showOrderFailed(String errInfo);
}
