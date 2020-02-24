package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.manager_interface.ActivityOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnActivityOrderListener;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.ErrorJsonData;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zwj on 2017/10/24.
 */

public class ActivityOrderImpl implements ActivityOrderInterface {
    @Override
    public void getActivityOrderFirst(final Context mContext, String userId, String eventId, int page, final OnActivityOrderListener listener) {
        LogUtil.i("-----------------------eventId"+eventId+"");
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.ORDERS + eventId + Constants.SYNCCALL + "&userId=" + userId + "&page=" + page + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .tag("ActivityOrderFragment")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()) {
                            LogUtil.e("ActivityOrder取消请求");
                        }else {
                            listener.showOrderFailed(mContext.getResources().getString(R.string.data_error));
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            listener.showOrderSuccess(response);
                        } else {
                            ErrorJsonData errorString = new Gson().fromJson(response, ErrorJsonData.class);
                            listener.showOrderFailed(errorString.getRespObject());
                        }
                    }
                });
    }

    @Override
    public void getActivityOrderFromTime(final Context mContext, String userId, String eventId, String updateTime, final OnActivityOrderListener listener) {
        LogUtil.i("-----------------------eventId"+eventId+"updateTime"+updateTime);
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.ORDERS + eventId + Constants.SYNCCALL + "&userId=" + userId + "&from_time=" + updateTime + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .tag("ActivityOrderFragment")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()) {
                            LogUtil.e("ActivityOrder取消请求");
                        }else {
                            listener.showOrderFailed(mContext.getResources().getString(R.string.data_error));
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("ActivityOrderImpl", response);
                        if (response.contains("\"retStatus\":200")) {
                            listener.showOrderSuccess(response);

                        } else {
                            ErrorJsonData errorString = new Gson().fromJson(response, ErrorJsonData.class);
                            listener.showOrderFailed(errorString.getRespObject());
                        }
                    }
                });
    }
}
