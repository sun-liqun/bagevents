package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnUploadEventInfoListener;

/**
 * Created by zwj on 2016/9/21.
 */
public interface UploadEventInfoInterface {
    void uploadLogo(Context mContext, int eventId, String userId, String textName, String textValue, OnUploadEventInfoListener listener);
}
