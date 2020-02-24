package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.SaleMoneyData;

/**
 * Created by zwj on 2016/9/2.
 */
public interface GetSaleMoneyView {
    Context mContext();

    String userId();
    int size();

    void showGetSaleMoneySuccess(SaleMoneyData response);
    void showGetSaleMoneyFailed();
}
