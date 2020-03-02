package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.AddTicketInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddTicketListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/19.
 */
public class AddTicketImpl implements AddTicketInterface {
    @Override
    public void addTicket(Context mContext, String userId, int eventId, String ticketName, String ticketCount, String ticketPrice, int audit, final OnAddTicketListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.SAVE_TICKET + eventId + "?userId=" + userId + "&ticketName=" + ticketName
                + "&ticketPrice="+ticketPrice + "&ticketCount=" + ticketCount + "&audit=" + audit + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.addSuccess();
                        }else {
                            listener.addFalied(response.getRespObject());
                        }
                    }
                });
    }
}
