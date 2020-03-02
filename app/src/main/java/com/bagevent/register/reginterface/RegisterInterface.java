package com.bagevent.register.reginterface;

import android.content.Context;

import com.bagevent.register.reginterface.clicklistener.RegisterOnClickInterface;

/**
 * Created by zwj on 2016/5/27.
 *
 * 注册的接口
 */
public interface RegisterInterface {
    void register(Context context,String phoneNum, String password, String identifyCode, RegisterOnClickInterface registerOnClickInterface);
}
