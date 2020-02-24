package com.bagevent.home.home_interface.callback;


import com.bagevent.home.data.ExportData;
import com.bagevent.new_home.data.AttendeeInfo;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/7/14.
 */
public abstract class ExportCalback extends Callback<ExportData> {

    @Override
    public ExportData parseNetworkResponse(Response response,int id) throws Exception {
        String string = response.body().string();
        ExportData data=new ExportData(new JsonParser().parse(string).getAsJsonObject());
//        ExportData data = new Gson().fromJson(string,ExportData.class);
        return data;

//        Log.e("export-->",string);
//        return data;
    }
}


