package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.DeleteFormInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnDeleteFormFieldIdListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/22.
 */
public class DeleteFormImpl implements DeleteFormInterface {
    @Override
    public void deleteForm(Context mContext, String eventId, String userId, int formFieldId, final OnDeleteFormFieldIdListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.DELETE_FORM + eventId)
                .addParams("userId",userId)
                .addParams("formFieldId",formFieldId+"")
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.deleteFormSuccess();
                        }else {
                            listener.deleteFormFailed(response.getRespObject());
                        }
                    }
                });
    }
}
