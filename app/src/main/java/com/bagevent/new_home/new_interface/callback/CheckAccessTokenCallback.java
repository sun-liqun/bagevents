package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.CheckAccessTokenData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2017/1/3.
 */
public abstract class CheckAccessTokenCallback extends Callback<CheckAccessTokenData> {
    @Override
    public CheckAccessTokenData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        //Log.e("checkAccess",string);
        CheckAccessTokenData data = new Gson().fromJson(string,CheckAccessTokenData.class);
        return data;
    }
}
