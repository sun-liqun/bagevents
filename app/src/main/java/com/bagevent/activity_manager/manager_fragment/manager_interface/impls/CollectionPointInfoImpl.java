package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.callback.CollectionInfoCallback;
import com.bagevent.activity_manager.manager_fragment.data.CollectionInfoData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.CollectPointInfoInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.CollectPointInfoListener;
import com.bagevent.common.Constants;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2017/6/13.
 */

public class CollectionPointInfoImpl implements CollectPointInfoInterface {
    private static final String TAG = "CollectionPointInfoImpl";
    @Override
    public void getCollectPointInfo(Context mContext, String eventId, String collectId, final CollectPointInfoListener listener) {
        Log.e(TAG,Constants.URL + Constants.COLLECTION_CONFIG_INFO + collectId + "?eventId=" + eventId + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET);
//        Log.e("--------------ee",Constants.URL + Constants.COLLECTION_CONFIG_INFO + collectId + "?eventId=" + eventId + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET);

        OkHttpUtils.get()
                .url(Constants.URL + Constants.COLLECTION_CONFIG_INFO + collectId + "?eventId=" + eventId + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new CollectionInfoCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,e.getMessage());
                        listener.showCollectPointInfoFailed(R.string.collect_point_error+"");
                    }

                    @Override
                    public void onResponse(CollectionInfoData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.showCollectPointInfo(response);
                        }else {
                            listener.showCollectPointInfoFailed(R.string.collect_point_error+"");
                        }
                    }
                });
    }
}
