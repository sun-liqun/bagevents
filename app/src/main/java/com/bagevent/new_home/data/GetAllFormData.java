package com.bagevent.new_home.data;

import java.util.List;

/**
 * Created by zwj on 2016/9/22.
 */
public class GetAllFormData {

    /**
     * respObject : [{"abstractUsed":0,"defState":2,"fieldGenre":21,"fieldIcon":"/resources/img/add_form_img01.png","fieldId":11,"fieldName":"text","fieldSort":0,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":1,"showName":"普通文本框","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":2,"fieldIcon":"","fieldId":19,"fieldName":"last_name","fieldSort":0,"fieldType":"0","maxLen":100,"regexpValidation":"^[a-zA-Z0-9\\u4e00-\\u9fa5\\s\\-]+$","reserveField":0,"showName":"姓","usedState":0},{"abstractUsed":0,"defState":0,"fieldGenre":1,"fieldIcon":"","fieldId":2,"fieldName":"username","fieldSort":1,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":0,"showName":"姓名","usedState":1},{"abstractUsed":0,"defState":2,"fieldGenre":2,"fieldIcon":"","fieldId":20,"fieldName":"first_name","fieldSort":1,"fieldType":"0","maxLen":100,"regexpValidation":"^[a-zA-Z0-9\\u4e00-\\u9fa5\\s\\-]+$","reserveField":0,"showName":"名","usedState":0},{"abstractUsed":1,"defState":0,"fieldGenre":3,"fieldIcon":"","fieldId":4,"fieldName":"email_address","fieldSort":2,"fieldType":"0","maxLen":100,"regexpValidation":"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$","reserveField":0,"showName":"邮箱","usedState":1},{"abstractUsed":1,"defState":1,"fieldGenre":4,"fieldIcon":"","fieldId":5,"fieldName":"mobile_phone","fieldSort":3,"fieldType":"0","maxLen":30,"regexpValidation":"^(13|15|18|14|17)[0-9]{9}$","reserveField":0,"showName":"手机","usedState":1},{"abstractUsed":1,"defState":2,"fieldGenre":5,"fieldIcon":"","fieldId":8,"fieldName":"company","fieldSort":4,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":0,"showName":"公司名称","usedState":1},{"abstractUsed":1,"defState":2,"fieldGenre":6,"fieldIcon":"","fieldId":7,"fieldName":"title","fieldSort":5,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":0,"showName":"职位","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":22,"fieldIcon":"/resources/img/add_form_img02.png","fieldId":12,"fieldName":"textarea","fieldSort":5,"fieldType":"0","maxLen":1000,"regexpValidation":"","reserveField":1,"showName":"多行文本框","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":13,"fieldIcon":"","fieldId":3,"fieldName":"home_phone","fieldSort":6,"fieldType":"0","maxLen":100,"regexpValidation":"^[0-9－\\- +]+$","reserveField":0,"showName":"家庭电话","usedState":0},{"abstractUsed":1,"defState":2,"fieldGenre":12,"fieldIcon":"","fieldId":6,"fieldName":"address","fieldSort":7,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":0,"showName":"家庭住址","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":11,"fieldIcon":"","fieldId":10,"fieldName":"blog","fieldSort":8,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":0,"showName":"个人网站","usedState":1},{"abstractUsed":0,"defState":2,"fieldGenre":10,"fieldIcon":"","fieldId":9,"fieldName":"website","fieldSort":9,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":0,"showName":"公司网站","usedState":1},{"abstractUsed":0,"defState":2,"fieldGenre":23,"fieldIcon":"/resources/img/add_form_img06.png","fieldId":13,"fieldName":"date","fieldSort":10,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":1,"showName":"日期","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":24,"fieldIcon":"/resources/img/add_form_img03.png","fieldId":14,"fieldName":"radio","fieldSort":15,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":1,"showName":"单选值","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":15,"fieldIcon":"","fieldId":38,"fieldName":"attendee_avatar","fieldSort":15,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":0,"showName":"图像采集","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":16,"fieldIcon":"","fieldId":36,"fieldName":"IDCard","fieldSort":16,"fieldType":"0","maxLen":100,"regexpValidation":"^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$","reserveField":0,"showName":"身份证","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":17,"fieldIcon":"","fieldId":37,"fieldName":"passport","fieldSort":17,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":0,"showName":"护照","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":25,"fieldIcon":"/resources/img/add_form_img04.png","fieldId":15,"fieldName":"checkbox","fieldSort":20,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":1,"showName":"多选值","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":26,"fieldIcon":"/resources/img/add_form_img05.png","fieldId":16,"fieldName":"select","fieldSort":25,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":1,"showName":"下拉选择框","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":27,"fieldIcon":"/resources/img/add_form_img07.png","fieldId":17,"fieldName":"file","fieldSort":30,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":1,"showName":"文件","usedState":0},{"abstractUsed":0,"defState":2,"fieldGenre":28,"fieldIcon":"/resources/img/add_form_img08.png","fieldId":18,"fieldName":"country","fieldSort":35,"fieldType":"0","maxLen":100,"regexpValidation":"","reserveField":1,"showName":"地区","usedState":0}]
     * respType : 1
     * retStatus : 200
     */

    private int respType;
    private int retStatus;
    /**
     * abstractUsed : 0
     * defState : 2
     * fieldGenre : 21
     * fieldIcon : /resources/img/add_form_img01.png
     * fieldId : 11
     * fieldName : text
     * fieldSort : 0
     * fieldType : 0
     * maxLen : 100
     * regexpValidation :
     * reserveField : 1
     * showName : 普通文本框
     * usedState : 0
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
        private int abstractUsed;
        private int defState;
        private int fieldGenre;
        private String fieldIcon;
        private int fieldId;
        private String fieldName;
        private int fieldSort;
        private String fieldType;
        private int maxLen;
        private String regexpValidation;
        private int reserveField;
        private String showName;
        private int usedState;

        public int getAbstractUsed() {
            return abstractUsed;
        }

        public void setAbstractUsed(int abstractUsed) {
            this.abstractUsed = abstractUsed;
        }

        public int getDefState() {
            return defState;
        }

        public void setDefState(int defState) {
            this.defState = defState;
        }

        public int getFieldGenre() {
            return fieldGenre;
        }

        public void setFieldGenre(int fieldGenre) {
            this.fieldGenre = fieldGenre;
        }

        public String getFieldIcon() {
            return fieldIcon;
        }

        public void setFieldIcon(String fieldIcon) {
            this.fieldIcon = fieldIcon;
        }

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

        public int getFieldSort() {
            return fieldSort;
        }

        public void setFieldSort(int fieldSort) {
            this.fieldSort = fieldSort;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public int getMaxLen() {
            return maxLen;
        }

        public void setMaxLen(int maxLen) {
            this.maxLen = maxLen;
        }

        public String getRegexpValidation() {
            return regexpValidation;
        }

        public void setRegexpValidation(String regexpValidation) {
            this.regexpValidation = regexpValidation;
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

        public int getUsedState() {
            return usedState;
        }

        public void setUsedState(int usedState) {
            this.usedState = usedState;
        }
    }
}
