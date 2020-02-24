package com.bagevent.util.dbutil;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.SingleOrderData;
import com.bagevent.common.Common;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.Order_Table;
import com.bagevent.db.Order;
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
 * Created by ZWJ on 2017/12/4 0004.
 */

public class SyncSingleOrderUtil {
    private Context mContext;
    private int eventId;
    private SingleOrderData.RespObjectBean bean;

    public SyncSingleOrderUtil(Context mContext, int eventId, SingleOrderData.RespObjectBean singleOrderData) {
        this.mContext = mContext;
        this.eventId = eventId;
        this.bean = singleOrderData;
    }

    public void syncSinleOrderUtil() {
        SyncSingleOrderThread thread = new SyncSingleOrderThread();
        thread.start();
    }

    private class SyncSingleOrderThread extends Thread {
        @Override
        public void run() {
            Delete.table(Order.class, OperatorGroup.clause().and(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(bean.getOrderId())));
            List<Order> orderList = new ArrayList<Order>();
            Order order = new Order();
            order.eventId = eventId;
            order.accountType = bean.getAccountType();
            order.apiUserName = bean.getApiUserName();
            order.audit = bean.getAudit();
            order.auditOrder = bean.isAuditOrder();
            order.bankCode = bean.getBankCode();
            order.buySource = bean.getBuySource();
            order.buyWay = bean.getBuyWay();
            order.buyerCellphone = bean.getBuyerCellphone();
            order.buyerEmail = bean.getBuyerEmail();
            order.buyerFirstName = bean.getBuyerFirstName();
            order.buyerIp = bean.getBuyerIp();
            order.buyerLastName = bean.getBuyerLastName();
            order.buyerName = bean.getBuyerName();
            order.currencySign = bean.getCurrencySign();
            order.deviceName = bean.getDeviceName();
            order.discountCode = bean.getDiscountCode();
            order.discountPrice = bean.getDiscountPrice();
            order.discountType = bean.getDiscountType();
            order.expireTime = bean.getExpireTime();
            order.hasGroup = bean.getHasGroup();
            order.invoiceTaxPrice = bean.getInvoiceTaxPrice();
            order.needInvoice = bean.getNeedInvoice();
            order.notes = bean.getNotes();
            order.orderId = bean.getOrderId();
            order.orderNumber = bean.getOrderNumber();
            order.orderStatus = bean.getOrderStatus();
            order.orderTime = bean.getOrderTime();
            order.payOrderId = bean.getPayOrderId();
            order.payStatus = bean.getPayStatus();
            order.payWay = bean.getPayWay();
            order.quantity = bean.getQuantity();
            order.rawTotalPrice = bean.getRawTotalPrice();
            order.sessionId = bean.getSessionId();
            order.status = bean.getStatus();
            order.totalFee = bean.getTotalFee();
            order.totalPrice = bean.getTotalPrice();
            order.ticketOrderTotalPrice = bean.getTicketOrderTotalPrice();
            order.areaCode = bean.getAreaCode();
            orderList.add(order);
            setDataToDB(orderList);
        }
    }

    private void setDataToDB(List<Order> list) {
        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Order>() {
                            @Override
                            public void processModel(Order model, DatabaseWrapper wrapper) {
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
                        EventBus.getDefault().post(new MsgEvent(Common.SINGLE_DATA));
                    }
                }).build().execute();
    }
}
