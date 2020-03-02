package com.bagevent.util.dbutil;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;
import com.bagevent.common.Common;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.EventList;
//import com.bagevent.db.EventList_Table;
import com.bagevent.db.EventList_Table;
import com.bagevent.db.Order;
import com.bagevent.home.data.ExercisingData;
import com.bagevent.util.CompareRex;
import com.bagevent.util.LogUtil;
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

import javax.annotation.Nonnull;

/**
 * Created by ZWJ on 2017/12/1 0001.
 */
public class SyncEventUtil {

    private Context mContext;
    private int userId;
    private ExercisingData eventData;

    public SyncEventUtil(Context mContext,int userId, ExercisingData eventData) {
        this.mContext = mContext;
        this.userId = userId;
        this.eventData = eventData;
    }

    public void startSyncEventUtil() {
        SyncEventThread thread = new SyncEventThread();
        thread.start();
    }

    public class SyncEventThread extends Thread {
        @Override
        public void run() {
            List<EventList> eventLists = new ArrayList<EventList>();
            Delete.table(EventList.class,OperatorGroup.clause().and(EventList_Table.userId.is(userId)));
            for (int i = 0; i < eventData.getRespObject().getApiEventList().size(); i++) {
                ExercisingData.RespObjectBean.ApiEventListBean event = eventData.getRespObject().getApiEventList().get(i);
                EventList eventList = new EventList();
                eventList.mark = "event";
                eventList.userId = userId;
                eventList.address = event.getAddress();
                eventList.attendeeCount = event.getAttendeeCount();
                eventList.auditCount = event.getAuditCount();
                eventList.brand = event.getBrand();
                eventList.checkinCount = event.getCheckinCount();
                eventList.collectInvoice = event.getCollectInvoice();
                eventList.endTime = event.getEndTime();
                eventList.eventId = event.getEventId();
                eventList.eventName = CompareRex.escapeCharacter(event.getEventName());
                eventList.eventType = event.getEventType();
                eventList.income = event.getIncome();
                eventList.logo = event.getLogo();
                eventList.nameType = event.getNameType();
                eventList.officialWebsite = event.getOfficialWebsite();
                eventList.participantsCount = event.getParticipantsCount();
                eventList.startTime = event.getStartTime();
                eventList.status = event.getStatus();
                eventList.ticketCount = event.getTicketCount();
                eventList.sType = event.getStType();
                eventList.exType = event.getExType();
                eventList.hasLiveModule=event.getHasLiveModule();
                eventLists.add(eventList);
            }

            for (int j = 0; j < eventData.getRespObject().getCollectionEventList().size(); j++) {
                EventList eventList = new EventList();
                ExercisingData.RespObjectBean.CollectionEventListBean collectData = eventData.getRespObject().getCollectionEventList().get(j);
                //Delete.table(EventList.class,OperatorGroup.clause().and(EventList_Table.eventId.is(collectData.getCollectPointId())));
                eventList.mark = "collect";
                eventList.eventId = collectData.getEventId();
                eventList.eventName = collectData.getEventName();
                eventList.collectionName = collectData.getCollectionName();
                eventList.eventTypes = collectData.getEventType();
                eventList.status = collectData.getStatus();
                eventList.startTime = collectData.getStartTime();
                eventList.endTime = collectData.getEndTime();
                eventList.collectPointId = collectData.getCollectPointId();
                eventList.checkinCount = collectData.getCheckinCount();
                eventList.export = collectData.getExport();
                eventList.isRepeat = collectData.getIsRepeat();
                eventList.userId = userId;
                eventList.ticketIds = collectData.getTicketIds();
                eventLists.add(eventList);
            }
            setEventListsToDB(eventLists);
        }

//        private void setEventListsToDB(final List<EventList> eventLists) {
//            FlowManager.getDatabase(AppDatabase.class)
//                    .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
//                            new ProcessModelTransaction.ProcessModel<EventList>() {
//                                @Override
//                                public void processModel(EventList model, DatabaseWrapper wrapper) {
//                                    model.save();
//                                }
//                            }).addAll(eventLists).build())
//                    .error(new Transaction.Error() {
//                        @Override
//                        public void onError(@Nonnull Transaction transaction,@Nonnull Throwable error) {
//                            //Log.e("list", "Database transaction failed.", error);
//                            EventBus.getDefault().post(new MsgEvent("newEventList"));
//                        }
//                    })
//                    .success(new Transaction.Success() {
//                        @Override
//                        public void onSuccess(@Nonnull Transaction transaction) {
//                            EventBus.getDefault().post(new MsgEvent("newEventList"));
//                        }
//                    }).build().execute();
//        }



        private void setEventListsToDB(final List<EventList> eventLists) {
            FlowManager.getDatabase(AppDatabase.class)
                    .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                            new ProcessModelTransaction.ProcessModel<EventList>() {
                                @Override
                                public void processModel(EventList model, DatabaseWrapper wrapper) {
                                    model.save();
                                }
                            }).addAll(eventLists).build())
                    .error(new Transaction.Error() {
                        @Override
                        public void onError(@Nonnull Transaction transaction,@Nonnull Throwable error) {
                            Log.e("-----------list", "Database transaction failed.", error);
                            EventBus.getDefault().post(new MsgEvent("newEventList"));
                        }
                    })
                    .success(new Transaction.Success() {
                        @Override
                        public void onSuccess(@Nonnull Transaction transaction) {
                            EventBus.getDefault().post(new MsgEvent("newEventList"));
                        }
                    }).build().execute();
        }

}
}

