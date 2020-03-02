package com.bagevent.new_home.new_interface.callback;



import android.util.Log;

import com.bagevent.new_home.data.WXAccessTokenData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;


/**
 * Created by zwj on 2016/12/30.
 */
public abstract class WXGetAccessTokenCallback extends Callback<WXAccessTokenData> {
    @Override
    public WXAccessTokenData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        WXAccessTokenData data = new Gson().fromJson(string,WXAccessTokenData.class);
//        Log.e("WXGetAccessTokenBack",string);
        return data;
    }
}
