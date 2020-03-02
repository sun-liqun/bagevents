package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.ChangeTicketInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.ChangeTicketListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by WenJie on 2017/11/13.
 */

public class ChangeTicketImpl implements ChangeTicketInterface {
    @Override
    public void changeTicket(Context mContext, int userId, int eventId, int attendeeId, int ticketId, int notice, final ChangeTicketListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.CHANGE_TICKET + eventId + "/" + attendeeId)
                .addParams("userId",userId+"")
                .addParams("notice",notice+"")
                .addParams("newTicketId",ticketId+"")
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
                            listener.showChangeTicketSuccess();
                        }else {
                            listener.showChangeTicketFailed(response.getRespObject());
                        }
                    }
                });
    }
}
