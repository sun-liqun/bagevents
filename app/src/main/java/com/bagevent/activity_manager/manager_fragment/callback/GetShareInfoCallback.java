package com.bagevent.activity_manager.manager_fragment.callback;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.ShareInfoData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/12/5.
 */
public abstract class GetShareInfoCallback extends Callback<ShareInfoData> {
    @Override
    public ShareInfoData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
//        Log.e("GetShareInfoCallback",string);
        ShareInfoData data = new Gson().fromJson(string,ShareInfoData.class);
        return data;
    }
}
