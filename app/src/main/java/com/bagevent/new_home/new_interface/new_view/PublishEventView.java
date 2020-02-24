package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/10/10.
 */
public interface PublishEventView {
    Context mContext();

    int eventId();
    String userIds();
    void publishEventSuccess();
    void publishEventFailed(String errInfo);
}
