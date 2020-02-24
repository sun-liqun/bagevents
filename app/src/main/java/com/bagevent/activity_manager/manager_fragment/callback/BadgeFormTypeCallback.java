package com.bagevent.activity_manager.manager_fragment.callback;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.common.Constants;
import com.bagevent.util.NetUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.io.Serializable;

import okhttp3.Response;

/**
 * Created by zwj on 2017/6/20.
 */

public abstract class BadgeFormTypeCallback extends Callback<FormType> {
    private Context mContext;
    private String eventId;
    public BadgeFormTypeCallback(Context mContext, String eventId) {
        this.mContext = mContext;
        this.eventId = eventId;
    }

    @Override
    public FormType parseNetworkResponse(Response response,int id) throws Exception {
        final String string = response.body().string();
        FormType formType = new Gson().fromJson(string,FormType.class);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                NetUtil.saveObject(mContext,(Serializable)string, Constants.BADGE_FORM_FILE_NAME+eventId);
            }
        };
        runnable.run();
        //保存json数据
//        Log.e("BadgeFormTypeCallback",string);
        return formType;
    }
}
