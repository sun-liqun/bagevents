package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.AddFormItemData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/26.
 */
public abstract class AddFormItemCallback extends Callback<AddFormItemData> {
    @Override
    public AddFormItemData parseNetworkResponse(Response response, int id) throws Exception {
        String string  = response.body().string();
        AddFormItemData data = new Gson().fromJson(string,AddFormItemData.class);
        //Log.e("fsd",string);
        return data;
    }
}
