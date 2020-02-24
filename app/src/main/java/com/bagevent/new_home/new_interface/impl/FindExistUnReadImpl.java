package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.FindUnreadData;
import com.bagevent.new_home.new_interface.FindExistUnRead;
import com.bagevent.new_home.new_interface.listenerInterface.OnFindExistUnReadListener;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public class FindExistUnReadImpl implements FindExistUnRead {
    @Override
    public void findExistUnRead(Context mContext, String userId, final OnFindExistUnReadListener listener) {
        OkHttpUtil.okGet(mContext).url(Constants.URL + Constants.EXIST_UNREAD_MSG + userId + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("FindExistUnReadImpl Err",e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("FindExistUnReadImpl",response+" ");
                        if(response.contains("\"retStatus\":200")) {
                            FindUnreadData data = new Gson().fromJson(response,FindUnreadData.class);
                            listener.findExistUnRead(data);
                        }else {
                            StringData err = new Gson().fromJson(response,StringData.class);
                            listener.findExistUnReadFailed(err.getRespObject());
                        }
                    }
                });
    }
}
