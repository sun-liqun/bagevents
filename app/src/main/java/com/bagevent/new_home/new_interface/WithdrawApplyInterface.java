package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnWithdrawApplyListener;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public interface WithdrawApplyInterface {
    void applyWithdraw(Context mContext, String userId,String applyAmount, String account, String accountName, String password, String bankName, int type, OnWithdrawApplyListener listener);
}
