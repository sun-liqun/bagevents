package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.WithDrawAccountData;
import com.bagevent.new_home.new_interface.WithdrawAccountInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnWithDrawListener;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by WenJie on 2017/11/16.
 */

public class WithDrawAccountImpl implements WithdrawAccountInterface {
    @Override
    public void withdrawAccount(Context mContext, String userId, final OnWithDrawListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.WITHDRAW_ACCOUNT)
                .addParams("userId",userId)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("WithDrawAccountImpl",e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("WithDrawAccountImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            WithDrawAccountData data = new Gson().fromJson(response,WithDrawAccountData.class);
                            listener.withdrawSuccess(data);
                        }else if (response.equals("")){
                            LogUtil.e("---------提款失败");
                        }else {
                            StringData stringData = new Gson().fromJson(response,StringData.class);
                            listener.withdrawFailed(stringData.getRespObject());
                        }
                    }
                });
    }
}
