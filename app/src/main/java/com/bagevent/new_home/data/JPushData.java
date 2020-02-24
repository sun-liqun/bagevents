package com.bagevent.new_home.data;

/**
 * Created by ZWJ on 2017/11/30 0030.
 */

public class JPushData {

    /**
     * respObject : {"eventId":"34965","senderToken":"zEYrleeIBzAkimtjpreTWtzDpP6FhU3a","attendeeId":"32158","sendTime":"2017-11-30 11:15:07","receiverToken":"t1qTwkAWWYpMwGV5OheQEC1SOyHSGXCL"}
     */

    private RespObjectBean respObject;

    public RespObjectBean getRespObject() {
        return respObject;
    }

    public void setRespObject(RespObjectBean respObject) {
        this.respObject = respObject;
    }

    public static class RespObjectBean {
        /**
         * eventId : 34965
         * senderToken : zEYrleeIBzAkimtjpreTWtzDpP6FhU3a
         * attendeeId : 32158
         * sendTime : 2017-11-30 11:15:07
         * receiverToken : t1qTwkAWWYpMwGV5OheQEC1SOyHSGXCL
         */

        private String eventId;
        private String senderToken;
        private String attendeeId;
        private String sendTime;
        private String receiverToken;

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getSenderToken() {
            return senderToken;
        }

        public void setSenderToken(String senderToken) {
            this.senderToken = senderToken;
        }

        public String getAttendeeId() {
            return attendeeId;
        }

        public void setAttendeeId(String attendeeId) {
            this.attendeeId = attendeeId;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getReceiverToken() {
            return receiverToken;
        }

        public void setReceiverToken(String receiverToken) {
            this.receiverToken = receiverToken;
        }
    }
}
