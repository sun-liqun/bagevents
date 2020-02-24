package com.bagevent.util.dbutil;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.AttendeeData;
import com.bagevent.activity_manager.manager_fragment.data.SingleAttendeeData;
import com.bagevent.activity_manager.manager_fragment.data.SingleAttendeeFromIdData;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.Attends;
import com.bagevent.db.Attends_Table;
import com.bagevent.util.CompareRex;
//import com.bagevent.util.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public class SyncSingleAttendeeUtil {
    private Context context;
    private int eventId;
    private String jsonString;
    private boolean isFromAttendeeId = false;

    public SyncSingleAttendeeUtil(Context context, int eventId, String jsonString, boolean where) {
        this.context = context;
        this.eventId = eventId;
        this.jsonString = jsonString;
        this.isFromAttendeeId = where;
    }

    public void syncSingleAttendeeUtil() {
        SyncSingleAttendeeThread thread = new SyncSingleAttendeeThread();
        thread.start();
    }


    private class SyncSingleAttendeeThread extends Thread {
        @Override
        public void run() {
            if (!isFromAttendeeId) {
                try {
                    JSONObject object = new JSONObject(jsonString).getJSONObject("respObject");
                    JSONArray array = object.getJSONArray("attendeeList");
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                            .create();
                    SingleAttendeeData attendee = gson.fromJson(jsonString, SingleAttendeeData.class);
                    List<Attends> listAttendee = new ArrayList<Attends>();
                    for (int i = 0; i < attendee.getRespObject().getAttendeeList().size(); i++) {
                        Delete.table(Attends.class, OperatorGroup.clause().and(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(Integer.parseInt(attendee.getRespObject().getAttendeeList().get(i).getAttendeeId()))));
                        String attendeeMap = "";
                        SingleAttendeeData.RespObjectBean.AttendeeListBean attendeeData = attendee.getRespObject().getAttendeeList().get(i);
                        attendeeMap = array.getJSONObject(i).getJSONObject("attendeeMap").toString();
                        Attends attend = attendsBean(attendeeData, attendeeMap);
                        listAttendee.add(attend);
                    }
                    setDataToDB(listAttendee);
                } catch (JSONException e) {
                    Log.e("SyncAttendeeUtil", "attendee------------>" + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                try {
                    JSONObject object = new JSONObject(jsonString).getJSONObject("respObject");
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                            .create();
                    SingleAttendeeFromIdData attendee = gson.fromJson(jsonString, SingleAttendeeFromIdData.class);
                    List<Attends> listAttendee = new ArrayList<Attends>();
                    JSONObject object1 = object.getJSONObject("attendeeInfo");
                    String attendeeMap = object1.getJSONObject("attendeeMap").toString();
                    Attends attend = attendsBeanFromId(attendee.getRespObject().getAttendeeInfo(), attendeeMap);
                    listAttendee.add(attend);
                    setDataToDB(listAttendee);
                } catch (JSONException e) {
                    Log.e("SyncAttendeeUtil", "attendee------------>" + e.getMessage());
                    e.printStackTrace();
                }
            }

        }
    }

    private Attends attendsBeanFromId(SingleAttendeeFromIdData.RespObjectBean.AttendeeInfoBean attendee, String attedeeMap) {
        Attends attend = new Attends();
        //根据attendId删除已存在的用户
        Delete.table(Attends.class, OperatorGroup.clause().and(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(Integer.parseInt(attendee.getAttendeeId()))));
        Log.e("SyncSingleAttendee", attendee.getName());
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
        attend.orderIds = Integer.parseInt(attendee.getOrderId());
        attend.ticketIds = Integer.parseInt(attendee.getTicketId());
        attend.payStatuss = Integer.parseInt(attendee.getPayStatus());
        attend.refCodes = attendee.getRefCode();
        attend.ticketPrices = attendee.getTicketPrice();
        attend.updateTimes = attendee.getUpdateTime();
        attend.weixinIds = attendee.getWeixinId();
        attend.productIds = attendee.getProductIds();
        attend.gsonUser = attedeeMap;
        attend.isSync = Constants.SYNC;
        attend.auditSync = Constants.AUDIT_SYNC;
        attend.addSync = Constants.ADD_SYNC;
        attend.modifyIsSync = Constants.MODIFY_SYNC;
        attend.orderItemId = attendee.getApiTicketOrderItem().getOrderItemId();
        return attend;
    }


    private Attends attendsBean(SingleAttendeeData.RespObjectBean.AttendeeListBean attendee, String attedeeMap) {

        Attends attend = new Attends();
        //根据attendId删除已存在的用户
        //  Delete.table(Attends.class, OperatorGroup.clause().and(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendee.getAttendeeId())));
        // Log.e("SyncSingleAttendee",attendee.getName() + " ");

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
        attend.ticketIds = Integer.parseInt(attendee.getTicketId());
        attend.payStatuss = Integer.parseInt(attendee.getPayStatus());
        attend.refCodes = attendee.getRefCode();
        attend.ticketPrices = attendee.getTicketPrice();
        attend.updateTimes = attendee.getUpdateTime();
        attend.weixinIds = attendee.getWeixinId();
        attend.productIds = attendee.getProductIds();
        attend.gsonUser = attedeeMap;
        attend.isSync = Constants.SYNC;
        attend.auditSync = Constants.AUDIT_SYNC;
        attend.addSync = Constants.ADD_SYNC;
        attend.modifyIsSync = Constants.MODIFY_SYNC;
        return attend;
    }

    private void setDataToDB(List<Attends> list) {
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
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        EventBus.getDefault().post(new MsgEvent(Common.SINGLE_DATA));
                    }
                }).build().execute();
    }

    public class NullStringToEmptyAdapterFactory implements TypeAdapterFactory {
        public TypeAdapter create(Gson gson, TypeToken type) {
            Class rawType = (Class) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter) new StringNullAdapter();
        }
    }

    public class StringNullAdapter extends TypeAdapter {

        @Override
        public void write(JsonWriter writer, Object value) throws IOException {
            if (value == null) {
                writer.value("");
                return;
            }
            writer.value(value.toString());
        }

        @Override
        public Object read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }
    }

}
