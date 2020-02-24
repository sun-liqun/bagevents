package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.AddFormResponse;
import com.bagevent.new_home.new_interface.AddFormInterface;
import com.bagevent.new_home.new_interface.callback.AddFormCallback;
import com.bagevent.new_home.new_interface.callback.GetFormContentCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/23.
 */
public class AddFormImpl implements AddFormInterface {
    @Override
    public void addForm(Context mContext, String eventId, String userId, String filedName, String showName , int filedType, final OnAddFormListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.ADD_FORM + eventId)
                .addParams("userId",userId)
                .addParams("fieldName",filedName)
                .addParams("fieldType",filedType+"")
                .addParams("access_secret","ipad_secret")
                .addParams("access_token","ipad")
                .build()
                .execute(new AddFormCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(AddFormResponse response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.addFormSuccess(response);
                        }else {
                            listener.addFormFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }

    @Override
    public void addMutiForm(Context mContext,String eventId, String userId, String filedName, String showName, String items, int filedType, OnAddFormListener listener) {

    }
}
