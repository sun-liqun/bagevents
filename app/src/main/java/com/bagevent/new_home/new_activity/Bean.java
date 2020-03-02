package com.bagevent.new_home.new_activity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/11
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class Bean {

    /**
     * retStatus : 200
     * nextPage :
     * resultObject : 查询成功！
     * responseType : 0
     * respObject : {"tagList":[{"tagId":3,"name":"得到的","sort":0},{"tagId":4,"name":"reed","sort":0}],"apiAttendee":{"attendeeId":2228281,"orderId":3078904,"ticketId":64567,"barcode":"10205252299040898409","refCode":"","checkinCode":"001197","checkin":0,"payStatus":1,"audit":0,"checkinTime":"","updateTime":"2019-02-01","emailAddr":"upon_ai@outlook.com","cellphone":"","name":"Joe upon","firstName":"","lastName":"","pinyinName":"Joe upon","weixinId":"","auditTime":"","avatar":"","notes":"","buyWay":0,"ticketPrice":0.02,"ticketName":"门票","ticketDesc":"","ticketAudit":"","orderPayStatus":"","attendeeMap":{"263706":"Joe upon","263707":"upon_ai@outlook.com","263708":"","263830":"","263831":"","263832":"","263833":"","263834":"","263835":""},"badgeMap":"","userId":"","attendeeAvatar":"","checkInAisle":"","giftIssuance":0,"giftReceiveTime":"","signCode":"","apiTicketOrderItem":{"orderItemId":3900456,"discountId":"","ticketOrderId":3078904,"ticketId":64567,"ticketPrice":0.02,"currentTicketPrice":0.02,"ticketFee":0,"discountPrice":0,"memberDiscountPrice":0,"gift":0},"attendeeNumber":0,"attendeeType":0,"tagName":"","originalTicketName":"","salesUserName":"","areaCode":"+11","productIds":"","wxTicketQrCodeUrl":"","currencySign":"￥","modifyFormDataCount":0,"thirdAuditState":0,"hasDiscount":0,"discountCode":"","discountRemark":"","discountId":"","checkInEquipment":"","formDataId":"","badgeFormDataId":"","orderItemId":"","sourceLocale":"zh_CN","editAttendeeKey":"66430e7c2415715bde82ffe1f764f8f1","formDataDTO":"","realStartEntryTime":"","realEndEntryTime":"","hasBuyingGrouping":false,"bgState":"","buyingGroupId":""},"formFieldList":[{"formFieldId":263706,"field":{"fieldId":2,"fieldName":"username","fieldType":"0","fieldGenre":1,"showName":"姓名","maxLen":100,"defState":0,"fieldSort":1,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":0,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true},"fieldName":"username","required":1,"sysOption":0,"regexpValidation":"","showName":"姓名","brief":"","options":"","multiOptions":"","sort":1,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":1,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"姓名","showOptions":"","showMultiOptions":"","enFieldName":"username","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263707,"field":{"fieldId":4,"fieldName":"email_address","fieldType":"0","fieldGenre":3,"showName":"邮箱","maxLen":100,"defState":0,"fieldSort":2,"reserveField":0,"regexpValidation":"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$","fieldIcon":"","usedState":0,"abstractUsed":1,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true},"fieldName":"email_address","required":1,"sysOption":0,"regexpValidation":"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$","showName":"邮箱","brief":"","options":"","multiOptions":"","sort":2,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":1,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"邮箱","showOptions":"","showMultiOptions":"","enFieldName":"email_address","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263708,"field":{"fieldId":5,"fieldName":"mobile_phone","fieldType":"0","fieldGenre":4,"showName":"手机号","maxLen":30,"defState":1,"fieldSort":3,"reserveField":0,"regexpValidation":"^(13|15|18|14|17|16|19)[0-9]{9}$","fieldIcon":"","usedState":0,"abstractUsed":1,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true},"fieldName":"mobile_phone","required":0,"sysOption":0,"regexpValidation":"^[0-9－\\- +]+$","showName":"手机号","brief":"","options":"","multiOptions":"","sort":3,"maxLen":30,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":1,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"手机号","showOptions":"","showMultiOptions":"","enFieldName":"mobile_phone","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263830,"field":{"fieldId":8,"fieldName":"company","fieldType":"0","fieldGenre":5,"showName":"公司/单位","maxLen":100,"defState":2,"fieldSort":4,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":1,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true},"fieldName":"company","required":0,"sysOption":0,"regexpValidation":"","showName":"公司/单位","brief":"","options":"","multiOptions":"","sort":4,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":0,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"公司/单位","showOptions":"","showMultiOptions":"","enFieldName":"company","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263831,"field":{"fieldId":7,"fieldName":"title","fieldType":"0","fieldGenre":6,"showName":"职位","maxLen":100,"defState":2,"fieldSort":5,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":1,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true},"fieldName":"title","required":0,"sysOption":0,"regexpValidation":"","showName":"职位","brief":"","options":"","multiOptions":"","sort":5,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":0,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"职位","showOptions":"","showMultiOptions":"","enFieldName":"title","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263832,"field":{"fieldId":3,"fieldName":"home_phone","fieldType":"0","fieldGenre":13,"showName":"家庭电话","maxLen":100,"defState":2,"fieldSort":6,"reserveField":0,"regexpValidation":"^[0-9－\\- +]+$","fieldIcon":"","usedState":0,"abstractUsed":0,"defaultOptions":"","showState":0,"formType":"1,2,3","showType":0,"reserve":true},"fieldName":"home_phone","required":0,"sysOption":0,"regexpValidation":"^[0-9－\\- +]+$","showName":"家庭电话","brief":"","options":"","multiOptions":"","sort":6,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":0,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"家庭电话","showOptions":"","showMultiOptions":"","enFieldName":"home_phone","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263833,"field":{"fieldId":36,"fieldName":"IDCard","fieldType":"0","fieldGenre":16,"showName":"身份证","maxLen":100,"defState":2,"fieldSort":16,"reserveField":0,"regexpValidation":"^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$","fieldIcon":"","usedState":0,"abstractUsed":0,"defaultOptions":"","showState":0,"formType":"1,2,3,4","showType":0,"reserve":true},"fieldName":"IDCard","required":0,"sysOption":0,"regexpValidation":"^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$","showName":"身份证","brief":"","options":"","multiOptions":"","sort":7,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":1,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"身份证","showOptions":"","showMultiOptions":"","enFieldName":"IDCard","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263834,"field":{"fieldId":38,"fieldName":"attendee_avatar","fieldType":"0","fieldGenre":15,"showName":"图像采集","maxLen":100,"defState":2,"fieldSort":15,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":0,"defaultOptions":"","showState":0,"formType":"1,2,3,4","showType":0,"reserve":true},"fieldName":"attendee_avatar","required":0,"sysOption":0,"regexpValidation":"","showName":"图像采集","brief":"","options":"","multiOptions":"","sort":8,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":0,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"图像采集","showOptions":"","showMultiOptions":"","enFieldName":"attendee_avatar","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263835,"field":{"fieldId":6,"fieldName":"address","fieldType":"0","fieldGenre":12,"showName":"家庭住址","maxLen":100,"defState":2,"fieldSort":7,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":1,"defaultOptions":"","showState":0,"formType":"1,2,3","showType":0,"reserve":true},"fieldName":"address","required":0,"sysOption":0,"regexpValidation":"","showName":"家庭住址","brief":"","options":"","multiOptions":"","sort":9,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":0,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"家庭住址","showOptions":"","showMultiOptions":"","enFieldName":"address","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""}]}
     * respType : 0
     * respData :
     * success : true
     * failureRetStatus : 200
     */

    private int retStatus;
    private String nextPage;
    private String resultObject;
    private int responseType;
    private RespObjectBean respObject;
    private int respType;
    private String respData;
    private boolean success;
    private int failureRetStatus;

    public int getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(int retStatus) {
        this.retStatus = retStatus;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getResultObject() {
        return resultObject;
    }

    public void setResultObject(String resultObject) {
        this.resultObject = resultObject;
    }

    public int getResponseType() {
        return responseType;
    }

    public void setResponseType(int responseType) {
        this.responseType = responseType;
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

    public String getRespData() {
        return respData;
    }

    public void setRespData(String respData) {
        this.respData = respData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getFailureRetStatus() {
        return failureRetStatus;
    }

    public void setFailureRetStatus(int failureRetStatus) {
        this.failureRetStatus = failureRetStatus;
    }

    public static class RespObjectBean {
        /**
         * tagList : [{"tagId":3,"name":"得到的","sort":0},{"tagId":4,"name":"reed","sort":0}]
         * apiAttendee : {"attendeeId":2228281,"orderId":3078904,"ticketId":64567,"barcode":"10205252299040898409","refCode":"","checkinCode":"001197","checkin":0,"payStatus":1,"audit":0,"checkinTime":"","updateTime":"2019-02-01","emailAddr":"upon_ai@outlook.com","cellphone":"","name":"Joe upon","firstName":"","lastName":"","pinyinName":"Joe upon","weixinId":"","auditTime":"","avatar":"","notes":"","buyWay":0,"ticketPrice":0.02,"ticketName":"门票","ticketDesc":"","ticketAudit":"","orderPayStatus":"","attendeeMap":{"263706":"Joe upon","263707":"upon_ai@outlook.com","263708":"","263830":"","263831":"","263832":"","263833":"","263834":"","263835":""},"badgeMap":"","userId":"","attendeeAvatar":"","checkInAisle":"","giftIssuance":0,"giftReceiveTime":"","signCode":"","apiTicketOrderItem":{"orderItemId":3900456,"discountId":"","ticketOrderId":3078904,"ticketId":64567,"ticketPrice":0.02,"currentTicketPrice":0.02,"ticketFee":0,"discountPrice":0,"memberDiscountPrice":0,"gift":0},"attendeeNumber":0,"attendeeType":0,"tagName":"","originalTicketName":"","salesUserName":"","areaCode":"+11","productIds":"","wxTicketQrCodeUrl":"","currencySign":"￥","modifyFormDataCount":0,"thirdAuditState":0,"hasDiscount":0,"discountCode":"","discountRemark":"","discountId":"","checkInEquipment":"","formDataId":"","badgeFormDataId":"","orderItemId":"","sourceLocale":"zh_CN","editAttendeeKey":"66430e7c2415715bde82ffe1f764f8f1","formDataDTO":"","realStartEntryTime":"","realEndEntryTime":"","hasBuyingGrouping":false,"bgState":"","buyingGroupId":""}
         * formFieldList : [{"formFieldId":263706,"field":{"fieldId":2,"fieldName":"username","fieldType":"0","fieldGenre":1,"showName":"姓名","maxLen":100,"defState":0,"fieldSort":1,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":0,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true},"fieldName":"username","required":1,"sysOption":0,"regexpValidation":"","showName":"姓名","brief":"","options":"","multiOptions":"","sort":1,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":1,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"姓名","showOptions":"","showMultiOptions":"","enFieldName":"username","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263707,"field":{"fieldId":4,"fieldName":"email_address","fieldType":"0","fieldGenre":3,"showName":"邮箱","maxLen":100,"defState":0,"fieldSort":2,"reserveField":0,"regexpValidation":"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$","fieldIcon":"","usedState":0,"abstractUsed":1,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true},"fieldName":"email_address","required":1,"sysOption":0,"regexpValidation":"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$","showName":"邮箱","brief":"","options":"","multiOptions":"","sort":2,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":1,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"邮箱","showOptions":"","showMultiOptions":"","enFieldName":"email_address","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263708,"field":{"fieldId":5,"fieldName":"mobile_phone","fieldType":"0","fieldGenre":4,"showName":"手机号","maxLen":30,"defState":1,"fieldSort":3,"reserveField":0,"regexpValidation":"^(13|15|18|14|17|16|19)[0-9]{9}$","fieldIcon":"","usedState":0,"abstractUsed":1,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true},"fieldName":"mobile_phone","required":0,"sysOption":0,"regexpValidation":"^[0-9－\\- +]+$","showName":"手机号","brief":"","options":"","multiOptions":"","sort":3,"maxLen":30,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":1,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"手机号","showOptions":"","showMultiOptions":"","enFieldName":"mobile_phone","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263830,"field":{"fieldId":8,"fieldName":"company","fieldType":"0","fieldGenre":5,"showName":"公司/单位","maxLen":100,"defState":2,"fieldSort":4,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":1,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true},"fieldName":"company","required":0,"sysOption":0,"regexpValidation":"","showName":"公司/单位","brief":"","options":"","multiOptions":"","sort":4,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":0,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"公司/单位","showOptions":"","showMultiOptions":"","enFieldName":"company","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263831,"field":{"fieldId":7,"fieldName":"title","fieldType":"0","fieldGenre":6,"showName":"职位","maxLen":100,"defState":2,"fieldSort":5,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":1,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true},"fieldName":"title","required":0,"sysOption":0,"regexpValidation":"","showName":"职位","brief":"","options":"","multiOptions":"","sort":5,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":0,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"职位","showOptions":"","showMultiOptions":"","enFieldName":"title","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263832,"field":{"fieldId":3,"fieldName":"home_phone","fieldType":"0","fieldGenre":13,"showName":"家庭电话","maxLen":100,"defState":2,"fieldSort":6,"reserveField":0,"regexpValidation":"^[0-9－\\- +]+$","fieldIcon":"","usedState":0,"abstractUsed":0,"defaultOptions":"","showState":0,"formType":"1,2,3","showType":0,"reserve":true},"fieldName":"home_phone","required":0,"sysOption":0,"regexpValidation":"^[0-9－\\- +]+$","showName":"家庭电话","brief":"","options":"","multiOptions":"","sort":6,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":0,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"家庭电话","showOptions":"","showMultiOptions":"","enFieldName":"home_phone","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263833,"field":{"fieldId":36,"fieldName":"IDCard","fieldType":"0","fieldGenre":16,"showName":"身份证","maxLen":100,"defState":2,"fieldSort":16,"reserveField":0,"regexpValidation":"^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$","fieldIcon":"","usedState":0,"abstractUsed":0,"defaultOptions":"","showState":0,"formType":"1,2,3,4","showType":0,"reserve":true},"fieldName":"IDCard","required":0,"sysOption":0,"regexpValidation":"^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$","showName":"身份证","brief":"","options":"","multiOptions":"","sort":7,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":1,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"身份证","showOptions":"","showMultiOptions":"","enFieldName":"IDCard","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263834,"field":{"fieldId":38,"fieldName":"attendee_avatar","fieldType":"0","fieldGenre":15,"showName":"图像采集","maxLen":100,"defState":2,"fieldSort":15,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":0,"defaultOptions":"","showState":0,"formType":"1,2,3,4","showType":0,"reserve":true},"fieldName":"attendee_avatar","required":0,"sysOption":0,"regexpValidation":"","showName":"图像采集","brief":"","options":"","multiOptions":"","sort":8,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":0,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"图像采集","showOptions":"","showMultiOptions":"","enFieldName":"attendee_avatar","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""},{"formFieldId":263835,"field":{"fieldId":6,"fieldName":"address","fieldType":"0","fieldGenre":12,"showName":"家庭住址","maxLen":100,"defState":2,"fieldSort":7,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":1,"defaultOptions":"","showState":0,"formType":"1,2,3","showType":0,"reserve":true},"fieldName":"address","required":0,"sysOption":0,"regexpValidation":"","showName":"家庭住址","brief":"","options":"","multiOptions":"","sort":9,"maxLen":100,"tempData":"","checkRepeat":0,"optionItems":"","multiOptionItems":"","state":0,"hasOther":0,"orgDisplay":0,"exDisplay":0,"orgEdit":0,"preAdd":0,"specialTag":0,"formFieldRichTextComponent":"","preTip":"","placeHolder":"","showItemLeftCount":false,"subEvent":false,"showFieldName":"家庭住址","showOptions":"","showMultiOptions":"","enFieldName":"address","enShowName":"","showSet":true,"speechType":4,"internationalizationShowName":"","termed":false,"relation":false,"relationList":"","self":0,"displayName":"","hideTicketIds":"","relatedFormFieldId":"","showStatus":0,"organizerField":false,"superOptionItems":"","showOptionItems":""}]
         */

        private ApiAttendeeBean apiAttendee;
        private List<TagListBean> tagList;
        private List<FormFieldListBean> formFieldList;

        public ApiAttendeeBean getApiAttendee() {
            return apiAttendee;
        }

        public void setApiAttendee(ApiAttendeeBean apiAttendee) {
            this.apiAttendee = apiAttendee;
        }

        public List<TagListBean> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagListBean> tagList) {
            this.tagList = tagList;
        }

        public List<FormFieldListBean> getFormFieldList() {
            return formFieldList;
        }

        public void setFormFieldList(List<FormFieldListBean> formFieldList) {
            this.formFieldList = formFieldList;
        }

        public static class ApiAttendeeBean {
            /**
             * attendeeId : 2228281
             * orderId : 3078904
             * ticketId : 64567
             * barcode : 10205252299040898409
             * refCode :
             * checkinCode : 001197
             * checkin : 0
             * payStatus : 1
             * audit : 0
             * checkinTime :
             * updateTime : 2019-02-01
             * emailAddr : upon_ai@outlook.com
             * cellphone :
             * name : Joe upon
             * firstName :
             * lastName :
             * pinyinName : Joe upon
             * weixinId :
             * auditTime :
             * avatar :
             * notes :
             * buyWay : 0
             * ticketPrice : 0.02
             * ticketName : 门票
             * ticketDesc :
             * ticketAudit :
             * orderPayStatus :
             * attendeeMap : {"263706":"Joe upon","263707":"upon_ai@outlook.com","263708":"","263830":"","263831":"","263832":"","263833":"","263834":"","263835":""}
             * badgeMap :
             * userId :
             * attendeeAvatar :
             * checkInAisle :
             * giftIssuance : 0
             * giftReceiveTime :
             * signCode :
             * apiTicketOrderItem : {"orderItemId":3900456,"discountId":"","ticketOrderId":3078904,"ticketId":64567,"ticketPrice":0.02,"currentTicketPrice":0.02,"ticketFee":0,"discountPrice":0,"memberDiscountPrice":0,"gift":0}
             * attendeeNumber : 0
             * attendeeType : 0
             * tagName :
             * originalTicketName :
             * salesUserName :
             * areaCode : +11
             * productIds :
             * wxTicketQrCodeUrl :
             * currencySign : ￥
             * modifyFormDataCount : 0
             * thirdAuditState : 0
             * hasDiscount : 0
             * discountCode :
             * discountRemark :
             * discountId :
             * checkInEquipment :
             * formDataId :
             * badgeFormDataId :
             * orderItemId :
             * sourceLocale : zh_CN
             * editAttendeeKey : 66430e7c2415715bde82ffe1f764f8f1
             * formDataDTO :
             * realStartEntryTime :
             * realEndEntryTime :
             * hasBuyingGrouping : false
             * bgState :
             * buyingGroupId :
             */

            private int attendeeId;
            private int orderId;
            private int ticketId;
            private String barcode;
            private String refCode;
            private String checkinCode;
            private int checkin;
            private int payStatus;
            private int audit;
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
            private int buyWay;
            private double ticketPrice;
            private String ticketName;
            private String ticketDesc;
            private String ticketAudit;
            private String orderPayStatus;
            private AttendeeMapBean attendeeMap;
            private String badgeMap;
            private String userId;
            private String attendeeAvatar;
            private String checkInAisle;
            private int giftIssuance;
            private String giftReceiveTime;
            private String signCode;
            private ApiTicketOrderItemBean apiTicketOrderItem;
            private int attendeeNumber;
            private int attendeeType;
            private String tagName;
            private String originalTicketName;
            private String salesUserName;
            private String areaCode;
            private String productIds;
            private String wxTicketQrCodeUrl;
            private String currencySign;
            private int modifyFormDataCount;
            private int thirdAuditState;
            private int hasDiscount;
            private String discountCode;
            private String discountRemark;
            private String discountId;
            private String checkInEquipment;
            private String formDataId;
            private String badgeFormDataId;
            private String orderItemId;
            private String sourceLocale;
            private String editAttendeeKey;
            private String formDataDTO;
            private String realStartEntryTime;
            private String realEndEntryTime;
            private boolean hasBuyingGrouping;
            private String bgState;
            private String buyingGroupId;

            public int getAttendeeId() {
                return attendeeId;
            }

            public void setAttendeeId(int attendeeId) {
                this.attendeeId = attendeeId;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getTicketId() {
                return ticketId;
            }

            public void setTicketId(int ticketId) {
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

            public int getCheckin() {
                return checkin;
            }

            public void setCheckin(int checkin) {
                this.checkin = checkin;
            }

            public int getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(int payStatus) {
                this.payStatus = payStatus;
            }

            public int getAudit() {
                return audit;
            }

            public void setAudit(int audit) {
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

            public int getBuyWay() {
                return buyWay;
            }

            public void setBuyWay(int buyWay) {
                this.buyWay = buyWay;
            }

            public double getTicketPrice() {
                return ticketPrice;
            }

            public void setTicketPrice(double ticketPrice) {
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

            public AttendeeMapBean getAttendeeMap() {
                return attendeeMap;
            }

            public void setAttendeeMap(AttendeeMapBean attendeeMap) {
                this.attendeeMap = attendeeMap;
            }

            public String getBadgeMap() {
                return badgeMap;
            }

            public void setBadgeMap(String badgeMap) {
                this.badgeMap = badgeMap;
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

            public int getGiftIssuance() {
                return giftIssuance;
            }

            public void setGiftIssuance(int giftIssuance) {
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

            public ApiTicketOrderItemBean getApiTicketOrderItem() {
                return apiTicketOrderItem;
            }

            public void setApiTicketOrderItem(ApiTicketOrderItemBean apiTicketOrderItem) {
                this.apiTicketOrderItem = apiTicketOrderItem;
            }

            public int getAttendeeNumber() {
                return attendeeNumber;
            }

            public void setAttendeeNumber(int attendeeNumber) {
                this.attendeeNumber = attendeeNumber;
            }

            public int getAttendeeType() {
                return attendeeType;
            }

            public void setAttendeeType(int attendeeType) {
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

            public String getWxTicketQrCodeUrl() {
                return wxTicketQrCodeUrl;
            }

            public void setWxTicketQrCodeUrl(String wxTicketQrCodeUrl) {
                this.wxTicketQrCodeUrl = wxTicketQrCodeUrl;
            }

            public String getCurrencySign() {
                return currencySign;
            }

            public void setCurrencySign(String currencySign) {
                this.currencySign = currencySign;
            }

            public int getModifyFormDataCount() {
                return modifyFormDataCount;
            }

            public void setModifyFormDataCount(int modifyFormDataCount) {
                this.modifyFormDataCount = modifyFormDataCount;
            }

            public int getThirdAuditState() {
                return thirdAuditState;
            }

            public void setThirdAuditState(int thirdAuditState) {
                this.thirdAuditState = thirdAuditState;
            }

            public int getHasDiscount() {
                return hasDiscount;
            }

            public void setHasDiscount(int hasDiscount) {
                this.hasDiscount = hasDiscount;
            }

            public String getDiscountCode() {
                return discountCode;
            }

            public void setDiscountCode(String discountCode) {
                this.discountCode = discountCode;
            }

            public String getDiscountRemark() {
                return discountRemark;
            }

            public void setDiscountRemark(String discountRemark) {
                this.discountRemark = discountRemark;
            }

            public String getDiscountId() {
                return discountId;
            }

            public void setDiscountId(String discountId) {
                this.discountId = discountId;
            }

            public String getCheckInEquipment() {
                return checkInEquipment;
            }

            public void setCheckInEquipment(String checkInEquipment) {
                this.checkInEquipment = checkInEquipment;
            }

            public String getFormDataId() {
                return formDataId;
            }

            public void setFormDataId(String formDataId) {
                this.formDataId = formDataId;
            }

            public String getBadgeFormDataId() {
                return badgeFormDataId;
            }

            public void setBadgeFormDataId(String badgeFormDataId) {
                this.badgeFormDataId = badgeFormDataId;
            }

            public String getOrderItemId() {
                return orderItemId;
            }

            public void setOrderItemId(String orderItemId) {
                this.orderItemId = orderItemId;
            }

            public String getSourceLocale() {
                return sourceLocale;
            }

            public void setSourceLocale(String sourceLocale) {
                this.sourceLocale = sourceLocale;
            }

            public String getEditAttendeeKey() {
                return editAttendeeKey;
            }

            public void setEditAttendeeKey(String editAttendeeKey) {
                this.editAttendeeKey = editAttendeeKey;
            }

            public String getFormDataDTO() {
                return formDataDTO;
            }

            public void setFormDataDTO(String formDataDTO) {
                this.formDataDTO = formDataDTO;
            }

            public String getRealStartEntryTime() {
                return realStartEntryTime;
            }

            public void setRealStartEntryTime(String realStartEntryTime) {
                this.realStartEntryTime = realStartEntryTime;
            }

            public String getRealEndEntryTime() {
                return realEndEntryTime;
            }

            public void setRealEndEntryTime(String realEndEntryTime) {
                this.realEndEntryTime = realEndEntryTime;
            }

            public boolean isHasBuyingGrouping() {
                return hasBuyingGrouping;
            }

            public void setHasBuyingGrouping(boolean hasBuyingGrouping) {
                this.hasBuyingGrouping = hasBuyingGrouping;
            }

            public String getBgState() {
                return bgState;
            }

            public void setBgState(String bgState) {
                this.bgState = bgState;
            }

            public String getBuyingGroupId() {
                return buyingGroupId;
            }

            public void setBuyingGroupId(String buyingGroupId) {
                this.buyingGroupId = buyingGroupId;
            }

            public static class AttendeeMapBean {
                /**
                 * 263706 : Joe upon
                 * 263707 : upon_ai@outlook.com
                 * 263708 :
                 * 263830 :
                 * 263831 :
                 * 263832 :
                 * 263833 :
                 * 263834 :
                 * 263835 :
                 */

                @SerializedName("263706")
                private String _$263706;
                @SerializedName("263707")
                private String _$263707;
                @SerializedName("263708")
                private String _$263708;
                @SerializedName("263830")
                private String _$263830;
                @SerializedName("263831")
                private String _$263831;
                @SerializedName("263832")
                private String _$263832;
                @SerializedName("263833")
                private String _$263833;
                @SerializedName("263834")
                private String _$263834;
                @SerializedName("263835")
                private String _$263835;

                public String get_$263706() {
                    return _$263706;
                }

                public void set_$263706(String _$263706) {
                    this._$263706 = _$263706;
                }

                public String get_$263707() {
                    return _$263707;
                }

                public void set_$263707(String _$263707) {
                    this._$263707 = _$263707;
                }

                public String get_$263708() {
                    return _$263708;
                }

                public void set_$263708(String _$263708) {
                    this._$263708 = _$263708;
                }

                public String get_$263830() {
                    return _$263830;
                }

                public void set_$263830(String _$263830) {
                    this._$263830 = _$263830;
                }

                public String get_$263831() {
                    return _$263831;
                }

                public void set_$263831(String _$263831) {
                    this._$263831 = _$263831;
                }

                public String get_$263832() {
                    return _$263832;
                }

                public void set_$263832(String _$263832) {
                    this._$263832 = _$263832;
                }

                public String get_$263833() {
                    return _$263833;
                }

                public void set_$263833(String _$263833) {
                    this._$263833 = _$263833;
                }

                public String get_$263834() {
                    return _$263834;
                }

                public void set_$263834(String _$263834) {
                    this._$263834 = _$263834;
                }

                public String get_$263835() {
                    return _$263835;
                }

                public void set_$263835(String _$263835) {
                    this._$263835 = _$263835;
                }
            }

            public static class ApiTicketOrderItemBean {
                /**
                 * orderItemId : 3900456
                 * discountId :
                 * ticketOrderId : 3078904
                 * ticketId : 64567
                 * ticketPrice : 0.02
                 * currentTicketPrice : 0.02
                 * ticketFee : 0
                 * discountPrice : 0
                 * memberDiscountPrice : 0
                 * gift : 0
                 */

                private int orderItemId;
                private String discountId;
                private int ticketOrderId;
                private int ticketId;
                private double ticketPrice;
                private double currentTicketPrice;
                private int ticketFee;
                private int discountPrice;
                private int memberDiscountPrice;
                private int gift;

                public int getOrderItemId() {
                    return orderItemId;
                }

                public void setOrderItemId(int orderItemId) {
                    this.orderItemId = orderItemId;
                }

                public String getDiscountId() {
                    return discountId;
                }

                public void setDiscountId(String discountId) {
                    this.discountId = discountId;
                }

                public int getTicketOrderId() {
                    return ticketOrderId;
                }

                public void setTicketOrderId(int ticketOrderId) {
                    this.ticketOrderId = ticketOrderId;
                }

                public int getTicketId() {
                    return ticketId;
                }

                public void setTicketId(int ticketId) {
                    this.ticketId = ticketId;
                }

                public double getTicketPrice() {
                    return ticketPrice;
                }

                public void setTicketPrice(double ticketPrice) {
                    this.ticketPrice = ticketPrice;
                }

                public double getCurrentTicketPrice() {
                    return currentTicketPrice;
                }

                public void setCurrentTicketPrice(double currentTicketPrice) {
                    this.currentTicketPrice = currentTicketPrice;
                }

                public int getTicketFee() {
                    return ticketFee;
                }

                public void setTicketFee(int ticketFee) {
                    this.ticketFee = ticketFee;
                }

                public int getDiscountPrice() {
                    return discountPrice;
                }

                public void setDiscountPrice(int discountPrice) {
                    this.discountPrice = discountPrice;
                }

                public int getMemberDiscountPrice() {
                    return memberDiscountPrice;
                }

                public void setMemberDiscountPrice(int memberDiscountPrice) {
                    this.memberDiscountPrice = memberDiscountPrice;
                }

                public int getGift() {
                    return gift;
                }

                public void setGift(int gift) {
                    this.gift = gift;
                }
            }
        }

        public static class TagListBean {
            /**
             * tagId : 3
             * name : 得到的
             * sort : 0
             */

            private int tagId;
            private String name;
            private int sort;

            public int getTagId() {
                return tagId;
            }

            public void setTagId(int tagId) {
                this.tagId = tagId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }

        public static class FormFieldListBean {
            /**
             * formFieldId : 263706
             * field : {"fieldId":2,"fieldName":"username","fieldType":"0","fieldGenre":1,"showName":"姓名","maxLen":100,"defState":0,"fieldSort":1,"reserveField":0,"regexpValidation":"","fieldIcon":"","usedState":0,"abstractUsed":0,"defaultOptions":"","showState":0,"formType":"1,2,3,4,5","showType":0,"reserve":true}
             * fieldName : username
             * required : 1
             * sysOption : 0
             * regexpValidation :
             * showName : 姓名
             * brief :
             * options :
             * multiOptions :
             * sort : 1
             * maxLen : 100
             * tempData :
             * checkRepeat : 0
             * optionItems :
             * multiOptionItems :
             * state : 0
             * hasOther : 0
             * orgDisplay : 0
             * exDisplay : 1
             * orgEdit : 0
             * preAdd : 0
             * specialTag : 0
             * formFieldRichTextComponent :
             * preTip :
             * placeHolder :
             * showItemLeftCount : false
             * subEvent : false
             * showFieldName : 姓名
             * showOptions :
             * showMultiOptions :
             * enFieldName : username
             * enShowName :
             * showSet : true
             * speechType : 4
             * internationalizationShowName :
             * termed : false
             * relation : false
             * relationList :
             * self : 0
             * displayName :
             * hideTicketIds :
             * relatedFormFieldId :
             * showStatus : 0
             * organizerField : false
             * superOptionItems :
             * showOptionItems :
             */

            private int formFieldId;
            private FieldBean field;
            private String fieldName;
            private int required;
            private int sysOption;
            private String regexpValidation;
            private String showName;
            private String brief;
            private String options;
            private String multiOptions;
            private int sort;
            private int maxLen;
            private String tempData;
            private int checkRepeat;
            private String optionItems;
            private String multiOptionItems;
            private int state;
            private int hasOther;
            private int orgDisplay;
            private int exDisplay;
            private int orgEdit;
            private int preAdd;
            private int specialTag;
            private String formFieldRichTextComponent;
            private String preTip;
            private String placeHolder;
            private boolean showItemLeftCount;
            private boolean subEvent;
            private String showFieldName;
            private String showOptions;
            private String showMultiOptions;
            private String enFieldName;
            private String enShowName;
            private boolean showSet;
            private int speechType;
            private String internationalizationShowName;
            private boolean termed;
            private boolean relation;
            private String relationList;
            private int self;
            private String displayName;
            private String hideTicketIds;
            private String relatedFormFieldId;
            private int showStatus;
            private boolean organizerField;
            private String superOptionItems;
            private String showOptionItems;

            public int getFormFieldId() {
                return formFieldId;
            }

            public void setFormFieldId(int formFieldId) {
                this.formFieldId = formFieldId;
            }

            public FieldBean getField() {
                return field;
            }

            public void setField(FieldBean field) {
                this.field = field;
            }

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }

            public int getRequired() {
                return required;
            }

            public void setRequired(int required) {
                this.required = required;
            }

            public int getSysOption() {
                return sysOption;
            }

            public void setSysOption(int sysOption) {
                this.sysOption = sysOption;
            }

            public String getRegexpValidation() {
                return regexpValidation;
            }

            public void setRegexpValidation(String regexpValidation) {
                this.regexpValidation = regexpValidation;
            }

            public String getShowName() {
                return showName;
            }

            public void setShowName(String showName) {
                this.showName = showName;
            }

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public String getOptions() {
                return options;
            }

            public void setOptions(String options) {
                this.options = options;
            }

            public String getMultiOptions() {
                return multiOptions;
            }

            public void setMultiOptions(String multiOptions) {
                this.multiOptions = multiOptions;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getMaxLen() {
                return maxLen;
            }

            public void setMaxLen(int maxLen) {
                this.maxLen = maxLen;
            }

            public String getTempData() {
                return tempData;
            }

            public void setTempData(String tempData) {
                this.tempData = tempData;
            }

            public int getCheckRepeat() {
                return checkRepeat;
            }

            public void setCheckRepeat(int checkRepeat) {
                this.checkRepeat = checkRepeat;
            }

            public String getOptionItems() {
                return optionItems;
            }

            public void setOptionItems(String optionItems) {
                this.optionItems = optionItems;
            }

            public String getMultiOptionItems() {
                return multiOptionItems;
            }

            public void setMultiOptionItems(String multiOptionItems) {
                this.multiOptionItems = multiOptionItems;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getHasOther() {
                return hasOther;
            }

            public void setHasOther(int hasOther) {
                this.hasOther = hasOther;
            }

            public int getOrgDisplay() {
                return orgDisplay;
            }

            public void setOrgDisplay(int orgDisplay) {
                this.orgDisplay = orgDisplay;
            }

            public int getExDisplay() {
                return exDisplay;
            }

            public void setExDisplay(int exDisplay) {
                this.exDisplay = exDisplay;
            }

            public int getOrgEdit() {
                return orgEdit;
            }

            public void setOrgEdit(int orgEdit) {
                this.orgEdit = orgEdit;
            }

            public int getPreAdd() {
                return preAdd;
            }

            public void setPreAdd(int preAdd) {
                this.preAdd = preAdd;
            }

            public int getSpecialTag() {
                return specialTag;
            }

            public void setSpecialTag(int specialTag) {
                this.specialTag = specialTag;
            }

            public String getFormFieldRichTextComponent() {
                return formFieldRichTextComponent;
            }

            public void setFormFieldRichTextComponent(String formFieldRichTextComponent) {
                this.formFieldRichTextComponent = formFieldRichTextComponent;
            }

            public String getPreTip() {
                return preTip;
            }

            public void setPreTip(String preTip) {
                this.preTip = preTip;
            }

            public String getPlaceHolder() {
                return placeHolder;
            }

            public void setPlaceHolder(String placeHolder) {
                this.placeHolder = placeHolder;
            }

            public boolean isShowItemLeftCount() {
                return showItemLeftCount;
            }

            public void setShowItemLeftCount(boolean showItemLeftCount) {
                this.showItemLeftCount = showItemLeftCount;
            }

            public boolean isSubEvent() {
                return subEvent;
            }

            public void setSubEvent(boolean subEvent) {
                this.subEvent = subEvent;
            }

            public String getShowFieldName() {
                return showFieldName;
            }

            public void setShowFieldName(String showFieldName) {
                this.showFieldName = showFieldName;
            }

            public String getShowOptions() {
                return showOptions;
            }

            public void setShowOptions(String showOptions) {
                this.showOptions = showOptions;
            }

            public String getShowMultiOptions() {
                return showMultiOptions;
            }

            public void setShowMultiOptions(String showMultiOptions) {
                this.showMultiOptions = showMultiOptions;
            }

            public String getEnFieldName() {
                return enFieldName;
            }

            public void setEnFieldName(String enFieldName) {
                this.enFieldName = enFieldName;
            }

            public String getEnShowName() {
                return enShowName;
            }

            public void setEnShowName(String enShowName) {
                this.enShowName = enShowName;
            }

            public boolean isShowSet() {
                return showSet;
            }

            public void setShowSet(boolean showSet) {
                this.showSet = showSet;
            }

            public int getSpeechType() {
                return speechType;
            }

            public void setSpeechType(int speechType) {
                this.speechType = speechType;
            }

            public String getInternationalizationShowName() {
                return internationalizationShowName;
            }

            public void setInternationalizationShowName(String internationalizationShowName) {
                this.internationalizationShowName = internationalizationShowName;
            }

            public boolean isTermed() {
                return termed;
            }

            public void setTermed(boolean termed) {
                this.termed = termed;
            }

            public boolean isRelation() {
                return relation;
            }

            public void setRelation(boolean relation) {
                this.relation = relation;
            }

            public String getRelationList() {
                return relationList;
            }

            public void setRelationList(String relationList) {
                this.relationList = relationList;
            }

            public int getSelf() {
                return self;
            }

            public void setSelf(int self) {
                this.self = self;
            }

            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            public String getHideTicketIds() {
                return hideTicketIds;
            }

            public void setHideTicketIds(String hideTicketIds) {
                this.hideTicketIds = hideTicketIds;
            }

            public String getRelatedFormFieldId() {
                return relatedFormFieldId;
            }

            public void setRelatedFormFieldId(String relatedFormFieldId) {
                this.relatedFormFieldId = relatedFormFieldId;
            }

            public int getShowStatus() {
                return showStatus;
            }

            public void setShowStatus(int showStatus) {
                this.showStatus = showStatus;
            }

            public boolean isOrganizerField() {
                return organizerField;
            }

            public void setOrganizerField(boolean organizerField) {
                this.organizerField = organizerField;
            }

            public String getSuperOptionItems() {
                return superOptionItems;
            }

            public void setSuperOptionItems(String superOptionItems) {
                this.superOptionItems = superOptionItems;
            }

            public String getShowOptionItems() {
                return showOptionItems;
            }

            public void setShowOptionItems(String showOptionItems) {
                this.showOptionItems = showOptionItems;
            }

            public static class FieldBean {
                /**
                 * fieldId : 2
                 * fieldName : username
                 * fieldType : 0
                 * fieldGenre : 1
                 * showName : 姓名
                 * maxLen : 100
                 * defState : 0
                 * fieldSort : 1
                 * reserveField : 0
                 * regexpValidation :
                 * fieldIcon :
                 * usedState : 0
                 * abstractUsed : 0
                 * defaultOptions :
                 * showState : 0
                 * formType : 1,2,3,4,5
                 * showType : 0
                 * reserve : true
                 */

                private int fieldId;
                private String fieldName;
                private String fieldType;
                private int fieldGenre;
                private String showName;
                private int maxLen;
                private int defState;
                private int fieldSort;
                private int reserveField;
                private String regexpValidation;
                private String fieldIcon;
                private int usedState;
                private int abstractUsed;
                private String defaultOptions;
                private int showState;
                private String formType;
                private int showType;
                private boolean reserve;

                public int getFieldId() {
                    return fieldId;
                }

                public void setFieldId(int fieldId) {
                    this.fieldId = fieldId;
                }

                public String getFieldName() {
                    return fieldName;
                }

                public void setFieldName(String fieldName) {
                    this.fieldName = fieldName;
                }

                public String getFieldType() {
                    return fieldType;
                }

                public void setFieldType(String fieldType) {
                    this.fieldType = fieldType;
                }

                public int getFieldGenre() {
                    return fieldGenre;
                }

                public void setFieldGenre(int fieldGenre) {
                    this.fieldGenre = fieldGenre;
                }

                public String getShowName() {
                    return showName;
                }

                public void setShowName(String showName) {
                    this.showName = showName;
                }

                public int getMaxLen() {
                    return maxLen;
                }

                public void setMaxLen(int maxLen) {
                    this.maxLen = maxLen;
                }

                public int getDefState() {
                    return defState;
                }

                public void setDefState(int defState) {
                    this.defState = defState;
                }

                public int getFieldSort() {
                    return fieldSort;
                }

                public void setFieldSort(int fieldSort) {
                    this.fieldSort = fieldSort;
                }

                public int getReserveField() {
                    return reserveField;
                }

                public void setReserveField(int reserveField) {
                    this.reserveField = reserveField;
                }

                public String getRegexpValidation() {
                    return regexpValidation;
                }

                public void setRegexpValidation(String regexpValidation) {
                    this.regexpValidation = regexpValidation;
                }

                public String getFieldIcon() {
                    return fieldIcon;
                }

                public void setFieldIcon(String fieldIcon) {
                    this.fieldIcon = fieldIcon;
                }

                public int getUsedState() {
                    return usedState;
                }

                public void setUsedState(int usedState) {
                    this.usedState = usedState;
                }

                public int getAbstractUsed() {
                    return abstractUsed;
                }

                public void setAbstractUsed(int abstractUsed) {
                    this.abstractUsed = abstractUsed;
                }

                public String getDefaultOptions() {
                    return defaultOptions;
                }

                public void setDefaultOptions(String defaultOptions) {
                    this.defaultOptions = defaultOptions;
                }

                public int getShowState() {
                    return showState;
                }

                public void setShowState(int showState) {
                    this.showState = showState;
                }

                public String getFormType() {
                    return formType;
                }

                public void setFormType(String formType) {
                    this.formType = formType;
                }

                public int getShowType() {
                    return showType;
                }

                public void setShowType(int showType) {
                    this.showType = showType;
                }

                public boolean isReserve() {
                    return reserve;
                }

                public void setReserve(boolean reserve) {
                    this.reserve = reserve;
                }
            }
        }
    }

}
