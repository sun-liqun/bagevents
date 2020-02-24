package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.UpdateChatTimeInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateChatTimeListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/11/30 0030.
 */

public class UpdateChatTimeImpl implements UpdateChatTimeInterface {
    @Override
    public void updateChatTime(Context mContext, String userId, int contactId, String updateTime, OnUpdateChatTimeListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.UPDATE_CHAT_TIME)
                .addParams("userId",userId)
                .addParams("contactId",contactId+"")
                .addParams("updateTime",updateTime)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("UpdateChatTimeImpl",e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("UpdateChatTimeImpl",response);

                    }
                });
    }
}
