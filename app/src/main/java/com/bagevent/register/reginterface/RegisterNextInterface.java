package com.bagevent.register.reginterface;

import android.content.Context;

import com.bagevent.register.reginterface.clicklistener.NextOnClickListener;

/**
 * Created by zwj on 2016/5/27.
 *
 * 判断是否为手机号的接口
 */
public interface RegisterNextInterface {
    void checkPhone(Context mcontext,String phone, NextOnClickListener nextOnClickListener);
}
