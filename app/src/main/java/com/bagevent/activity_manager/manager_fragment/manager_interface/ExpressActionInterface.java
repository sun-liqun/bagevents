package com.bagevent.activity_manager.manager_fragment.manager_interface;


import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnExpressHandleListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnOrderHandleListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnOrderServiceListener;

/**
 * Created by ZWJ on 2017/12/26 0026.
 */

public interface ExpressActionInterface {
    void orderHandle(Context mContext,String requestData, String eBusinessID, String requestType, String dataSign, String dataType, OnOrderHandleListener listener);
    void queryAction(Context mContext,String requestData, String eBusinessID, String requestType, String dataSign, OnExpressHandleListener listener);
    void eOrderService(Context mContext,String requestData, String eBusinessID, String requestType, String dataSign, String dataType, OnOrderServiceListener listener);
}
