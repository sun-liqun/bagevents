package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.ChatData;
import com.bagevent.new_home.new_interface.ChatListInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnChatListListener;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/11/21 0021.
 */

public class ChatListImpl implements ChatListInterface {
    @Override
    public void chat(Context mContext, String userId, final OnChatListListener listListener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.CHAT_LIST)
                .addParams("userId", userId)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        LogUtil.e("ChatListImpl");
//                        LogUtil.e(response);
                        if (response.contains("\"retStatus\":200")) {
//                            ChatData data = new Gson().fromJson(response, ChatData.class);
                            ChatData data = new ChatData(new JsonParser().parse(response).getAsJsonObject());
                            listListener.showChatListSuccess(data);
                        } else {
                            StringData data = new Gson().fromJson(response, StringData.class);
                            listListener.showChatListFailed(data.getRespObject());
                        }
                    }
                });
    }

    @Override
    public void singleChat(final Context mContext, String userId, int contactId, final OnChatListListener listListener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.SINGLE_CHAT)
                .addParams("userId", userId)
                .addParams("contactId", contactId + "")
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("SingleChatLisImpl", response + " ");
                        if (response.contains("\"retStatus\":200")) {
//                            ChatData data = new Gson().fromJson(response, ChatData.class);
                            ChatData data = new ChatData(new JsonParser().parse(response).getAsJsonObject());
                            listListener.showChatListSuccess(data);
                        } else {
                            //  StringData data = new Gson().fromJson(response,StringData.class);
                            listListener.showChatListFailed(mContext.getResources().getString(R.string.request_was_aborted));
                        }
                    }
                });
    }

    @Override
    public void attendeeSingleChat(Context mContext, String userId, int eventId, int attendeeId, final OnChatListListener listListener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.SINGLE_CHAT)
                .addParams("userId", userId)
                .addParams("eventId", eventId + "")
                .addParams("attendeeId", attendeeId + "")
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("SingleChatLisImpl", response + " ");
                        if (response.contains("\"retStatus\":200")) {
//                            ChatData data = new Gson().fromJson(response, ChatData.class);
                            ChatData data = new ChatData(new JsonParser().parse(response).getAsJsonObject());
                            listListener.showChatListSuccess(data);
                        } else {
                            StringData data = new Gson().fromJson(response, StringData.class);
                            listListener.showChatListFailed(data.getRespObject());
                        }
                    }
                });
    }
}
