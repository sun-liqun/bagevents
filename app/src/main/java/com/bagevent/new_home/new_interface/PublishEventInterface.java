package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnPublishEventListener;

/**
 * Created by zwj on 2016/10/10.
 */
public interface PublishEventInterface {
    void publish(Context mContext, int eventId, String userId, OnPublishEventListener listener);
}
