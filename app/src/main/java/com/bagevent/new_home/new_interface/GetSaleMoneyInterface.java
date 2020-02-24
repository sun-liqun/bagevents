package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.OnGetSaleMoneyListener;

/**
 * Created by zwj on 2016/9/2.
 */
public interface GetSaleMoneyInterface {
    void getSaleMoney(Context mContext, String userId, int size, OnGetSaleMoneyListener listener);
}
