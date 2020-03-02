package com.bagevent.new_home.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/04
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class CollectionAttendeeData {
    private int respType;
    private int retStatus;
    private RespObjectBean respObject;

    public int getRespType() {
        return respType;
    }

    public int getRetStatus() {
        return retStatus;
    }

    public RespObjectBean getRespObject() {
        return respObject;
    }

    public CollectionAttendeeData(JsonObject jsonObject){
        if (jsonObject.get("respType") != null) {
            respType = jsonObject.get("respType").getAsInt();
        }
        if (jsonObject.get("retStatus") != null) {
            retStatus = jsonObject.get("retStatus").getAsInt();
        }
        if (jsonObject.get("respObject") != null && jsonObject.get("respObject").isJsonArray()) {
            respObject = new RespObjectBean(jsonObject.get("respObject").getAsJsonArray());
        }
    }

    public static class RespObjectBean{
        private ArrayList<CollectionList> collectionLists;

        public ArrayList<CollectionList> getCollectionLists() {
            return collectionLists;
        }

        public RespObjectBean(JsonArray jsonArray){
            collectionLists=new ArrayList<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size() ; i++) {
                collectionLists.add(new CollectionList(jsonArray.get(i).getAsJsonObject()));
            }
        }
    }

    public static  class  CollectionList{
        private String attendeeName;
        private ArrayList<TagList> tagLists;
        private String updateTime;
        private String avater;
        private int attendeeId;
        private int exhibitorAttendeeId;
        private String email;

        public String getAttendeeName() {
            return attendeeName;
        }

        public ArrayList<TagList> getTagLists() {
            return tagLists;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getAvater() {
            return avater;
        }

        public int getAttendeeId() {
            return attendeeId;
        }

        public int getExhibitorAttendeeId() {
            return exhibitorAttendeeId;
        }

        public String getEmail() {
            return email;
        }

        public CollectionList(JsonObject jsonObject){
            attendeeName=jsonObject.get("attendeeName").getAsString();
            if (jsonObject.get("tagList").isJsonArray() && jsonObject.get("tagList")!=null){
                JsonArray tagListArray = jsonObject.get("tagList").getAsJsonArray();
                tagLists=new ArrayList<>(tagListArray.size());
                for (int i = 0; i < tagListArray.size() ; i++) {
                    tagLists.add(new TagList(tagListArray.get(i).getAsJsonObject()));
                }
            }
            updateTime=jsonObject.get("updateTime").getAsString();
            avater=jsonObject.get("avatar").getAsString();
            attendeeId=jsonObject.get("attendeeId").getAsInt();
            exhibitorAttendeeId=jsonObject.get("exhibitorAttendeeId").getAsInt();

        }
    }

    public static class TagList{
        int tagId;
        String name;
        int sort;

        public int getTagId() {
            return tagId;
        }

        public String getName() {
            return name;
        }

        public int getSort() {
            return sort;
        }

        public TagList(JsonObject jsonObject){
            tagId=jsonObject.get("tagId").getAsInt();
            name=jsonObject.get("name").getAsString();
            sort=jsonObject.get("sort").getAsInt();
        }
    }


}
