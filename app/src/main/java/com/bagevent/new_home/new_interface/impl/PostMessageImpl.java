package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.ChatMessageData;
import com.bagevent.new_home.data.PostChatMessageData;
import com.bagevent.new_home.new_interface.PostChatMessageInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnPostMessageListener;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/11/29 0029.
 */

public class PostMessageImpl implements PostChatMessageInterface {
    @Override
    public void postMessage(Context mContext, String userId, int eventId, int attendeeId, String sendToken, String receiverToken, String content, final OnPostMessageListener listener) {
        PostFormBuilder builder = OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.POST_CHAT_MESSAGE)
                .addParams("userId",userId)
                .addParams("eventId",eventId+"")
                .addParams("receiverToken",receiverToken)
                .addParams("content",content)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret");

        if(attendeeId != 0) {
            builder.addParams("attendeeId",attendeeId+"");
        }

        builder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                       // Log.e("aaa",e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("PostMessageImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            PostChatMessageData data = new Gson().fromJson(response,PostChatMessageData.class);
                            listener.postMessageSuccess(data);
                        }else {
                            StringData err = new Gson().fromJson(response,StringData.class);
                            listener.postMessageFailed(err.getRespObject());
                        }
                    }
                });
    }
}
