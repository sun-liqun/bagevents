package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.SaleMoneyData;
import com.bagevent.new_home.new_interface.GetSaleMoneyInterface;
import com.bagevent.new_home.new_interface.callback.GetSaleMoneyCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetSaleMoneyListener;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/2.
 */
public class GetSaleMoneyImpl implements GetSaleMoneyInterface{
    @Override
    public void getSaleMoney(Context mContext, String userId, int size, final OnGetSaleMoneyListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.INCOME  + "userId="+userId + "&size="+size + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .tag("GetSaleMoney")
                .build()
                .execute(new GetSaleMoneyCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                       if (call.isCanceled()){
                           LogUtil.e("GetSaleMoney请求取消");
                       }
                    }

                    @Override
                    public void onResponse(SaleMoneyData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.showGetSuccess(response);
                        }else {
                            listener.showGetFailed();
                        }
                    }
                });
    }
}
