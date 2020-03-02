package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.AddWithdrawAccount;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddWithDrawAccount;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public class AddWithDrawAccountImpl implements AddWithdrawAccount {
    @Override
    public void addWithDrawAccount(Context mContext, String userId, int type, String accountName, String account, String bankName, final OnAddWithDrawAccount listener) {
        PostFormBuilder builder = OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.ADD_WITHDRAW_ACCOUNT + userId)
                .addParams("type",type +"")
                .addParams("userId",userId)
                .addParams("account",account)
                .addParams("accountName",accountName)
                .addParams("access_secret","ipad_secret")
                .addParams("access_token","ipad");

        if(!TextUtils.isEmpty(bankName)) {
            builder.addParams("bankName",bankName);
        }

        builder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("AddWithDrawAccountImpl",e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("AddWithDrawAccountImpl",response);
                        StringData data = new Gson().fromJson(response,StringData.class);
                        if(data.getRetStatus() == 200) {
                            listener.addWithdrawAccountSuccess(data.getRespObject());
                        }else {
                            listener.addWithdrawAccountFailed(data.getRespObject());
                        }
                    }
                });
    }
}
