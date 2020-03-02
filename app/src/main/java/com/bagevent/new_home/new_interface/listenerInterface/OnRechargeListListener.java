package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.MsgRecordData;

/**
 * Created by zwj on 2017/10/23.
 */

public interface OnRechargeListListener {
    void showRechargeList(MsgRecordData response);
    void showRechargeListErrInfo(String errInfo);
}
