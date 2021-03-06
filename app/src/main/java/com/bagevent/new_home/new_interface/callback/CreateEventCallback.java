package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.CreateEventData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/19.
 */
public abstract class CreateEventCallback extends Callback<CreateEventData> {
    @Override
    public CreateEventData parseNetworkResponse(Response response, int id) throws Exception {
        String string  = response.body().string();
        CreateEventData data = new Gson().fromJson(string,CreateEventData.class);
//        Log.e("CreateEventCallback",string);
        return data;
    }
}
