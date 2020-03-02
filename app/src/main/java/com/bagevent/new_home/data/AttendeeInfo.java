package com.bagevent.new_home.data;

import android.renderscript.Sampler;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.AttendPeople;
import com.bagevent.activity_manager.manager_fragment.data.TempData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.text.Format;
import java.util.ArrayList;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/06
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class AttendeeInfo {

    private int respType;
    private int retStatus;
    private RespObjectData respObject;


    public int getRespType() {
        return respType;
    }

    public int getRetStatus() {
        return retStatus;
    }

    public RespObjectData getRespObject() {
        return respObject;
    }


    public AttendeeInfo(JsonObject jsonObject){
        if (jsonObject.get("respType") != null) {
            respType = jsonObject.get("respType").getAsInt();
        }
        if (jsonObject.get("retStatus") != null) {
            retStatus = jsonObject.get("retStatus").getAsInt();
        }
        if (jsonObject.get("respObject") != null && jsonObject.get("respObject").isJsonObject()) {
            respObject = new RespObjectData(jsonObject.getAsJsonObject("respObject"));
        }

    }

    public static class RespObjectData{
        JsonArray jsonArray;
        private ArrayList<TagList> tagLists;
        private ApiAttendee apiAttendee;
        private AttendeeMap attendeeMap;
        private ArrayList<FromFieldList> fromFieldLists;

        public ArrayList<TagList> getTagLists() {
            return tagLists;
        }

        public ApiAttendee getApiAttendee() {
            return apiAttendee;
        }

        public ArrayList<FromFieldList> getFromFieldLists() {
            return fromFieldLists;
        }

        public RespObjectData(JsonObject jsonObject){
            if (jsonObject.get("tagList")!=null&&jsonObject.get("tagList").isJsonArray()){
                JsonArray jsonArray=jsonObject.get("tagList").getAsJsonArray();
                tagLists=new ArrayList<>(jsonArray.size());
                for (int i = 0; i < jsonArray.size(); i++) {
                    tagLists.add(new TagList(jsonArray.get(i).getAsJsonObject()));
                }
            }

            if (jsonObject.get("apiAttendee").isJsonObject()){
                apiAttendee=new ApiAttendee(jsonObject.getAsJsonObject("apiAttendee"));

            }


            if (jsonObject.get("formFieldList")!=null&&jsonObject.get("formFieldList").isJsonArray()){
                jsonArray=jsonObject.get("formFieldList").getAsJsonArray();
                fromFieldLists=new ArrayList<>(jsonArray.size());

                for (int i = 0; i < jsonArray.size(); i++) {
                    fromFieldLists.add(new FromFieldList(jsonArray.get(i).getAsJsonObject()));
                }
            }

            if (jsonObject.getAsJsonObject("apiAttendee").getAsJsonObject("attendeeMap").isJsonObject()){
                attendeeMap=new AttendeeMap(jsonObject.getAsJsonObject("apiAttendee").getAsJsonObject("attendeeMap"),jsonArray);

            }
        }
    }

    public static class TagList{

        private int tagId;
        private int sort;
        private String name;

        public int getTagId() {
            return tagId;
        }

        public int getSort() {
            return sort;
        }

        public String getName() {
            return name;
        }

        public TagList(JsonObject jsonObject){
            tagId=jsonObject.get("tagId").getAsInt();
            sort=jsonObject.get("sort").getAsInt();
            name=jsonObject.get("name").getAsString();
        }

    }
    public static class ApiAttendee{

        int attendeeId;
        int orderId;
        int ticketId;
        String barcode;
        String refCode;
        String checkinCode;
        int checkin;
        int payStatus;
        int audit;
        int checkinTime;
        String emailAddr;
        String cellphone;
        String name;
        String weixinId;
        String avatar;
        double ticketPrice;
        String ticketName;
        String ticketDesc;
        String ticketAudit;
        String orderPayStatus;
        AttendeeMap attendeeMap;
        String userId;
        String attendeeAvatar;
        String attendeeType;
        String editAttendeeKey;

        public int getAttendeeId() {
            return attendeeId;
        }

        public int getOrderId() {
            return orderId;
        }

        public int getTicketId() {
            return ticketId;
        }

        public String getBarcode() {
            return barcode;
        }

        public String getRefCode() {
            return refCode;
        }

        public String getCheckinCode() {
            return checkinCode;
        }

        public int getCheckin() {
            return checkin;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public int getAudit() {
            return audit;
        }

        public int getCheckinTime() {
            return checkinTime;
        }

        public String getEmailAddr() {
            return emailAddr;
        }

        public String getCellphone() {
            return cellphone;
        }

        public String getName() {
            return name;
        }

        public String getWeixinId() {
            return weixinId;
        }

        public String getAvatar() {
            return avatar;
        }

        public double getTicketPrice() {
            return ticketPrice;
        }

        public String getTicketName() {
            return ticketName;
        }

        public String getTicketDesc() {
            return ticketDesc;
        }

        public String getTicketAudit() {
            return ticketAudit;
        }

        public String getOrderPayStatus() {
            return orderPayStatus;
        }

        public AttendeeMap getAttendeeMap() {
            return attendeeMap;
        }

        public String getUserId() {
            return userId;
        }

        public String getAttendeeAvatar() {
            return attendeeAvatar;
        }

        public String getAttendeeType() {
            return attendeeType;
        }

        public String getEditAttendeeKey() {
            return editAttendeeKey;
        }

        public ApiAttendee(JsonObject jsonObject){
            attendeeAvatar=jsonObject.get("attendeeAvatar").getAsString();
//               if (jsonObject.get("attendeeMap").isJsonObject()){
//                   attendeeMap=new AttendeeMap(jsonObject.getAsJsonObject("attendeeMap"),);
//               }
        }
    }

    public static class FromFieldList{
        public int formFieldId;
        private Field field;
        private String showName;
        private int exDisplay;
        public int getExDisplay() {
            return exDisplay;
        }

        public int getFormFieldId() {
            return formFieldId;
        }

        public Field getField() {
            return field;
        }

        public String getShowName() {
            return showName;
        }

        public FromFieldList(JsonObject jsonObject){
              formFieldId=jsonObject.get("formFieldId").getAsInt();
              showName=jsonObject.get("showFieldName").getAsString();
              exDisplay=jsonObject.get("exDisplay").getAsInt();
        }
    }

    public static class AttendeeMap{

        private ArrayList<String> value=new ArrayList<>();
        private String username;
        private String filedName;

        public ArrayList<String> getValue() {
            return value;
        }

        public String getUsername() {
            return username;
        }

        public String getFiledName() {
            return filedName;
        }

        public AttendeeMap(JsonObject jsonObject, JsonArray jsonArray){
            for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject object=jsonArray.get(i).getAsJsonObject();
                    filedName=object.getAsJsonObject("field").get("fieldName").getAsString();
                    username = jsonObject.get(object.get("formFieldId").getAsString()).getAsString();
                    value.add(username);
            }
//            Log.i("---------1",value+"==");
        }

    }

    public static class Field{

    }


}
