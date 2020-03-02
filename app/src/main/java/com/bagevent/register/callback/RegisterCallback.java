package com.bagevent.register.callback;

import android.util.Log;

import com.bagevent.register.data.RegisterData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/5/30.
 */
public abstract class RegisterCallback extends Callback<RegisterData>{
    @Override
    public RegisterData parseNetworkResponse(Response response,int id) throws Exception {
        String string = response.body().string();
        //Log.e("register-->",string);
        //RegisterData data = new Gson().fromJson(string,RegisterData.class);
        return new Gson().fromJson(string,RegisterData.class);
    }
}
