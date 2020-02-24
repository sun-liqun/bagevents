package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.ErrorJsonData;
import com.bagevent.new_home.data.MsgRecordData;
import com.bagevent.new_home.new_interface.RechargeListInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnRechargeListListener;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zwj on 2017/10/23.
 */

public class RechargeListImpl implements RechargeListInterface {
    @Override
    public void rechargeList(Context mContext, String userId, int page, int pageSize, final OnRechargeListListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.LOAD_SMS_LIST)
                .addParams("userId",userId)
                .addParams("page",page+"")
                .addParams("pageSize",pageSize+"")
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("RechargeListImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            MsgRecordData rechargeString = new Gson().fromJson(response,MsgRecordData.class);
                            listener.showRechargeList(rechargeString);
                        }else {
                            ErrorJsonData errorString = new Gson().fromJson(response,ErrorJsonData.class);
                            listener.showRechargeListErrInfo(errorString.getRespObject());
                        }
                    }
                });
    }
}
