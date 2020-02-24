package com.bagevent.activity_manager.manager_fragment.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zwj on 2017/9/11.
 * 用于同步参会人员
 */

public class AttendeeData {

    /**
     * respObject : {"objects":[{"apiTicketOrderItem":{"currentTicketPrice":0,"discountId":0,"discountPrice":0,"gift":0,"memberDiscountPrice":0,"orderItemId":1592114,"ticketFee":0,"ticketId":7027,"ticketOrderId":1199060,"ticketPrice":0},"areaCode":"+86","attendeeAvatar":"","attendeeId":940023,"attendeeMap":{"27492":"jlkjaf","27493":"1@qq.com","27494":"","27581":""},"attendeeNumber":0,"attendeeType":0,"audit":0,"auditTime":"","avatar":"","badgeMap":null,"barcode":"5000208225870025098","buyWay":2,"cellphone":"","checkInAisle":"","checkin":0,"checkinCode":"931191","checkinTime":"","emailAddr":"1@qq.com","firstName":"","giftIssuance":0,"giftReceiveTime":"","lastName":"","name":"jlkjaf","notes":"","orderId":1199060,"orderPayStatus":0,"originalTicketName":"","payStatus":1,"pinyinName":"jlkjaf","refCode":"","salesUserName":"","signCode":"","tagName":"","ticketAudit":0,"ticketDesc":"","ticketId":7027,"ticketName":"","ticketPrice":0,"updateTime":"2017-09-11 17:33","userId":0,"weixinId":""}],"pagination":{"currentTime":1505122416356,"objectCount":1,"pageCount":1,"pageNumber":1,"pageSize":50},"syncTime":0}
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
         * objects : [{"apiTicketOrderItem":{"currentTicketPrice":0,"discountId":0,"discountPrice":0,"gift":0,"memberDiscountPrice":0,"orderItemId":1592114,"ticketFee":0,"ticketId":7027,"ticketOrderId":1199060,"ticketPrice":0},"areaCode":"+86","attendeeAvatar":"","attendeeId":940023,"attendeeMap":{"27492":"jlkjaf","27493":"1@qq.com","27494":"","27581":""},"attendeeNumber":0,"attendeeType":0,"audit":0,"auditTime":"","avatar":"","badgeMap":null,"barcode":"5000208225870025098","buyWay":2,"cellphone":"","checkInAisle":"","checkin":0,"checkinCode":"931191","checkinTime":"","emailAddr":"1@qq.com","firstName":"","giftIssuance":0,"giftReceiveTime":"","lastName":"","name":"jlkjaf","notes":"","orderId":1199060,"orderPayStatus":0,"originalTicketName":"","payStatus":1,"pinyinName":"jlkjaf","refCode":"","salesUserName":"","signCode":"","tagName":"","ticketAudit":0,"ticketDesc":"","ticketId":7027,"ticketName":"","ticketPrice":0,"updateTime":"2017-09-11 17:33","userId":0,"weixinId":""}]
         * pagination : {"currentTime":1505122416356,"objectCount":1,"pageCount":1,"pageNumber":1,"pageSize":50}
         * syncTime : 0
         */

        private PaginationBean pagination;
        private long syncTime;
        private List<ObjectsBean> objects;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public long getSyncTime() {
            return syncTime;
        }

        public void setSyncTime(long syncTime) {
            this.syncTime = syncTime;
        }

        public List<ObjectsBean> getObjects() {
            return objects;
        }

        public void setObjects(List<ObjectsBean> objects) {
            this.objects = objects;
        }

        public static class PaginationBean {
            /**
             * currentTime : 1505122416356
             * objectCount : 1
             * pageCount : 1
             * pageNumber : 1
             * pageSize : 50
             */

            private long currentTime;
            private int objectCount;
            private int pageCount;
            private int pageNumber;
            private int pageSize;

            public long getCurrentTime() {
                return currentTime;
            }

            public void setCurrentTime(long currentTime) {
                this.currentTime = currentTime;
            }

            public int getObjectCount() {
                return objectCount;
            }

            public void setObjectCount(int objectCount) {
                this.objectCount = objectCount;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }
        }

        public static class ObjectsBean {
            /**
             * apiTicketOrderItem : {"currentTicketPrice":0,"discountId":0,"discountPrice":0,"gift":0,"memberDiscountPrice":0,"orderItemId":1592114,"ticketFee":0,"ticketId":7027,"ticketOrderId":1199060,"ticketPrice":0}
             * areaCode : +86
             * attendeeAvatar :
             * attendeeId : 940023
             * attendeeMap : {"27492":"jlkjaf","27493":"1@qq.com","27494":"","27581":""}
             * attendeeNumber : 0
             * attendeeType : 0
             * audit : 0
             * auditTime :
             * avatar :
             * badgeMap : null
             * barcode : 5000208225870025098
             * buyWay : 2
             * cellphone :
             * checkInAisle :
             * checkin : 0
             * checkinCode : 931191
             * checkinTime :
             * emailAddr : 1@qq.com
             * firstName :
             * giftIssuance : 0
             * giftReceiveTime :
             * lastName :
             * name : jlkjaf
             * notes :
             * orderId : 1199060
             * orderPayStatus : 0
             * originalTicketName :
             * payStatus : 1
             * pinyinName : jlkjaf
             * refCode :
             * salesUserName :
             * signCode :
             * tagName :
             * ticketAudit : 0
             * ticketDesc :
             * ticketId : 7027
             * ticketName :
             * ticketPrice : 0
             * updateTime : 2017-09-11 17:33
             * userId : 0
             * weixinId :
             */

            private String areaCode;
            private String attendeeAvatar;
            private String attendeeId;
            //  private AttendeeMapBean attendeeMap;
            private String attendeeNumber;
            private String attendeeType;
            private String audit;
            private String auditTime;
            private String avatar;
            //   private Object badgeMap;
            private String barcode;
            private String buyWay;
            private String cellphone;
            private String checkInAisle;
            private String checkin;
            private String checkinCode;
            private String checkinTime;
            private String emailAddr;
            private String firstName;
            private String giftIssuance;
            private String giftReceiveTime;
            private String lastName;
            private String name;
            private String notes;
            private String orderId;
            private String orderPayStatus;
            private String originalTicketName;
            private String payStatus;
            private String pinyinName;
            private String refCode;
            private String salesUserName;
            private String signCode;
            private String tagName;
            private String ticketAudit;
            private String ticketDesc;
            private String ticketId;
            private String ticketName;
            private float ticketPrice;
            private String updateTime;
            private String userId;
            private String weixinId;
            private String productIds;
            private boolean hasBuyingGrouping;
            private int bgState;
            private int buyingGroupId;

            public boolean isHasBuyingGrouping() {
                return hasBuyingGrouping;
            }

            public void setHasBuyingGrouping(boolean hasBuyingGrouping) {
                this.hasBuyingGrouping = hasBuyingGrouping;
            }

            public int getBgState() {
                return bgState;
            }

            public void setBgState(int bgState) {
                this.bgState = bgState;
            }

            public int getBuyingGroupId() {
                return buyingGroupId;
            }

            public void setBuyingGroupId(int buyingGroupId) {
                this.buyingGroupId = buyingGroupId;
            }

            public String getProductIds() {
                return productIds;
            }

            public void setProductIds(String productIds) {
                this.productIds = productIds;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getAttendeeAvatar() {
                return attendeeAvatar;
            }

            public void setAttendeeAvatar(String attendeeAvatar) {
                this.attendeeAvatar = attendeeAvatar;
            }

            public String getAttendeeId() {
                return attendeeId;
            }

            public void setAttendeeId(String attendeeId) {
                this.attendeeId = attendeeId;
            }

            public String getAttendeeNumber() {
                return attendeeNumber;
            }

            public void setAttendeeNumber(String attendeeNumber) {
                this.attendeeNumber = attendeeNumber;
            }

            public String getAttendeeType() {
                return attendeeType;
            }

            public void setAttendeeType(String attendeeType) {
                this.attendeeType = attendeeType;
            }

            public String getAudit() {
                return audit;
            }

            public void setAudit(String audit) {
                this.audit = audit;
            }

            public String getAuditTime() {
                return auditTime;
            }

            public void setAuditTime(String auditTime) {
                this.auditTime = auditTime;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getBarcode() {
                return barcode;
            }

            public void setBarcode(String barcode) {
                this.barcode = barcode;
            }

            public String getBuyWay() {
                return buyWay;
            }

            public void setBuyWay(String buyWay) {
                this.buyWay = buyWay;
            }

            public String getCellphone() {
                return cellphone;
            }

            public void setCellphone(String cellphone) {
                this.cellphone = cellphone;
            }

            public String getCheckInAisle() {
                return checkInAisle;
            }

            public void setCheckInAisle(String checkInAisle) {
                this.checkInAisle = checkInAisle;
            }

            public String getCheckin() {
                return checkin;
            }

            public void setCheckin(String checkin) {
                this.checkin = checkin;
            }

            public String getCheckinCode() {
                return checkinCode;
            }

            public void setCheckinCode(String checkinCode) {
                this.checkinCode = checkinCode;
            }

            public String getCheckinTime() {
                return checkinTime;
            }

            public void setCheckinTime(String checkinTime) {
                this.checkinTime = checkinTime;
            }

            public String getEmailAddr() {
                return emailAddr;
            }

            public void setEmailAddr(String emailAddr) {
                this.emailAddr = emailAddr;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getGiftIssuance() {
                return giftIssuance;
            }

            public void setGiftIssuance(String giftIssuance) {
                this.giftIssuance = giftIssuance;
            }

            public String getGiftReceiveTime() {
                return giftReceiveTime;
            }

            public void setGiftReceiveTime(String giftReceiveTime) {
                this.giftReceiveTime = giftReceiveTime;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOrderPayStatus() {
                return orderPayStatus;
            }

            public void setOrderPayStatus(String orderPayStatus) {
                this.orderPayStatus = orderPayStatus;
            }

            public String getOriginalTicketName() {
                return originalTicketName;
            }

            public void setOriginalTicketName(String originalTicketName) {
                this.originalTicketName = originalTicketName;
            }

            public String getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(String payStatus) {
                this.payStatus = payStatus;
            }

            public String getPinyinName() {
                return pinyinName;
            }

            public void setPinyinName(String pinyinName) {
                this.pinyinName = pinyinName;
            }

            public String getRefCode() {
                return refCode;
            }

            public void setRefCode(String refCode) {
                this.refCode = refCode;
            }

            public String getSalesUserName() {
                return salesUserName;
            }

            public void setSalesUserName(String salesUserName) {
                this.salesUserName = salesUserName;
            }

            public String getSignCode() {
                return signCode;
            }

            public void setSignCode(String signCode) {
                this.signCode = signCode;
            }

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public String getTicketAudit() {
                return ticketAudit;
            }

            public void setTicketAudit(String ticketAudit) {
                this.ticketAudit = ticketAudit;
            }

            public String getTicketDesc() {
                return ticketDesc;
            }

            public void setTicketDesc(String ticketDesc) {
                this.ticketDesc = ticketDesc;
            }

            public String getTicketId() {
                return ticketId;
            }

            public void setTicketId(String ticketId) {
                this.ticketId = ticketId;
            }

            public String getTicketName() {
                return ticketName;
            }

            public void setTicketName(String ticketName) {
                this.ticketName = ticketName;
            }

            public float getTicketPrice() {
                return ticketPrice;
            }

            public void setTicketPrice(float ticketPrice) {
                this.ticketPrice = ticketPrice;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getWeixinId() {
                return weixinId;
            }

            public void setWeixinId(String weixinId) {
                this.weixinId = weixinId;
            }

        }
    }
}
