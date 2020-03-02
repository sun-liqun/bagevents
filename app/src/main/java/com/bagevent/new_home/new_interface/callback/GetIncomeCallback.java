package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.IncomeData;
import com.bagevent.util.LogUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/2.
 */
public abstract class GetIncomeCallback extends Callback<IncomeData>{
    @Override
    public IncomeData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        IncomeData data = new Gson().fromJson(string,IncomeData.class);
//        Log.e("GetIncomeCallback",string);
        return data;
    }
}
