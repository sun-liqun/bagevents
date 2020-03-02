package com.bagevent.activity_manager.manager_fragment.callback;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.ModifyData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/7/5.
 */
public abstract class SubmitOrderCallback extends Callback<ModifyData>{
    @Override
    public ModifyData parseNetworkResponse(Response response,int id) throws Exception {
        String string = response.body().string();
//        Log.e("SubmitOrderCallback",string);
        ModifyData data = new Gson().fromJson(string,ModifyData.class);
        return data;
    }
}
