package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnLoadImageListener;

/**
 * Created by zwj on 2016/8/8.
 */
public interface LoadImageInterface {
    void loadImage(Context mContext, String url, OnLoadImageListener listener);
}
