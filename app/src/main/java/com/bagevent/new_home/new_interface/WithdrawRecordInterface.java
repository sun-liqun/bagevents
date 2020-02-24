package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnWithdrawRecordListener;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public interface WithdrawRecordInterface {
    void withdrawRecord(Context mContext, String userId, int page, int pageSize, OnWithdrawRecordListener listener);
}
