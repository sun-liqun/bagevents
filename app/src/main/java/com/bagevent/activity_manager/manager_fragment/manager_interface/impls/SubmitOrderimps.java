package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.SubmitOrderCallback;
import com.bagevent.activity_manager.manager_fragment.data.ModifyData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.SubmitOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnSubmitOrderListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2016/7/5.
 */
public class SubmitOrderimps implements SubmitOrderInterface {
    @Override
    public void submitOrder(Context mContext, String eventId, String submitInfo, String buyWay , final OnSubmitOrderListener listener) {

        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.SUBMIT_ORDER + "eventId="+eventId + "&submitInfo=" + submitInfo + "&buyWay=" + buyWay + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new SubmitOrderCallback() {
                    @Override
                    public void onError(Call call, Exception e,int id) {
                        listener.submitFailed(null);
                    }

                    @Override
                    public void onResponse(ModifyData response,int id) {
                        if(response.getRespObject() == 0 && response.getRetStatus() == 200){
                            listener.submitSuccess(response);
                        }else {
                            listener.submitFailed(response);
                        }
                    }
                });
    }
}
