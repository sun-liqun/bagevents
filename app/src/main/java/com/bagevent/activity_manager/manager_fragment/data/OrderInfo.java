package com.bagevent.activity_manager.manager_fragment.data;

import java.util.List;

/**
 * Created by zwj on 2017/4/14.
 */

public class OrderInfo {


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

        private PaginationBean pagination;
        private int syncTime;
        private List<ObjectsBean> objects;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public int getSyncTime() {
            return syncTime;
        }

        public void setSyncTime(int syncTime) {
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
             * currentTime : 1492140763501
             * objectCount : 48
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
             * accountType :
             * apiUserName :
             * attendeeList : []
             * audit : 0
             * auditOrder : false
             * bankCode :
             * buySource : 0
             * buyWay : 0
             * buyerCellphone : 13951670785
             * buyerEmail : kevinds@sina.com
             * buyerFirstName :
             * buyerIp : 183.206.161.215
             * buyerLastName :
             * buyerName : KEVIN XIAO
             * currencySign : ￥
             * deviceName :
             * discountCode :
             * discountPrice : 0
             * discountType : 0
             * expireTime : 2016-07-07 16:37
             * hasGroup : 0
             * invoiceTaxPrice : 0
             * needInvoice : 0
             * notes :
             * orderId : 127956
             * orderNumber : 216070775209948
             * orderStatus : 3
             * orderTime : 2016-07-07 16:07
             * payOrderId : 0
             * payStatus : 1
             * payWay : 0
             * quantity : 1
             * rawTotalPrice : 0
             * sessionId : 80E665B93D728B4E9D647F0D1595BD00
             * status : A
             * totalFee : 0
             * totalPrice : 0
             * updateTime : 2016-07-07 16:08
             */

            private String accountType;
            private String apiUserName;
            private int audit;
            private String areaCode;
            private boolean auditOrder;
            private String bankCode;
            private int buySource;
            private int buyWay;
            private String buyerCellphone;
            private String buyerEmail;
            private String buyerFirstName;
            private String buyerIp;
            private String buyerLastName;
            private String buyerName;
            private int buyingGroupId;

            private int buyingGroupState;
            private String currencySign;
            private String deviceName;
            private String discountCode;
            private float discountPrice;
            private int discountType;
            private String expireTime;
            private int hasGroup;
            private float invoiceTaxPrice;
            private int needInvoice;
            private String notes;


            public int getBuyingGroupId() {
                return buyingGroupId;
            }

            public void setBuyingGroupId(int buyingGroupId) {
                this.buyingGroupId = buyingGroupId;
            }

            public int getBuyingGroupState() {
                return buyingGroupState;
            }

            public void setBuyingGroupState(int buyingGroupState) {
                this.buyingGroupState = buyingGroupState;
            }

            public void setRawTotalPrice(float rawTotalPrice) {
                this.rawTotalPrice = rawTotalPrice;
            }

            private int orderId;
            private String orderNumber;
            private int orderStatus;
            private String orderTime;
            private int payOrderId;
            private int payStatus;
            private int payWay;
            private int quantity;
            private float rawTotalPrice;
            private String sessionId;
            private String status;
            private float totalFee;
            private float totalPrice;
            private float ticketOrderTotalPrice;
            private String updateTime;
            private String sort;
            private List<?> attendeeList;

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public float getTicketOrderTotalPrice() {
                return ticketOrderTotalPrice;
            }

            public void setTicketOrderTotalPrice(float ticketOrderTotalPrice) {
                this.ticketOrderTotalPrice = ticketOrderTotalPrice;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getAccountType() {
                return accountType;
            }

            public void setAccountType(String accountType) {
                this.accountType = accountType;
            }

            public String getApiUserName() {
                return apiUserName;
            }

            public void setApiUserName(String apiUserName) {
                this.apiUserName = apiUserName;
            }

            public int getAudit() {
                return audit;
            }

            public void setAudit(int audit) {
                this.audit = audit;
            }

            public boolean isAuditOrder() {
                return auditOrder;
            }

            public void setAuditOrder(boolean auditOrder) {
                this.auditOrder = auditOrder;
            }

            public String getBankCode() {
                return bankCode;
            }

            public void setBankCode(String bankCode) {
                this.bankCode = bankCode;
            }

            public int getBuySource() {
                return buySource;
            }

            public void setBuySource(int buySource) {
                this.buySource = buySource;
            }

            public int getBuyWay() {
                return buyWay;
            }

            public void setBuyWay(int buyWay) {
                this.buyWay = buyWay;
            }

            public String getBuyerCellphone() {
                return buyerCellphone;
            }

            public void setBuyerCellphone(String buyerCellphone) {
                this.buyerCellphone = buyerCellphone;
            }

            public String getBuyerEmail() {
                return buyerEmail;
            }

            public void setBuyerEmail(String buyerEmail) {
                this.buyerEmail = buyerEmail;
            }

            public String getBuyerFirstName() {
                return buyerFirstName;
            }

            public void setBuyerFirstName(String buyerFirstName) {
                this.buyerFirstName = buyerFirstName;
            }

            public String getBuyerIp() {
                return buyerIp;
            }

            public void setBuyerIp(String buyerIp) {
                this.buyerIp = buyerIp;
            }

            public String getBuyerLastName() {
                return buyerLastName;
            }

            public void setBuyerLastName(String buyerLastName) {
                this.buyerLastName = buyerLastName;
            }

            public String getBuyerName() {
                return buyerName;
            }

            public void setBuyerName(String buyerName) {
                this.buyerName = buyerName;
            }

            public String getCurrencySign() {
                return currencySign;
            }

            public void setCurrencySign(String currencySign) {
                this.currencySign = currencySign;
            }

            public String getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(String deviceName) {
                this.deviceName = deviceName;
            }

            public String getDiscountCode() {
                return discountCode;
            }

            public void setDiscountCode(String discountCode) {
                this.discountCode = discountCode;
            }

            public float getDiscountPrice() {
                return discountPrice;
            }

            public void setDiscountPrice(float discountPrice) {
                this.discountPrice = discountPrice;
            }

            public int getDiscountType() {
                return discountType;
            }

            public void setDiscountType(int discountType) {
                this.discountType = discountType;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public int getHasGroup() {
                return hasGroup;
            }

            public void setHasGroup(int hasGroup) {
                this.hasGroup = hasGroup;
            }

            public float getInvoiceTaxPrice() {
                return invoiceTaxPrice;
            }

            public void setInvoiceTaxPrice(float invoiceTaxPrice) {
                this.invoiceTaxPrice = invoiceTaxPrice;
            }

            public int getNeedInvoice() {
                return needInvoice;
            }

            public void setNeedInvoice(int needInvoice) {
                this.needInvoice = needInvoice;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public String getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(String orderNumber) {
                this.orderNumber = orderNumber;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderTime() {
                return orderTime;
            }

            public void setOrderTime(String orderTime) {
                this.orderTime = orderTime;
            }

            public int getPayOrderId() {
                return payOrderId;
            }

            public void setPayOrderId(int payOrderId) {
                this.payOrderId = payOrderId;
            }

            public int getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(int payStatus) {
                this.payStatus = payStatus;
            }

            public int getPayWay() {
                return payWay;
            }

            public void setPayWay(int payWay) {
                this.payWay = payWay;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public float getRawTotalPrice() {
                return rawTotalPrice;
            }

            public void setRawTotalPrice(int rawTotalPrice) {
                this.rawTotalPrice = rawTotalPrice;
            }

            public String getSessionId() {
                return sessionId;
            }

            public void setSessionId(String sessionId) {
                this.sessionId = sessionId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public float getTotalFee() {
                return totalFee;
            }

            public void setTotalFee(float totalFee) {
                this.totalFee = totalFee;
            }

            public float getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(float totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public List<?> getAttendeeList() {
                return attendeeList;
            }

            public void setAttendeeList(List<?> attendeeList) {
                this.attendeeList = attendeeList;
            }
        }
    }
}
