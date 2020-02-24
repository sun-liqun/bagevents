package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.ConfirmOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnConfirmOrderListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by WenJie on 2017/11/1.
 */

public class ConfirmOrderImpl implements ConfirmOrderInterface {
    @Override
    public void confirmOrder(Context mContext, int eventId, int orderId, final OnConfirmOrderListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + "event/" + eventId + "/order/offline/" + orderId)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("ConfirmOrderImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            listener.showConfirmSuccess("success");
                        }else {
                            StringData data = new Gson().fromJson(response,StringData.class);
                            listener.showConfirmFailed(data.getRespObject());
                        }
                    }
                });
    }
}
