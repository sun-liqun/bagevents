package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.EventTotalIncome;

/**
 * Created by zwj on 2016/10/11.
 */
public interface GetEventTotalIncomeView {
    Context mContext();

    String userId();

    void getEventTotalIncomeSuccess(EventTotalIncome response);
    void getEventTotalIncomeFailed(String errInfo);
}
