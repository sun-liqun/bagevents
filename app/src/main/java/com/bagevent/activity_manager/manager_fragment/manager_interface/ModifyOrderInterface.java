package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnModifyOrderListener;

/**
 * Created by WenJie on 2017/10/30.
 */

public interface ModifyOrderInterface {
    void modifyOrderInfo(Context mContext, int eventId, String orderNumber, String buerName, String buerEmail, String buyerCellphone, String areaCode,OnModifyOrderListener listener);
    void modifyOrderAuditStatus(Context mContext,int eventId,String orderNumber,int audit,OnModifyOrderListener listener);
}
