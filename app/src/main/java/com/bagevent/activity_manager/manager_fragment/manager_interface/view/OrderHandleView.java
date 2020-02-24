package com.bagevent.activity_manager.manager_fragment.manager_interface.view;


import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.OrderHandleData;
import com.bagevent.activity_manager.manager_fragment.data.OrderServiceData;

/**
 * Created by ZWJ on 2017/12/26 0026.
 */

public interface OrderHandleView {
    Context mContext();
    String requestData();
    String eBusinessID();
    String requestType();
    String dataSign();
    String dataType();
    void querySuccess();
    void queryFailed();
    void orderHandleSuccess(OrderHandleData response);
    void orderHandleFailed(String errCode);
    void orderServiceSuccess(OrderServiceData response);
    void orderServiceFailed();
}
