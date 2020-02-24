package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateVersionListener;

/**
 * Created by zwj on 2016/12/7.
 */
public interface UpdateVersionInterface {
    void updateVersion(Context mContext, OnUpdateVersionListener listener);
}
