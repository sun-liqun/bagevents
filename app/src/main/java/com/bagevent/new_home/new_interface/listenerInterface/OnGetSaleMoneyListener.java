package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.SaleMoneyData;

/**
 * Created by zwj on 2016/9/2.
 */
public interface OnGetSaleMoneyListener {
    void showGetSuccess(SaleMoneyData response);
    void showGetFailed();
}
