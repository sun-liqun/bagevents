package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.SaveEventTimeInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnSaveEventTimeListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/20.
 */
public class SaveEventTimeImpl implements SaveEventTimeInterface {
    @Override
    public void saveEventInfo(Context mContext, int eventId, String userId, String textName, String startTime, String endTime, final OnSaveEventTimeListener listener) {
        String url = Constants.URL + Constants.SAVE_EVENT_INFO + eventId + "?userId=" + userId +"&textName=" + textName + "&startTime=" + startTime + "&endTime=" + endTime + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN;

        OkHttpUtil.okGet(mContext)
                .url(url)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.showSaveSuccess();
                        } else {
                            listener.showSaveFailed(response.getRespObject());
                        }
                    }
                });
    }
}
