package com.bagevent.login.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.login.interfaces.ModifyPasswordInterface;
import com.bagevent.login.interfaces.OnModifyPwListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2018/1/11 0011.
 */

public class ModifyPwImpl implements ModifyPasswordInterface {
    @Override
    public void modifyPassword(final Context mContext, String phoneNum, String smsCode, String p1, String p2, final OnModifyPwListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.RESET_PASSWORD)
                .addParams("account", phoneNum)
                .addParams("smsCode", smsCode)
                .addParams("password1", p1)
                .addParams("password2", p2)
                .addParams("access_secret", "ipad_secret")
                .addParams("access_token", "ipad")
                .addParams("SOURCE","1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("ModifyPwImpl",response);
                        if (response.contains("\"code\":200")) {
                            listener.modifySuccess(response);
                        }else {
                            listener.modifyFailed(mContext.getResources().getString(R.string.modify_people_err));
                        }
                    }
                });

    }
}
