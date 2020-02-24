package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.PublishEventInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnPublishEventListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/10/10.
 */
public class PublishEventImpl implements PublishEventInterface {
    @Override
    public void publish(Context mContext, int eventId, String userId, final OnPublishEventListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.PUBLISH_EVENT + eventId)
                .addParams("userId",userId)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.publishSuccess();
                        }else {
                            listener.publishFailed(response.getRespObject());
                        }
                    }
                });
    }
}
