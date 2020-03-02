package com.bagevent.new_home.new_interface.listenerInterface;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public interface OnGetWithdrawValidCodeListener {
    void showValidCodeSuccess(String response);
    void showValidCodeFailed(String errInfo);
}
