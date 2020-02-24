package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.new_home.data.EventTotalIncome;

/**
 * Created by zwj on 2016/10/11.
 */
public interface OnGetEventTotalIncomeListener {
    void totalIncomeSuccess(EventTotalIncome response);
    void totalIncomeFailed(String errInfo);
}
