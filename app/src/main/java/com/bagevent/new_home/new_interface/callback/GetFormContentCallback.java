package com.bagevent.new_home.new_interface.callback;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/9/22.
 */
public abstract class GetFormContentCallback extends Callback<FormType> {
    @Override
    public FormType parseNetworkResponse(Response response, int id) throws Exception {
        String string  = response.body().string();
        Log.e("GetFormContentCallback",string);
        FormType formType = new Gson().fromJson(string,FormType.class);
      //  Log.e("form",string);
        return formType;
    }
}
