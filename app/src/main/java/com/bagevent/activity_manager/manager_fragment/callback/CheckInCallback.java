package com.bagevent.activity_manager.manager_fragment.callback;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/6/16.
 */
public abstract class CheckInCallback extends Callback<CheckIn> {
    @Override
    public CheckIn parseNetworkResponse(Response response,int id) throws Exception {
        String string = response.body().string();
//        Log.e("CheckInCallback----->",string);
        CheckIn checkIn = new Gson().fromJson(string,CheckIn.class);
        return checkIn;
    }
}
