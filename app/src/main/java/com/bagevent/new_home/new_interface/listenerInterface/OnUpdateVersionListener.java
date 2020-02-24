package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.UpdateVersionData;

/**
 * Created by zwj on 2016/12/7.
 */
public interface OnUpdateVersionListener {
    void getVersionInfo(UpdateVersionData response);
    void getVersionInfoFailed(String errInfo);
}
