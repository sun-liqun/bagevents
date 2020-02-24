package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.WithdrawRecordData;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public interface WithdrawRecordView {
    Context mContext();
    String userId();
    int page();
    int pageSize();

    void showWithDrawRecordSuccess(WithdrawRecordData response);
    void showWithDrawRecordFailed(String errInfo);
}
