package com.bagevent.activity_manager.manager_fragment.data;

import java.util.List;

/**
 * Created by zwj on 2016/7/15.
 */
public class CollectManagerData {


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
         *  "collectionFieldList": [
         *       160834,
         *       160835,
         *       160836
         *     ],
         */
        private List<?> collectionFieldList;

        /**
         * "collectionList": [
         *       {
         *         "collectPointId": 1360,
         *         "collectionName": "信息收集",
         *         "userEmail": "",
         *         "collectionType": 0,
         *         "isAllTicket": 0,
         *         "isAllProduct": 0,
         *         "limitType": 0,
         *         "availableDateType": 0,
         *         "startTime": "2018-11-14 09:00",
         *         "endTime": "2018-12-27 19:50",
         *         "isBegin": 1,
         *         "isRepeat": 1,
         *         "export": 0,
         *         "showNum": 1,
         *         "checkinCount": 49,
         *         "ticketStr": "",
         *         "ticketIdStr": "",
         *         "productStr": "",
         *         "productIdStr": ""
         *       },
         *       {
         *         "collectPointId": 1361,
         *         "collectionName": "进出控制",
         *         "userEmail": "",
         *         "collectionType": 2,
         *         "isAllTicket": 0,
         *         "isAllProduct": 0,
         *         "limitType": 0,
         *         "availableDateType": 1,
         *         "startTime": "2018-05-14 10:55",
         *         "endTime": "2018-05-18 18:00",
         *         "isBegin": 1,
         *         "isRepeat": 1,
         *         "export": 1,
         *         "showNum": 1,
         *         "checkinCount": 4,
         *         "ticketStr": "",
         *         "ticketIdStr": "",
         *         "productStr": "",
         *         "productIdStr": ""
         *       },
         *       {
         *         "collectPointId": 1362,
         *         "collectionName": "采集点test",
         *         "userEmail": "",
         *         "collectionType": 0,
         *         "isAllTicket": 0,
         *         "isAllProduct": 0,
         *         "limitType": 0,
         *         "availableDateType": 0,
         *         "startTime": "2018-11-14 09:00",
         *         "endTime": "2018-12-27 19:50",
         *         "isBegin": 1,
         *         "isRepeat": 0,
         *         "export": 1,
         *         "showNum": 0,
         *         "checkinCount": 12,
         *         "ticketStr": "",
         *         "ticketIdStr": "",
         *         "productStr": "",
         *         "productIdStr": ""
         *       },
         *       {
         *         "collectPointId": 1363,
         *         "collectionName": "采集点-2号大厅",
         *         "userEmail": "",
         *         "collectionType": 2,
         *         "isAllTicket": 0,
         *         "isAllProduct": 0,
         *         "limitType": 0,
         *         "availableDateType": 0,
         *         "startTime": "2018-11-14 09:00",
         *         "endTime": "2018-12-27 19:50",
         *         "isBegin": 1,
         *         "isRepeat": 1,
         *         "export": 1,
         *         "showNum": 1,
         *         "checkinCount": 22,
         *         "ticketStr": "",
         *         "ticketIdStr": "",
         *         "productStr": "",
         *         "productIdStr": ""
         *       },
         *       {
         *         "collectPointId": 1405,
         *         "collectionName": "test222",
         *         "userEmail": "",
         *         "collectionType": 0,
         *         "isAllTicket": 1,
         *         "isAllProduct": 0,
         *         "limitType": 0,
         *         "availableDateType": 0,
         *         "startTime": "2018-11-14 09:00",
         *         "endTime": "2018-12-27 19:50",
         *         "isBegin": 1,
         *         "isRepeat": 1,
         *         "export": 1,
         *         "showNum": 1,
         *         "checkinCount": 7,
         *         "ticketStr": "成人票 免费票 ",
         *         "ticketIdStr": "39076||39077||",
         *         "productStr": "",
         *         "productIdStr": ""
         *       },
         *       {
         *         "collectPointId": 1406,
         *         "collectionName": "001",
         *         "userEmail": "",
         *         "collectionType": 0,
         *         "isAllTicket": 1,
         *         "isAllProduct": 0,
         *         "limitType": 0,
         *         "availableDateType": 0,
         *         "startTime": "2018-11-14 09:00",
         *         "endTime": "2018-12-27 19:50",
         *         "isBegin": 1,
         *         "isRepeat": 0,
         *         "export": 1,
         *         "showNum": 1,
         *         "checkinCount": 4,
         *         "ticketStr": "成人票 免费票 ",
         *         "ticketIdStr": "39076||39077||",
         *         "productStr": "",
         *         "productIdStr": ""
         *       },
         *       {
         *         "collectPointId": 1407,
         *         "collectionName": "002",
         *         "userEmail": "",
         *         "collectionType": 0,
         *         "isAllTicket": 1,
         *         "isAllProduct": 0,
         *         "limitType": 0,
         *         "availableDateType": 1,
         *         "startTime": "2018-06-04 09:00",
         *         "endTime": "2018-06-19 19:10",
         *         "isBegin": 1,
         *         "isRepeat": 1,
         *         "export": 1,
         *         "showNum": 1,
         *         "checkinCount": 8,
         *         "ticketStr": "成人票 免费票 ",
         *         "ticketIdStr": "39076||39077||",
         *         "productStr": "",
         *         "productIdStr": ""
         *       },
         *       {
         *         "collectPointId": 1408,
         *         "collectionName": "商品采集",
         *         "userEmail": "",
         *         "collectionType": 0,
         *         "isAllTicket": 0,
         *         "isAllProduct": 1,
         *         "limitType": 1,
         *         "availableDateType": 1,
         *         "startTime": "2018-06-04 09:00",
         *         "endTime": "2018-11-21 19:25",
         *         "isBegin": 1,
         *         "isRepeat": 1,
         *         "export": 1,
         *         "showNum": 0,
         *         "checkinCount": 8,
         *         "ticketStr": "",
         *         "ticketIdStr": "",
         *         "productStr": "333 ",
         *         "productIdStr": "492||"
         *       }
         *     ]
         */
        private List<CollectionListBean> collectionList;

        public List<?> getCollectionFieldList() {
            return collectionFieldList;
        }

        public void setCollectionFieldList(List<?> collectionFieldList) {
            this.collectionFieldList = collectionFieldList;
        }

        public List<CollectionListBean> getCollectionList() {
            return collectionList;
        }

        public void setCollectionList(List<CollectionListBean> collectionList) {
            this.collectionList = collectionList;
        }

        public static class CollectionListBean {
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
            private int checkinCount;
            private String ticketStr;
            private String ticketIdStr;
            private int showNum;
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

            public int getShowNum() {
                return showNum;
            }

            public void setShowNum(int showNum) {
                this.showNum = showNum;
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
}
