package com.bagevent.new_home.data;

/**
 * Created by ZWJ on 2017/11/29 0029.
 */

public class PostChatMessageData {

    /**
     * retStatus : 200
     * respObject : {"chatId":312,"sendTime":"2017-11-29 18:15:41","content":"fg","status":0,"senderToken":"zEYrleeIBzAkimtjpreTWtzDpP6FhU3a","receiverToken":"t1qTwkAWWYpMwGV5OheQEC1SOyHSGXCL","eventId":34965,"attendeeId":32158,"sendSeconds":1511950541,"todaySend":true,"sendDay":"2017-11-29","org":true}
     * respType : 0
     */

    private int retStatus;
    private RespObjectBean respObject;
    private int respType;

    public int getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(int retStatus) {
        this.retStatus = retStatus;
    }

    public RespObjectBean getRespObject() {
        return respObject;
    }

    public void setRespObject(RespObjectBean respObject) {
        this.respObject = respObject;
    }

    public int getRespType() {
        return respType;
    }

    public void setRespType(int respType) {
        this.respType = respType;
    }

    public static class RespObjectBean {
        /**
         * chatId : 312
         * sendTime : 2017-11-29 18:15:41
         * content : fg
         * status : 0
         * senderToken : zEYrleeIBzAkimtjpreTWtzDpP6FhU3a
         * receiverToken : t1qTwkAWWYpMwGV5OheQEC1SOyHSGXCL
         * eventId : 34965
         * attendeeId : 32158
         * sendSeconds : 1511950541
         * todaySend : true
         * sendDay : 2017-11-29
         * org : true
         */

        private int chatId;
        private String sendTime;
        private String content;
        private int status;
        private String senderToken;
        private String receiverToken;
        private int eventId;
        private String attendeeId;
        private int sendSeconds;
        private boolean todaySend;
        private String sendDay;
        private boolean org;

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

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public String getAttendeeId() {
            return attendeeId;
        }

        public void setAttendeeId(String attendeeId) {
            this.attendeeId = attendeeId;
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

        public boolean isOrg() {
            return org;
        }

        public void setOrg(boolean org) {
            this.org = org;
        }
    }
}
