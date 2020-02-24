package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnAuditListener;

/**
 * Created by zwj on 2016/7/7.
 */
public interface AuditInterface {
    void audit(Context mContext, String eventId, String attendeeId, String audit, String updateTime, OnAuditListener listener);
}
