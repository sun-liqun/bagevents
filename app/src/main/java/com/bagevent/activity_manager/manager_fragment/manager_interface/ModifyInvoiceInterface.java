package com.bagevent.activity_manager.manager_fragment.manager_interface;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnModifyInvoiceListener;

/**
 * Created by WenJie on 2017/11/1.
 */

public interface ModifyInvoiceInterface {
    void modifyCommonInvoice(Context mContext, int eventId, String orderNumber, int obtainWay, int invoiceType, String receiver, String cellphone,
                             String address, String invoiceTitle, String invoiceItem, String brief, String taxNum, int companyCodeType, String companyCode, int receiverType,
                             OnModifyInvoiceListener listener);

    void modifyVatmanInvoice(Context mContext, int eventId, String orderNumber, int obtainWay, int invoiceType, String receiver, String cellphone,
                             String address, String invoiceTitle, String invoiceItem, String brief, String taxNum, int companyCodeType, String companyCode, int receiverType,
                             String companyResAddr,String companyFinanceContact,String companyBank,String bankAccount,OnModifyInvoiceListener listener);
}
