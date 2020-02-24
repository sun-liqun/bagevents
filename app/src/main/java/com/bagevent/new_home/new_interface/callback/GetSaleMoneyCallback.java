package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.SaleMoneyData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/2.
 */
public abstract class GetSaleMoneyCallback extends Callback<SaleMoneyData> {
    @Override
    public SaleMoneyData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
//        Log.e("GetSaleMoneyCallback",string);
        SaleMoneyData data = new Gson().fromJson(string,SaleMoneyData.class);
        return data;
    }
}
