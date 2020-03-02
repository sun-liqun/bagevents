package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.AddFormResponse;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/23.
 */
public abstract class AddFormCallback extends Callback<AddFormResponse> {
    @Override
    public AddFormResponse parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        AddFormResponse data = new Gson().fromJson(string,AddFormResponse.class);
//        Log.e("AddFormCallback",string);
        return data;
    }
}
