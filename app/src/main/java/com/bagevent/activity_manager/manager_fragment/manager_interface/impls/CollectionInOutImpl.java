package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.CollectionInOutInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.CollectionInOutListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2017/6/14.
 */

public class CollectionInOutImpl implements CollectionInOutInterface{
    private static final String TAG = "CollectionInOutImpl";
    @Override
    public void setCollectionInout(Context mContext, final String collectionId, final String barcode, String state, final String checkinTime, final CollectionInOutListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.COLLECT_IN_OUT + collectionId)
                .addParams("barcode",barcode)
                .addParams("state",state)
                .addParams("checkInTime",checkinTime)
                .addParams("access_token", "ipad")
                .addParams("access_secret", "ipad_secret")
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
//                        if(response.getRetStatus() == 200) {
                            listener.setCollectionInOutSuccess(response);
//                        }else {
//                            listener.setCollectionInOutFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
//                        }
                    }
                });
    }
}
