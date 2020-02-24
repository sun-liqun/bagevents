package com.bagevent.register.callback;

import com.bagevent.register.data.PhoneIsExistData;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/5/27.
 */
public abstract class NextRegisterPhoneIsExistCallback extends Callback<PhoneIsExistData> {
    @Override
    public PhoneIsExistData parseNetworkResponse(Response response,int id) throws Exception {
        String string = response.body().string();
        PhoneIsExistData phone = new Gson().fromJson(string,PhoneIsExistData.class);
        return phone;
    }
}
