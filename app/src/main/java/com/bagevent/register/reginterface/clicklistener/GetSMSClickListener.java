package com.bagevent.register.reginterface.clicklistener;

/**
 * Created by zwj on 2016/5/27.
 *
 * 实现获得手机号的功能
 */
public interface GetSMSClickListener {
    void doCountDown();
    void errInfo(String errInfo);
    void setToastMsg();
}
