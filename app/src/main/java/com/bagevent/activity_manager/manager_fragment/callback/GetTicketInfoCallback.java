package com.bagevent.activity_manager.manager_fragment.callback;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by zwj on 2016/6/2.
 */
public abstract class GetTicketInfoCallback extends Callback<TicketInfo> {
    @Override
    public TicketInfo parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        TicketInfo tickets = new Gson().fromJson(string, TicketInfo.class);
//        Log.e("GetTicketInfoCallback", string);

        return tickets;
    }
}
