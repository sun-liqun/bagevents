package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.WithdrawRecordData;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public interface OnWithdrawRecordListener {
    void showWithdrawRecordSuccess(WithdrawRecordData data);
    void showWithddrawRecordFailed(String errInfo);
}
