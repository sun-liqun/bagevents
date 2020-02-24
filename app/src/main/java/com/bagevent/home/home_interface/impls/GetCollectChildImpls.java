package com.bagevent.home.home_interface.impls;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.home.data.CollectDetailData;
import com.bagevent.home.home_interface.GetCollectChildInterface;
import com.bagevent.home.home_interface.OnGetCollectChildInterface;
import com.bagevent.home.home_interface.callback.GetCollectChildCallback;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/7/13.
 */
public class GetCollectChildImpls implements GetCollectChildInterface {
    @Override
    public void getCollectChild(Context mContext, String collectionId, String eventId, final OnGetCollectChildInterface listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.COLLECT_CHILD + collectionId + "?eventId=" + eventId + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new GetCollectChildCallback() {
                    @Override
                    public void onError(Call call, Exception e,int id) {
                        listener.showFailed();
                    }

                    @Override
                    public void onResponse(CollectDetailData response,int id) {
                        if(response.getRetStatus() == 200){
                            listener.showSuccess(response);
                        }else {
                            listener.showFailed();
                        }
                    }
                });
    }
}
