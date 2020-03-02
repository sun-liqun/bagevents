package com.bagevent.activity_manager.manager_fragment.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/6/27.
 */
public class FormType {

    /**
     * respObject : [{"brief":"","checkRepeat":0,"fieldName":"username","fieldTypeName":"username","formFieldId":80,"maxLen":100,"optionItems":[],"options":"","regexpValidation":"^[a-zA-Z0-9\\u4e00-\\u9fa5\\s\\-]+$","required":1,"reserveField":0,"showName":"姓名","sort":1,"tempData":""},{"brief":"","checkRepeat":0,"fieldName":"email_address","fieldTypeName":"email_address","formFieldId":81,"maxLen":100,"optionItems":[],"options":"","regexpValidation":"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$","required":1,"reserveField":0,"showName":"邮箱","sort":2,"tempData":""},{"brief":"","checkRepeat":0,"fieldName":"mobile_phone","fieldTypeName":"mobile_phone","formFieldId":82,"maxLen":30,"optionItems":[],"options":"","regexpValidation":"^[0-9－-]+\\d*$","required":0,"reserveField":0,"showName":"手机","sort":3,"tempData":""},{"brief":"","checkRepeat":0,"fieldName":"address","fieldTypeName":"address","formFieldId":106,"maxLen":100,"optionItems":[],"options":"","regexpValidation":"","required":0,"reserveField":0,"showName":"家庭住址","sort":4,"tempData":""},{"brief":"","checkRepeat":0,"fieldName":"radio_1464742710277","fieldTypeName":"radio","formFieldId":9613,"maxLen":100,"optionItems":[{"key":"radio_item_1464742710278","sort":1,"value":"111"},{"key":"radio_item_1464742713508","sort":1,"value":"222"},{"key":"radio_item_1464742715702","sort":1,"value":"333"}],"options":"[{\"key\":\"radio_item_1464742710278\",\"sort\":1,\"value\":\"111\"},{\"key\":\"radio_item_1464742713508\",\"sort\":1,\"value\":\"222\"},{\"key\":\"radio_item_1464742715702\",\"sort\":1,\"value\":\"333\"}]","regexpValidation":"","required":0,"reserveField":1,"showName":"单选值","sort":5,"tempData":""},{"brief":"","checkRepeat":0,"fieldName":"checkbox_1464743155415","fieldTypeName":"checkbox","formFieldId":9616,"maxLen":100,"optionItems":[{"key":"checkbox_item_1464743155415","sort":1,"value":"444"},{"key":"checkbox_item_1464743159332","sort":1,"value":"555"},{"key":"checkbox_item_1464743161310","sort":1,"value":"666"}],"options":"[{\"key\":\"checkbox_item_1464743155415\",\"sort\":1,\"value\":\"444\"},{\"key\":\"checkbox_item_1464743159332\",\"sort\":1,\"value\":\"555\"},{\"key\":\"checkbox_item_1464743161310\",\"sort\":1,\"value\":\"666\"}]","regexpValidation":"","required":0,"reserveField":1,"showName":"多选值","sort":6,"tempData":""},{"brief":"","checkRepeat":0,"fieldName":"select_1464747379098","fieldTypeName":"select","formFieldId":9621,"maxLen":100,"optionItems":[{"key":"select_item_1464747379099","sort":1,"value":"777"},{"key":"select_item_1464747383194","sort":1,"value":"888"},{"key":"select_item_1464747385572","sort":1,"value":"999"}],"options":"[{\"key\":\"select_item_1464747379099\",\"sort\":1,\"value\":\"777\"},{\"key\":\"select_item_1464747383194\",\"sort\":1,\"value\":\"888\"},{\"key\":\"select_item_1464747385572\",\"sort\":1,\"value\":\"999\"}]","regexpValidation":"","required":0,"reserveField":1,"showName":"下拉选择框","sort":7,"tempData":""},{"brief":"","checkRepeat":0,"fieldName":"textarea_1466995473721","fieldTypeName":"textarea","formFieldId":12379,"maxLen":1000,"optionItems":[],"options":"","regexpValidation":"","required":0,"reserveField":1,"showName":"多行文本框","sort":8,"tempData":""},{"brief":"","checkRepeat":0,"fieldName":"textarea_1466995473911","fieldTypeName":"textarea","formFieldId":12380,"maxLen":1000,"optionItems":[],"options":"","regexpValidation":"","required":0,"reserveField":1,"showName":"多行文本框","sort":9,"tempData":""}]
     * respType : 1
     * retStatus : 200
     */

    private int respType;
    private int retStatus;
    /**
     * brief :
     * checkRepeat : 0
     * fieldName : username
     * fieldTypeName : username
     * formFieldId : 80
     * maxLen : 100
     * optionItems : []
     * options :
     * regexpValidation : ^[a-zA-Z0-9\u4e00-\u9fa5\s\-]+$
     * required : 1
     * reserveField : 0
     * showName : 姓名
     * sort : 1
     * tempData :
     */

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
        private String brief;
        private int checkRepeat;
        private String fieldName;
        private String fieldTypeName;
        private int formFieldId;
        private int maxLen;
        private String options;
        private String regexpValidation;
        private int required;
        private int reserveField;
        private String showName;
        private int sort;
        private String tempData;
        private ArrayList<options> optionItems;

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

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldTypeName() {
            return fieldTypeName;
        }

        public void setFieldTypeName(String fieldTypeName) {
            this.fieldTypeName = fieldTypeName;
        }

        public int getFormFieldId() {
            return formFieldId;
        }

        public void setFormFieldId(int formFieldId) {
            this.formFieldId = formFieldId;
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

        public int getReserveField() {
            return reserveField;
        }

        public void setReserveField(int reserveField) {
            this.reserveField = reserveField;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getTempData() {
            return tempData;
        }

        public void setTempData(String tempData) {
            this.tempData = tempData;
        }

        public ArrayList<options> getOptionItems() {
            return optionItems;
        }

        public void setOptionItems(ArrayList<options> optionItems) {
            this.optionItems = optionItems;
        }
    }
    public static class options implements Serializable{
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
