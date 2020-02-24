package com.bagevent.util.dbutil;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.InvoiceListData;
import com.bagevent.common.Common;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.Invoice;
//import com.bagevent.db.Invoice_Table;
//import com.bagevent.db.Order_Table;
import com.bagevent.db.Invoice_Table;
import com.bagevent.util.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZWJ on 2017/12/28 0028.
 */

public class SyncInvoiceUtil {
    private Context context;
    private String userId;
    private String eventId;
    private String jsonString;

    public SyncInvoiceUtil(Context context,String userId, String eventId, String jsonString) {
        this.context = context;
        this.userId = userId;
        this.eventId = eventId;
        this.jsonString = jsonString;
    }

    public void syncInvoice() {
        SyncInvoiceThread thread = new SyncInvoiceThread();
        thread.start();
    }


    private class SyncInvoiceThread extends Thread {
        @Override
        public void run() {
            List<Invoice> orderList = new ArrayList<Invoice>();
            InvoiceListData info = new Gson().fromJson(jsonString,InvoiceListData.class);
            long currentTime = info.getRespObject().getPagination().getCurrentTime();
            int pageNum = info.getRespObject().getPagination().getPageNumber();
            int pageCount = info.getRespObject().getPagination().getPageCount();
            int nextPage = pageNum;
            if(pageCount != 0 && pageCount != pageNum) {
                nextPage = pageNum + 1;
            }
            for (int i = 0; i < info.getRespObject().getObjects().size(); i++) {
                InvoiceListData.RespObjectBean.ObjectsBean bean = info.getRespObject().getObjects().get(i);
                Invoice order = invoiceBean(bean);
                orderList.add(order);
            }
            setDataToDB(orderList,currentTime,pageCount,pageNum,nextPage);
        }
    }

    private Invoice invoiceBean(InvoiceListData.RespObjectBean.ObjectsBean bean) {
        Delete.table(Invoice.class, OperatorGroup.clause().and(Invoice_Table.userId.is(Integer.parseInt(userId))).and(Invoice_Table.eventId.is(eventId)).and(Invoice_Table.orderInvoiceId.is(bean.getTicketOrderInvoiceInfo().getOrderInvoiceId())));
        InvoiceListData.RespObjectBean.ObjectsBean.TicketOrderInvoiceInfoBean invoiceBean = bean.getTicketOrderInvoiceInfo();
        Invoice invoice = new Invoice();
        invoice.userId = Integer.parseInt(userId);
        invoice.eventId = eventId;
        invoice.buyerCellphone = bean.getBuyerCellphone();
        invoice.buyerEmail = bean.getBuyerEmail();
        invoice.buyerFirstName = bean.getBuyerFirstName();
        invoice.buyerLastName = bean.getBuyerLastName();
        invoice.buyerName = bean.getBuyerName();
        invoice.orderId = bean.getOrderId();
        invoice.payTotalPrice = bean.getPayTotalPrice();
        invoice.rawTotalPrice = bean.getRawTotalPrice();
        invoice.address = invoiceBean.getAddress();
        invoice.bankAccount = invoiceBean.getBankAccount();
        invoice.brief = invoiceBean.getBrief();
        invoice.cellphone = invoiceBean.getCellphone();
        invoice.companyBank = invoiceBean.getCompanyBank();
        invoice.companyCode = invoiceBean.getCompanyCode();
        invoice.companyFinanceContact = invoiceBean.getCompanyFinanceContact();
        invoice.companyRegAddr = invoiceBean.getCompanyRegAddr();
        invoice.confirm = invoiceBean.getConfirm();
        invoice.eInvoiceCellPhone = invoiceBean.getEInvoiceCellPhone();
        invoice.expressCompany = invoiceBean.getExpressCompany();
        invoice.expressNum = invoiceBean.getExpressNum();
        invoice.invoiceCode = invoiceBean.getInvoiceCode();
        invoice.invoiceItem = invoiceBean.getInvoiceItem();
        invoice.invoiceNumber = invoiceBean.getInvoiceNumber();
        invoice.invoiceTitle = invoiceBean.getInvoiceTitle();
        invoice.invoiceType = invoiceBean.getInvoiceType();
        invoice.obtaied = invoiceBean.getObtaied();
        invoice.obtainMethod = invoiceBean.getObtainMethod();
        invoice.obtainWay = invoiceBean.getObtainWay();
        invoice.orderInvoiceId = invoiceBean.getOrderInvoiceId();
        invoice.orderNumber = invoiceBean.getOrderNumber();
        invoice.receiver = invoiceBean.getReceiver();
        invoice.receiverType = invoiceBean.getReceiverType();
        invoice.taxNum = invoiceBean.getTaxNum();
        invoice.updateTime =invoiceBean.getUpdateTime();
        invoice.useCompanyCode =invoiceBean.getUseCompanyCode();
        return invoice;
    }

    private void setDataToDB(List<Invoice> list, final long currentTime, final int pageCount, final int pageNum, final int nextPage) {
        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Invoice>() {
                            @Override
                            public void processModel(Invoice model, DatabaseWrapper wrapper) {
                                model.save();
                            }
                        }).addAll(list).build())
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        Log.e("list", "Database transaction failed.", error);
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        //通知UI更新界面
                        if(pageNum == pageCount || pageCount == 0) {
                            SharedPreferencesUtil.put(context, "invoiceTime" + eventId, currentTime+"");
                            EventBus.getDefault().postSticky(new MsgEvent(Common.SYNC_INFO_SUCCESS));
                        }else {
                            //   Log.e("SyncOrderUtil","pageCount=======================================" + nextPage);
                            EventBus.getDefault().postSticky(new MsgEvent(pageCount,nextPage, Common.ORDER_PAGE));
                        }
                    }
                }).build().execute();
    }
}