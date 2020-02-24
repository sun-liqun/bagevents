package com.bagevent.register.callback;

import android.util.Log;

import com.bagevent.register.data.GetSMSData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/5/27.
 */
public abstract class GetSMSCallback extends Callback<GetSMSData> {
    @Override
    public GetSMSData parseNetworkResponse(Response response,int id) throws Exception {
        String string = response.body().string();
        GetSMSData sms = new Gson().fromJson(string,GetSMSData.class);
//        Log.e("sms",string);
        return sms;
    }
}
