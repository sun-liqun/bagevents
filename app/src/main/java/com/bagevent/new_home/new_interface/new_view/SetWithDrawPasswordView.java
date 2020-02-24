package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public interface SetWithDrawPasswordView {
    Context mContext();
    String userId();
    String password();
    String confirmPassword();
    String validCode();
    void showSetWithdrawPasswordSuccess(String response);
    void showSetWithdrawPasswordFailed(String errInfo);
}
