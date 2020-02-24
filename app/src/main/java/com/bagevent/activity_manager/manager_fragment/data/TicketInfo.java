package com.bagevent.activity_manager.manager_fragment.data;

import java.util.List;

/**
 * Created by zwj on 2016/6/2.
 */
public class TicketInfo {

    private int respType;
    private int retStatus;

    private List<RespObjectBean> respObject;

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

    public List<RespObjectBean> getRespObject() {
        return respObject;
    }

    public void setRespObject(List<RespObjectBean> respObject) {
        this.respObject = respObject;
    }

    public static class RespObjectBean {
        private int attendeeCount;
        private int audit;
        private boolean auditFeeTicket;
        private boolean auditTicket;
        private int checkinCount;
        private String description;
        private Object discount;
        private String endSaleTime;
        private boolean freeTicket;
        private int hideStatus;
        private int limitCount;
        private int lockCount;
        private int maxCount;
        private int minCount;
        private int salesTime;
        private int selledCount;
        private int selledTimeStatus;
        private String showDescription;
        private String showTicketName;
        private int sort;
        private String startSaleTime;
        private int status;
        private boolean subEvent;
        private int ticketCount;
        private int ticketFee;
        private int ticketId;
        private String ticketName;
        private Object ticketPremise;
        private float ticketPrice;
        private boolean validTicket;

        public boolean isAuditFeeTicket() {
            return auditFeeTicket;
        }

        public void setAuditFeeTicket(boolean auditFeeTicket) {
            this.auditFeeTicket = auditFeeTicket;
        }

        public int getAttendeeCount() {
            return attendeeCount;
        }

        public void setAttendeeCount(int attendeeCount) {
            this.attendeeCount = attendeeCount;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public boolean isAuditTicket() {
            return auditTicket;
        }

        public void setAuditTicket(boolean auditTicket) {
            this.auditTicket = auditTicket;
        }

        public int getCheckinCount() {
            return checkinCount;
        }

        public void setCheckinCount(int checkinCount) {
            this.checkinCount = checkinCount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public String getEndSaleTime() {
            return endSaleTime;
        }

        public void setEndSaleTime(String endSaleTime) {
            this.endSaleTime = endSaleTime;
        }

        public boolean isFreeTicket() {
            return freeTicket;
        }

        public void setFreeTicket(boolean freeTicket) {
            this.freeTicket = freeTicket;
        }

        public int getHideStatus() {
            return hideStatus;
        }

        public void setHideStatus(int hideStatus) {
            this.hideStatus = hideStatus;
        }

        public int getLimitCount() {
            return limitCount;
        }

        public void setLimitCount(int limitCount) {
            this.limitCount = limitCount;
        }

        public int getLockCount() {
            return lockCount;
        }

        public void setLockCount(int lockCount) {
            this.lockCount = lockCount;
        }

        public int getMaxCount() {
            return maxCount;
        }

        public void setMaxCount(int maxCount) {
            this.maxCount = maxCount;
        }

        public int getMinCount() {
            return minCount;
        }

        public void setMinCount(int minCount) {
            this.minCount = minCount;
        }

        public int getSalesTime() {
            return salesTime;
        }

        public void setSalesTime(int salesTime) {
            this.salesTime = salesTime;
        }

        public int getSelledCount() {
            return selledCount;
        }

        public void setSelledCount(int selledCount) {
            this.selledCount = selledCount;
        }

        public int getSelledTimeStatus() {
            return selledTimeStatus;
        }

        public void setSelledTimeStatus(int selledTimeStatus) {
            this.selledTimeStatus = selledTimeStatus;
        }

        public String getShowDescription() {
            return showDescription;
        }

        public void setShowDescription(String showDescription) {
            this.showDescription = showDescription;
        }

        public String getShowTicketName() {
            return showTicketName;
        }

        public void setShowTicketName(String showTicketName) {
            this.showTicketName = showTicketName;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getStartSaleTime() {
            return startSaleTime;
        }

        public void setStartSaleTime(String startSaleTime) {
            this.startSaleTime = startSaleTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isSubEvent() {
            return subEvent;
        }

        public void setSubEvent(boolean subEvent) {
            this.subEvent = subEvent;
        }

        public int getTicketCount() {
            return ticketCount;
        }

        public void setTicketCount(int ticketCount) {
            this.ticketCount = ticketCount;
        }

        public int getTicketFee() {
            return ticketFee;
        }

        public void setTicketFee(int ticketFee) {
            this.ticketFee = ticketFee;
        }

        public int getTicketId() {
            return ticketId;
        }

        public void setTicketId(int ticketId) {
            this.ticketId = ticketId;
        }

        public String getTicketName() {
            return ticketName;
        }

        public void setTicketName(String ticketName) {
            this.ticketName = ticketName;
        }

        public Object getTicketPremise() {
            return ticketPremise;
        }

        public void setTicketPremise(Object ticketPremise) {
            this.ticketPremise = ticketPremise;
        }

        public float getTicketPrice() {
            return ticketPrice;
        }

        public void setTicketPrice(float ticketPrice) {
            this.ticketPrice = ticketPrice;
        }

        public boolean isValidTicket() {
            return validTicket;
        }

        public void setValidTicket(boolean validTicket) {
            this.validTicket = validTicket;
        }
    }
}
