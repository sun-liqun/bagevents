package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.QuitTiicketInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnQuickTicketListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import java.net.ConnectException;
import java.net.UnknownHostException;

import okhttp3.Call;

/**
 * Created by WenJie on 2017/11/2.
 */

public class QuitTicketImpl implements QuitTiicketInterface {
    @Override
    public void quitTicketFromOrder(Context mContext, int eventId, int orderId, int isSendEmail, final OnQuickTicketListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.QUIT_TICKET + eventId + "/" + orderId)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .addParams("sendEmail", isSendEmail + "")
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.showQuitSuccess(response.getRespObject());
                        } else {
                            listener.showQuitFailed(response.getRespObject());
                        }
                    }
                });
    }

    @Override
    public void quitTicketFromAttnedee(final Context mContext, int eventId, int orderId, int attendeeId, int isSendEmail, final OnQuickTicketListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.QUIT_TICKET + eventId + "/" + orderId)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .addParams("attendeeId", attendeeId + "")
                .addParams("sendEmail", isSendEmail + "")
                .build()
                .readTimeOut(2000)
                .writeTimeOut(2000)
                .connTimeOut(2000)
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (e instanceof UnknownHostException || e instanceof ConnectException) {
                            listener.showQuitFailed(mContext.getResources().getString(R.string.check_network));
                        } else {
                            listener.showQuitFailed(mContext.getResources().getString(R.string.back_ticket_error));
                        }
                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.showQuitSuccess(response.getRespObject());
                        } else {
                            listener.showQuitFailed(mContext.getResources().getString(R.string.back_ticket_error));
                        }
                    }
                });
    }
}
