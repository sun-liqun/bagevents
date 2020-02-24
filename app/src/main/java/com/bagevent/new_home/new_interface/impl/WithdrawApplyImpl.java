package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.WithdrawApplyInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnWithdrawApplyListener;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public class WithdrawApplyImpl implements WithdrawApplyInterface {
    @Override
    public void applyWithdraw(Context mContext, String userId, String applyAmount, String account, String accountName, String password, String bankName, int type, final OnWithdrawApplyListener listener) {
        PostFormBuilder builder = OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.APPLY_WITHDRAW)
                .addParams("userId",userId)
                .addParams("applyAmount",applyAmount)
                .addParams("account",account)
                .addParams("accountName",accountName)
                .addParams("password",password)
                .addParams("type", type+"")
                .addParams("access_secret","ipad_secret")
                .addParams("access_token","ipad");

        if(!TextUtils.isEmpty(bankName)) {
            builder.addParams("bankName",bankName);
        }
        CrashReport.postCatchedException(new Throwable("提现异常"+"userId:"+userId+"applyAmount:"
                +applyAmount+"account:"+account+"accountName:"+accountName+"password:"+password+"type:"+type));

        builder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.e("提现申请失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        StringData data = new Gson().fromJson(response,StringData.class);
                        if(data.getRetStatus() == 200) {
                            listener.applyWithdrawSuccess(data.getRespObject());
                        }else {
                            listener.applyWithdrawFailed(data.getRespObject());
                        }
                    }
                });
    }
}
