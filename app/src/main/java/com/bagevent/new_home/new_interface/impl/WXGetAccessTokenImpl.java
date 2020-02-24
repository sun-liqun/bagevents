package com.bagevent.new_home.new_interface.impl;

import android.util.Log;

import com.bagevent.new_home.data.WXAccessTokenData;
import com.bagevent.new_home.new_interface.WXGetAccessTokenInterface;
import com.bagevent.new_home.new_interface.callback.WXGetAccessTokenCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnWXGetAccessTokenListener;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/12/30.
 */
public class WXGetAccessTokenImpl implements WXGetAccessTokenInterface {
    @Override
    public void wxLogin(String code, String appId, String secret, final OnWXGetAccessTokenListener listener) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret="+ secret+"&code=" + code + "&grant_type=authorization_code";
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new WXGetAccessTokenCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wxLogin","get access_token failed");
                    }

                    @Override
                    public void onResponse(WXAccessTokenData response, int id) {
                        if(response.getErrmsg() == null) {
                            listener.getAccessTokenSuccess(response);
                        }else {
                            listener.getAccessTokenFailed();
                        }
                    }
                });
    }
}
