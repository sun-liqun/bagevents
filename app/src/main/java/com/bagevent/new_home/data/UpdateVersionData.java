package com.bagevent.new_home.data;

/**
 * Created by zwj on 2016/12/7.
 */
public class UpdateVersionData {

    /**
     * updateWay : 1
     * downloadUrl : http://file.bagevent.com/BagEvent.apk
     * version : 2.0.4
     */

    private RespObjectBean respObject;
    /**
     * respObject : {"updateWay":1,"downloadUrl":"http://file.bagevent.com/BagEvent.apk","version":"2.0.4"}
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
        private int updateWay;//0---非强制,1---强制更新
        private String downloadUrl;
        private String version;

        public int getUpdateWay() {
            return updateWay;
        }

        public void setUpdateWay(int updateWay) {
            this.updateWay = updateWay;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
