package com.bagevent.login.callback;

import android.util.Log;

import com.bagevent.login.model.UserInfo;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/5/25.
 */
public abstract class LoginCallback extends Callback<UserInfo>{
    @Override
    public UserInfo parseNetworkResponse(Response response,int id) throws Exception {
        String string = response.body().string();
       // UserInfo user = new Gson().fromJson(string,UserInfo.class);
//        Log.e("login string--->",string);
        return new Gson().fromJson(string,UserInfo.class);
    }
}
