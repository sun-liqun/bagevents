package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.GetAllFormData;
import com.bagevent.new_home.new_interface.GetAllFormInterface;
import com.bagevent.new_home.new_interface.callback.GetAllFormCallback;
import com.bagevent.new_home.new_interface.callback.GetFormContentCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetAllFormListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/22.
 */
public class GetAllFormImpl implements GetAllFormInterface {
    @Override
    public void getAllForm(Context mContext, String eventId, String userId, final OnGetAllFormListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.GET_FIELDLIST + eventId + "?userId=" + userId + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .build()
                .execute(new GetAllFormCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(GetAllFormData response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.getAllFormSuccess(response);
                        }else {
                            listener.getAllFormFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
