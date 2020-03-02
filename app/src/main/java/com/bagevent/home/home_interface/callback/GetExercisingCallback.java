package com.bagevent.home.home_interface.callback;

import android.util.Log;

import com.bagevent.home.data.ExercisingData;
import com.bagevent.util.LogUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/5/31.
 */
public abstract class GetExercisingCallback extends Callback<ExercisingData> {

    @Override
    public ExercisingData parseNetworkResponse(Response response,int id) throws Exception {
        String string = response.body().string();
//        Log.e("活动列表11",string+"F");

        ExercisingData data = new Gson().fromJson(string,ExercisingData.class);
        return data;
    }
}
