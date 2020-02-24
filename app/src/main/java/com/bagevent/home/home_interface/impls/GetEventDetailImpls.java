package com.bagevent.home.home_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.common.Constants;
import com.bagevent.home.data.EventDetailData;
import com.bagevent.home.home_interface.GetEventDetailInterface;
import com.bagevent.home.home_interface.GetEventDetailListener;
import com.bagevent.home.home_interface.callback.GetEventDetailCallback;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/27.
 */
public class GetEventDetailImpls implements GetEventDetailInterface {
    @Override
    public void eventDetailInterface(Context mContext,String getEventId, String userId, final GetEventDetailListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.EVENT_DETAIL + userId + "/" + getEventId + Constants.ACCESS_TOKENS + Constants.ACCESS_SERCRET)
                .tag("GetEventDetail")
                .build()
                .execute(new GetEventDetailCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("GetEventDetailImpls",e.getMessage());
                        if (call.isCanceled()){
                            LogUtil.e("GetEventDetail请求取消");
                            return;
                        }
                    }

                    @Override
                    public void onResponse(EventDetailData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.getEventDetailSuccess(response);
                        }else {
                            listener.getEventDetailFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
