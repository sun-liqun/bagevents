package com.bagevent.home.home_interface.callback;

import android.util.Log;

import com.bagevent.home.data.EventDetailData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/27.
 */
public abstract class GetEventDetailCallback extends Callback<EventDetailData> {
    @Override
    public EventDetailData parseNetworkResponse(Response response, int id) throws Exception {
        String string  = response.body().string();
//        Log.e("event detail-->",string);
        EventDetailData data = new Gson().fromJson(string,EventDetailData.class);
        return data;
    }
}
