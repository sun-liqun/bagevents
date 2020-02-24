package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.RechargeMsgData;

/**
 * Created by zwj on 2017/10/11.
 */

public interface RechargeMessageView {
    Context mContext();
    String userId();
    String count();
    String payway();
    String accountType();
    String drawPassword();
    void thirdRechargeSuccess(RechargeMsgData response);
    void thirdRechargeFailed(String errInfo);
    void rechargeSuccess(RechargeMsgData response);
    void rechargeFailed(String errInfo);
}
