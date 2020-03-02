package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.callback.OrderInfoCallback;
import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.OrderInfoInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnOrderInfoListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2017/4/14.
 */

public class OrderInfoImpls implements OrderInfoInterface {
    @Override
    public void getOrderInfo(final Context mContext, String eventId, long fromTime, String syncAllStat, final OnOrderInfoListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.GET_ORDER_INFO + eventId + "?from_time=" + fromTime + "&sync_all=" + syncAllStat + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .build()
                .execute(new OrderInfoCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.getOrderInfoFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(OrderInfo response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.getOrderInfoSuccess(response);
                        }else {
                            listener.getOrderInfoFailed(mContext.getResources().getString(R.string.gain_order_error));
                        }
                    }
                });
    }
}
