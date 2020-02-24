package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.InvoiceBean;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetInvoiceInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.GetInvoiceListListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetInvoiceListener;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.ErrorJsonData;
import com.bagevent.util.OkHttpUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zwj on 2017/10/26.
 */

public class GetInvoiceImpl implements GetInvoiceInterface {
    @Override
    public void getInvoiceList(Context mContext, String userId, String eventId, int page, final GetInvoiceListListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.INVOICE_LIST + eventId + "?eventId=" + eventId + "&userId=" + userId + "&page=" + page + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("GetInvoiceImpl",e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("GetInvoiceImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            //InvoiceListData data = new Gson().fromJson(response,InvoiceListData.class);
                            listener.invoiceListSuccess(response);
                        }else {
                            StringData err = new Gson().fromJson(response,StringData.class);
                            listener.invoiceListFailed(err.getRespObject());
                        }
                    }
                });

    }

    @Override
    public void getInvoiceListFromTime(Context mContext, String userId, String eventId, String updateTime, final GetInvoiceListListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.INVOICE_LIST + eventId + "?eventId=" + eventId + "&userId=" + userId + "&from_time=" + updateTime + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("GetInvoiceImpl",e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("GetInvoiceImpl","fromTime"+response);
                        if(response.contains("\"retStatus\":200")) {
                        //    InvoiceListData data = new Gson().fromJson(response,InvoiceListData.class);
                            listener.invoiceListSuccess(response);
                        }else {
                            StringData err = new Gson().fromJson(response,StringData.class);
                            listener.invoiceListFailed(err.getRespObject());
                        }
                    }
                });
    }

    @Override
    public void getInvoice(Context mContext, int eventId, String orderNumber, final OnGetInvoiceListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.INVOICE + eventId + "/" + orderNumber + Constants.ACCESS_TOKENS + Constants.ACCESS_SERCRET)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("GetInvoiceImpl",e.getMessage() +"F");
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        Log.e("GetInvoiceImpl",response);
                        if(response.contains("\"retStatus\":200")) {
                            if(response.contains("\"respObject\":null")) {
                                listener.showInvoiceFailed("0");//无发票信息
                            }else {
                                InvoiceBean res = new Gson().fromJson(response,InvoiceBean.class);
                                listener.showInvoiceSuccess(res);
                            }
                        }else {
                            ErrorJsonData errorString = new Gson().fromJson(response,ErrorJsonData.class);
                            listener.showInvoiceFailed(errorString.getRespObject());
                        }
                    }
                });
    }
}
