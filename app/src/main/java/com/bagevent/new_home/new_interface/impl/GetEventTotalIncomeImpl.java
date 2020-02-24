package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.EventTotalIncome;
import com.bagevent.new_home.new_interface.GetEventTotalIncomeInterface;
import com.bagevent.new_home.new_interface.callback.GetEventTotalIncomeCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetEventTotalIncomeListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/10/11.
 */
public class GetEventTotalIncomeImpl implements GetEventTotalIncomeInterface {
    @Override
    public void eventTotalincome(Context mContext, String userId, final OnGetEventTotalIncomeListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.EVENT_INCOME + "userId=" +userId + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .build()
                .execute(new GetEventTotalIncomeCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(EventTotalIncome response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.totalIncomeSuccess(response);
                        }else {
                            listener.totalIncomeFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
