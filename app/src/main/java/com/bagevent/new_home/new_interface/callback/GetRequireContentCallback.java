package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.RequireContentData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/12.
 */
public abstract class GetRequireContentCallback extends Callback<RequireContentData> {
    @Override
    public RequireContentData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        RequireContentData data = new Gson().fromJson(string,RequireContentData.class);
//        Log.e("GetRequireContentBack",string);
        return data;
    }
}
