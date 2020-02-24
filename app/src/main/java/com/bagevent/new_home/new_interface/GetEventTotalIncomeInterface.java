package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnGetEventTotalIncomeListener;

/**
 * Created by zwj on 2016/10/11.
 */
public interface GetEventTotalIncomeInterface {
    void eventTotalincome(Context mContext, String userId, OnGetEventTotalIncomeListener listener);
}
