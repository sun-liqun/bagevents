package com.bagevent.new_home.data;

/**
 * Created by zwj on 2016/9/26.
 */
public class AddFormItemData {

    /**
     * key : checkbox_item_1474857527733
     * sort : 4
     * value : 子项目
     */

    private RespObjectBean respObject;
    /**
     * respObject : {"key":"checkbox_item_1474857527733","sort":4,"value":"子项目"}
     * respType : 0
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
