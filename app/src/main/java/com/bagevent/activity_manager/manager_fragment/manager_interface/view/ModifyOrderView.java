package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by WenJie on 2017/10/30.
 */

public interface ModifyOrderView {
    Context mContext();
    int eventId();
    String orderNumber();
    String buyerName();
    String buyerEmail();
    String buyerCellphone();
    String areaCode();
    void showModifySuccess(String response);
    void showModifyFailed(String errInfo);
}
