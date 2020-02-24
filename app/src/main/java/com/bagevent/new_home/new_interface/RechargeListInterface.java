package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnRechargeListListener;
import com.bagevent.new_home.new_interface.listenerInterface.OnRechargeMessageListener;

/**
 * Created by zwj on 2017/10/23.
 */

public interface RechargeListInterface {
    void rechargeList(Context mContext, String userId, int page, int pageSize, OnRechargeListListener listener);
}
