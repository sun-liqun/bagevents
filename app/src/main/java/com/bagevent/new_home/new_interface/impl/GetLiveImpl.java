package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.AddFormResponse;
import com.bagevent.new_home.new_interface.AddFormInterface;
import com.bagevent.new_home.new_interface.GetLiveInterface;
import com.bagevent.new_home.new_interface.callback.AddFormCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormListener;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetLiveUrlListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.TosUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/23.
 */
public class GetLiveImpl implements GetLiveInterface {


    @Override
    public void getLiveUrl(Context mContext, int eventId, OnGetLiveUrlListener listener) {
        OkHttpUtil.Post(mContext)
                .url(Constants.NEW_URL+"/live/"+eventId+"/NE/channel/push")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.show(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        StringData data = new Gson().fromJson(response,StringData.class);
                        if(response.contains("\"retStatus\":200")) {
                            listener.getLiveUrlSuccess(data.getRespObject());
                        }else {
                            listener.getLiveUrl(data.getRespObject());
                        }
                    }
                });
    }
}
