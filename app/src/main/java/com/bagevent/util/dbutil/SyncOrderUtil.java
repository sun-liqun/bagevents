package com.bagevent.util.dbutil;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;
import com.bagevent.common.Common;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.Order;
//import com.bagevent.db.Order_Table;
import com.bagevent.db.Order_Table;
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
 * Created by zwj on 2017/10/24.
 */

public class SyncOrderUtil {
    private Context context;
    private int eventId;
    private String jsonString;
    private boolean isMore;

    public SyncOrderUtil(Context context, int eventId, String jsonString) {
        this.context = context;
        this.eventId = eventId;
        this.jsonString = jsonString;
    }

    public void setMore(boolean isMore) {
        this.isMore = isMore;
    }

    public void syncOrder() {
        SyncOrderThread thread = new SyncOrderThread();
        thread.start();
    }

    private class SyncOrderThread extends Thread {
        @Override
        public void run() {
            List<Order> orderList = new ArrayList<Order>();
            OrderInfo info = new Gson().fromJson(jsonString, OrderInfo.class);
            long currentTime = info.getRespObject().getPagination().getCurrentTime();
            int pageNum = info.getRespObject().getPagination().getPageNumber();
            int pageCount = info.getRespObject().getPagination().getPageCount();
            int nextPage = pageNum;
            if (pageCount != 0 && pageCount != pageNum) {
                nextPage = pageNum + 1;
            }
            for (int i = 0; i < info.getRespObject().getObjects().size(); i++) {
                OrderInfo.RespObjectBean.ObjectsBean bean = info.getRespObject().getObjects().get(i);
                Order order = orderBean(bean);
                orderList.add(order);
            }

            setDataToDB(orderList, currentTime, pageCount, pageNum, nextPage);
        }

    }

    private Order orderBean(OrderInfo.RespObjectBean.ObjectsBean bean) {
        Delete.table(Order.class, OperatorGroup.clause().and(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(bean.getOrderId())));
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
        order.buyingGroupId=bean.getBuyingGroupId();
        order.buyingGroupState=bean.getBuyingGroupState();
        return order;
    }

    private void setDataToDB(List<Order> list, final long currentTime, final int pageCount, final int pageNum, final int nextPage) {
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
//                        Log.e("list", "Database transaction failed.", error);
                        Log.e("---------EventBus","订单刷新s");
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
//                        SharedPreferencesUtil.put(context, "orderTime" + eventId, currentTime + "");
//                        if (!isMore) {
//                            EventBus.getDefault().postSticky(new MsgEvent(Common.SYNC_INFO_SUCCESS));
//                        } else {
//                            EventBus.getDefault().postSticky(new MsgEvent(Common.SYNC_INFO_MORE));
//                        }

                        //通知UI更新界面
                        if(pageNum == pageCount || pageCount == 0) {
                            SharedPreferencesUtil.put(context, "orderTime" + eventId, currentTime + "");
                            EventBus.getDefault().postSticky(new MsgEvent(Common.SYNC_INFO_SUCCESS));
                        }else {
                         //   Log.e("SyncOrderUtil","pageCount=======================================" + nextPage);
                            EventBus.getDefault().postSticky(new MsgEvent(pageCount,nextPage, Common.ORDER_PAGE));
                        }
                    }
                }).build().execute();
    }
}
