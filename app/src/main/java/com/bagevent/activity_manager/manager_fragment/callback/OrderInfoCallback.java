package com.bagevent.activity_manager.manager_fragment.callback;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2017/4/14.
 */

public abstract class OrderInfoCallback extends Callback<OrderInfo> {
    @Override
    public OrderInfo parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        OrderInfo info = new Gson().fromJson(string,OrderInfo.class);
//        Log.e("OrderInfoCallback",string);
        return info;
    }
}
