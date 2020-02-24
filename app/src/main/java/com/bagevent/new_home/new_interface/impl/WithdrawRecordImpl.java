package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.ErrorJsonData;
import com.bagevent.new_home.data.MsgRecordData;
import com.bagevent.new_home.data.WithdrawRecordData;
import com.bagevent.new_home.new_interface.WithdrawRecordInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnWithdrawRecordListener;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import java.security.acl.LastOwnerException;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public class WithdrawRecordImpl implements WithdrawRecordInterface {
    @Override
    public void withdrawRecord(Context mContext, String userId, int page, int pageSize, final OnWithdrawRecordListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.WITHDRAW_RECORD+ "?userId=" + userId + "&page=" + page + "&pageSize=" + pageSize + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("WithdrawRecordImpl",e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("WithdrawRecordImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            WithdrawRecordData rechargeString = new Gson().fromJson(response,WithdrawRecordData.class);
                            listener.showWithdrawRecordSuccess(rechargeString);
                        }else {
                            ErrorJsonData errorString = new Gson().fromJson(response,ErrorJsonData.class);
                            listener.showWithddrawRecordFailed(errorString.getRespObject());
                        }
                    }
                });
    }
}
