package com.bagevent.home.data;

import java.util.List;

/**
 * Created by zwj on 2016/7/13.
 */
public class CollectDetailData {

    private RespObjectBean respObject;
    /**
     * respObject : {"checkInList":[{"attendeeName":"啊啊啊","attendeePinyinName":"aaa","attendeeImg":"","attendeeCheckInTime":"2016-06-08 14:02","attendeeBarcode":"2080503050601032027","attendeeFormData":{}},{"attendeeName":"阿斯lfd","attendeePinyinName":"asilfd","attendeeImg":"","attendeeCheckInTime":"2016-06-08 14:10","attendeeBarcode":"2080503030705022027","attendeeFormData":{}},{"attendeeName":"阿","attendeePinyinName":"a","attendeeImg":"","attendeeCheckInTime":"2016-06-08 14:28","attendeeBarcode":"2080503020002062027","attendeeFormData":{}},{"attendeeName":"伦","attendeePinyinName":"lun","attendeeImg":"","attendeeCheckInTime":"2016-06-08 14:29","attendeeBarcode":"2080503010201052027","attendeeFormData":{}},{"attendeeName":"summer","attendeePinyinName":"summer","attendeeImg":"//wx.qlogo.cn/mmopen/yZu2HEtqgnCxYlD66Y4omMnBicRYT3A86ia152ibcsynGnFzxqSkKsvCaLYFV1U1NShGL7NSw4JB9kpdthyr2oxyyMXEmlqzUkz/0","attendeeCheckInTime":"2016-06-08 14:33","attendeeBarcode":"1090901010901022027","attendeeFormData":{}},{"attendeeName":"吴姗姗","attendeePinyinName":"wushanshan","attendeeImg":"","attendeeCheckInTime":"2016-06-08 14:35","attendeeBarcode":"1050105070907012027","attendeeFormData":{}},{"attendeeName":"金麟","attendeePinyinName":"jinlin","attendeeImg":"","attendeeCheckInTime":"2016-06-08 15:02","attendeeBarcode":"1050105080805062027","attendeeFormData":{}},{"attendeeName":"Gouala Thibault","attendeePinyinName":"Gouala Thibault","attendeeImg":"","attendeeCheckInTime":"2016-06-08 15:40","attendeeBarcode":"1050105070808012027","attendeeFormData":{}},{"attendeeName":"周宇飞","attendeePinyinName":"zhouyufei","attendeeImg":"","attendeeCheckInTime":"2016-06-08 15:41","attendeeBarcode":"1050105060900052027","attendeeFormData":{}},{"attendeeName":"balle","attendeePinyinName":"balle","attendeeImg":"","attendeeCheckInTime":"2016-06-21 10:57","attendeeBarcode":"2090100090204002027","attendeeFormData":{}},{"attendeeName":"wwww","attendeePinyinName":"wwww","attendeeImg":"","attendeeCheckInTime":"2016-06-21 10:57","attendeeBarcode":"2080504070309072027","attendeeFormData":{}},{"attendeeName":"程勋","attendeePinyinName":"chengxun","attendeeImg":"","attendeeCheckInTime":"2016-06-21 10:57","attendeeBarcode":"2090100070204012027","attendeeFormData":{}}],"collectionFieldList":[]}
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
        /**
         * attendeeName : 啊啊啊
         * attendeePinyinName : aaa
         * attendeeImg :
         * attendeeCheckInTime : 2016-06-08 14:02
         * attendeeBarcode : 2080503050601032027
         * attendeeFormData : {}
         */

        private List<CheckInListBean> checkInList;
        private List<?> collectionFieldList;

        public List<CheckInListBean> getCheckInList() {
            return checkInList;
        }

        public void setCheckInList(List<CheckInListBean> checkInList) {
            this.checkInList = checkInList;
        }

        public List<?> getCollectionFieldList() {
            return collectionFieldList;
        }

        public void setCollectionFieldList(List<?> collectionFieldList) {
            this.collectionFieldList = collectionFieldList;
        }

        public static class CheckInListBean {
            private int attendeeId;
            private String attendeeName;
            private String attendeePinyinName;
            private String attendeeImg;
            private String attendeeCheckInTime;
            private String attendeeBarcode;

            public int getAttendeeId() {
                return attendeeId;
            }

            public void setAttendeeId(int attendeeId) {
                this.attendeeId = attendeeId;
            }

            public String getAttendeeName() {
                return attendeeName;
            }

            public void setAttendeeName(String attendeeName) {
                this.attendeeName = attendeeName;
            }

            public String getAttendeePinyinName() {
                return attendeePinyinName;
            }

            public void setAttendeePinyinName(String attendeePinyinName) {
                this.attendeePinyinName = attendeePinyinName;
            }

            public String getAttendeeImg() {
                return attendeeImg;
            }

            public void setAttendeeImg(String attendeeImg) {
                this.attendeeImg = attendeeImg;
            }

            public String getAttendeeCheckInTime() {
                return attendeeCheckInTime;
            }

            public void setAttendeeCheckInTime(String attendeeCheckInTime) {
                this.attendeeCheckInTime = attendeeCheckInTime;
            }

            public String getAttendeeBarcode() {
                return attendeeBarcode;
            }

            public void setAttendeeBarcode(String attendeeBarcode) {
                this.attendeeBarcode = attendeeBarcode;
            }
        }
    }
}
