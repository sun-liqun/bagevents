package com.bagevent.activity_manager.manager_fragment.data;

import java.io.Serializable;

/**
 * Created by zwj on 2017/10/27.
 */

public class InvoiceBean implements Serializable {

    /**
     * respObject : {"address":"","bankAccount":"","brief":"1","cellphone":"","companyBank":"","companyCode":"","companyFinanceContact":"","companyRegAddr":"","confirm":0,"expressCompany":"","expressNum":"","invoiceCode":"","invoiceItem":"会议费","invoiceNumber":"","invoiceTitle":"1","invoiceType":0,"obtaied":0,"obtainMethod":2,"obtainWay":0,"orderInvoiceId":1185,"orderNumber":"217091529262993","receiver":"","receiverType":1,"taxNum":"1","updateTime":"2017-09-15 11:52"}
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

    public static class RespObjectBean implements Serializable {
        /**
         * address :
         * bankAccount :
         * brief : 1
         * cellphone :
         * companyBank :
         * companyCode :
         * companyFinanceContact :
         * companyRegAddr :
         * confirm : 0
         * expressCompany :
         * expressNum :
         * invoiceCode :
         * invoiceItem : 会议费
         * invoiceNumber :
         * invoiceTitle : 1
         * invoiceType : 0
         * obtaied : 0
         * obtainMethod : 2
         * obtainWay : 0
         * orderInvoiceId : 1185
         * orderNumber : 217091529262993
         * receiver :
         * receiverType : 1
         * taxNum : 1
         * updateTime : 2017-09-15 11:52
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

        public int getUseCompanyCode() {
            return useCompanyCode;
        }

        public void setUseCompanyCode(int useCompanyCode) {
            this.useCompanyCode = useCompanyCode;
        }

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
    }
}
