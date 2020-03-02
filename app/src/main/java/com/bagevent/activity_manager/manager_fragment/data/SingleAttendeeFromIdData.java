package com.bagevent.activity_manager.manager_fragment.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZWJ on 2017/12/19 0019.
 */

public class SingleAttendeeFromIdData {

    /**
     * respObject : {"formFields":{"107770":"姓名","107771":"邮箱","107772":"手机号","108821":"公司名称","111282":"图像采集","111283":"身份证","111284":"日期","111286":"家庭电话","112330":"职位","112331":"性别","112332":"地区","112333":"家庭住址","112334":"个人网站","112335":"公司网站","112336":"普通文本框","112337":"多行文本框","112339":"单选值","112340":"多选值","112342":"文件","112343":"省/市","116673":"下拉选择框","116680":"人脸识别","120448":"护照"},"attendeeInfo":{"apiTicketOrderItem":{"currentTicketPrice":0.01,"discountId":0,"discountPrice":0,"gift":0,"memberDiscountPrice":0,"orderItemId":1949815,"ticketFee":0,"ticketId":26372,"ticketOrderId":1486380,"ticketPrice":0.01},"areaCode":"+86","attendeeAvatar":"","attendeeId":1136440,"attendeeMap":{"107770":"aa","107771":"1224756536@qq.com","107772":"","108821":"","111282":"","111283":"","111284":"2017-12-15","111286":"","112330":"","112331":"","112332":"","112333":"","112334":"","112335":"","112336":"","112337":"","112339":"","112340":"","112342":"","112343":"山西省运城市","116673":"","116680":"","120448":""},"attendeeNumber":0,"attendeeType":0,"audit":0,"auditTime":"","avatar":"","badgeMap":null,"barcode":"6010606905070080028","buyWay":1,"cellphone":"","checkInAisle":"","checkin":1,"checkinCode":"248568","checkinTime":"2017-12-14 17:21","emailAddr":"1224756536@qq.com","firstName":"","giftIssuance":0,"giftReceiveTime":"","lastName":"","name":"aa","notes":"45616","orderId":1486380,"orderPayStatus":0,"originalTicketName":"","payStatus":1,"pinyinName":"aa","productIds":"","refCode":"","salesUserName":"","signCode":"","tagName":"","ticketAudit":0,"ticketDesc":"","ticketId":26372,"ticketName":"","ticketPrice":0,"updateTime":"2017-12-18 15:05","userId":0,"weixinId":""}}
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
         * formFields : {"107770":"姓名","107771":"邮箱","107772":"手机号","108821":"公司名称","111282":"图像采集","111283":"身份证","111284":"日期","111286":"家庭电话","112330":"职位","112331":"性别","112332":"地区","112333":"家庭住址","112334":"个人网站","112335":"公司网站","112336":"普通文本框","112337":"多行文本框","112339":"单选值","112340":"多选值","112342":"文件","112343":"省/市","116673":"下拉选择框","116680":"人脸识别","120448":"护照"}
         * attendeeInfo : {"apiTicketOrderItem":{"currentTicketPrice":0.01,"discountId":0,"discountPrice":0,"gift":0,"memberDiscountPrice":0,"orderItemId":1949815,"ticketFee":0,"ticketId":26372,"ticketOrderId":1486380,"ticketPrice":0.01},"areaCode":"+86","attendeeAvatar":"","attendeeId":1136440,"attendeeMap":{"107770":"aa","107771":"1224756536@qq.com","107772":"","108821":"","111282":"","111283":"","111284":"2017-12-15","111286":"","112330":"","112331":"","112332":"","112333":"","112334":"","112335":"","112336":"","112337":"","112339":"","112340":"","112342":"","112343":"山西省运城市","116673":"","116680":"","120448":""},"attendeeNumber":0,"attendeeType":0,"audit":0,"auditTime":"","avatar":"","badgeMap":null,"barcode":"6010606905070080028","buyWay":1,"cellphone":"","checkInAisle":"","checkin":1,"checkinCode":"248568","checkinTime":"2017-12-14 17:21","emailAddr":"1224756536@qq.com","firstName":"","giftIssuance":0,"giftReceiveTime":"","lastName":"","name":"aa","notes":"45616","orderId":1486380,"orderPayStatus":0,"originalTicketName":"","payStatus":1,"pinyinName":"aa","productIds":"","refCode":"","salesUserName":"","signCode":"","tagName":"","ticketAudit":0,"ticketDesc":"","ticketId":26372,"ticketName":"","ticketPrice":0,"updateTime":"2017-12-18 15:05","userId":0,"weixinId":""}
         */

        private FormFieldsBean formFields;
        private AttendeeInfoBean attendeeInfo;

        public FormFieldsBean getFormFields() {
            return formFields;
        }

        public void setFormFields(FormFieldsBean formFields) {
            this.formFields = formFields;
        }

        public AttendeeInfoBean getAttendeeInfo() {
            return attendeeInfo;
        }

        public void setAttendeeInfo(AttendeeInfoBean attendeeInfo) {
            this.attendeeInfo = attendeeInfo;
        }

        public static class FormFieldsBean {
            /**
             * 107770 : 姓名
             * 107771 : 邮箱
             * 107772 : 手机号
             * 108821 : 公司名称
             * 111282 : 图像采集
             * 111283 : 身份证
             * 111284 : 日期
             * 111286 : 家庭电话
             * 112330 : 职位
             * 112331 : 性别
             * 112332 : 地区
             * 112333 : 家庭住址
             * 112334 : 个人网站
             * 112335 : 公司网站
             * 112336 : 普通文本框
             * 112337 : 多行文本框
             * 112339 : 单选值
             * 112340 : 多选值
             * 112342 : 文件
             * 112343 : 省/市
             * 116673 : 下拉选择框
             * 116680 : 人脸识别
             * 120448 : 护照
             */

            @SerializedName("107770")
            private String _$107770;
            @SerializedName("107771")
            private String _$107771;
            @SerializedName("107772")
            private String _$107772;
            @SerializedName("108821")
            private String _$108821;
            @SerializedName("111282")
            private String _$111282;
            @SerializedName("111283")
            private String _$111283;
            @SerializedName("111284")
            private String _$111284;
            @SerializedName("111286")
            private String _$111286;
            @SerializedName("112330")
            private String _$112330;
            @SerializedName("112331")
            private String _$112331;
            @SerializedName("112332")
            private String _$112332;
            @SerializedName("112333")
            private String _$112333;
            @SerializedName("112334")
            private String _$112334;
            @SerializedName("112335")
            private String _$112335;
            @SerializedName("112336")
            private String _$112336;
            @SerializedName("112337")
            private String _$112337;
            @SerializedName("112339")
            private String _$112339;
            @SerializedName("112340")
            private String _$112340;
            @SerializedName("112342")
            private String _$112342;
            @SerializedName("112343")
            private String _$112343;
            @SerializedName("116673")
            private String _$116673;
            @SerializedName("116680")
            private String _$116680;
            @SerializedName("120448")
            private String _$120448;

            public String get_$107770() {
                return _$107770;
            }

            public void set_$107770(String _$107770) {
                this._$107770 = _$107770;
            }

            public String get_$107771() {
                return _$107771;
            }

            public void set_$107771(String _$107771) {
                this._$107771 = _$107771;
            }

            public String get_$107772() {
                return _$107772;
            }

            public void set_$107772(String _$107772) {
                this._$107772 = _$107772;
            }

            public String get_$108821() {
                return _$108821;
            }

            public void set_$108821(String _$108821) {
                this._$108821 = _$108821;
            }

            public String get_$111282() {
                return _$111282;
            }

            public void set_$111282(String _$111282) {
                this._$111282 = _$111282;
            }

            public String get_$111283() {
                return _$111283;
            }

            public void set_$111283(String _$111283) {
                this._$111283 = _$111283;
            }

            public String get_$111284() {
                return _$111284;
            }

            public void set_$111284(String _$111284) {
                this._$111284 = _$111284;
            }

            public String get_$111286() {
                return _$111286;
            }

            public void set_$111286(String _$111286) {
                this._$111286 = _$111286;
            }

            public String get_$112330() {
                return _$112330;
            }

            public void set_$112330(String _$112330) {
                this._$112330 = _$112330;
            }

            public String get_$112331() {
                return _$112331;
            }

            public void set_$112331(String _$112331) {
                this._$112331 = _$112331;
            }

            public String get_$112332() {
                return _$112332;
            }

            public void set_$112332(String _$112332) {
                this._$112332 = _$112332;
            }

            public String get_$112333() {
                return _$112333;
            }

            public void set_$112333(String _$112333) {
                this._$112333 = _$112333;
            }

            public String get_$112334() {
                return _$112334;
            }

            public void set_$112334(String _$112334) {
                this._$112334 = _$112334;
            }

            public String get_$112335() {
                return _$112335;
            }

            public void set_$112335(String _$112335) {
                this._$112335 = _$112335;
            }

            public String get_$112336() {
                return _$112336;
            }

            public void set_$112336(String _$112336) {
                this._$112336 = _$112336;
            }

            public String get_$112337() {
                return _$112337;
            }

            public void set_$112337(String _$112337) {
                this._$112337 = _$112337;
            }

            public String get_$112339() {
                return _$112339;
            }

            public void set_$112339(String _$112339) {
                this._$112339 = _$112339;
            }

            public String get_$112340() {
                return _$112340;
            }

            public void set_$112340(String _$112340) {
                this._$112340 = _$112340;
            }

            public String get_$112342() {
                return _$112342;
            }

            public void set_$112342(String _$112342) {
                this._$112342 = _$112342;
            }

            public String get_$112343() {
                return _$112343;
            }

            public void set_$112343(String _$112343) {
                this._$112343 = _$112343;
            }

            public String get_$116673() {
                return _$116673;
            }

            public void set_$116673(String _$116673) {
                this._$116673 = _$116673;
            }

            public String get_$116680() {
                return _$116680;
            }

            public void set_$116680(String _$116680) {
                this._$116680 = _$116680;
            }

            public String get_$120448() {
                return _$120448;
            }

            public void set_$120448(String _$120448) {
                this._$120448 = _$120448;
            }
        }

        public static class AttendeeInfoBean {
            /**
             * apiTicketOrderItem : {"currentTicketPrice":0.01,"discountId":0,"discountPrice":0,"gift":0,"memberDiscountPrice":0,"orderItemId":1949815,"ticketFee":0,"ticketId":26372,"ticketOrderId":1486380,"ticketPrice":0.01}
             * areaCode : +86
             * attendeeAvatar :
             * attendeeId : 1136440
             * attendeeMap : {"107770":"aa","107771":"1224756536@qq.com","107772":"","108821":"","111282":"","111283":"","111284":"2017-12-15","111286":"","112330":"","112331":"","112332":"","112333":"","112334":"","112335":"","112336":"","112337":"","112339":"","112340":"","112342":"","112343":"山西省运城市","116673":"","116680":"","120448":""}
             * attendeeNumber : 0
             * attendeeType : 0
             * audit : 0
             * auditTime :
             * avatar :
             * badgeMap : null
             * barcode : 6010606905070080028
             * buyWay : 1
             * cellphone :
             * checkInAisle :
             * checkin : 1
             * checkinCode : 248568
             * checkinTime : 2017-12-14 17:21
             * emailAddr : 1224756536@qq.com
             * firstName :
             * giftIssuance : 0
             * giftReceiveTime :
             * lastName :
             * name : aa
             * notes : 45616
             * orderId : 1486380
             * orderPayStatus : 0
             * originalTicketName :
             * payStatus : 1
             * pinyinName : aa
             * productIds :
             * refCode :
             * salesUserName :
             * signCode :
             * tagName :
             * ticketAudit : 0
             * ticketDesc :
             * ticketId : 26372
             * ticketName :
             * ticketPrice : 0
             * updateTime : 2017-12-18 15:05
             * userId : 0
             * weixinId :
             */

            private ApiTicketOrderItemBean apiTicketOrderItem;
            private String areaCode;
            private String attendeeAvatar;
            private String attendeeId;
            private AttendeeMapBean attendeeMap;
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

            public ApiTicketOrderItemBean getApiTicketOrderItem() {
                return apiTicketOrderItem;
            }

            public void setApiTicketOrderItem(ApiTicketOrderItemBean apiTicketOrderItem) {
                this.apiTicketOrderItem = apiTicketOrderItem;
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

            public AttendeeMapBean getAttendeeMap() {
                return attendeeMap;
            }

            public void setAttendeeMap(AttendeeMapBean attendeeMap) {
                this.attendeeMap = attendeeMap;
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

            public static class ApiTicketOrderItemBean {
                /**
                 * currentTicketPrice : 0.01
                 * discountId : 0
                 * discountPrice : 0
                 * gift : 0
                 * memberDiscountPrice : 0
                 * orderItemId : 1949815
                 * ticketFee : 0
                 * ticketId : 26372
                 * ticketOrderId : 1486380
                 * ticketPrice : 0.01
                 */

                private float currentTicketPrice;
                private int discountId;
                private float discountPrice;
                private int gift;
                private float memberDiscountPrice;
                private int orderItemId;
                private int ticketFee;
                private int ticketId;
                private int ticketOrderId;
                private float ticketPrice;

                public float getCurrentTicketPrice() {
                    return currentTicketPrice;
                }

                public void setCurrentTicketPrice(float currentTicketPrice) {
                    this.currentTicketPrice = currentTicketPrice;
                }

                public int getDiscountId() {
                    return discountId;
                }

                public void setDiscountId(int discountId) {
                    this.discountId = discountId;
                }

                public float getDiscountPrice() {
                    return discountPrice;
                }

                public void setDiscountPrice(float discountPrice) {
                    this.discountPrice = discountPrice;
                }

                public int getGift() {
                    return gift;
                }

                public void setGift(int gift) {
                    this.gift = gift;
                }

                public float getMemberDiscountPrice() {
                    return memberDiscountPrice;
                }

                public void setMemberDiscountPrice(float memberDiscountPrice) {
                    this.memberDiscountPrice = memberDiscountPrice;
                }

                public int getOrderItemId() {
                    return orderItemId;
                }

                public void setOrderItemId(int orderItemId) {
                    this.orderItemId = orderItemId;
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

                public int getTicketOrderId() {
                    return ticketOrderId;
                }

                public void setTicketOrderId(int ticketOrderId) {
                    this.ticketOrderId = ticketOrderId;
                }

                public float getTicketPrice() {
                    return ticketPrice;
                }

                public void setTicketPrice(float ticketPrice) {
                    this.ticketPrice = ticketPrice;
                }
            }

            public static class AttendeeMapBean {
                /**
                 * 107770 : aa
                 * 107771 : 1224756536@qq.com
                 * 107772 :
                 * 108821 :
                 * 111282 :
                 * 111283 :
                 * 111284 : 2017-12-15
                 * 111286 :
                 * 112330 :
                 * 112331 :
                 * 112332 :
                 * 112333 :
                 * 112334 :
                 * 112335 :
                 * 112336 :
                 * 112337 :
                 * 112339 :
                 * 112340 :
                 * 112342 :
                 * 112343 : 山西省运城市
                 * 116673 :
                 * 116680 :
                 * 120448 :
                 */

                @SerializedName("107770")
                private String _$107770;
                @SerializedName("107771")
                private String _$107771;
                @SerializedName("107772")
                private String _$107772;
                @SerializedName("108821")
                private String _$108821;
                @SerializedName("111282")
                private String _$111282;
                @SerializedName("111283")
                private String _$111283;
                @SerializedName("111284")
                private String _$111284;
                @SerializedName("111286")
                private String _$111286;
                @SerializedName("112330")
                private String _$112330;
                @SerializedName("112331")
                private String _$112331;
                @SerializedName("112332")
                private String _$112332;
                @SerializedName("112333")
                private String _$112333;
                @SerializedName("112334")
                private String _$112334;
                @SerializedName("112335")
                private String _$112335;
                @SerializedName("112336")
                private String _$112336;
                @SerializedName("112337")
                private String _$112337;
                @SerializedName("112339")
                private String _$112339;
                @SerializedName("112340")
                private String _$112340;
                @SerializedName("112342")
                private String _$112342;
                @SerializedName("112343")
                private String _$112343;
                @SerializedName("116673")
                private String _$116673;
                @SerializedName("116680")
                private String _$116680;
                @SerializedName("120448")
                private String _$120448;

                public String get_$107770() {
                    return _$107770;
                }

                public void set_$107770(String _$107770) {
                    this._$107770 = _$107770;
                }

                public String get_$107771() {
                    return _$107771;
                }

                public void set_$107771(String _$107771) {
                    this._$107771 = _$107771;
                }

                public String get_$107772() {
                    return _$107772;
                }

                public void set_$107772(String _$107772) {
                    this._$107772 = _$107772;
                }

                public String get_$108821() {
                    return _$108821;
                }

                public void set_$108821(String _$108821) {
                    this._$108821 = _$108821;
                }

                public String get_$111282() {
                    return _$111282;
                }

                public void set_$111282(String _$111282) {
                    this._$111282 = _$111282;
                }

                public String get_$111283() {
                    return _$111283;
                }

                public void set_$111283(String _$111283) {
                    this._$111283 = _$111283;
                }

                public String get_$111284() {
                    return _$111284;
                }

                public void set_$111284(String _$111284) {
                    this._$111284 = _$111284;
                }

                public String get_$111286() {
                    return _$111286;
                }

                public void set_$111286(String _$111286) {
                    this._$111286 = _$111286;
                }

                public String get_$112330() {
                    return _$112330;
                }

                public void set_$112330(String _$112330) {
                    this._$112330 = _$112330;
                }

                public String get_$112331() {
                    return _$112331;
                }

                public void set_$112331(String _$112331) {
                    this._$112331 = _$112331;
                }

                public String get_$112332() {
                    return _$112332;
                }

                public void set_$112332(String _$112332) {
                    this._$112332 = _$112332;
                }

                public String get_$112333() {
                    return _$112333;
                }

                public void set_$112333(String _$112333) {
                    this._$112333 = _$112333;
                }

                public String get_$112334() {
                    return _$112334;
                }

                public void set_$112334(String _$112334) {
                    this._$112334 = _$112334;
                }

                public String get_$112335() {
                    return _$112335;
                }

                public void set_$112335(String _$112335) {
                    this._$112335 = _$112335;
                }

                public String get_$112336() {
                    return _$112336;
                }

                public void set_$112336(String _$112336) {
                    this._$112336 = _$112336;
                }

                public String get_$112337() {
                    return _$112337;
                }

                public void set_$112337(String _$112337) {
                    this._$112337 = _$112337;
                }

                public String get_$112339() {
                    return _$112339;
                }

                public void set_$112339(String _$112339) {
                    this._$112339 = _$112339;
                }

                public String get_$112340() {
                    return _$112340;
                }

                public void set_$112340(String _$112340) {
                    this._$112340 = _$112340;
                }

                public String get_$112342() {
                    return _$112342;
                }

                public void set_$112342(String _$112342) {
                    this._$112342 = _$112342;
                }

                public String get_$112343() {
                    return _$112343;
                }

                public void set_$112343(String _$112343) {
                    this._$112343 = _$112343;
                }

                public String get_$116673() {
                    return _$116673;
                }

                public void set_$116673(String _$116673) {
                    this._$116673 = _$116673;
                }

                public String get_$116680() {
                    return _$116680;
                }

                public void set_$116680(String _$116680) {
                    this._$116680 = _$116680;
                }

                public String get_$120448() {
                    return _$120448;
                }

                public void set_$120448(String _$120448) {
                    this._$120448 = _$120448;
                }
            }
        }
    }
}
