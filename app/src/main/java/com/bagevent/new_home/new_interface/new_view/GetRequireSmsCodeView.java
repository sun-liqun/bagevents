package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.register.data.GetSMSData;

/**
 * Created by zwj on 2016/11/9.
 */
public interface GetRequireSmsCodeView {
    Context mContext();
    String phoneNum();
    int source();
    void showSmsCode(GetSMSData response);
    void showSmsFailed(String errInfo);
}
