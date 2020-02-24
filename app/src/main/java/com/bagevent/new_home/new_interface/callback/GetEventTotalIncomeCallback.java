package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.EventTotalIncome;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/10/11.
 */
public abstract class GetEventTotalIncomeCallback extends Callback<EventTotalIncome> {
    @Override
    public EventTotalIncome parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        EventTotalIncome data = new Gson().fromJson(string,EventTotalIncome.class);
//        Log.e("GetEventTotalIncomeBack",string);
        return data;
    }
}
