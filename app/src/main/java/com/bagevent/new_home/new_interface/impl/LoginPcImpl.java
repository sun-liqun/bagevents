package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.LoginPcInterface;
import com.bagevent.new_home.new_interface.listenerInterface.LoginPcListener;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

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
public class LoginPcImpl implements LoginPcInterface {
    @Override
    public void getLoginPcInfo(Context mContext, String confirmQrCode, int eventId, String functionTag, LoginPcListener listener) {
        OkHttpUtil.Post(mContext)
                .url(Constants.NEW_URL + Constants.LOGIN_PC+confirmQrCode)
                .addParams("eventId",eventId+"")
                .addParams("confirmQrCode",confirmQrCode)
                .addParams("functionTag",functionTag)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                           listener.loginPcSuccess();
                        }else {
                           listener.loginPcFailed(response.getRespObject());
                        }
                    }
                });
    }
}
