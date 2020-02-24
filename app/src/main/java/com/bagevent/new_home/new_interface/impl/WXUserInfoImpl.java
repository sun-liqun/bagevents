package com.bagevent.new_home.new_interface.impl;

import android.util.Log;

import com.bagevent.new_home.data.WXUserInfoData;
import com.bagevent.new_home.new_interface.WXUserInfoInterface;
import com.bagevent.new_home.new_interface.callback.WXUserInfoCallback;
import com.bagevent.new_home.new_interface.listenerInterface.WXUserInfoListener;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2017/1/3.
 */
public class WXUserInfoImpl implements WXUserInfoInterface {
    @Override
    public void getWXUserInfo(String accessToken, String openId, final WXUserInfoListener listener) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid="+openId;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new WXUserInfoCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("WXUserInfo","Get WXUserInfo Is Err");
                    }

                    @Override
                    public void onResponse(WXUserInfoData response, int id) {
                        if(response.getErrmsg() == null) {
                            listener.userInfo(response);
                        }else {
                            listener.userInfoErr();
                        }
                    }
                });
    }
}
