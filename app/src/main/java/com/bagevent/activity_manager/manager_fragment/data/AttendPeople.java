package com.bagevent.activity_manager.manager_fragment.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zwj on 2016/6/6.
 * 添加和审核参会人员的时候使用
 *
 */
public class AttendPeople implements Serializable {
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

        public static class PaginationBean implements Serializable{
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

        public static class ObjectsBean implements Serializable {
            private String attendeeAvatar;
            private int eventId;
            private int attendeeId;
            private AttendeeMapBean attendeeMap;
            private int audit;
            private String auditTime;
            private String avatar;
            private String barcode;
            private int buyWay;
            private String cellphone;
            private int checkin;
            private String checkinCode;
            private String checkinTime;
            private String emailAddr;
            private String name;
            private String notes;
            private int orderId;
            private int payStatus;
            private String pinyinName;
            private String refCode;
            private int ticketId;
            private double ticketPrice;
            private String updateTime;
            private String weixinId;
            private String ticketName;
            private String gsonUser;
            private String sort;
            private int sync;
            private String add;
            private String imagePath;
            private String badgeMaps;
            private String productIds;

            public void setBadgeMaps(String badgeMaps) {
                this.badgeMaps = badgeMaps;
            }

            public String getBadgeMaps() {
                return badgeMaps;
            }

            public void setBadgeMap(String badgeMap) {
                this.badgeMaps = badgeMap;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public String getAttendeeAvatar() {
                return attendeeAvatar;
            }

            public void setAttendeeAvatar(String attendeeAvatar) {
                this.attendeeAvatar = attendeeAvatar;
            }

            public String getAdd() {
                return add;
            }

            public void setAdd(String add) {
                this.add = add;
            }

            public double getTicketPrice() {
                return ticketPrice;
            }

            public void setTicketPrice(double ticketPrice) {
                this.ticketPrice = ticketPrice;
            }

            public int getSync() {
                return sync;
            }

            public void setSync(int sync) {
                this.sync = sync;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getGsonUser() {
                return gsonUser;
            }

            public void setGsonUser(String gsonUser) {
                this.gsonUser = gsonUser;
            }


            public int getEventId() {
                return eventId;
            }

            public void setEventId(int eventId) {
                this.eventId = eventId;
            }
            public String getTicketName() {
                return ticketName;
            }

            public void setTicketName(String ticketName) {
                this.ticketName = ticketName;
            }

            public int getAttendeeId() {
                return attendeeId;
            }

            public void setAttendeeId(int attendeeId) {
                this.attendeeId = attendeeId;
            }

            public AttendeeMapBean getAttendeeMap() {
                return attendeeMap;
            }

            public void setAttendeeMap(AttendeeMapBean attendeeMap) {
                this.attendeeMap = attendeeMap;
            }

            public int getAudit() {
                return audit;
            }

            public void setAudit(int audit) {
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

            public int getBuyWay() {
                return buyWay;
            }

            public void setBuyWay(int buyWay) {
                this.buyWay = buyWay;
            }

            public String getCellphone() {
                return cellphone;
            }

            public void setCellphone(String cellphone) {
                this.cellphone = cellphone;
            }

            public int getCheckin() {
                return checkin;
            }

            public void setCheckin(int checkin) {
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

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(int payStatus) {
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

            public int getTicketId() {
                return ticketId;
            }

            public void setTicketId(int ticketId) {
                this.ticketId = ticketId;
            }



            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getWeixinId() {
                return weixinId;
            }

            public void setWeixinId(String weixinId) {
                this.weixinId = weixinId;
            }

            public static class AttendeeMapBean implements Serializable{
                @SerializedName("80")
                private String value80;
                @SerializedName("81")
                private String value81;
                @SerializedName("82")
                private String value82;
                @SerializedName("106")
                private String value106;
                @SerializedName("9613")
                private String value9613;
                @SerializedName("9616")
                private String value9616;
                @SerializedName("9621")
                private String value9621;
                @SerializedName("12379")
                private String value12379;
                @SerializedName("12380")
                private String value12380;

                public String getValue12379() {
                    return value12379;
                }

                public void setValue12379(String value12379) {
                    this.value12379 = value12379;
                }

                public String getValue12380() {
                    return value12380;
                }

                public void setValue12380(String value12380) {
                    this.value12380 = value12380;
                }

                public String getValue80() {
                    return value80;
                }

                public void setValue80(String value80) {
                    this.value80 = value80;
                }

                public String getValue81() {
                    return value81;
                }

                public void setValue81(String value81) {
                    this.value81 = value81;
                }

                public String getValue82() {
                    return value82;
                }

                public void setValue82(String value82) {
                    this.value82 = value82;
                }

                public String getValue106() {
                    return value106;
                }

                public void setValue106(String value106) {
                    this.value106 = value106;
                }

                public String getValue9613() {
                    return value9613;
                }

                public void setValue9613(String value9613) {
                    this.value9613 = value9613;
                }

                public String getValue9616() {
                    return value9616;
                }

                public void setValue9616(String value9616) {
                    this.value9616 = value9616;
                }

                public String getValue9621() {
                    return value9621;
                }

                public void setValue9621(String value9621) {
                    this.value9621 = value9621;
                }
            }
        }
    }
}
