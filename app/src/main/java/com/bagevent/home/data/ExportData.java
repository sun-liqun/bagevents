package com.bagevent.home.data;


import com.bagevent.activity_manager.manager_fragment.data.ExhibitorData;
import com.bagevent.new_home.data.ExhibitionListData;
import com.bagevent.new_home.data.TagsData;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by zwj on 2016/7/14.
 */
public class ExportData {

    private int respType;
    private int retStatus;
    private Object respObject;

    public int getRespType() {
        return respType;
    }

    public int getRetStatus() {
        return retStatus;
    }

    public Object getRespObject() {
        return respObject;
    }

    public ExportData(JsonObject jsonObject){
        if (jsonObject.get("respType") != null) {
            respType = jsonObject.get("respType").getAsInt();
        }
        if (jsonObject.get("retStatus") != null) {
            retStatus = jsonObject.get("retStatus").getAsInt();
        }

        if (jsonObject.get("respObject").toString().equals("null")){
            respObject = new JsonNull();
        }else if (jsonObject.get("respObject") != null && jsonObject.get("respObject").isJsonObject()){
            respObject = new RespObjectData(jsonObject.getAsJsonObject("respObject"));
        }else {
            respObject=jsonObject.get("respObject").getAsString();
        }
    }


    public static class RespObjectData{
        private String attendeeName;
        private ExhibitorAttendeeDTO attendeeDTO;
        private ArrayList<ExhibitorAttendeeTagList> tagList;

        public String getAttendeeName() {
            return attendeeName;
        }

        public ExhibitorAttendeeDTO getAttendeeDTO() {
            return attendeeDTO;
        }

        public ArrayList<ExhibitorAttendeeTagList> getTagList() {
            return tagList;
        }

        public RespObjectData(JsonObject jsonObject){
          attendeeName=jsonObject.get("attendeeName").getAsString();
            if (jsonObject.get("exhibitorAttendeeDTO").isJsonObject()) {
                attendeeDTO = new ExhibitorAttendeeDTO(jsonObject.getAsJsonObject("exhibitorAttendeeDTO"));
            }

            if (jsonObject.get("exhibitorAttendeeTagList") != null && jsonObject.get("exhibitorAttendeeTagList").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("exhibitorAttendeeTagList").getAsJsonArray();
                tagList = new ArrayList<>(jsonArray.size());
                for (int i = 0; i < jsonArray.size(); i++) {
                    tagList.add(new ExhibitorAttendeeTagList(jsonArray.get(i).getAsJsonObject()));
                }
            }
        }

    }


    public static class ExhibitorAttendeeDTO{
        private int attendeeId;
        private int exhibitorId;
        private int exhibitorAttendeeId;
        private int type;
        private String createTime;
        private String updateTime;

        public int getExhibitorAttendeeId() {
            return exhibitorAttendeeId;
        }

        public int getAttendeeId() {
            return attendeeId;
        }

        public int getExhibitorId() {
            return exhibitorId;
        }

        public int getType() {
            return type;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public ExhibitorAttendeeDTO(JsonObject jsonObject){
            attendeeId=jsonObject.get("attendeeId").getAsInt();
            exhibitorId=jsonObject.get("exhibitorId").getAsInt();
            exhibitorAttendeeId=jsonObject.get("exhibitorAttendeeId").getAsInt();
            type=jsonObject.get("type").getAsInt();
            updateTime=jsonObject.get("updateTime").getAsString();
            createTime=jsonObject.get("createTime").getAsString();
        }
    }

    public static class ExhibitorAttendeeTagList{
        private String createTime;
        private String updateTime;
        private String remark;
        private String tagName;
        private int exhibitorAttendeeId;
        private int exhibitorAttendeeTagId;
        private int remarkId;

        public String getCreateTime() {
            return createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public String getTagName() {
            return tagName;
        }

        public int getExhibitorAttendeeId() {
            return exhibitorAttendeeId;
        }

        public int getExhibitorAttendeeTagId() {
            return exhibitorAttendeeTagId;
        }

        public int getRemarkId() {
            return remarkId;
        }

        public ExhibitorAttendeeTagList(JsonObject jsonObject){
            createTime=jsonObject.get("createTime").getAsString();
            updateTime=jsonObject.get("updateTime").getAsString();
            exhibitorAttendeeId=jsonObject.get("exhibitorAttendeeId").getAsInt();
            exhibitorAttendeeTagId=jsonObject.get("exhibitorAttendeeTagId").getAsInt();
            remark=jsonObject.get("remark").getAsString();
            remarkId=jsonObject.get("remarkId").getAsInt();
            tagName=jsonObject.get("tagName").getAsString();
        }

    }
//    private RespObjectBean respObject;
//
//    private String resobject;
//    private int respType;
//    private int retStatus;
//
//
//    public int getRetStatus() {
//        return retStatus;
//    }
//
//    public void setRetStatus(int retStatus) {
//        this.retStatus = retStatus;
//    }
//
//    public RespObjectBean getRespObject() {
//        return respObject;
//    }
//
//    public void setRespObject(RespObjectBean respObject) {
//        this.respObject = respObject;
//    }
//
//    public int getRespType() {
//        return respType;
//    }
//
//    public void setRespType(int respType) {
//        this.respType = respType;
//    }
//
//
//    public static class RespObjectBean {
//        /**
//         * attendeeId : 2264183
//         * createTime : 2019-03-08 11:30
//         * exhibitorAttendeeId : 6
//         * exhibitorId : 121
//         * type : 0
//         * updateTime : 2019-03-20 14:49
//         */
//
//        private int attendeeId;
//        private String createTime;
//        private int exhibitorAttendeeId;
//        private int exhibitorId;
//        private int type;
//        private String updateTime;
//
//        public int getAttendeeId() {
//            return attendeeId;
//        }
//
//        public void setAttendeeId(int attendeeId) {
//            this.attendeeId = attendeeId;
//        }
//
//        public String getCreateTime() {
//            return createTime;
//        }
//
//        public void setCreateTime(String createTime) {
//            this.createTime = createTime;
//        }
//
//        public int getExhibitorAttendeeId() {
//            return exhibitorAttendeeId;
//        }
//
//        public void setExhibitorAttendeeId(int exhibitorAttendeeId) {
//            this.exhibitorAttendeeId = exhibitorAttendeeId;
//        }
//
//        public int getExhibitorId() {
//            return exhibitorId;
//        }
//
//        public void setExhibitorId(int exhibitorId) {
//            this.exhibitorId = exhibitorId;
//        }
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public String getUpdateTime() {
//            return updateTime;
//        }
//
//        public void setUpdateTime(String updateTime) {
//            this.updateTime = updateTime;
//        }
//    }


    @Override
    public String toString() {
        return "ExportData{" +
                "respType=" + respType +
                ", retStatus=" + retStatus +
                ", respObject=" + respObject +
                '}';
    }
}
