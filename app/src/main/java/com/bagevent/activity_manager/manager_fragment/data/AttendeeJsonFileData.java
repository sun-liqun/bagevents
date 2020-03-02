package com.bagevent.activity_manager.manager_fragment.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2017/9/11.
 * 用于首次同步参会人员文件
 */

public class AttendeeJsonFileData {


    private int retStatus;
    private RespObjectBean respObject;
    private int respType;

    public AttendeeJsonFileData(JsonObject jsonObject) {
        retStatus = jsonObject.get("retStatus").getAsInt();
        respType = jsonObject.get("respType").getAsInt();
        respObject = new RespObjectBean(jsonObject.getAsJsonObject("respObject"));
    }

    public int getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(int retStatus) {
        this.retStatus = retStatus;
    }

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

    public static class RespObjectBean {

        private long currentTime;
        private List<ApiAttendeesBean> apiAttendees;

        public RespObjectBean(JsonObject jsonObject) {
            currentTime = jsonObject.get("currentTime").getAsLong();
            JsonArray jsonArray = jsonObject.getAsJsonArray("apiAttendees");
            apiAttendees = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                apiAttendees.add(new ApiAttendeesBean(jsonArray.get(i).getAsJsonObject()));
            }

        }

        public long getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(long currentTime) {
            this.currentTime = currentTime;
        }

        public List<ApiAttendeesBean> getApiAttendees() {
            return apiAttendees;
        }

        public void setApiAttendees(List<ApiAttendeesBean> apiAttendees) {
            this.apiAttendees = apiAttendees;
        }

        public static class ApiAttendeesBean {
            private String attendeeId;
            private String orderId;
            private String ticketId;
            private String barcode;
            private String refCode;
            private String checkinCode;
            private String checkin;
            private String payStatus;
            private String audit;
            private String checkinTime;
            private String updateTime;
            private String emailAddr;
            private String cellphone;
            private String name;
            private String firstName;
            private String lastName;
            private String pinyinName;
            private String weixinId;
            private String auditTime;
            private String avatar;
            private String notes;
            private String buyWay;
            private float ticketPrice;
            private String ticketName;
            private String ticketDesc;
            private String ticketAudit;
            private String orderPayStatus;
            private String attendeeMap;
            private String badgeMap;
            private String userId;
            private String attendeeAvatar;
            private String checkInAisle;
            private String giftIssuance;
            private String giftReceiveTime;
            private String signCode;
            // private ApiTicketOrderItemBean apiTicketOrderItem;
            private String attendeeNumber;
            private String attendeeType;
            private String tagName;
            private String originalTicketName;
            private String salesUserName;
            private String areaCode;
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

            public ApiAttendeesBean(JsonObject jsonObject) {
                attendeeId = jsonObject.get("attendeeId").getAsString();
                orderId = jsonObject.get("orderId").getAsString();
                ticketId = jsonObject.get("ticketId").getAsString();
                barcode = jsonObject.get("barcode").getAsString();
                refCode = jsonObject.get("refCode").getAsString();
                checkinCode = jsonObject.get("checkinCode").getAsString();
                checkin = jsonObject.get("checkin").getAsString();
                payStatus = jsonObject.get("payStatus").getAsString();
                audit = jsonObject.get("audit").getAsString();
                checkinTime = jsonObject.get("checkinTime").getAsString();
                updateTime = jsonObject.get("updateTime").getAsString();
                emailAddr = jsonObject.get("emailAddr").getAsString();
                cellphone = jsonObject.get("cellphone").getAsString();
                name = jsonObject.get("name").getAsString();
                firstName = jsonObject.get("firstName").getAsString();
                lastName = jsonObject.get("lastName").getAsString();
                pinyinName = jsonObject.get("pinyinName").getAsString();
                weixinId = jsonObject.get("weixinId").getAsString();
                auditTime = jsonObject.get("auditTime").getAsString();
                avatar = jsonObject.get("avatar").getAsString();
                notes = jsonObject.get("notes").getAsString();
                buyWay = jsonObject.get("buyWay").getAsString();
                ticketName = jsonObject.get("ticketName").getAsString();
                ticketDesc = jsonObject.get("ticketDesc").getAsString();
                ticketAudit = jsonObject.get("ticketAudit").getAsString();
                orderPayStatus = jsonObject.get("orderPayStatus").getAsString();
                userId = jsonObject.get("userId").getAsString();
                attendeeAvatar = jsonObject.get("attendeeAvatar").getAsString();
                checkInAisle = jsonObject.get("checkInAisle").getAsString();
                giftIssuance = jsonObject.get("giftIssuance").getAsString();
                giftReceiveTime = jsonObject.get("giftReceiveTime").getAsString();
                signCode = jsonObject.get("signCode").getAsString();
                attendeeNumber = jsonObject.get("attendeeNumber").getAsString();
                attendeeType = jsonObject.get("attendeeType").getAsString();
                tagName = jsonObject.get("tagName").getAsString();
                originalTicketName = jsonObject.get("originalTicketName").getAsString();
                salesUserName = jsonObject.get("salesUserName").getAsString();
                areaCode = jsonObject.get("areaCode").getAsString();
                productIds = jsonObject.get("productIds").getAsString();
                hasBuyingGrouping = jsonObject.get("hasBuyingGrouping").getAsBoolean();
                bgState = jsonObject.get("bgState").getAsInt();
                buyingGroupId = jsonObject.get("buyingGroupId").getAsInt();

                try {
                    ticketPrice = jsonObject.get("ticketPrice").getAsFloat();
                } catch (Exception e) {
                }

                if (jsonObject.get("attendeeMap").isJsonObject()) {
                    attendeeMap = jsonObject.get("attendeeMap").getAsJsonObject().toString();
                } else {
                    attendeeMap = "";
                }

                if (jsonObject.get("badgeMap").isJsonObject()) {
                    badgeMap = jsonObject.get("badgeMap").getAsJsonObject().toString();
                } else {
                    badgeMap = "";
                }
            }


            public String getAttendeeId() {
                return attendeeId;
            }

            public void setAttendeeId(String attendeeId) {
                this.attendeeId = attendeeId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getTicketId() {
                return ticketId;
            }

            public void setTicketId(String ticketId) {
                this.ticketId = ticketId;
            }

            public String getBarcode() {
                return barcode;
            }

            public void setBarcode(String barcode) {
                this.barcode = barcode;
            }

            public String getRefCode() {
                return refCode;
            }

            public void setRefCode(String refCode) {
                this.refCode = refCode;
            }

            public String getCheckinCode() {
                return checkinCode;
            }

            public void setCheckinCode(String checkinCode) {
                this.checkinCode = checkinCode;
            }

            public String getCheckin() {
                return checkin;
            }

            public void setCheckin(String checkin) {
                this.checkin = checkin;
            }

            public String getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(String payStatus) {
                this.payStatus = payStatus;
            }

            public String getAudit() {
                return audit;
            }

            public void setAudit(String audit) {
                this.audit = audit;
            }

            public String getCheckinTime() {
                return checkinTime;
            }

            public void setCheckinTime(String checkinTime) {
                this.checkinTime = checkinTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getEmailAddr() {
                return emailAddr;
            }

            public void setEmailAddr(String emailAddr) {
                this.emailAddr = emailAddr;
            }

            public String getAttendeeMap() {
                return attendeeMap;
            }

            public String getBadgeMap() {
                return badgeMap;
            }

            public String getCellphone() {
                return cellphone;
            }

            public void setCellphone(String cellphone) {
                this.cellphone = cellphone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getPinyinName() {
                return pinyinName;
            }

            public void setPinyinName(String pinyinName) {
                this.pinyinName = pinyinName;
            }

            public String getWeixinId() {
                return weixinId;
            }

            public void setWeixinId(String weixinId) {
                this.weixinId = weixinId;
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

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public String getBuyWay() {
                return buyWay;
            }

            public void setBuyWay(String buyWay) {
                this.buyWay = buyWay;
            }

            public float getTicketPrice() {
                return ticketPrice;
            }

            public void setTicketPrice(float ticketPrice) {
                this.ticketPrice = ticketPrice;
            }

            public String getTicketName() {
                return ticketName;
            }

            public void setTicketName(String ticketName) {
                this.ticketName = ticketName;
            }

            public String getTicketDesc() {
                return ticketDesc;
            }

            public void setTicketDesc(String ticketDesc) {
                this.ticketDesc = ticketDesc;
            }

            public String getTicketAudit() {
                return ticketAudit;
            }

            public void setTicketAudit(String ticketAudit) {
                this.ticketAudit = ticketAudit;
            }

            public String getOrderPayStatus() {
                return orderPayStatus;
            }

            public void setOrderPayStatus(String orderPayStatus) {
                this.orderPayStatus = orderPayStatus;
            }


            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getAttendeeAvatar() {
                return attendeeAvatar;
            }

            public void setAttendeeAvatar(String attendeeAvatar) {
                this.attendeeAvatar = attendeeAvatar;
            }

            public String getCheckInAisle() {
                return checkInAisle;
            }

            public void setCheckInAisle(String checkInAisle) {
                this.checkInAisle = checkInAisle;
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

            public String getSignCode() {
                return signCode;
            }

            public void setSignCode(String signCode) {
                this.signCode = signCode;
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

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public String getOriginalTicketName() {
                return originalTicketName;
            }

            public void setOriginalTicketName(String originalTicketName) {
                this.originalTicketName = originalTicketName;
            }

            public String getSalesUserName() {
                return salesUserName;
            }

            public void setSalesUserName(String salesUserName) {
                this.salesUserName = salesUserName;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getProductIds() {
                return productIds;
            }

            public void setProductIds(String productIds) {
                this.productIds = productIds;
            }

            public static class AttendeeMapBean {
                /**
                 * 108102 : Barrowman
                 * 108103 : John
                 * 108099 : 1224756536@qq.com
                 * 108100 : 15651628756
                 * 108101 : Allmilmo-Zeiler Mobelwerk GmbH &amp; Co. KG
                 * 122165 : 中国
                 * 122166 : /resource/20180105/16560818839923.pdf
                 * 122168 : 2018-02-27
                 * 122169 :
                 * 122170 : /resource/20171226/13390521039923.xls
                 */

                @SerializedName("108102")
                private String _$108102;
                @SerializedName("108103")
                private String _$108103;
                @SerializedName("108099")
                private String _$108099;
                @SerializedName("108100")
                private String _$108100;
                @SerializedName("108101")
                private String _$108101;
                @SerializedName("122165")
                private String _$122165;
                @SerializedName("122166")
                private String _$122166;
                @SerializedName("122168")
                private String _$122168;
                @SerializedName("122169")
                private String _$122169;
                @SerializedName("122170")
                private String _$122170;

                public String get_$108102() {
                    return _$108102;
                }

                public void set_$108102(String _$108102) {
                    this._$108102 = _$108102;
                }

                public String get_$108103() {
                    return _$108103;
                }

                public void set_$108103(String _$108103) {
                    this._$108103 = _$108103;
                }

                public String get_$108099() {
                    return _$108099;
                }

                public void set_$108099(String _$108099) {
                    this._$108099 = _$108099;
                }

                public String get_$108100() {
                    return _$108100;
                }

                public void set_$108100(String _$108100) {
                    this._$108100 = _$108100;
                }

                public String get_$108101() {
                    return _$108101;
                }

                public void set_$108101(String _$108101) {
                    this._$108101 = _$108101;
                }

                public String get_$122165() {
                    return _$122165;
                }

                public void set_$122165(String _$122165) {
                    this._$122165 = _$122165;
                }

                public String get_$122166() {
                    return _$122166;
                }

                public void set_$122166(String _$122166) {
                    this._$122166 = _$122166;
                }

                public String get_$122168() {
                    return _$122168;
                }

                public void set_$122168(String _$122168) {
                    this._$122168 = _$122168;
                }

                public String get_$122169() {
                    return _$122169;
                }

                public void set_$122169(String _$122169) {
                    this._$122169 = _$122169;
                }

                public String get_$122170() {
                    return _$122170;
                }

                public void set_$122170(String _$122170) {
                    this._$122170 = _$122170;
                }
            }

            public static class BadgeMapBean {
                /**
                 * 108105 : 唐
                 * 108106 : 嫣
                 * 108104 : 江苏南京
                 * 112088 : cto
                 * 114487 : 1224756536@qq.com
                 * 119746 : 男
                 * 122171 : 威锋网
                 * 122172 : 为发热
                 * 122173 : /resource/20171229/15590412839923.xls
                 * 122174 : /resource/20171229/15591523039923.xls
                 * 122175 : 0
                 * 122176 : 2018-01-28
                 */

                @SerializedName("108105")
                private String _$108105;
                @SerializedName("108106")
                private String _$108106;
                @SerializedName("108104")
                private String _$108104;
                @SerializedName("112088")
                private String _$112088;
                @SerializedName("114487")
                private String _$114487;
                @SerializedName("119746")
                private String _$119746;
                @SerializedName("122171")
                private String _$122171;
                @SerializedName("122172")
                private String _$122172;
                @SerializedName("122173")
                private String _$122173;
                @SerializedName("122174")
                private String _$122174;
                @SerializedName("122175")
                private String _$122175;
                @SerializedName("122176")
                private String _$122176;

                public String get_$108105() {
                    return _$108105;
                }

                public void set_$108105(String _$108105) {
                    this._$108105 = _$108105;
                }

                public String get_$108106() {
                    return _$108106;
                }

                public void set_$108106(String _$108106) {
                    this._$108106 = _$108106;
                }

                public String get_$108104() {
                    return _$108104;
                }

                public void set_$108104(String _$108104) {
                    this._$108104 = _$108104;
                }

                public String get_$112088() {
                    return _$112088;
                }

                public void set_$112088(String _$112088) {
                    this._$112088 = _$112088;
                }

                public String get_$114487() {
                    return _$114487;
                }

                public void set_$114487(String _$114487) {
                    this._$114487 = _$114487;
                }

                public String get_$119746() {
                    return _$119746;
                }

                public void set_$119746(String _$119746) {
                    this._$119746 = _$119746;
                }

                public String get_$122171() {
                    return _$122171;
                }

                public void set_$122171(String _$122171) {
                    this._$122171 = _$122171;
                }

                public String get_$122172() {
                    return _$122172;
                }

                public void set_$122172(String _$122172) {
                    this._$122172 = _$122172;
                }

                public String get_$122173() {
                    return _$122173;
                }

                public void set_$122173(String _$122173) {
                    this._$122173 = _$122173;
                }

                public String get_$122174() {
                    return _$122174;
                }

                public void set_$122174(String _$122174) {
                    this._$122174 = _$122174;
                }

                public String get_$122175() {
                    return _$122175;
                }

                public void set_$122175(String _$122175) {
                    this._$122175 = _$122175;
                }

                public String get_$122176() {
                    return _$122176;
                }

                public void set_$122176(String _$122176) {
                    this._$122176 = _$122176;
                }
            }

        }
    }
}
