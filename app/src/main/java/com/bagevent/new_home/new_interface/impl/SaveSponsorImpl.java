package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.SaveSponsorInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnSaveSponsorListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/20.
 */
public class SaveSponsorImpl implements SaveSponsorInterface {
    @Override
    public void saveSponsor(Context mContext, int eventId, String userId, String textName, String organizerIds, final OnSaveSponsorListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.SAVE_EVENT_INFO + eventId + "?userId=" + userId + "&textName=" + textName + "&organizerIds=" + organizerIds + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.showSaveSponsorSuccess();
                        } else {
                            listener.showSaveSponsorFailed(response.getRespObject());
                        }
                    }
                });
    }
}
