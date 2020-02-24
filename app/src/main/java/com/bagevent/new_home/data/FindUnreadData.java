package com.bagevent.new_home.data;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public class FindUnreadData {

    /**
     * respObject : true
     * respType : 0
     * retStatus : 200
     */

    private boolean respObject;
    private int respType;
    private int retStatus;

    public boolean isRespObject() {
        return respObject;
    }

    public void setRespObject(boolean respObject) {
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
}
