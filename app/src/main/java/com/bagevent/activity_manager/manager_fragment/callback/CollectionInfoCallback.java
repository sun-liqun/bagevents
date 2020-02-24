package com.bagevent.activity_manager.manager_fragment.callback;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.CollectionInfoData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2017/6/15.
 */

public abstract class CollectionInfoCallback extends Callback<CollectionInfoData> {
    private static final String TAG = "CollectionInfoCallback";
    @Override
    public CollectionInfoData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
//        Log.e(TAG,string);
        CollectionInfoData data = new Gson().fromJson(string,CollectionInfoData.class);
        return data;
    }
}
