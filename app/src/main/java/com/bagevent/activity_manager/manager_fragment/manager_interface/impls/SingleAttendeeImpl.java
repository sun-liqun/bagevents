package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.SingleAttendeeInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnSingleAttendeeListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public class SingleAttendeeImpl implements SingleAttendeeInterface {

    @Override
    public void singleAttendee(Context mContext, int eventId, int orderId, String userId, final OnSingleAttendeeListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.SINGLE_ATTENDEE + eventId + "/" + orderId + "?userId=" + userId + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("SingleAttendeeImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                          //  SingleAttendeeData data = new Gson().fromJson(response,SingleAttendeeData.class);
                            listener.showSingleAttendeeSuccess(response);
                        }else {
                            StringData err = new Gson().fromJson(response,StringData.class);
                            listener.showSingleAttendeeFailed(err.getRespObject());
                        }
                    }
                });
    }

    @Override
    public void singleAttendeeFromAttendeeId(Context mContext, int eventId, int attendeeId, String userId, final OnSingleAttendeeListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.SINGLE_ATTENDEE_ATTENDEEID + eventId + "/" + attendeeId + "?userId=" + userId + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("SingleAttendeeImpl",e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("SingleAttendeeImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            //  SingleAttendeeData data = new Gson().fromJson(response,SingleAttendeeData.class);
                            listener.showSingleAttendeeSuccess(response);
                        }else {
                            StringData err = new Gson().fromJson(response,StringData.class);
                            listener.showSingleAttendeeFailed(err.getRespObject());
                        }
                    }
                });
    }
}
