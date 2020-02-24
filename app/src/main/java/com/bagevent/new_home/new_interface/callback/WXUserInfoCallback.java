package com.bagevent.new_home.new_interface.callback;

import com.bagevent.new_home.data.WXUserInfoData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2017/1/3.
 */
public abstract class WXUserInfoCallback extends Callback<WXUserInfoData> {
    @Override
    public WXUserInfoData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        WXUserInfoData data = new Gson().fromJson(string,WXUserInfoData.class);
        return data;
    }
}
