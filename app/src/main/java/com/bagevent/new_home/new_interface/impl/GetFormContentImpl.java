package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetFormTypeInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetFormTypeListener;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.callback.GetFormContentCallback;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/22.
 */
public class GetFormContentImpl implements GetFormTypeInterface {
    @Override
    public void getFormType(final Context context, String eventId, final OnGetFormTypeListener onGetFormTypeListener) {
        OkHttpUtil.okGet(context)
                .url(Constants.URL + Constants.FORMTYPE + eventId + Constants.ACCESS_TOKENS + Constants.ACCESS_SERCRET)
                .build()
                .execute(new GetFormContentCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(FormType response, int id) {
                        if(response.getRetStatus() == 200) {
                            onGetFormTypeListener.getFormTypeSuccess(response);
                        }else {
                            ErrCodeUtil e=new ErrCodeUtil(context);
                            onGetFormTypeListener.getFormTypeFailed(e.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }

    @Override
    public void getBadgeFormType(Context context, String eventId, int sType, OnGetFormTypeListener listener) {

    }
}
