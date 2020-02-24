package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetCollectionInOutNumInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.GetCollectionInOutNumListener;
import com.bagevent.common.Constants;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2017/6/15.
 */

public class GetCollectionInOutNumImpl implements GetCollectionInOutNumInterface {
    @Override
    public void getInOutNum(Context mContext, String collectionId, final GetCollectionInOutNumListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.COLLECT_IN_OUT_NUM + collectionId + Constants.T_ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.showCollectionInOutNum(response.getRespObject());
                        }else {
                            listener.showCollectionInOutNumFailed(ErrCodeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
