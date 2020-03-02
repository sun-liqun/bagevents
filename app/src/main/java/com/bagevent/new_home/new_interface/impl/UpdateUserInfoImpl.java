package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.UpdateUserInfoInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateUserInfoListener;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2016/10/13.
 */
public class UpdateUserInfoImpl implements UpdateUserInfoInterface {
    @Override
    public void updateUserInfo(Context mContext, String userId, String formName, String formValue, final OnUpdateUserInfoListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.UPDATE_USER_INFO)
                .addParams("userId",userId)
                .addParams("formName",formName)
                .addParams("formValue",formValue)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.userInfoSuccess();
                        }else {
                            listener.userInfoFailed(response.getRespObject());
                        }
                    }
                });
    }
}
