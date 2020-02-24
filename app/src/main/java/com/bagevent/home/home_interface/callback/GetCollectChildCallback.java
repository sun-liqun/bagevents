package com.bagevent.home.home_interface.callback;

import android.util.Log;

import com.bagevent.home.data.CollectDetailData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/7/13.
 */
public abstract class GetCollectChildCallback extends Callback<CollectDetailData>{
    @Override
    public CollectDetailData parseNetworkResponse(Response response,int id) throws Exception {
        String string  = response.body().string();
        CollectDetailData data = new Gson().fromJson(string, CollectDetailData.class);
//        Log.e("GetCollectChildCallback",string);
        return data;
    }
}
