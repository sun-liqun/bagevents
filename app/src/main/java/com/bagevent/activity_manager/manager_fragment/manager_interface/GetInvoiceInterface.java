package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.GetInvoiceListListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetInvoiceListener;

/**
 * Created by zwj on 2017/10/26.
 */

public interface GetInvoiceInterface {
    void getInvoiceList(Context mContext,String userId,String eventId,int page,GetInvoiceListListener listener);
    void getInvoiceListFromTime(Context mContext,String userId,String eventId,String updateTime,GetInvoiceListListener listener);
    void getInvoice(Context mContext, int eventId, String  orderNumber, OnGetInvoiceListener listener);
}
