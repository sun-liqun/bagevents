package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.SaveEventDetailInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnSaveEventDetailListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/10/10.
 */
public class SaveEventDetailImpl implements SaveEventDetailInterface {
    @Override
    public void saveEventDetail(Context mContext, int eventId, String userId, String textName, String eventContent, String eventBrief, final OnSaveEventDetailListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.SAVE_EVENT_INFO + eventId)
                .addParams("userId",userId)
                .addParams("textName",textName)
                .addParams("eventContent",eventContent)
                .addParams("eventBrief",eventBrief)
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
                            listener.saveDetailSuccess();
                        }else {
                            listener.saveDetailFailed(response.getRespObject());
                        }
                    }
                });
    }
}
