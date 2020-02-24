package com.bagevent.util.dbutil;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.common.Common;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.EventTicket;
import com.bagevent.db.EventTicket_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created by WenJie on 2017/11/13.
 */

public class SyncTicketUtil {
    private Context mContext;
    private int eventId;
    private TicketInfo ticketInfo;
    private boolean isSendEventBus = false;

    public SyncTicketUtil(Context mContext, int eventId, TicketInfo ticketInfo,boolean isSend) {
        this.mContext = mContext;
        this.eventId = eventId;
        this.ticketInfo = ticketInfo;
        this.isSendEventBus = isSend;
    }

    public void syncTicket() {
        SyncTicketThread thread = new SyncTicketThread();
        thread.start();
    }

    private class SyncTicketThread extends Thread {
        @Override
        public void run() {
            List<EventTicket> ticketList = new ArrayList<EventTicket>();
            Delete.table(EventTicket.class, OperatorGroup.clause().and(EventTicket_Table.eventIds.is(eventId)));
            for (int i = 0; i < ticketInfo.getRespObject().size(); i++) {
                TicketInfo.RespObjectBean bean = ticketInfo.getRespObject().get(i);
                EventTicket ticket = new EventTicket();
                ticket.eventIds = eventId;
                ticket.audits = bean.getAudit();
                ticket.auditTickets = bean.isAuditTicket();
                ticket.descriptions = bean.getDescription();
                ticket.endSaleTimes = bean.getEndSaleTime();
                ticket.freeTickets = bean.isFreeTicket();
                ticket.hideStatuss = bean.getHideStatus();
                ticket.selledTimeStatuss = bean.getSelledTimeStatus();
                ticket.showDescriptions = bean.getShowDescription();
                ticket.showTicketNames = bean.getShowTicketName();
                ticket.limitCounts = bean.getLimitCount();
                ticket.maxCounts = bean.getMaxCount();
                ticket.salesTimes = bean.getSalesTime();
                ticket.selledCounts = bean.getSelledCount();
                ticket.checkinCounts = bean.getCheckinCount();
                ticket.sorts = bean.getSort();
                ticket.startSaleTimes = bean.getStartSaleTime();
                ticket.statuss = bean.getStatus();
                ticket.ticketCounts = bean.getTicketCount();
                ticket.ticketFees = bean.getTicketFee();
                ticket.ticketIds = bean.getTicketId();
                ticket.ticketNames = bean.getTicketName();
                ticket.ticketPrices = bean.getTicketPrice();
                ticket.validTickets = bean.isValidTicket();
                ticketList.add(ticket);
            }
            setTicketToDB(ticketList);
        }
    }

    private void setTicketToDB(List<EventTicket> ticketList) {
        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<EventTicket>() {
                            @Override
                            public void processModel(EventTicket model, DatabaseWrapper wrapper) {
                                model.save();
                            }
                        }).addAll(ticketList).build())
                .error(new Transaction.Error() {
                    @Override
                    public void onError(@Nonnull Transaction transaction, @Nonnull Throwable error) {
                        Log.e("list", "Database transaction failed.", error);
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@Nonnull Transaction transaction) {
                        if(isSendEventBus) {
                            EventBus.getDefault().post(new MsgEvent(Common.SYNC_SUCCESS));
                        }
                    }
                }).build().execute();
    }
}
