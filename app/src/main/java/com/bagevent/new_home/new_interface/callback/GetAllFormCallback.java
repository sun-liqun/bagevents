package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.GetAllFormData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/22.
 */
public abstract class GetAllFormCallback extends Callback<GetAllFormData> {
    @Override
    public GetAllFormData parseNetworkResponse(Response response, int id) throws Exception {
        String string  = response.body().string();
        GetAllFormData formType = new Gson().fromJson(string,GetAllFormData.class);
       // Log.e("all form",string);
        return formType;
    }
}
