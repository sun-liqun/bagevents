package com.bagevent.home.data;

import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;

import java.util.List;

/**
 * Created by zwj on 2016/5/24.
 */
public class ExercisingData {
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
        private String mark;

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        private PaginationBean pagination;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        private List<ApiEventListBean> apiEventList;

        private List<CollectionEventListBean> collectionEventList;

        public List<ApiEventListBean> getApiEventList() {
            return apiEventList;
        }

        public void setApiEventList(List<ApiEventListBean> apiEventList) {
            this.apiEventList = apiEventList;
        }

        public List<CollectionEventListBean> getCollectionEventList() {
            return collectionEventList;
        }

        public void setCollectionEventList(List<CollectionEventListBean> collectionEventList) {
            this.collectionEventList = collectionEventList;
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

        public static class ApiEventListBean {
            private String address;
            private int attendeeCount;
            private int auditCount;
            private int brand;
            private int checkinCount;
            private int collectInvoice;
            private String endTime;
            private int eventId;
            private String eventName;
            private int eventType;
            private double income;
            private String logo;
            private String officialWebsite;
            private int participantsCount;
            private String startTime;
            private int status;
            private int ticketCount;
            private long websiteId;
            private int nameType;
            private int stType;

            private int exType;

            public int getExType() {
                return exType;
            }

            public void setExType(int exType) {
                this.exType = exType;
            }

            private String collectionName;
            private int collectPointId;
            private int export;
            private int isRepeat;
            private String ticketIds;


            public int getStType() {
                return stType;
            }

            public void setStType(int stType) {
                this.stType = stType;
            }

            public int getNameType() {
                return nameType;
            }

            public void setNameType(int nameType) {
                this.nameType = nameType;
            }

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

            @Override
            public String toString() {
                return "ApiEventListBean{" +
                        "address='" + address + '\'' +
                        ", attendeeCount=" + attendeeCount +
                        ", auditCount=" + auditCount +
                        ", brand=" + brand +
                        ", checkinCount=" + checkinCount +
                        ", collectInvoice=" + collectInvoice +
                        ", endTime='" + endTime + '\'' +
                        ", eventId=" + eventId +
                        ", eventName='" + eventName + '\'' +
                        ", eventType=" + eventType +
                        ", income=" + income +
                        ", logo='" + logo + '\'' +
                        ", officialWebsite='" + officialWebsite + '\'' +
                        ", participantsCount=" + participantsCount +
                        ", startTime='" + startTime + '\'' +
                        ", status=" + status +
                        ", ticketCount=" + ticketCount +
                        ", websiteId=" + websiteId +
                        '}';
            }
        }

        public static class CollectionEventListBean {
            private int eventId;
            private String eventName;
            private String collectionName;
            private String eventType;
            private int status;
            private String startTime;
            private String endTime;
            private int collectPointId;
            private int checkinCount;
            private int export;
            private int isRepeat;
            private String ticketIds;

            public String getTicketIds() {
                return ticketIds;
            }

            public void setTicketIds(String ticketIds) {
                this.ticketIds = ticketIds;
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

            public String getCollectionName() {
                return collectionName;
            }

            public void setCollectionName(String collectionName) {
                this.collectionName = collectionName;
            }

            public String getEventType() {
                return eventType;
            }

            public void setEventType(String eventType) {
                this.eventType = eventType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public int getCollectPointId() {
                return collectPointId;
            }

            public void setCollectPointId(int collectPointId) {
                this.collectPointId = collectPointId;
            }

            public int getCheckinCount() {
                return checkinCount;
            }

            public void setCheckinCount(int checkinCount) {
                this.checkinCount = checkinCount;
            }

            public int getExport() {
                return export;
            }

            public void setExport(int export) {
                this.export = export;
            }

            public int getIsRepeat() {
                return isRepeat;
            }

            public void setIsRepeat(int isRepeat) {
                this.isRepeat = isRepeat;
            }

            @Override
            public String toString() {
                return "CollectionEventListBean{" +
                        "eventId=" + eventId +
                        ", eventName='" + eventName + '\'' +
                        ", collectionName='" + collectionName + '\'' +
                        ", eventType='" + eventType + '\'' +
                        ", status=" + status +
                        ", startTime='" + startTime + '\'' +
                        ", endTime='" + endTime + '\'' +
                        ", collectPointId=" + collectPointId +
                        ", checkinCount=" + checkinCount +
                        ", export=" + export +
                        ", isRepeat=" + isRepeat +
                        '}';
            }
        }


    }
}
