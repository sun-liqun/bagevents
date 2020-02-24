package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnAddWithDrawAccount;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public interface AddWithdrawAccount {
    void addWithDrawAccount(Context mContext, String userId, int type,String accountName, String account, String bankName, OnAddWithDrawAccount listener);
}
