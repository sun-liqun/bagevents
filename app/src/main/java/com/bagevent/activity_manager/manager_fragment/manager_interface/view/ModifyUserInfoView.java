package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.ModifyData;

import java.util.Map;

/**
 * Created by zwj on 2016/7/1.
 */
public interface ModifyUserInfoView {
    Context mContext();
    int eventId();
    String attendId();
    String infoMap();

    void showModifySuccess(ModifyData modifyData);
    void showModifyFailed(ModifyData modifyData);
}
