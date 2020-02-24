package com.bagevent.new_home.data;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/04/26
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class YunXinToken {

    /**
     * retStatus : 200
     * nextPage :
     * resultObject :
     * responseType : 0
     * respObject : {"accid":"ec633dd1cbea0888347029fa7f4f6f81","token":"8acf893234bc9f2f8921e1a5e341283f"}
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
         * accid : ec633dd1cbea0888347029fa7f4f6f81
         * token : 8acf893234bc9f2f8921e1a5e341283f
         */

        private String accid;
        private String token;

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
