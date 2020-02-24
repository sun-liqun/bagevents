package com.bagevent.new_home.data;

import java.util.List;

/**
 * Created by ZWJ on 2017/11/27 0027.
 */

public class ChatMessageData {

    /**
     * retStatus : 200
     * respObject : [{"chatId":5,"sendTime":"2017-11-21 13:53:11","content":"dfdfasfdasffdfdsff","status":0,"senderToken":"xFUQg4Ez9LykiHdjnvvUsvWBBM9LSXYz","receiverToken":"zEYrleeIBzAkimtjpreTWtzDpP6FhU3a","eventId":"","attendeeId":"","org":false,"sendSeconds":1511243591,"todaySend":false,"sendDay":"2017-11-21"}]
     * respType : 0
     */

    private int retStatus;
    private int respType;
    private List<RespObjectBean> respObject;

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
        return respObject;
    }

    public void setRespObject(List<RespObjectBean> respObject) {
        this.respObject = respObject;
    }

    public static class RespObjectBean {
        /**
         * chatId : 5
         * sendTime : 2017-11-21 13:53:11
         * content : dfdfasfdasffdfdsff
         * status : 0
         * senderToken : xFUQg4Ez9LykiHdjnvvUsvWBBM9LSXYz
         * receiverToken : zEYrleeIBzAkimtjpreTWtzDpP6FhU3a
         * eventId :
         * attendeeId :
         * org : false
         * sendSeconds : 1511243591
         * todaySend : false
         * sendDay : 2017-11-21
         */
        private String contactId;
        private int chatId;
        private String sendTime;
        private String content;
        private int status;
        private String senderToken;
        private String receiverToken;
        private String eventId;
        private String attendeeId;
        private boolean org;
        private int sendSeconds;
        private boolean todaySend;
        private String sendDay;

        public String getContactId() {
            return contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

        public int getChatId() {
            return chatId;
        }

        public void setChatId(int chatId) {
            this.chatId = chatId;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSenderToken() {
            return senderToken;
        }

        public void setSenderToken(String senderToken) {
            this.senderToken = senderToken;
        }

        public String getReceiverToken() {
            return receiverToken;
        }

        public void setReceiverToken(String receiverToken) {
            this.receiverToken = receiverToken;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getAttendeeId() {
            return attendeeId;
        }

        public void setAttendeeId(String attendeeId) {
            this.attendeeId = attendeeId;
        }

        public boolean isOrg() {
            return org;
        }

        public void setOrg(boolean org) {
            this.org = org;
        }

        public int getSendSeconds() {
            return sendSeconds;
        }

        public void setSendSeconds(int sendSeconds) {
            this.sendSeconds = sendSeconds;
        }

        public boolean isTodaySend() {
            return todaySend;
        }

        public void setTodaySend(boolean todaySend) {
            this.todaySend = todaySend;
        }

        public String getSendDay() {
            return sendDay;
        }

        public void setSendDay(String sendDay) {
            this.sendDay = sendDay;
        }
    }
}
