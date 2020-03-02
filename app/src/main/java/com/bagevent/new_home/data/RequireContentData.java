package com.bagevent.new_home.data;

import java.util.List;

/**
 * Created by zwj on 2016/9/12.
 */
public class RequireContentData {

    /**
     * respObject : [{"eventOfferTypeId":1,"name":"会场/酒店","state":1},{"eventOfferTypeId":2,"name":"活动网站","state":1},{"eventOfferTypeId":3,"name":"报名收款","state":1},{"eventOfferTypeId":4,"name":"现场签到","state":1},{"eventOfferTypeId":5,"name":"打印胸卡","state":1},{"eventOfferTypeId":6,"name":"学术会议","state":1},{"eventOfferTypeId":7,"name":"活动推广","state":1},{"eventOfferTypeId":8,"name":"全流程托管","state":1}]
     * respType : 1
     * retStatus : 200
     */

    private int respType;
    private int retStatus;
    /**
     * eventOfferTypeId : 1
     * name : 会场/酒店
     * state : 1
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
        private int eventOfferTypeId;
        private String name;
        private int state;

        public int getEventOfferTypeId() {
            return eventOfferTypeId;
        }

        public void setEventOfferTypeId(int eventOfferTypeId) {
            this.eventOfferTypeId = eventOfferTypeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
