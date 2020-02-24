package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.ErrorJsonData;
import com.bagevent.new_home.data.RechargeMsgData;
import com.bagevent.new_home.new_interface.RechargeMessageInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnRechargeMessageListener;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zwj on 2017/10/11.
 */

public class RechargeMessageImpl implements RechargeMessageInterface {
    @Override
    public void thirdRechargeMessage(Context context, String userId, String count, String payway, String accountType, final OnRechargeMessageListener listener) {
        OkHttpUtil.okPost(context)
                .url(Constants.URL + Constants.RECHARGE_MESSAGE)
                .addParams("userId",userId)
                .addParams("functionCode","SMS_RECHARGE")
                .addParams("count",count)
                .addParams("payWay",payway)
                .addParams("account_type",accountType)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("RechargeMessageImpl",e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("RechargeMessageImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            RechargeMsgData data = new Gson().fromJson(response,RechargeMsgData.class);
                            listener.rechargeSuccess(data);
                        }else {
                            StringData stringData = new Gson().fromJson(response,StringData.class);
                            listener.rechargeFailed(stringData.getRespObject());
                        }
                    }

                });
    }

    @Override
    public void accountRechargeMessage(Context context, String userId, String count, String payway, String drawPassword, final OnRechargeMessageListener listener) {
        OkHttpUtil.okPost(context)
                .url(Constants.URL + Constants.RECHARGE_MESSAGE)
                .addParams("userId",userId)
                .addParams("functionCode","SMS_RECHARGE")
                .addParams("count",count)
                .addParams("payWay",payway)
                .addParams("drawPassword",drawPassword)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("RechargeMessageImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            RechargeMsgData rechargeString = new Gson().fromJson(response,RechargeMsgData.class);
                            listener.rechargeSuccess(rechargeString);
                        }else {
                            ErrorJsonData errorString = new Gson().fromJson(response,ErrorJsonData.class);
                            listener.rechargeFailed(errorString.getRespObject());
                        }
                    }
                });

    }
}
