package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.manager_interface.SubmitDelegateOrderInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.SubmitDelegateOrderListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zwj on 2017/6/20.
 */

public class SubmitDelegateOrderImpl implements SubmitDelegateOrderInterface {
    private static final String TAG = "SubmitDelegateOrderImpl";
    @Override
    public void submitDelegate(Context mContext, String eventId, String ticketId,String buyway, String payType, String attendeeMap, String badgeMap, SubmitDelegateOrderListener listener) {
        Log.e(TAG,eventId +"  " + buyway+ "  " + ticketId + attendeeMap + badgeMap + payType);
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.SUBMIT_DELEGATE_ORDER)
                .addParams("eventId",eventId)
                .addParams("payWay",buyway)
                .addParams("ticketId",ticketId)
                .addParams("attendeeMap",attendeeMap)
                .addParams("badgeMap",badgeMap)
                .addParams("payType",payType)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e(TAG,response);
                    }
                });
    }
}
