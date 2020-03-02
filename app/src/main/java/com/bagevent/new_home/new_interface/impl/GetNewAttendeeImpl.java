package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.NewAttendeeData;
import com.bagevent.new_home.new_interface.GetNewAttendeeInterface;
import com.bagevent.new_home.new_interface.callback.GetNewAttendeeCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetNewAttendeeListener;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/2.
 */
public class GetNewAttendeeImpl implements GetNewAttendeeInterface {

    @Override
    public void getNewAttendee(Context mContext, String userId, int size, final OnGetNewAttendeeListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.NEW_ATTENDEE_COUNT + "userId="+ userId + "&size="+size + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .tag("NewAttendee")
                .build()
                .execute(new GetNewAttendeeCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                       if (call.isCanceled()){
                           LogUtil.e("NewAttendee请求取消");
                       }
                    }

                    @Override
                    public void onResponse(NewAttendeeData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.showGetSuccess(response);
                        }else {
                            listener.showGetFailed();
                        }
                    }
                });
    }
}
