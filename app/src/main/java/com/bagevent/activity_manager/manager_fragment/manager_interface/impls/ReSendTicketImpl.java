package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.ReSendTicketInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnReSendListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import java.net.ConnectException;
import java.net.UnknownHostException;

import okhttp3.Call;

/**
 * Created by WenJie on 2017/10/31.
 */

public class ReSendTicketImpl implements ReSendTicketInterface {
    @Override
    public void reSendTicketFromOrder(Context mContext, int eventId, int orderId, final OnReSendListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.RESEND_TICKET + eventId + "/" + orderId)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.showSendSuccess(response.getRespObject());
                        } else {
                            listener.showSendFailed(response.getRespObject());
                        }
                    }
                });
    }

    @Override
    public void reSendTicketFromAttendee(final Context mContext, int eventId, int orderId, String attendeeId, int sendToAttendee, final OnReSendListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.RESEND_TICKET + eventId + "/" + orderId)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .addParams("attendeeId", attendeeId)
                .addParams("sendToAttendee", sendToAttendee + "")
                .build()
                .connTimeOut(2000)
                .writeTimeOut(2000)
                .readTimeOut(2000)
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (e instanceof UnknownHostException || e instanceof ConnectException) {
                            listener.showSendFailed(mContext.getResources().getString(R.string.check_network));
                        } else {
                            listener.showSendFailed(mContext.getResources().getString(R.string.error_send));
                        }
                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        //   Toast.makeText()
                        if (response.getRetStatus() == 200) {
                            listener.showSendSuccess(response.getRespObject());
                        } else {
                            listener.showSendFailed(response.getRespObject());
                        }
                    }
                });
    }
}
