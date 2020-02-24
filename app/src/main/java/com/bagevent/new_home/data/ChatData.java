package com.bagevent.new_home.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.umeng.commonsdk.debug.E;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZWJ on 2017/11/21 0021.
 */

public class ChatData {


    /**
     * retStatus : 200
     * respObject : [{"contactId":1,"interactPeople":{"peopleId":1,"token":"d4hnjbpgqKDBEnZ9yCi1XsfK3jZS9iXp","type":-1,"createTime":"2017-11-09 15:33:50","eventId":"","avatar":"/resources/img/logo_index.png","sys":true,"showName":"","organizer":false},"sort":-1,"createTime":"2017-11-09 15:35:36","updateTime":"2017-12-03 21:58:09","status":true,"unReadCount":0,"lastMessage":""},{"contactId":7,"interactPeople":{"peopleId":19,"token":"QiJdfJIkY8yNRMrrlgLjFFIxbTW7V4Wm","type":1,"createTime":"2017-11-20 16:10:08","eventId":"","avatar":"/resources/img/logo_index.png","sys":false,"showName":"tanlangan1995","organizer":false},"sort":10,"createTime":"2017-11-20 16:17:05","updateTime":"2017-12-03 20:03:27","status":true,"unReadCount":0,"lastMessage":"ghh"},{"contactId":9,"interactPeople":{"peopleId":23,"token":"t1qTwkAWWYpMwGV5OheQEC1SOyHSGXCL","type":1,"createTime":"2017-11-29 15:01:50","eventId":"","avatar":"//wx.qlogo.cn/mmopen/2mPzhsmVWKk7xwZTZUzmJOBQvOff13eic5Be2kib58cLjEq7YPzM9ILKv96paricKuzziatKjZUqOWic7ESTbADawGRE3qwUQeqIy/0","sys":false,"showName":"ABC","organizer":false},"sort":10,"createTime":"2017-11-29 16:53:42","updateTime":"2017-12-03 19:36:32","status":false,"unReadCount":0,"lastMessage":"hhj"},{"contactId":10,"interactPeople":{"peopleId":24,"token":"OFJICuvfbWONk3WfyA8TA6J2TUI6p8QQ","type":1,"createTime":"2017-12-01 16:36:22","eventId":"","avatar":"//wx.qlogo.cn/mmopen/FK0ILxXLSGOy5MwXcd754ib3qnFPqIaoz6fQwXU8nwDbicFITErX6xfrePxky98HKRKaSbibNV6sJ87x5vuxESbGokSTv0j5Xn0/0","sys":false,"showName":"ABC","organizer":false},"sort":10,"createTime":"2017-12-01 16:43:33","updateTime":"2017-12-04 09:37:20","status":true,"unReadCount":0,"lastMessage":"恐惧"},{"contactId":8,"interactPeople":{"peopleId":20,"token":"BjH7wNJLUxNZpOwcH5zM1sLiYCgUjbFu","type":1,"createTime":"2017-11-20 16:17:35","eventId":"","avatar":"/resources/img/logo_index.png","sys":false,"showName":"weixizhen","organizer":false},"sort":11,"createTime":"2017-11-20 16:17:05","updateTime":"2017-12-03 21:53:07","status":true,"unReadCount":0,"lastMessage":"发的说法"}]
     * respType : 0
     */

    private int retStatus;
    private int respType;
    private List<RespObjectBean> respData;

    public ChatData(JsonObject jsonObject) {
        retStatus = jsonObject.get("retStatus").getAsInt();
        respType = jsonObject.get("respType").getAsInt();
        if (jsonObject.get("respData").isJsonObject()) {
            JsonObject objRespData = jsonObject.get("respData").getAsJsonObject();
            parserArray(objRespData.getAsJsonArray("data"));
        } else {
            parserArray(jsonObject.getAsJsonArray("respObject"));
        }
    }


    private void parserArray(JsonArray jsonArray) {
        if (jsonArray != null && jsonArray.size() > 0) {
            respData = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                respData.add(new RespObjectBean(jsonArray.get(i).getAsJsonObject()));
            }
        }
    }

    public int getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(int retStatus) {
        this.retStatus = retStatus;
    }

    public int getRespType() {
        return respType;
    }

    public void setRespType(int respType) {
        this.respType = respType;
    }

    public List<RespObjectBean> getRespObject() {
        return respData;
    }

    public void setRespObject(List<RespObjectBean> respObject) {
        this.respData = respObject;
    }

    //
    public static class RespObjectBean {
        /**
         * contactId : 1
         * interactPeople : {"peopleId":1,"token":"d4hnjbpgqKDBEnZ9yCi1XsfK3jZS9iXp","type":-1,"createTime":"2017-11-09 15:33:50","eventId":"","avatar":"/resources/img/logo_index.png","sys":true,"showName":"","organizer":false}
         * sort : -1
         * createTime : 2017-11-09 15:35:36
         * updateTime : 2017-12-03 21:58:09
         * status : true
         * unReadCount : 0
         * lastMessage :
         */

        private int contactId;
        private InteractPeopleBean interactPeople;
        private int sort;
        private String createTime;
        private String updateTime;
        private boolean status;
        private int unReadCount;
        private String lastMessage;
        private String lastMessageTime;

        public RespObjectBean(JsonObject jsonObject) {
            contactId = jsonObject.get("contactId").getAsInt();
            sort = jsonObject.get("sort").getAsInt();
            unReadCount = jsonObject.get("unReadCount").getAsInt();
            createTime = jsonObject.get("createTime").getAsString();
            updateTime = jsonObject.get("updateTime").getAsString();
            lastMessage = jsonObject.get("lastMessage").getAsString();
            lastMessageTime = jsonObject.get("lastMessageTime").getAsString();
            status = jsonObject.get("status").getAsBoolean();
            JsonObject objInteractPeople = jsonObject.get("interactPeople").getAsJsonObject();
            if (objInteractPeople != null) {
                interactPeople = new InteractPeopleBean(objInteractPeople);
            }
        }

        public String getLastMessageTime() {
            return lastMessageTime;
        }

        public void setLastMessageTime(String lastMessageTime) {
            this.lastMessageTime = lastMessageTime;
        }

        public int getContactId() {
            return contactId;
        }

        public void setContactId(int contactId) {
            this.contactId = contactId;
        }

        public InteractPeopleBean getInteractPeople() {
            return interactPeople;
        }

        public void setInteractPeople(InteractPeopleBean interactPeople) {
            this.interactPeople = interactPeople;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getUnReadCount() {
            return unReadCount;
        }

        public void setUnReadCount(int unReadCount) {
            this.unReadCount = unReadCount;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
        }

        public static class InteractPeopleBean {
            /**
             * peopleId : 1
             * token : d4hnjbpgqKDBEnZ9yCi1XsfK3jZS9iXp
             * type : -1
             * createTime : 2017-11-09 15:33:50
             * eventId :
             * avatar : /resources/img/logo_index.png
             * sys : true
             * showName :
             * organizer : false
             */

            private int peopleId;
            private String token;
            private int type;
            private String createTime;
            private String eventId;
            private String avatar;
            private boolean sys;
            private String showName;
            private boolean organizer;

            public InteractPeopleBean(JsonObject jsonObject) {
                peopleId = jsonObject.get("peopleId").getAsInt();
                type = jsonObject.get("type").getAsInt();
                token = jsonObject.get("token").getAsString();
                createTime = jsonObject.get("createTime").getAsString();
                eventId = jsonObject.get("eventId").getAsString();
                avatar = jsonObject.get("avatar").getAsString();
                showName = jsonObject.get("showName").getAsString();
                sys = jsonObject.get("sys").getAsBoolean();
                organizer = jsonObject.get("organizer").getAsBoolean();
            }

            public int getPeopleId() {
                return peopleId;
            }

            public void setPeopleId(int peopleId) {
                this.peopleId = peopleId;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getEventId() {
                return eventId;
            }

            public void setEventId(String eventId) {
                this.eventId = eventId;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public boolean isSys() {
                return sys;
            }

            public void setSys(boolean sys) {
                this.sys = sys;
            }

            public String getShowName() {
                return showName;
            }

            public void setShowName(String showName) {
                this.showName = showName;
            }

            public boolean isOrganizer() {
                return organizer;
            }

            public void setOrganizer(boolean organizer) {
                this.organizer = organizer;
            }
        }
    }
//
//    public static class RespData{
//        private List<RespObjectBean> data;
//        private String respMessage;
//        private int code;

//        public List<RespObjectBean> getData() {
//            return data;
//        }
//
//        public void setData(List<RespObjectBean> data) {
//            this.data = data;
//        }

//        public String getRespMessage() {
//            return respMessage;
//        }
//
//        public void setRespMessage(String respMessage) {
//            this.respMessage = respMessage;
//        }
//
//        public int getCode() {
//            return code;
//        }
//
//        public void setCode(int code) {
//            this.code = code;
//        }
//    }
}
