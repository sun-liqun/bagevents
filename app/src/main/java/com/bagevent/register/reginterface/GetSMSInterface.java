package com.bagevent.register.reginterface;

import android.content.Context;

import com.bagevent.register.reginterface.clicklistener.GetSMSClickListener;

/**
 * Created by zwj on 2016/5/27.
 *
 * 获得短信验证码的接口
 */
public interface GetSMSInterface {
    void getSMS(Context mContext, String phoneNum, int source , GetSMSClickListener getSMSClickListener);
}
