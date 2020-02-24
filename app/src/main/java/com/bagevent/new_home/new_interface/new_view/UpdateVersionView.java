package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.UpdateVersionData;

/**
 * Created by zwj on 2016/12/7.
 */
public interface UpdateVersionView {
    Context mContext();
    void versionInfo(UpdateVersionData response);
    void errVersionInfo(String errInfo);
}
