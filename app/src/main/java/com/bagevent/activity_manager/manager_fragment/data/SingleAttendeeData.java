package com.bagevent.activity_manager.manager_fragment.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public class SingleAttendeeData {

    /**
     * respObject : {"formFields":{"5631":"濮撳悕","5632":"閭","5633":"鎵嬫満"},"attendeeList":[{"apiTicketOrderItem":{"currentTicketPrice":0,"discountId":0,"discountPrice":0,"gift":0,"memberDiscountPrice":0,"orderItemId":33814,"ticketFee":0,"ticketId":2105,"ticketOrderId":33575,"ticketPrice":0},"areaCode":"+86","attendeeAvatar":"","attendeeId":32258,"attendeeMap":{"5631":"fsdfa","5632":"jeese@eventslack.com","5633":""},"attendeeNumber":0,"attendeeType":0,"audit":1,"auditTime":"","avatar":"","badgeMap":null,"barcode":"2000905083648936057","buyWay":0,"cellphone":"","checkInAisle":"","checkin":0,"checkinCode":"525310","checkinTime":"","emailAddr":"jeese@eventslack.com","firstName":"","giftIssuance":0,"giftReceiveTime":"","lastName":"","name":"fsdfa","notes":"","orderId":33575,"orderPayStatus":0,"originalTicketName":"","payStatus":1,"pinyinName":"fsdfa","productIds":"","refCode":"","salesUserName":"","signCode":"","tagName":"","ticketAudit":0,"ticketDesc":"","ticketId":2105,"ticketName":"","ticketPrice":0,"updateTime":"2017-12-05 11:35","userId":0,"weixinId":""}]}
     * respType : 1
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
         * formFields : {"5631":"濮撳悕","5632":"閭","5633":"鎵嬫満"}
         * attendeeList : [{"apiTicketOrderItem":{"currentTicketPrice":0,"discountId":0,"discountPrice":0,"gift":0,"memberDiscountPrice":0,"orderItemId":33814,"ticketFee":0,"ticketId":2105,"ticketOrderId":33575,"ticketPrice":0},"areaCode":"+86","attendeeAvatar":"","attendeeId":32258,"attendeeMap":{"5631":"fsdfa","5632":"jeese@eventslack.com","5633":""},"attendeeNumber":0,"attendeeType":0,"audit":1,"auditTime":"","avatar":"","badgeMap":null,"barcode":"2000905083648936057","buyWay":0,"cellphone":"","checkInAisle":"","checkin":0,"checkinCode":"525310","checkinTime":"","emailAddr":"jeese@eventslack.com","firstName":"","giftIssuance":0,"giftReceiveTime":"","lastName":"","name":"fsdfa","notes":"","orderId":33575,"orderPayStatus":0,"originalTicketName":"","payStatus":1,"pinyinName":"fsdfa","productIds":"","refCode":"","salesUserName":"","signCode":"","tagName":"","ticketAudit":0,"ticketDesc":"","ticketId":2105,"ticketName":"","ticketPrice":0,"updateTime":"2017-12-05 11:35","userId":0,"weixinId":""}]
         */

        private FormFieldsBean formFields;
        private List<AttendeeListBean> attendeeList;

        public FormFieldsBean getFormFields() {
            return formFields;
        }

        public void setFormFields(FormFieldsBean formFields) {
            this.formFields = formFields;
        }

        public List<AttendeeListBean> getAttendeeList() {
            return attendeeList;
        }

        public void setAttendeeList(List<AttendeeListBean> attendeeList) {
            this.attendeeList = attendeeList;
        }

        public static class FormFieldsBean {
            /**
             * 5631 : 濮撳悕
             * 5632 : 閭
             * 5633 : 鎵嬫満
             */

            @SerializedName("5631")
            private String _$5631;
            @SerializedName("5632")
            private String _$5632;
            @SerializedName("5633")
            private String _$5633;

            public String get_$5631() {
                return _$5631;
            }

            public void set_$5631(String _$5631) {
                this._$5631 = _$5631;
            }

            public String get_$5632() {
                return _$5632;
            }

            public void set_$5632(String _$5632) {
                this._$5632 = _$5632;
            }

            public String get_$5633() {
                return _$5633;
            }

            public void set_$5633(String _$5633) {
                this._$5633 = _$5633;
            }
        }

        public static class AttendeeListBean {
            /**
             * apiTicketOrderItem : {"currentTicketPrice":0,"discountId":0,"discountPrice":0,"gift":0,"memberDiscountPrice":0,"orderItemId":33814,"ticketFee":0,"ticketId":2105,"ticketOrderId":33575,"ticketPrice":0}
             * areaCode : +86
             * attendeeAvatar :
             * attendeeId : 32258
             * attendeeMap : {"5631":"fsdfa","5632":"jeese@eventslack.com","5633":""}
             * attendeeNumber : 0
             * attendeeType : 0
             * audit : 1
             * auditTime :
             * avatar :
             * badgeMap : null
             * barcode : 2000905083648936057
             * buyWay : 0
             * cellphone :
             * checkInAisle :
             * checkin : 0
             * checkinCode : 525310
             * checkinTime :
             * emailAddr : jeese@eventslack.com
             * firstName :
             * giftIssuance : 0
             * giftReceiveTime :
             * lastName :
             * name : fsdfa
             * notes :
             * orderId : 33575
             * orderPayStatus : 0
             * originalTicketName :
             * payStatus : 1
             * pinyinName : fsdfa
             * productIds :
             * refCode :
             * salesUserName :
             * signCode :
             * tagName :
             * ticketAudit : 0
             * ticketDesc :
             * ticketId : 2105
             * ticketName :
             * ticketPrice : 0
             * updateTime : 2017-12-05 11:35
             * userId : 0
             * weixinId :
             */

            private String areaCode;
            private String attendeeAvatar;
            private String attendeeId;
            private String attendeeNumber;
            private String attendeeType;
            private String audit;
            private String auditTime;
            private String avatar;
            private Object badgeMap;
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
            private String productIds;
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

            public Object getBadgeMap() {
                return badgeMap;
            }

            public void setBadgeMap(Object badgeMap) {
                this.badgeMap = badgeMap;
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

            public String getProductIds() {
                return productIds;
            }

            public void setProductIds(String productIds) {
                this.productIds = productIds;
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
