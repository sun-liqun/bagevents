package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.NewAttendeeData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/2.
 */
public abstract class GetNewAttendeeCallback extends Callback<NewAttendeeData>{
    @Override
    public NewAttendeeData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        NewAttendeeData data = new Gson().fromJson(string,NewAttendeeData.class);
//        Log.e("GetNewAttendeeCallback",string);
        return data;
    }
}
