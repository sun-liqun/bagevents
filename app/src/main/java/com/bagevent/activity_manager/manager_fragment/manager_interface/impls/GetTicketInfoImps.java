package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.GetTicketInfoCallback;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetTicketInfoInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetTicketListener;
import com.bagevent.common.Constants;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2016/6/2.
 */
public class GetTicketInfoImps implements GetTicketInfoInterface {
    @Override
    public void getTicket(Context context, final String eventId, final OnGetTicketListener ticketListener) {
        OkHttpUtil.okGet(context)
                .url(Constants.URL + Constants.TICKET + eventId + Constants.T_ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .tag("GetTicketInfo")
                .build()
                .execute(new GetTicketInfoCallback() {
                    @Override
                    public void onError(Call call, Exception e,int id) {
                        if (call.isCanceled()){
                            LogUtil.e("GetTicketInfo请求取消");
                            return;
                        }else {
                            ticketListener.getTicketFailed("err");
                        }


                    }
                    @Override
                    public void onResponse(TicketInfo response,int id) {
                        ticketListener.getTicketSuccess(response);
                    }
                });

    }
}
