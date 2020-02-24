package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.FindUnreadData;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public interface FindExistUnReadView {
    Context mContext();
    String userId();
    void findExistUnReadMsg(FindUnreadData response);
    void findExistUnReadMsgFailed(String errInfo);
}
