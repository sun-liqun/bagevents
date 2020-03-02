package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.AlipayData;
import com.bagevent.new_home.data.WXPayData;
import com.bagevent.new_home.data.WithDrawAccountData;
import com.bagevent.new_home.new_interface.ThirdPayInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnThirdPayListener;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zwj on 2017/10/23.
 */

public class ThirdPayImpl implements ThirdPayInterface {
    @Override
    public void getThirdPay(Context mContext, String userId , String orderNum, final String payType, final OnThirdPayListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.RECHARGE_THIRD_MESSAGE + orderNum + "/" + payType)
                .addParams("userId",userId)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("ThirdPayImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            if(payType.equals("0")) {
                                AlipayData data = new Gson().fromJson(response,AlipayData.class);
                                if(data.getRetStatus() == 200) {
                                    listener.showAlipaySuccess(data.getRespObject().getOrderStr());
                                }else {
                                    StringData stringData = new Gson().fromJson(response,StringData.class);
                                    listener.showAliPayFailed(stringData.getRespObject());
                                }
                            }else {
                                WXPayData data = new Gson().fromJson(response,WXPayData.class);
                                listener.showWxpaySuccess(data);
                            }

                        }else {
                            StringData stringData = new Gson().fromJson(response,StringData.class);
                            listener.showThirdPayFailed(stringData.getRespObject());
                        }
                    }
                });
    }
}
