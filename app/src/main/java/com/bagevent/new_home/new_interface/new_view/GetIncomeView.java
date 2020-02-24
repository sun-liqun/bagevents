package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.IncomeData;

/**
 * Created by zwj on 2016/9/2.
 */
public interface GetIncomeView {
    Context mContext();

    String userId();
    void showGetIncomeSuccess(IncomeData response);
    void showGetIncomeFailed();
}
