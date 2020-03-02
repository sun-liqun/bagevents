package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.UploadEventInfoInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnUploadEventInfoListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/21.
 */
public class UploadEventInfoImpl implements UploadEventInfoInterface {
    @Override
    public void uploadLogo(Context mContext, int eventId, String userId, String textName, String textValue, final OnUploadEventInfoListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.SAVE_EVENT_INFO + eventId + "?userId=" + userId + "&textName="+ textName + "&textValue=" + textValue + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200){
                            listener.upLoadLogoSuccess();
                        }else {
                           // Log.e("fd",response.getRespObject());
                            listener.upLoadLogoFailed(response.getRespObject());
                        }
                    }
                });
    }
}
