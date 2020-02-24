package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnGetIncomeListener;

/**
 * Created by zwj on 2016/9/2.
 */
public interface GetIncomeInterface {
    void getIncome(Context mContext, String userId, OnGetIncomeListener listener);
}
