package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/11/6.
 */

public interface AuditOrderView {
    Context mContext();
    int eventId();
    String orderNumber();
    int audit();
    void showAuditSuccess(String response);
    void showAuditFailed(String errInfo);
}
