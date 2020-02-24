package com.bagevent.activity_manager.manager_fragment.data;

/**
 * Created by zwj on 2017/6/15.
 */

public class CollectionInfoData {

    /**
     * retStatus : 200
     * respObject : {"collectPointId":60,"collectionName":"进出会场3","userEmail":"","collectionType":2,"isAllTicket":0,"availableDateType":0,"startTime":"2017-05-20 08:30","endTime":"2017-10-21 18:15","isBegin":1,"isRepeat":1,"export":1,"showNum":1,"checkinCount":0,"ticketStr":"","ticketIdStr":""}
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
         * collectPointId : 60
         * collectionName : 进出会场3
         * userEmail :
         * collectionType : 2
         * isAllTicket : 0
         * availableDateType : 0
         * startTime : 2017-05-20 08:30
         * endTime : 2017-10-21 18:15
         * isBegin : 1
         * isRepeat : 1
         * export : 1
         * showNum : 1
         * checkinCount : 0
         * ticketStr :
         * ticketIdStr :
         */

        private int collectPointId;
        private String collectionName;
        private String userEmail;
        private int collectionType;
        private int isAllTicket;
        private int availableDateType;
        private String startTime;
        private String endTime;
        private int isBegin;
        private int isRepeat;
        private int export;
        private int showNum;
        private int checkinCount;
        private String ticketStr;
        private String ticketIdStr;
        private int isAllProduct;
        private int limitType;
        private String productStr;
        private String productIdStr;

        public int getIsAllProduct() {
            return isAllProduct;
        }

        public void setIsAllProduct(int isAllProduct) {
            this.isAllProduct = isAllProduct;
        }

        public int getLimitType() {
            return limitType;
        }

        public void setLimitType(int limitType) {
            this.limitType = limitType;
        }

        public String getProductStr() {
            return productStr;
        }

        public void setProductStr(String productStr) {
            this.productStr = productStr;
        }

        public String getProductIdStr() {
            return productIdStr;
        }

        public void setProductIdStr(String productIdStr) {
            this.productIdStr = productIdStr;
        }

        public int getCollectPointId() {
            return collectPointId;
        }

        public void setCollectPointId(int collectPointId) {
            this.collectPointId = collectPointId;
        }

        public String getCollectionName() {
            return collectionName;
        }

        public void setCollectionName(String collectionName) {
            this.collectionName = collectionName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public int getCollectionType() {
            return collectionType;
        }

        public void setCollectionType(int collectionType) {
            this.collectionType = collectionType;
        }

        public int getIsAllTicket() {
            return isAllTicket;
        }

        public void setIsAllTicket(int isAllTicket) {
            this.isAllTicket = isAllTicket;
        }

        public int getAvailableDateType() {
            return availableDateType;
        }

        public void setAvailableDateType(int availableDateType) {
            this.availableDateType = availableDateType;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getIsBegin() {
            return isBegin;
        }

        public void setIsBegin(int isBegin) {
            this.isBegin = isBegin;
        }

        public int getIsRepeat() {
            return isRepeat;
        }

        public void setIsRepeat(int isRepeat) {
            this.isRepeat = isRepeat;
        }

        public int getExport() {
            return export;
        }

        public void setExport(int export) {
            this.export = export;
        }

        public int getShowNum() {
            return showNum;
        }

        public void setShowNum(int showNum) {
            this.showNum = showNum;
        }

        public int getCheckinCount() {
            return checkinCount;
        }

        public void setCheckinCount(int checkinCount) {
            this.checkinCount = checkinCount;
        }

        public String getTicketStr() {
            return ticketStr;
        }

        public void setTicketStr(String ticketStr) {
            this.ticketStr = ticketStr;
        }

        public String getTicketIdStr() {
            return ticketIdStr;
        }

        public void setTicketIdStr(String ticketIdStr) {
            this.ticketIdStr = ticketIdStr;
        }
    }
}
