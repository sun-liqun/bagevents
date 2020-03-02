package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.SingleOrderData;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.SingleOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnSingleOrderListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/12/4 0004.
 */

public class SingleOrderImpl implements SingleOrderInterface {
    @Override
    public void getSingleOrder(Context mContext, int eventId, String userId, String orderNumber, int orderId,final OnSingleOrderListener listener) {
     //   Log.e("SingleOrderImpl",Constants.URL + Constants.SINGLE_ORDER + eventId + "/" + orderNumber + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET);
        PostFormBuilder builder = OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.SINGLE_ORDER + eventId + "/" + orderNumber)
                .addParams("userId",userId)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret");
        if(orderId != 0) {
            builder.addParams("orderId",orderId+"");
        }
                builder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("SingleOrderImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            SingleOrderData data = new Gson().fromJson(response,SingleOrderData.class);
                            listener.showSingleOrderSuccess(data);
                        }else {
                            StringData err = new Gson().fromJson(response,StringData.class);
                            listener.showSingleOrderFailed(err.getRespObject());
                        }
                    }
                });
    }
}
