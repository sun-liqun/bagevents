package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.new_home.data.SponsorData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/18.
 */
public abstract class GetSponsorCallback extends Callback<SponsorData> {
    @Override
    public SponsorData parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        SponsorData data = new Gson().fromJson(string, SponsorData.class);
        //Log.e("sponsor-->",string);
        return data;
    }
}
