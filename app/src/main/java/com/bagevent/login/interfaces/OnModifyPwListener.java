package com.bagevent.login.interfaces;

/**
 * Created by ZWJ on 2018/1/11 0011.
 */

public interface OnModifyPwListener {
    void modifySuccess(String response);
    void modifyFailed(String errInfo);
}
