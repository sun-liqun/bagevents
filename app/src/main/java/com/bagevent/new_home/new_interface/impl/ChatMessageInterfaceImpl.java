package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.ChatMessageData;
import com.bagevent.new_home.new_interface.ChatMessageInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetChatMessageListener;
import com.bagevent.util.OkHttpUtil;

import com.google.gson.Gson;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/11/24 0024.
 */

public class ChatMessageInterfaceImpl implements ChatMessageInterface {
    @Override
    public void chatMessage(final Context mContext, String userId, String sendToken, int eventId, int page, final OnGetChatMessageListener listener) {

       // Log.e("aa",Constants.URL + Constants.MESSAGE_LIST + "?userId="+ userId +"&sendToken=" + sendToken + "&page=" + page + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET);

        PostFormBuilder builder = OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.MESSAGE_LIST)
                .addParams("userId",userId)
                .addParams("sendToken",sendToken)
                .addParams("page",page+"")
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret");

        if(eventId != 0) {
            builder.addParams("eventId",eventId+"");
        }

        builder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.showChatMessageFailed(mContext.getResources().getString(R.string.request_was_aborted));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("ChatMessageImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            ChatMessageData data = new Gson().fromJson(response,ChatMessageData.class);
                            listener.showChatMessageSuccess(data);
                        }else {
                            StringData err = new Gson().fromJson(response,StringData.class);
                            listener.showChatMessageFailed(err.getRespObject());
                        }
                    }
                });

    }

    @Override
    public void upFetchChatMessage(Context mContext, String userId, String sendToken, int eventId, String sendTime, final OnGetChatMessageListener listener) {
        PostFormBuilder builder = OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.MESSAGE_LIST)
                .addParams("userId",userId)
                .addParams("sendToken",sendToken)
                .addParams("sendTime",sendTime)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret");

        if(eventId != 0) {
            builder.addParams("eventId",eventId+"");
        }

        builder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("ChatMessageImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            ChatMessageData data = new Gson().fromJson(response,ChatMessageData.class);
                            listener.showChatMessageSuccess(data);
                        }else {
                            StringData err = new Gson().fromJson(response,StringData.class);
                            listener.showChatMessageFailed(err.getRespObject());
                        }
                    }
                });
    }
}
