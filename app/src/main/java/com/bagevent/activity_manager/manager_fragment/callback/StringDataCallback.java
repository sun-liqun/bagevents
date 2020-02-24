package com.bagevent.activity_manager.manager_fragment.callback;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.util.LogUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/8/16.
 */
public abstract class StringDataCallback extends Callback<StringData> {
    @Override
    public StringData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.i("--------------response",string);
//        Log.e("StringDataCallback",string);
     //   StringData data = new Gson().fromJson(string,StringData.class);
        return new Gson().fromJson(string,StringData.class);
    }
}
