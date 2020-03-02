package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnUpLoadImageListener;

import java.io.File;

/**
 * Created by zwj on 2016/8/16.
 */
public interface UpLoadImageInterface {
    void upload(Context mContext,File file, String userId, OnUpLoadImageListener listener);
}
