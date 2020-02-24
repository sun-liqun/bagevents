package com.bagevent.activity_manager.manager_fragment.callback;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.CollectManagerData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/7/15.
 */
public abstract class CollectManagerCallback extends Callback<CollectManagerData> {
    @Override
    public CollectManagerData parseNetworkResponse(Response response,int id) throws Exception {
        String string = response.body().string();
//        Log.e("CollectManagerCallback",string);

        CollectManagerData data = new Gson().fromJson(string,CollectManagerData.class);
        return data;
    }
}
