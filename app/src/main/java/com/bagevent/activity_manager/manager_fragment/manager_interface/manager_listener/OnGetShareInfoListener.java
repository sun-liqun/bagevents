package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.ShareInfoData;

/**
 * Created by zwj on 2016/12/5.
 */
public interface OnGetShareInfoListener {
    void shareInfo(ShareInfoData response);
    void shareErr(String errInfo);
}
