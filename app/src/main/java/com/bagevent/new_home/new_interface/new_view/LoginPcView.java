package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.PostChatMessageData;

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
public interface LoginPcView {
    Context mContext();
    String confirmQrCode();
    int eventId();
    String functionTag();

    void loginPcSuccess();
    void loginPcFailed(String errInfo);
}
