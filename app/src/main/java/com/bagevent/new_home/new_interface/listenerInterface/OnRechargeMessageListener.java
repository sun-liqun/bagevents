package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.RechargeMsgData;

/**
 * Created by zwj on 2017/10/11.
 */

public interface OnRechargeMessageListener {
    void rechargeSuccess(RechargeMsgData rechargeMsgData);
    void rechargeFailed(String errInfo);
}
