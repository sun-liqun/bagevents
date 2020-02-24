package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnModifyUserInfoListener;

/**
 * Created by zwj on 2016/7/1.
 */
public interface ModifyUserInfoInterface {
    void modifyUserInfo(Context mContext, String eventId, String attendId, String map, OnModifyUserInfoListener onModifyUserInfoListener);
}
