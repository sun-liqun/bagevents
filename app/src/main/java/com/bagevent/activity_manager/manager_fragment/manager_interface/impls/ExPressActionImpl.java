package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.OrderHandleData;
import com.bagevent.activity_manager.manager_fragment.data.OrderServiceData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.ExpressActionInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnExpressHandleListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnOrderHandleListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnOrderServiceListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by ZWJ on 2017/12/26 0026.
 */

public class ExPressActionImpl implements ExpressActionInterface {

    @Override
    public void orderHandle(Context mContext,final String requestData, String eBusinessID, String requestType, String dataSign, String dataType, final OnOrderHandleListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.QUERY_EXPRESS_URL)
                .addParams("RequestData",requestData)
                .addParams("EBusinessID",eBusinessID)
                .addParams("RequestType",requestType)
                .addParams("DataSign",dataSign)
                .addParams("DataType",dataType)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("ExpressActionImpl",response + " ");
                        if(response.contains("\"Success\": true")) {
                            OrderHandleData gsons = new Gson().fromJson(response,OrderHandleData.class);
                            listener.orderHandleSuccess(gsons);
                        }else {
                            OrderHandleData errCode = new Gson().fromJson(response,OrderHandleData.class);
                            listener.orderHandleFailed(errCode.getCode());
                        }
                    }
                });
    }

    @Override
    public void queryAction(Context mContext,String requestData, String eBusinessID, String requestType, String dataSign,final OnExpressHandleListener listener) {

        OkHttpUtil.okPost(mContext)
                .url(Constants.QUERY_EXPRESS_URL)
                .addParams("RequestData",requestData)
                .addParams("EBusinessID",eBusinessID)
                .addParams("RequestType",requestType)
                .addParams("DataSign",dataSign)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("ExpressActionImpl",response);
                    }
                });
    }

    @Override
    public void eOrderService(Context mContext,String requestData, String eBusinessID, String requestType, String dataSign, String dataType, final OnOrderServiceListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.TEST_ORDER_SERVICE_URL)
                .addParams("RequestData",requestData)
                .addParams("EBusinessID",eBusinessID)
                .addParams("RequestType",requestType)
                .addParams("DataSign",dataSign)
                .addParams("DataType",dataType)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("ExpressActionImpl",response);
                        if(response.contains("\"Success\": true")) {
                            OrderServiceData data = new Gson().fromJson(response,OrderServiceData.class);
                            listener.orderServiceSuccess(data);
                        }else {
                            listener.orderServiceFailed();
                        }
                    }
                });
    }
}
