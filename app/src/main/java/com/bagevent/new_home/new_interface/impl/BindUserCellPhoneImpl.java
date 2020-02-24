package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.BindUserCellPhoneInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnBindUserCellPhoneListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/12.
 */
public class BindUserCellPhoneImpl implements BindUserCellPhoneInterface {
    @Override
    public void bindUserCellPhone(Context mContext, String cellphone, String randomCode, String userId, final OnBindUserCellPhoneListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.BIND_USER_CELLPHONE + "cellphone=" + cellphone + "&randomCode=" + randomCode + "&userId=" + userId + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        Log.e("bind response",response.toString());
                        if(response.getRetStatus() == 200) {
                            listener.bindSuccess();
                        }else {
                            listener.bindFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
