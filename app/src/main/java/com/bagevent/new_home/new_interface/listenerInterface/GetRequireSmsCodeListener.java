package com.bagevent.new_home.new_interface.listenerInterface;

import com.bagevent.register.data.GetSMSData;

/**
 * Created by zwj on 2016/11/9.
 */
public interface GetRequireSmsCodeListener {
    void getSmsCodeSuccess(GetSMSData response);
    void getSmsCodeFailed(String errInfo);
}
