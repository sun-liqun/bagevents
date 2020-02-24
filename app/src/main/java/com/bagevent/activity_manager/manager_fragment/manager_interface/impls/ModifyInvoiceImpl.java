package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.ModifyInvoiceInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnModifyInvoiceListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import okhttp3.Call;

/**
 * Created by WenJie on 2017/11/1.
 */

public class ModifyInvoiceImpl implements ModifyInvoiceInterface {
    @Override
    public void modifyCommonInvoice(Context mContext, int eventId, String orderNumber, int obtainWay, int invoiceType, String receiver,
                                    String cellphone, String address, String invoiceTitle, String invoiceItem, String brief, final String taxNum,
                                    final int companyCodeType, String companyCode, final int receiverType, final OnModifyInvoiceListener listener) {
        PostFormBuilder post = OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.MODIFY_INVOICE + eventId + "/" + orderNumber)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .addParams("obtainWay",obtainWay + "")
                .addParams("invoiceType",invoiceType+"")
                .addParams("invoiceItem",invoiceItem)
                .addParams("useCompanyCode",companyCodeType+"")
                .addParams("receiverType",receiverType+"");

        if(companyCodeType == 0) {
            post.addParams("invoiceTitle",invoiceTitle);
            if(receiverType == 1) {
                post.addParams("taxNum",taxNum);
            }
        }else {
            post.addParams("companyCode",companyCode)
            .addParams("invoiceTitle","invoiceTitle");
        }

        if(obtainWay == 1) {
            post.addParams("receiver",receiver)
                    .addParams("cellphone",cellphone)
                    .addParams("address",address);
        }

        if (!TextUtils.isEmpty(brief)) {
            post.addParams("brief",brief);
        }

        post.build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        Log.e("ModifyInvoiceImpl",response.getRespObject() + companyCodeType+"");
                        if (response.getRetStatus() == 200) {
                            listener.showModifyInvoiceSuccess(response.getRespObject());
                        }else {
                            listener.showModifyInvoiceFailed(response.getRespObject());
                        }
                    }
                });
    }

    @Override
    public void modifyVatmanInvoice(Context mContext, int eventId, String orderNumber, int obtainWay, int invoiceType, final String receiver,
                                    String cellphone, String address, String invoiceTitle, String invoiceItem, String brief, String taxNum, int companyCodeType,
                                    String companyCode, int receiverType, String companyResAddr, String companyFinanceContact, String companyBank,
                                    String bankAccount, final OnModifyInvoiceListener listener) {

        PostFormBuilder post = OkHttpUtil.okPost(mContext)
                .url(Constants.URL + Constants.MODIFY_INVOICE + eventId + "/" + orderNumber)
                .addParams("access_token","ipad")
                .addParams("access_secret","ipad_secret")
                .addParams("obtainWay",obtainWay + "")
                .addParams("invoiceType",invoiceType+"")
                .addParams("invoiceItem",invoiceItem)
                .addParams("useCompanyCode",companyCodeType+"")
                .addParams("receiverType",receiverType+"");

        if(companyCodeType == 0) {
            post.addParams("invoiceTitle",invoiceTitle)
                    .addParams("taxNum",taxNum)
                    .addParams("companyRegAddr",companyResAddr)
                    .addParams("companyFinanceContact",companyFinanceContact)
                    .addParams("companyBank",companyBank)
                    .addParams("bankAccount",bankAccount);
        }else {
            post.addParams("companyCode",companyCode)
                    .addParams("invoiceTitle","invoiceTitle");
        }

        if(obtainWay == 1) {
            post.addParams("receiver",receiver)
                    .addParams("cellphone",cellphone)
                    .addParams("address",address);
        }

        if (!TextUtils.isEmpty(brief)) {
            post.addParams("brief",brief);
        }

        post.build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(StringData response, int id) {
//                        Log.e("ModifyInvoiceImpl",response.getRespObject());
                        if (response.getRetStatus() == 200) {
                            listener.showModifyInvoiceSuccess(response.getRespObject());
                        }else {
                            listener.showModifyInvoiceFailed(response.getRespObject());
                        }
                    }
                });
    }
}
