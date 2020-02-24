package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.MsgRecordData;

/**
 * Created by zwj on 2017/10/23.
 */

public interface RechargeListView {
    Context mContext();
    String userId();
    int page();
    int pageSize();
    void showRechargeList(MsgRecordData response);
    void showRechargeListErrInfo(String errInfo);
}
