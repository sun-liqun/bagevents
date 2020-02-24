package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by zwj on 2016/7/7.
 */
public interface AuditView {
    Context mContext();

    int eventId();
    String attendeeId();
    String upDateTime();

    void showAuditSuccess();

    void showAuditFailed();
}
