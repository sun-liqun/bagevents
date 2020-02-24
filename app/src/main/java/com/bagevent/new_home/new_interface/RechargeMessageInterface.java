package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnRechargeMessageListener;

/**
 * Created by zwj on 2017/10/11.
 */

public interface RechargeMessageInterface {
    void thirdRechargeMessage(Context context, String userId, String count, String payway, String accountType, OnRechargeMessageListener listener);
    void accountRechargeMessage(Context context, String userId, String count, String payway, String drawPassword, OnRechargeMessageListener listener);
}
