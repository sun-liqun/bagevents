package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.IncomeData;

/**
 * Created by zwj on 2016/9/2.
 */
public interface OnGetIncomeListener {
    void showGetSuccess(IncomeData response);
    void showGetFailed();
}
