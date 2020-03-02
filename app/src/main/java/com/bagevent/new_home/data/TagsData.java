package com.bagevent.new_home.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/05
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class TagsData {
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

    public TagsData(JsonObject jsonObject){
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
        private ArrayList<TagDataList> tagDataLists;

        public ArrayList<TagDataList> getTagDataLists() {
            return tagDataLists;
        }

        public RespObjectBean(JsonArray jsonArray){
            tagDataLists=new ArrayList<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                tagDataLists.add(new TagDataList(jsonArray.get(i).getAsJsonObject()));
            }
        }
    }

    public static class  TagDataList{
        int tagId;
        int count;
        String name;

        public int getTagId() {
            return tagId;
        }

        public int getCount() {
            return count;
        }

        public String getName() {
            return name;
        }

        public TagDataList(JsonObject jsonObject){
            tagId=jsonObject.get("tagId").getAsInt();
            count=jsonObject.get("count").getAsInt();
            name=jsonObject.get("name").getAsString();
        }
    }

}
