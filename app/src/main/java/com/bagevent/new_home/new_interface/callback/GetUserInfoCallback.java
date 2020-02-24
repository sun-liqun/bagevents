package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.UserInfoData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/10/11.
 */
public abstract class GetUserInfoCallback extends Callback<UserInfoData> {
    @Override
    public UserInfoData parseNetworkResponse(Response response, int id) throws Exception {
        String string  = response.body().string();
        UserInfoData data = new Gson().fromJson(string,UserInfoData.class);
//        Log.e("GetUserInfoCallback",string);
        return data;
    }
}
