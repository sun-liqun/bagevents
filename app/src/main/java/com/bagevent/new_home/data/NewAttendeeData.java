package com.bagevent.new_home.data;

import java.util.List;

/**
 * Created by zwj on 2016/9/2.
 */
public class NewAttendeeData {

    /**
     * respObject : [{"attendeeCount":1,"signDate":"08-24"},{"attendeeCount":3,"signDate":"08-23"},{"attendeeCount":2,"signDate":"08-16"},{"attendeeCount":10,"signDate":"08-15"},{"attendeeCount":7,"signDate":"08-10"},{"attendeeCount":8,"signDate":"08-09"},{"attendeeCount":1,"signDate":"08-08"}]
     * respType : 1
     * retStatus : 200
     */

    private int respType;
    private int retStatus;
    /**
     * attendeeCount : 1
     * signDate : 08-24
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
        private int attendeeCount;
        private String signDate;

        public int getAttendeeCount() {
            return attendeeCount;
        }

        public void setAttendeeCount(int attendeeCount) {
            this.attendeeCount = attendeeCount;
        }

        public String getSignDate() {
            return signDate;
        }

        public void setSignDate(String signDate) {
            this.signDate = signDate;
        }
    }
}
