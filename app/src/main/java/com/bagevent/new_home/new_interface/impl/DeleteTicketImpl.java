package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.DeleteTicketInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnDeleteTicketListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/21.
 */
public class DeleteTicketImpl implements DeleteTicketInterface {
    @Override
    public void deleteTikcet(Context mContext, int eventId, String userId, int ticketId, final OnDeleteTicketListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.DELETE_TICKET + eventId + "?userId=" + userId + "&ticketId=" + ticketId + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.deleteTicketSuccess();
                        }else {
                            listener.deleteTicketFailed(response.getRespObject());
                        }
                    }
                });
    }
}
