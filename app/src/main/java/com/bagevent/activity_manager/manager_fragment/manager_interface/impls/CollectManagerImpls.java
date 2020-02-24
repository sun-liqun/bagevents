package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.CollectManagerCallback;
import com.bagevent.activity_manager.manager_fragment.data.CollectManagerData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.CollectManagerInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnCollectManagerListener;
import com.bagevent.common.Constants;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2016/7/15.
 */
public class CollectManagerImpls implements CollectManagerInterface {
    @Override
    public void getCollectList(Context mContext, String eventId, final OnCollectManagerListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.COLLECT_MANAGER + eventId + Constants.ACCESS_TOKENS + Constants.ACCESS_SERCRET)
                .tag("CollectManagerFragment")
                .build()
                .execute(new CollectManagerCallback() {
                    @Override
                    public void onError(Call call, Exception e,int id) {
                        if (call.isCanceled()){
                            LogUtil.e("CollectManagerFragment取消请求");
                        }else {
                            Log.e("CollectManagerImpls",e.getMessage());
                            listener.showFailed();
                        }

                    }

                    @Override
                    public void onResponse(CollectManagerData response,int id) {
                        if (response.getRetStatus() == 200 ){
                            listener.showCollectList(response);
                        }else {
                            listener.showFailed();
                        }
                    }
                });
    }
}
