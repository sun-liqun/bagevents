package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.RemoveChatInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnRemoveChatListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public class RemoveChatImpl implements RemoveChatInterface {
    @Override
    public void removeChat(final Context mContext, String userId, int contactId, final OnRemoveChatListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.DELETE_CHAT)
                .addParams("userId",userId)
                .addParams("contactId",contactId+"")
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("RemoveChatImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            listener.removeChat();
                        }else {
                            listener.removeChatFailed(mContext.getResources().getString(R.string.error_delete));
                        }
                    }
                });
    }
}
