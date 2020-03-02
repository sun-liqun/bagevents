package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.CreateEventData;
import com.bagevent.new_home.new_interface.CreateEventInterface;
import com.bagevent.new_home.new_interface.callback.CreateEventCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnCreateEventListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/19.
 */
public class CreateEventImpl implements CreateEventInterface {
    @Override
    public void create(Context mContext, String userId, int type, final OnCreateEventListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.CREATE_EVENT + type + "?userId=" + userId + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new CreateEventCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(CreateEventData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.createSuccess(response);
                        }else {
                            listener.createFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
