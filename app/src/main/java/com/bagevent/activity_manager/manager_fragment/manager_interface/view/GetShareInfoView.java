package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.ShareInfoData;

/**
 * Created by zwj on 2016/12/5.
 */
public interface GetShareInfoView {
    Context mContext();
    String getEventId();
    String userId();
    void showShareInfo(ShareInfoData response);
    void showShareInfoErr(String errInfo);
}
