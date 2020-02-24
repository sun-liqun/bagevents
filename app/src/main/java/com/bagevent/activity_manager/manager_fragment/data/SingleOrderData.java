package com.bagevent.activity_manager.manager_fragment.data;

import java.util.List;

/**
 * Created by ZWJ on 2017/12/4 0004.
 */

public class SingleOrderData {


    /**
     * respObject : {"accountType":"","apiUserName":"","areaCode":"+86","attendeeList":[],"audit":0,"auditOrder":false,"bankCode":"","buySource":0,"buyWay":0,"buyerCellphone":"","buyerEmail":"12@163.com","buyerFirstName":"","buyerIp":"49.77.179.143","buyerLastName":"","buyerName":"ccc","currencySign":"￥","deviceName":"","discountCode":"","discountPrice":0,"discountType":0,"expireTime":"2017-12-04 16:36","fee":0,"hasChangedReceivedAmount":0,"hasGroup":0,"invoiceTaxPrice":0,"invoiceTaxPriceOriginal":0,"needInvoice":0,"notes":"","orderId":33531,"orderNumber":"217120429549682","orderStatus":3,"orderTime":"2017-12-04 16:06","orderType":-1,"payOrderId":0,"payStatus":1,"payTotalPrice":0,"payWay":0,"productPrice":0,"quantity":1,"rawTotalPrice":0,"serviceFee":0,"sessionId":"e0646815-e369-4a80-a3ee-c26bedb1bc23","status":"A","ticketOrderInvoiceInfo":null,"ticketOrderTotalPrice":0,"totalFee":0,"totalPrice":0,"updateTime":"2017-12-04 16:07"}
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
         * accountType :
         * apiUserName :
         * areaCode : +86
         * attendeeList : []
         * audit : 0
         * auditOrder : false
         * bankCode :
         * buySource : 0
         * buyWay : 0
         * buyerCellphone :
         * buyerEmail : 12@163.com
         * buyerFirstName :
         * buyerIp : 49.77.179.143
         * buyerLastName :
         * buyerName : ccc
         * currencySign : ￥
         * deviceName :
         * discountCode :
         * discountPrice : 0
         * discountType : 0
         * expireTime : 2017-12-04 16:36
         * fee : 0
         * hasChangedReceivedAmount : 0
         * hasGroup : 0
         * invoiceTaxPrice : 0
         * invoiceTaxPriceOriginal : 0
         * needInvoice : 0
         * notes :
         * orderId : 33531
         * orderNumber : 217120429549682
         * orderStatus : 3
         * orderTime : 2017-12-04 16:06
         * orderType : -1
         * payOrderId : 0
         * payStatus : 1
         * payTotalPrice : 0
         * payWay : 0
         * productPrice : 0
         * quantity : 1
         * rawTotalPrice : 0
         * serviceFee : 0
         * sessionId : e0646815-e369-4a80-a3ee-c26bedb1bc23
         * status : A
         * ticketOrderInvoiceInfo : null
         * ticketOrderTotalPrice : 0
         * totalFee : 0
         * totalPrice : 0
         * updateTime : 2017-12-04 16:07
         */

        private String accountType;
        private String apiUserName;
        private String areaCode;
        private int audit;
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
        private String currencySign;
        private String deviceName;
        private String discountCode;
        private float discountPrice;
        private int discountType;
        private String expireTime;
        private int fee;
        private int hasChangedReceivedAmount;
        private int hasGroup;
        private float invoiceTaxPrice;
        private float invoiceTaxPriceOriginal;
        private int needInvoice;
        private String notes;
        private int orderId;
        private String orderNumber;
        private int orderStatus;
        private String orderTime;
        private int orderType;
        private int payOrderId;
        private int payStatus;
        private float payTotalPrice;
        private int payWay;
        private float productPrice;
        private int quantity;
        private float rawTotalPrice;
        private int serviceFee;
        private String sessionId;
        private String status;
        private Object ticketOrderInvoiceInfo;
        private float ticketOrderTotalPrice;
        private int totalFee;
        private float totalPrice;
        private String updateTime;
        private List<?> attendeeList;

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

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
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

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public int getHasChangedReceivedAmount() {
            return hasChangedReceivedAmount;
        }

        public void setHasChangedReceivedAmount(int hasChangedReceivedAmount) {
            this.hasChangedReceivedAmount = hasChangedReceivedAmount;
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

        public float getInvoiceTaxPriceOriginal() {
            return invoiceTaxPriceOriginal;
        }

        public void setInvoiceTaxPriceOriginal(float invoiceTaxPriceOriginal) {
            this.invoiceTaxPriceOriginal = invoiceTaxPriceOriginal;
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

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
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

        public float getPayTotalPrice() {
            return payTotalPrice;
        }

        public void setPayTotalPrice(float payTotalPrice) {
            this.payTotalPrice = payTotalPrice;
        }

        public int getPayWay() {
            return payWay;
        }

        public void setPayWay(int payWay) {
            this.payWay = payWay;
        }

        public float getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(float productPrice) {
            this.productPrice = productPrice;
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

        public void setRawTotalPrice(float rawTotalPrice) {
            this.rawTotalPrice = rawTotalPrice;
        }

        public int getServiceFee() {
            return serviceFee;
        }

        public void setServiceFee(int serviceFee) {
            this.serviceFee = serviceFee;
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

        public Object getTicketOrderInvoiceInfo() {
            return ticketOrderInvoiceInfo;
        }

        public void setTicketOrderInvoiceInfo(Object ticketOrderInvoiceInfo) {
            this.ticketOrderInvoiceInfo = ticketOrderInvoiceInfo;
        }

        public float getTicketOrderTotalPrice() {
            return ticketOrderTotalPrice;
        }

        public void setTicketOrderTotalPrice(float ticketOrderTotalPrice) {
            this.ticketOrderTotalPrice = ticketOrderTotalPrice;
        }

        public int getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(int totalFee) {
            this.totalFee = totalFee;
        }

        public float getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
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
