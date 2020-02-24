package com.bagevent.register.reginterface.clicklistener;

import com.bagevent.register.data.RegisterData;

/**
 * Created by zwj on 2016/5/27.
 *
 * 执行注册
 */
public interface RegisterOnClickInterface {
    void registerSuccess(RegisterData response);
    void registerFailed(String err);
}
