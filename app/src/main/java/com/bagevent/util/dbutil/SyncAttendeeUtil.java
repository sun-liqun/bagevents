package com.bagevent.util.dbutil;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.AttendeeData;
import com.bagevent.activity_manager.manager_fragment.data.AttendeeJsonFileData;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.util.CompareRex;
import com.bagevent.util.LogUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
 * Created by zwj on 2017/9/11.
 */

public class SyncAttendeeUtil {
    private Context mContext;
    private String jsonString = "";
    private int eventId = -1;
    private int stType = -1;

    public SyncAttendeeUtil(Context mContext, String jsonString, int eventId, int stType) {
        this.mContext = mContext;
        this.jsonString = jsonString;
        this.eventId = eventId;
        this.stType = stType;
    }

    public void syncAttendeeFile() {
        SyncAttendeeFileThread thread = new SyncAttendeeFileThread();
        thread.start();
    }

    public void syncAttendees() {
        SyncAttendeeThread thread = new SyncAttendeeThread();
        thread.start();
    }

    private class SyncAttendeeFileThread extends Thread {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
            AttendeeJsonFileData attendeeJsonFileData = new AttendeeJsonFileData(jsonObject);
            List<Attends> listAttendee = new ArrayList<Attends>();
            long currentTime = attendeeJsonFileData.getRespObject().getCurrentTime();
            for (int i = 0; i < attendeeJsonFileData.getRespObject().getApiAttendees().size(); i++) {
                AttendeeJsonFileData.RespObjectBean.ApiAttendeesBean attendeesBean = attendeeJsonFileData.getRespObject().getApiAttendees().get(i);
                String attendeeMap = "";
                String badgeMap = "";
                attendeeMap = attendeesBean.getAttendeeMap();
                if (stType == 1) {
                    badgeMap = attendeesBean.getBadgeMap();
                }
                Attends attend = attendsFileBean(attendeesBean, attendeeMap, badgeMap);
                listAttendee.add(attend);
            }
            long endTime = System.currentTimeMillis();
            int dTime = (int) ((endTime - startTime) / 1000); //9
            LogUtil.e("json字符串解析时长" + dTime);
            setDataToDB(listAttendee, currentTime, 1, 0, 0, 0);


//                JSONObject object = new JSONObject(jsonString).getJSONObject("respObject");
//                JSONArray array = object.getJSONArray("apiAttendees");
//                Gson gson = new GsonBuilder()
//                        .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
//                        .create();
//                AttendeeJsonFileData attendee = gson.fromJson(jsonString,AttendeeJsonFileData.class);
//                List<Attends> listAttendee = new ArrayList<Attends>();
//                long currentTime = attendee.getRespObject().getCurrentTime();
//                for (int i = 0; i < attendee.getRespObject().getApiAttendees().size(); i++) {
//                    String attendeeMap = "";
//                    String badgeMap = "";
//                    AttendeeJsonFileData.RespObjectBean.ApiAttendeesBean attendeeData = attendee.getRespObject().getApiAttendees().get(i);
//                    attendeeMap = array.getJSONObject(i).getJSONObject("attendeeMap").toString();
//                    if(stType == 1) {
//                        badgeMap = array.getJSONObject(i).getJSONObject("badgeMap").toString();
//                    }
//                    Attends attend = attendsFileBean(attendeeData,attendeeMap,badgeMap);
//                    listAttendee.add(attend);
//                }
//                setDataToDB(listAttendee,currentTime,1,0,0,0);
        }
    }

    private class SyncAttendeeThread extends Thread {
        @Override
        public void run() {
            List<Attends> attendeeList = new ArrayList<Attends>();
            JsonObject object = new JsonParser().parse(jsonString).getAsJsonObject().getAsJsonObject("respObject");
            JsonArray jsonArray = object.getAsJsonArray("objects");
            JsonObject objectPage = object.getAsJsonObject("pagination");
            AttendeeData attendeeData = new Gson().fromJson(jsonString, AttendeeData.class);
            long currentTime = objectPage.get("currentTime").getAsLong();
            int pageCount = attendeeData.getRespObject().getPagination().getPageCount();
            int pageNumber = attendeeData.getRespObject().getPagination().getPageNumber();
            int nextPage = pageNumber;
            if (pageCount != 0 && pageCount != pageNumber) {
                nextPage = pageNumber + 1;
            }
            for (int i = 0; i < attendeeData.getRespObject().getObjects().size(); i++) {
                String attendeeMap = "";
                String badgeMap = "";
                AttendeeData.RespObjectBean.ObjectsBean attendee = attendeeData.getRespObject().getObjects().get(i);
                if (jsonArray.get(i).getAsJsonObject().get("attendeeMap").isJsonObject()) {
                    attendeeMap = jsonArray.get(i).getAsJsonObject().getAsJsonObject("attendeeMap").toString();
                }
                if (stType == 1 && jsonArray.get(i).getAsJsonObject().get("badgeMap").isJsonObject()) {
                    badgeMap = jsonArray.get(i).getAsJsonObject().get("badgeMap").toString();
                }
                Attends attend = attendsBean(attendee, attendeeMap, badgeMap);
                attendeeList.add(attend);
            }
            setDataToDB(attendeeList, currentTime, 0, pageCount, pageNumber, nextPage);


//                List<Attends> attendeeList = new ArrayList<Attends>();
//                final JSONObject object = new JSONObject(jsonString).getJSONObject("respObject");
//                final JSONArray jsonArray = object.getJSONArray("objects");
//                final JSONObject objectPage = object.getJSONObject("pagination");
//                Gson gson = new GsonBuilder()
//                        .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
//                        .create();
//                AttendeeData attendeeData = gson.fromJson(jsonString, AttendeeData.class);
//                long currentTime = objectPage.getLong("currentTime");
//                int pageCount = attendeeData.getRespObject().getPagination().getPageCount();
//                int pageNumber = attendeeData.getRespObject().getPagination().getPageNumber();
//                int nextPage = pageNumber;
//                if (pageCount != 0 && pageCount != pageNumber) {
//                    nextPage = pageNumber + 1;
//                }
//                for (int i = 0; i < attendeeData.getRespObject().getObjects().size(); i++) {
//                    String attendeeMap = "";
//                    String badgeMap = "";
//                    AttendeeData.RespObjectBean.ObjectsBean attendee = attendeeData.getRespObject().getObjects().get(i);
//                    attendeeMap = jsonArray.getJSONObject(i).getJSONObject("attendeeMap").toString();
//                    if (stType == 1) {
//                        badgeMap = jsonArray.getJSONObject(i).getJSONObject("badgeMap").toString();
//                    }
//                    Attends attend = attendsBean(attendee, attendeeMap, badgeMap);
//                    attendeeList.add(attend);
//                }
//                setDataToDB(attendeeList, currentTime, 0, pageCount, pageNumber, nextPage);
        }
    }

    private Attends attendsFileBean(AttendeeJsonFileData.RespObjectBean.ApiAttendeesBean attendee, String attedeeMap, String badgeMap) {
        //    Log.e("attendeeId",attendee.getAttendeeId() +" F" + attendee.getName());
        Delete.table(Attends.class, OperatorGroup.clause().and(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(Integer.parseInt(attendee.getAttendeeId()))));
        Attends attend = new Attends();
        attend.attendeeAvatar = attendee.getAttendeeAvatar();
        attend.eventId = eventId;
        attend.attendId = Integer.parseInt(attendee.getAttendeeId());
        if (!TextUtils.isEmpty(attendee.getName())) {
            attend.names = CompareRex.escapeCharacter(attendee.getName());
        } else {
            attend.names = "null";
        }

        if (!TextUtils.isEmpty(attendee.getPinyinName())) {
            attend.pinyinNames = attendee.getPinyinName();
            attend.strSort = attendee.getPinyinName().substring(0, 1).toUpperCase();
        } else {
            attend.pinyinNames = "null";
            attend.strSort = "#";
        }

        if (!TextUtils.isEmpty(attendee.getFirstName())) {
            attend.firstName = attendee.getFirstName();
        } else {
            attend.firstName = "null";
        }

        if (!TextUtils.isEmpty(attendee.getLastName())) {
            attend.firstName = attendee.getLastName();
        } else {
            attend.firstName = "null";
        }

        attend.audits = Integer.parseInt(attendee.getAudit());
        attend.auditTimes = attendee.getAuditTime();
        attend.avatars = attendee.getAvatar();
        attend.barcodes = attendee.getBarcode();
        attend.buyWays = Integer.parseInt(attendee.getBuyWay());
        attend.cellphones = attendee.getCellphone();
        attend.checkinCodes = attendee.getCheckinCode();
        attend.checkins = Integer.parseInt(attendee.getCheckin());
        attend.checkinTimes = attendee.getCheckinTime();
        attend.emailAddrs = attendee.getEmailAddr();
        attend.notes = attendee.getNotes();
        if (!TextUtils.isEmpty(attendee.getOrderId())) {
            attend.orderIds = Integer.parseInt(attendee.getOrderId());
        }
        attend.payStatuss = Integer.parseInt(attendee.getPayStatus());
        attend.refCodes = attendee.getRefCode();
        if (!TextUtils.isEmpty(attendee.getTicketId())) {
            attend.ticketIds = Integer.parseInt(attendee.getTicketId());
        }
        attend.ticketPrices = attendee.getTicketPrice();
        attend.updateTimes = attendee.getUpdateTime();
        attend.weixinIds = attendee.getWeixinId();
        attend.productIds = attendee.getProductIds();
        attend.gsonUser = attedeeMap;
        attend.badgeMap = badgeMap;
        attend.buyingGroupId=attendee.getBuyingGroupId();
        attend.bgState=attendee.getBgState();
        attend.hasBuyingGrouping=attendee.isHasBuyingGrouping();
        attend.isSync = Constants.SYNC;
        attend.auditSync = Constants.AUDIT_SYNC;
        attend.addSync = Constants.ADD_SYNC;
        attend.modifyIsSync = Constants.MODIFY_SYNC;
        //   Log.e("SyncAttendeeUtil","orderId=" + attendee.getOrderId() + "ticketId="+attendee.getTicketId() +"payStatus=" + Integer.parseInt(attendee.getPayStatus()) + "names="+attendee.getName());
        return attend;
    }

    private Attends attendsBean(AttendeeData.RespObjectBean.ObjectsBean attendee, String attedeeMap, String badgeMap) {
        Attends attend = new Attends();
        //根据attendId删除已存在的用户
        Delete.table(Attends.class, OperatorGroup.clause().and(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(Integer.parseInt(attendee.getAttendeeId()))));
        if (!TextUtils.isEmpty(attendee.getName())) {
            attend.names = CompareRex.escapeCharacter(attendee.getName());
        } else {
            attend.names = "null";
        }

        if (!TextUtils.isEmpty(attendee.getPinyinName())) {
            attend.pinyinNames = attendee.getPinyinName();
            attend.strSort = attendee.getPinyinName().substring(0, 1).toUpperCase();
        } else {
            attend.pinyinNames = "null";
            attend.names = "#";
        }

        if (!TextUtils.isEmpty(attendee.getFirstName())) {
            attend.firstName = attendee.getFirstName();
        } else {
            attend.firstName = "null";
        }

        if (!TextUtils.isEmpty(attendee.getLastName())) {
            attend.firstName = attendee.getLastName();
        } else {
            attend.firstName = "null";
        }
        attend.attendeeAvatar = attendee.getAttendeeAvatar();
        attend.eventId = eventId;
        attend.attendId = Integer.parseInt(attendee.getAttendeeId());
        attend.audits = Integer.parseInt(attendee.getAudit());
        attend.auditTimes = attendee.getAuditTime();
        attend.avatars = attendee.getAvatar();
        attend.barcodes = attendee.getBarcode();
        attend.buyWays = Integer.parseInt(attendee.getBuyWay());
        attend.cellphones = attendee.getCellphone();
        attend.checkinCodes = attendee.getCheckinCode();
        attend.checkins = Integer.parseInt(attendee.getCheckin());
        attend.checkinTimes = attendee.getCheckinTime();
        attend.emailAddrs = attendee.getEmailAddr();
        attend.notes = attendee.getNotes();
        attend.orderIds = Integer.parseInt(attendee.getOrderId());
        attend.payStatuss = Integer.parseInt(attendee.getPayStatus());
        attend.refCodes = attendee.getRefCode();
        attend.ticketIds = Integer.parseInt(attendee.getTicketId());
        attend.ticketPrices = attendee.getTicketPrice();
        attend.updateTimes = attendee.getUpdateTime();
        attend.weixinIds = attendee.getWeixinId();
        attend.productIds = attendee.getProductIds();
        attend.gsonUser = attedeeMap;
        attend.badgeMap = badgeMap;
        attend.buyingGroupId=attendee.getBuyingGroupId();
        attend.bgState=attendee.getBgState();
        attend.hasBuyingGrouping=attendee.isHasBuyingGrouping();
        attend.isSync = Constants.SYNC;
        attend.auditSync = Constants.AUDIT_SYNC;
        attend.addSync = Constants.ADD_SYNC;
        attend.modifyIsSync = Constants.MODIFY_SYNC;
        //    Log.e("SyncAttendeeUtil","orderId=" + attendee.getOrderId() + "ticketId="+attendee.getTicketId());
        return attend;
    }

    /**
     * @param list
     * @param currentTime
     * @param type        0---参会人员Json串 1---参会人员文件
     */
    private void setDataToDB(final List<Attends> list, final long currentTime, final int type, final int pageCount, final int pageNum, final int nextPage) {
        final long startTime = System.currentTimeMillis();
        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Attends>() {
                            @Override
                            public void processModel(Attends model, DatabaseWrapper wrapper) {
                                model.save();
                            }
                        }).addAll(list).build())
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        Log.e("list", "Database transaction failed.", error);
                        EventBus.getDefault().post(new MsgEvent(Common.SYNC_ATTEND_INFO_FAIED));
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        long endTime = System.currentTimeMillis();
                        int dTime = (int) ((endTime - startTime) / 1000);
                        LogUtil.e("插入到数据库时长" + dTime);
                        //通知UI更新界面
                        if (type == 1) {
                            SharedPreferencesUtil.put(mContext, "currentTime" + eventId, currentTime + "");
                            EventBus.getDefault().postSticky(new MsgEvent(Common.SYNC_ATTEND_INFO_SUCCESS));
                        } else {
                            if (pageNum == pageCount || pageCount == 0) {
                                SharedPreferencesUtil.put(mContext, "currentTime" + eventId, currentTime + "");
                                EventBus.getDefault().postSticky(new MsgEvent(Common.SYNC_ATTEND_INFO_SUCCESS));
                            } else {
                                EventBus.getDefault().postSticky(new MsgEvent(pageCount, nextPage, Common.ATTENDEE_PAGE));
                            }
                        }
                    }
                }).build().execute();
    }

//    public class NullStringToEmptyAdapterFactory implements TypeAdapterFactory {
//        public TypeAdapter create(Gson gson, TypeToken type) {
//            Class rawType = (Class) type.getRawType();
//            if (rawType != String.class) {
//                return null;
//            }
//            return (TypeAdapter) new StringNullAdapter();
//        }
//    }
//
//    public class StringNullAdapter extends TypeAdapter {
//
//        @Override
//        public void write(JsonWriter writer, Object value) throws IOException {
//            if (value == null) {
//                writer.value("");
//                return;
//            }
//            writer.value(value.toString());
//        }
//
//        @Override
//        public Object read(JsonReader reader) throws IOException {
//            if (reader.peek() == JsonToken.NULL) {
//                reader.nextNull();
//                return "";
//            }
//
//            return reader.nextString();
//        }
//    }
}
