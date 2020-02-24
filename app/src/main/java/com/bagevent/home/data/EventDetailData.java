package com.bagevent.home.data;

/**
 * Created by zwj on 2016/9/27.
 */
public class EventDetailData {


    /**
     * respObject : {"address":"","attendeeCount":110,"auditCount":0,"brand":0,"changePrintName":0,"checkInRoom":0,"checkinCount":5,"collectInvoice":1,"endTime":"2018-08-01 18:00","eventId":30932,"eventName":"123服务器测试活动","eventType":1,"hasFaceDetectPermission":false,"income":1107,"logo":"/resources/img/event_logo/08.jpg","modelUploadAvatarAfterCheckIn":false,"nameType":0,"officialWebsite":"","participantsCount":0,"stType":0,"startTime":"2017-07-21 08:30","status":2,"ticketCount":15911,"websiteId":927043199529}
     * respType : 0
     * retStatus : 200
     */

    private RespObjectBean respObject;
    private int respType;
    private int retStatus;

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

    public int getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(int retStatus) {
        this.retStatus = retStatus;
    }

    public static class RespObjectBean {
        /**
         * address :
         * attendeeCount : 110
         * auditCount : 0
         * brand : 0
         * changePrintName : 0
         * checkInRoom : 0
         * checkinCount : 5
         * collectInvoice : 1
         * endTime : 2018-08-01 18:00
         * eventId : 30932
         * eventName : 123服务器测试活动
         * eventType : 1
         * hasFaceDetectPermission : false
         * income : 1107
         * logo : /resources/img/event_logo/08.jpg
         * modelUploadAvatarAfterCheckIn : false
         * nameType : 0
         * officialWebsite :
         * participantsCount : 0
         * stType : 0
         * startTime : 2017-07-21 08:30
         * status : 2
         * ticketCount : 15911
         * websiteId : 927043199529
         */

        private String address;
        private int attendeeCount;
        private int auditCount;
        private int brand;
        private int changePrintName;
        private int checkInRoom;
        private int checkinCount;
        private int collectInvoice;
        private String endTime;
        private int eventId;
        private String eventName;
        private int eventType;
        private boolean hasFaceDetectPermission;
        private double income;
        private String logo;
        private boolean modelUploadAvatarAfterCheckIn;
        private int nameType;
        private String officialWebsite;
        private int participantsCount;
        private int stType;
        private String startTime;
        private int status;
        private int ticketCount;
        private long websiteId;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAttendeeCount() {
            return attendeeCount;
        }

        public void setAttendeeCount(int attendeeCount) {
            this.attendeeCount = attendeeCount;
        }

        public int getAuditCount() {
            return auditCount;
        }

        public void setAuditCount(int auditCount) {
            this.auditCount = auditCount;
        }

        public int getBrand() {
            return brand;
        }

        public void setBrand(int brand) {
            this.brand = brand;
        }

        public int getChangePrintName() {
            return changePrintName;
        }

        public void setChangePrintName(int changePrintName) {
            this.changePrintName = changePrintName;
        }

        public int getCheckInRoom() {
            return checkInRoom;
        }

        public void setCheckInRoom(int checkInRoom) {
            this.checkInRoom = checkInRoom;
        }

        public int getCheckinCount() {
            return checkinCount;
        }

        public void setCheckinCount(int checkinCount) {
            this.checkinCount = checkinCount;
        }

        public int getCollectInvoice() {
            return collectInvoice;
        }

        public void setCollectInvoice(int collectInvoice) {
            this.collectInvoice = collectInvoice;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public int getEventType() {
            return eventType;
        }

        public void setEventType(int eventType) {
            this.eventType = eventType;
        }

        public boolean isHasFaceDetectPermission() {
            return hasFaceDetectPermission;
        }

        public void setHasFaceDetectPermission(boolean hasFaceDetectPermission) {
            this.hasFaceDetectPermission = hasFaceDetectPermission;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public boolean isModelUploadAvatarAfterCheckIn() {
            return modelUploadAvatarAfterCheckIn;
        }

        public void setModelUploadAvatarAfterCheckIn(boolean modelUploadAvatarAfterCheckIn) {
            this.modelUploadAvatarAfterCheckIn = modelUploadAvatarAfterCheckIn;
        }

        public int getNameType() {
            return nameType;
        }

        public void setNameType(int nameType) {
            this.nameType = nameType;
        }

        public String getOfficialWebsite() {
            return officialWebsite;
        }

        public void setOfficialWebsite(String officialWebsite) {
            this.officialWebsite = officialWebsite;
        }

        public int getParticipantsCount() {
            return participantsCount;
        }

        public void setParticipantsCount(int participantsCount) {
            this.participantsCount = participantsCount;
        }

        public int getStType() {
            return stType;
        }

        public void setStType(int stType) {
            this.stType = stType;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTicketCount() {
            return ticketCount;
        }

        public void setTicketCount(int ticketCount) {
            this.ticketCount = ticketCount;
        }

        public long getWebsiteId() {
            return websiteId;
        }

        public void setWebsiteId(long websiteId) {
            this.websiteId = websiteId;
        }
    }
}
