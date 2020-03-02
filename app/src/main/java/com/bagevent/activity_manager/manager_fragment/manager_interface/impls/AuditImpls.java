package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.CheckInCallback;
import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.manager_interface.AuditInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnAuditListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2016/7/7.
 */
public class AuditImpls implements AuditInterface {
    @Override
    public void audit(Context mContext, String eventId, String attendeeId, String audit, String upDateTime, final OnAuditListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + "/event/" + eventId + "/audit/" + attendeeId + "/" + audit + "?update_time" + upDateTime + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build().execute(new CheckInCallback() {
            @Override
            public void onError(Call call, Exception e,int id) {
                listener.showAuditRefused();
            }

            @Override
            public void onResponse(CheckIn response,int id) {
                if(response.getRetStatus() == 1){
                    listener.showAuditPass();
                }else {
                    listener.showAuditRefused();
                }
            }
        });
    }
}
