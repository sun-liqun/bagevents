package com.bagevent.register.registerview;

import android.content.Context;

/**
 * Created by zwj on 2016/5/27.
 */
public interface GetSMSCountDown {
    Context mContext();
    String getPhoneNum();
    int getSource();
    void startCutDown(Long mills);
    void stopCutDown();
    void showErrInfo(String errInfo);
    void showToastMsg();
    void textClickEnable();
    void textClickUnable();
}
