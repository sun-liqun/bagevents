package com.bagevent.new_home.new_interface.impl;

import android.util.Log;

import com.bagevent.new_home.data.CheckAccessTokenData;
import com.bagevent.new_home.new_interface.CheckAccessTokenInterface;
import com.bagevent.new_home.new_interface.callback.CheckAccessTokenCallback;
import com.bagevent.new_home.new_interface.listenerInterface.CheckAccessTokenlistener;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2017/1/3.
 */
public class CheckAccessTokenImpl implements CheckAccessTokenInterface {
    @Override
    public void checkAccessToken(String accessToken, String openId, final CheckAccessTokenlistener listener) {
        String url = "https://api.weixin.qq.com/sns/auth?access_token=" + accessToken +  "&openid=" + openId;

        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new CheckAccessTokenCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("checkAccessToken","checkAccessToken is err");
                    }

                    @Override
                    public void onResponse(CheckAccessTokenData response, int id) {
                        if(response.getErrcode() == 0 && "ok".equals(response.getErrmsg())) {
                            listener.accessTokenEnable(response);
                        }else {
                            listener.accessTokenUnable();
                        }
                    }
                });
    }
}
