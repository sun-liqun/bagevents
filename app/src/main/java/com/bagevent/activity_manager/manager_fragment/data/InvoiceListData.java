package com.bagevent.activity_manager.manager_fragment.data;

import java.util.List;

/**
 * Created by ZWJ on 2017/12/28 0028.
 */

public class InvoiceListData {


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
            private double discountPrice;
            private int discountType;
            private String expireTime;
            private int fee;
            private int hasChangedReceivedAmount;
            private int hasGroup;
            private double invoiceTaxPrice;
            private double invoiceTaxPriceOriginal;
            private int needInvoice;
            private String notes;
            private int orderId;
            private String orderNumber;
            private int orderStatus;
            private String orderTime;
            private int orderType;
            private int payOrderId;
            private int payStatus;
            private double payTotalPrice;
            private int payWay;
            private double productPrice;
            private int quantity;
            private double rawTotalPrice;
            private double serviceFee;
            private String sessionId;
            private String status;
            private TicketOrderInvoiceInfoBean ticketOrderInvoiceInfo;
            private double ticketOrderTotalPrice;
            private double totalFee;
            private double totalPrice;
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

            public double getDiscountPrice() {
                return discountPrice;
            }

            public void setDiscountPrice(double discountPrice) {
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

            public double getInvoiceTaxPrice() {
                return invoiceTaxPrice;
            }

            public void setInvoiceTaxPrice(double invoiceTaxPrice) {
                this.invoiceTaxPrice = invoiceTaxPrice;
            }

            public double getInvoiceTaxPriceOriginal() {
                return invoiceTaxPriceOriginal;
            }

            public void setInvoiceTaxPriceOriginal(double invoiceTaxPriceOriginal) {
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

            public double getPayTotalPrice() {
                return payTotalPrice;
            }

            public void setPayTotalPrice(double payTotalPrice) {
                this.payTotalPrice = payTotalPrice;
            }

            public int getPayWay() {
                return payWay;
            }

            public void setPayWay(int payWay) {
                this.payWay = payWay;
            }

            public double getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(double productPrice) {
                this.productPrice = productPrice;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public double getRawTotalPrice() {
                return rawTotalPrice;
            }

            public void setRawTotalPrice(double rawTotalPrice) {
                this.rawTotalPrice = rawTotalPrice;
            }

            public double getServiceFee() {
                return serviceFee;
            }

            public void setServiceFee(double serviceFee) {
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

            public TicketOrderInvoiceInfoBean getTicketOrderInvoiceInfo() {
                return ticketOrderInvoiceInfo;
            }

            public void setTicketOrderInvoiceInfo(TicketOrderInvoiceInfoBean ticketOrderInvoiceInfo) {
                this.ticketOrderInvoiceInfo = ticketOrderInvoiceInfo;
            }

            public double getTicketOrderTotalPrice() {
                return ticketOrderTotalPrice;
            }

            public void setTicketOrderTotalPrice(double ticketOrderTotalPrice) {
                this.ticketOrderTotalPrice = ticketOrderTotalPrice;
            }

            public double getTotalFee() {
                return totalFee;
            }

            public void setTotalFee(double totalFee) {
                this.totalFee = totalFee;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
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

            public static class TicketOrderInvoiceInfoBean {
                /**
                 * address :
                 * bankAccount :
                 * brief :
                 * cellphone :
                 * companyBank :
                 * companyCode :
                 * companyFinanceContact :
                 * companyRegAddr :
                 * confirm : 0
                 * eInvoiceCellPhone :
                 * expressCompany :
                 * expressNum :
                 * invoiceCode :
                 * invoiceItem : 会议费
                 * invoiceNumber :
                 * invoiceTitle : 唐嫣
                 * invoiceType : 0
                 * obtaied : 0
                 * obtainMethod : 2
                 * obtainWay : 0
                 * orderInvoiceId : 1187
                 * orderNumber : 217111329329109
                 * receiver :
                 * receiverType : 1
                 * taxNum : 123465789
                 * updateTime : 2017-11-13 18:23:23
                 * useCompanyCode : 0
                 */

                private String address;
                private String bankAccount;
                private String brief;
                private String cellphone;
                private String companyBank;
                private String companyCode;
                private String companyFinanceContact;
                private String companyRegAddr;
                private int confirm;
                private String eInvoiceCellPhone;
                private String expressCompany;
                private String expressNum;
                private String invoiceCode;
                private String invoiceItem;
                private String invoiceNumber;
                private String invoiceTitle;
                private int invoiceType;
                private int obtaied;
                private int obtainMethod;
                private int obtainWay;
                private int orderInvoiceId;
                private String orderNumber;
                private String receiver;
                private int receiverType;
                private String taxNum;
                private String updateTime;
                private int useCompanyCode;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getBankAccount() {
                    return bankAccount;
                }

                public void setBankAccount(String bankAccount) {
                    this.bankAccount = bankAccount;
                }

                public String getBrief() {
                    return brief;
                }

                public void setBrief(String brief) {
                    this.brief = brief;
                }

                public String getCellphone() {
                    return cellphone;
                }

                public void setCellphone(String cellphone) {
                    this.cellphone = cellphone;
                }

                public String getCompanyBank() {
                    return companyBank;
                }

                public void setCompanyBank(String companyBank) {
                    this.companyBank = companyBank;
                }

                public String getCompanyCode() {
                    return companyCode;
                }

                public void setCompanyCode(String companyCode) {
                    this.companyCode = companyCode;
                }

                public String getCompanyFinanceContact() {
                    return companyFinanceContact;
                }

                public void setCompanyFinanceContact(String companyFinanceContact) {
                    this.companyFinanceContact = companyFinanceContact;
                }

                public String getCompanyRegAddr() {
                    return companyRegAddr;
                }

                public void setCompanyRegAddr(String companyRegAddr) {
                    this.companyRegAddr = companyRegAddr;
                }

                public int getConfirm() {
                    return confirm;
                }

                public void setConfirm(int confirm) {
                    this.confirm = confirm;
                }

                public String getEInvoiceCellPhone() {
                    return eInvoiceCellPhone;
                }

                public void setEInvoiceCellPhone(String eInvoiceCellPhone) {
                    this.eInvoiceCellPhone = eInvoiceCellPhone;
                }

                public String getExpressCompany() {
                    return expressCompany;
                }

                public void setExpressCompany(String expressCompany) {
                    this.expressCompany = expressCompany;
                }

                public String getExpressNum() {
                    return expressNum;
                }

                public void setExpressNum(String expressNum) {
                    this.expressNum = expressNum;
                }

                public String getInvoiceCode() {
                    return invoiceCode;
                }

                public void setInvoiceCode(String invoiceCode) {
                    this.invoiceCode = invoiceCode;
                }

                public String getInvoiceItem() {
                    return invoiceItem;
                }

                public void setInvoiceItem(String invoiceItem) {
                    this.invoiceItem = invoiceItem;
                }

                public String getInvoiceNumber() {
                    return invoiceNumber;
                }

                public void setInvoiceNumber(String invoiceNumber) {
                    this.invoiceNumber = invoiceNumber;
                }

                public String getInvoiceTitle() {
                    return invoiceTitle;
                }

                public void setInvoiceTitle(String invoiceTitle) {
                    this.invoiceTitle = invoiceTitle;
                }

                public int getInvoiceType() {
                    return invoiceType;
                }

                public void setInvoiceType(int invoiceType) {
                    this.invoiceType = invoiceType;
                }

                public int getObtaied() {
                    return obtaied;
                }

                public void setObtaied(int obtaied) {
                    this.obtaied = obtaied;
                }

                public int getObtainMethod() {
                    return obtainMethod;
                }

                public void setObtainMethod(int obtainMethod) {
                    this.obtainMethod = obtainMethod;
                }

                public int getObtainWay() {
                    return obtainWay;
                }

                public void setObtainWay(int obtainWay) {
                    this.obtainWay = obtainWay;
                }

                public int getOrderInvoiceId() {
                    return orderInvoiceId;
                }

                public void setOrderInvoiceId(int orderInvoiceId) {
                    this.orderInvoiceId = orderInvoiceId;
                }

                public String getOrderNumber() {
                    return orderNumber;
                }

                public void setOrderNumber(String orderNumber) {
                    this.orderNumber = orderNumber;
                }

                public String getReceiver() {
                    return receiver;
                }

                public void setReceiver(String receiver) {
                    this.receiver = receiver;
                }

                public int getReceiverType() {
                    return receiverType;
                }

                public void setReceiverType(int receiverType) {
                    this.receiverType = receiverType;
                }

                public String getTaxNum() {
                    return taxNum;
                }

                public void setTaxNum(String taxNum) {
                    this.taxNum = taxNum;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public int getUseCompanyCode() {
                    return useCompanyCode;
                }

                public void setUseCompanyCode(int useCompanyCode) {
                    this.useCompanyCode = useCompanyCode;
                }
            }
        }
    }
}
