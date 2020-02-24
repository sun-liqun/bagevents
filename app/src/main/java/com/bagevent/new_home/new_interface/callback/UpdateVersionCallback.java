package com.bagevent.new_home.new_interface.callback;

import android.telecom.Call;
import android.util.Log;

import com.bagevent.new_home.data.UpdateVersionData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/12/7.
 */
public abstract class UpdateVersionCallback extends Callback<UpdateVersionData> {
    @Override
    public UpdateVersionData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        UpdateVersionData data = new Gson().fromJson(string,UpdateVersionData.class);
//        Log.e("UpdateVersionCallback",string);
        return data;
    }
}
