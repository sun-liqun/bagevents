package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.UpdateTicketInterface;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateTicketListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/20.
 */
public class UpdateTicketImpl implements UpdateTicketInterface {
    @Override
    public void updateTicket(Context mContext, int eventId, String userId, int ticketId, String ticketName, String ticketCount, String ticketPrice, int audit, final OnUpdateTicketListener listener) {
      //  Log.e("fdf",ticketPrice + "F");
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.SAVE_TICKET + eventId + "?userId="+userId + "&ticketId=" + ticketId + "&ticketName=" + ticketName + "&ticketCount=" + ticketCount
                + "&ticketPrice=" + ticketPrice + "&audit=" + audit + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.updateTicketSuccess();
                        }else {
                            listener.updateTicketFailed(response.getRespObject());
                        }
                    }
                });
    }
}
