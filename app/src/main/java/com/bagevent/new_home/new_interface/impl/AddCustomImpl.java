package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.AddFormResponse;
import com.bagevent.new_home.new_interface.AddFormInterface;
import com.bagevent.new_home.new_interface.callback.AddFormCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/23.
 */
public class AddCustomImpl implements AddFormInterface {
    @Override
    public void addForm(Context mContext, String eventId, String userId, String filedName, String showName, int filedType, final OnAddFormListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.ADD_FORM + eventId)
                .addParams("userId", userId)
                .addParams("fieldName", filedName)
                .addParams("fieldType", filedType + "")
                .addParams("showName", showName)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new AddFormCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(AddFormResponse response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.addFormSuccess(response);
                        } else {
                            listener.addFormFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }

    @Override
    public void addMutiForm(Context mContext,String eventId, String userId, String filedName, String showName, String items, int filedType, final OnAddFormListener listener) {
        OkHttpUtils.post()
                .url(Constants.URL + Constants.ADD_FORM + eventId)
                .addParams("userId", userId)
                .addParams("fieldName", filedName)
                .addParams("fieldType", filedType + "")
                .addParams("showName", showName)
                .addParams("items",items)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new AddFormCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(AddFormResponse response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.addFormSuccess(response);
                        } else {
                            listener.addFormFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
