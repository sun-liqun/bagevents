package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.LoginPcListener;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2020/01/13
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public interface LoginPcInterface {
    void getLoginPcInfo(Context mContext, String confirmQrCode, int eventId, String functionTag, LoginPcListener listener);
}
