package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public interface WithdrawApplyView {
    Context mContext();
    String userId();
    String applyAmount();
    String account();
    String accountName();
    String password();
    String bankName();
    int type();

    void showApplySuccess(String response);
    void showApplyFailed(String errInfo);
}
