package com.bagevent.new_home.new_interface;

import android.content.Context;

import com.bagevent.new_home.new_interface.listenerInterface.GetRequireSmsCodeListener;

/**
 * Created by zwj on 2016/11/9.
 */
public interface GetReqireSmsCodeInterface {
    void getSmsCode(Context mContext,String phoneNum, int source, GetRequireSmsCodeListener listener);
}
