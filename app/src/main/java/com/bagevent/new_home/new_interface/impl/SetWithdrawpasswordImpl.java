package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.SetWithdrawPasswordInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetWithdrawValidCodeListener;
import com.bagevent.new_home.new_interface.listenerInterface.OnSetWithdrawpasswordListener;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public class SetWithdrawpasswordImpl implements SetWithdrawPasswordInterface{
    @Override
    public void setWithdrawPassword(final Context mContext, String userId, String password, String confirmPassword, String validCode, final OnSetWithdrawpasswordListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.SET_PASSWORD)
                .addParams("userId",userId)
                .addParams("newPassword",password)
                .addParams("confirmPass",confirmPassword)
                .addParams("validCode",validCode)
                .addParams("access_secret","ipad_secret")
                .addParams("access_token","ipad")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("SetWithdrawpasswordImpl",response);
                        StringData data = new Gson().fromJson(response,StringData.class);
                        if(data.getRetStatus() == 200) {
                            listener.setPasswordSuccess(mContext.getResources().getString(R.string.success_set));
                        }else {
                            listener.setPasswordFailed(data.getRespObject());
                        }
                    }
                });
    }

    @Override
    public void getWithdrawValidCode(final Context mContext, String userId, int type, final OnGetWithdrawValidCodeListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.PASSWORD_VALID_CODE)
                .addParams("userId",userId)
                .addParams("type",type+"")
                .addParams("access_secret","ipad_secret")
                .addParams("access_token","ipad")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("SetWithdrawpasswordImpl",response);
                        StringData data = new Gson().fromJson(response,StringData.class);
                        if(data.getRetStatus() == 200) {
                            listener.showValidCodeSuccess(mContext.getResources().getString(R.string.verification_code_send));
                        }else {
                            listener.showValidCodeFailed(data.getRespObject());
                        }
                    }
                });
    }
}
