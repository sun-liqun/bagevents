package com.bagevent.new_home.data;

import java.util.List;

/**
 * Created by zwj on 2016/9/23.
 */
public class AddFormResponse {

    /**
     * brief :
     * checkRepeat : 0
     * enFieldName : home_phone
     * enShowName :
     * fieldName : home_phone
     * formFieldId : 22839
     * hasOther : 0
     * maxLen : 100
     * optionItems : []
     * options :
     * regexpValidation : ^[0-9－\- +]+$
     * required : 0
     * showFieldName : 家庭电话
     * showName : 家庭电话
     * showOptions :
     * sort : 7
     * subEvent : false
     * sysOption : 0
     * tempData :
     */

    private RespObjectBean respObject;
    /**
     * respObject : {"brief":"","checkRepeat":0,"enFieldName":"home_phone","enShowName":"","fieldName":"home_phone","formFieldId":22839,"hasOther":0,"maxLen":100,"optionItems":[],"options":"","regexpValidation":"^[0-9－\\- +]+$","required":0,"showFieldName":"家庭电话","showName":"家庭电话","showOptions":"","sort":7,"subEvent":false,"sysOption":0,"tempData":""}
     * respType : 1
     * retStatus : 200
     */

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
        private String brief;
        private int checkRepeat;
        private String enFieldName;
        private String enShowName;
        private String fieldName;
        private int formFieldId;
        private int hasOther;
        private int maxLen;
        private String options;
        private String regexpValidation;
        private int required;
        private String showFieldName;
        private String showName;
        private String showOptions;
        private int sort;
        private boolean subEvent;
        private int sysOption;
        private String tempData;
        private List<options> optionItems;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public int getCheckRepeat() {
            return checkRepeat;
        }

        public void setCheckRepeat(int checkRepeat) {
            this.checkRepeat = checkRepeat;
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

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public int getFormFieldId() {
            return formFieldId;
        }

        public void setFormFieldId(int formFieldId) {
            this.formFieldId = formFieldId;
        }

        public int getHasOther() {
            return hasOther;
        }

        public void setHasOther(int hasOther) {
            this.hasOther = hasOther;
        }

        public int getMaxLen() {
            return maxLen;
        }

        public void setMaxLen(int maxLen) {
            this.maxLen = maxLen;
        }

        public String getOptions() {
            return options;
        }

        public void setOptions(String options) {
            this.options = options;
        }

        public String getRegexpValidation() {
            return regexpValidation;
        }

        public void setRegexpValidation(String regexpValidation) {
            this.regexpValidation = regexpValidation;
        }

        public int getRequired() {
            return required;
        }

        public void setRequired(int required) {
            this.required = required;
        }

        public String getShowFieldName() {
            return showFieldName;
        }

        public void setShowFieldName(String showFieldName) {
            this.showFieldName = showFieldName;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public String getShowOptions() {
            return showOptions;
        }

        public void setShowOptions(String showOptions) {
            this.showOptions = showOptions;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public boolean isSubEvent() {
            return subEvent;
        }

        public void setSubEvent(boolean subEvent) {
            this.subEvent = subEvent;
        }

        public int getSysOption() {
            return sysOption;
        }

        public void setSysOption(int sysOption) {
            this.sysOption = sysOption;
        }

        public String getTempData() {
            return tempData;
        }

        public void setTempData(String tempData) {
            this.tempData = tempData;
        }

        public List<options> getOptionItems() {
            return optionItems;
        }

        public void setOptionItems(List<options> optionItems) {
            this.optionItems = optionItems;
        }
    }
    public static class options{
        private String key;
        private int sort;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
