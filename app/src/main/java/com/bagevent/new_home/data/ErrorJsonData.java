package com.bagevent.new_home.data;

/**
 * Created by zwj on 2017/10/13.
 */

public class ErrorJsonData {

    /**
     * respObject : 密码错误
     * respType : 0
     * retStatus : 213
     */

    private String respObject;
    private int respType;
    private int retStatus;

    public String getRespObject() {
        return respObject;
    }

    public void setRespObject(String respObject) {
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
