package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.IncomeData;
import com.bagevent.new_home.new_interface.GetIncomeInterface;
import com.bagevent.new_home.new_interface.callback.GetIncomeCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetIncomeListener;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/2.
 */
public class GetIncomeImpl implements GetIncomeInterface {
    @Override
    public void getIncome(Context mContext, String userId, final OnGetIncomeListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.NEW_INCREASE + "userId="+userId + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .tag("GetIncome")
                .build()
                .execute(new GetIncomeCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                       if (call.isCanceled()){
                           LogUtil.e("GetIncome取消请求");
                       }
                    }

                    @Override
                    public void onResponse(IncomeData response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.showGetSuccess(response);
                        }else {
                            listener.showGetFailed();
                        }
                    }
                });
    }
}
