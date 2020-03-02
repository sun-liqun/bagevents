package com.bagevent.activity_manager.manager_fragment.data;

import java.util.List;

/**
 * Created by ZWJ on 2017/12/26 0026.
 */

public class OrderHandleData {

    /**
     * EBusinessID : 1257021
     * Success : true
     * LogisticCode : 3967950525457
     * Shippers : [{"ShipperCode":"YD","ShipperName":"韵达快递"}]
     */

    private String EBusinessID;
    private boolean Success;
    private String Code;
    private String LogisticCode;
    private List<ShippersBean> Shippers;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public List<ShippersBean> getShippers() {
        return Shippers;
    }

    public void setShippers(List<ShippersBean> Shippers) {
        this.Shippers = Shippers;
    }

    public static class ShippersBean {
        /**
         * ShipperCode : YD
         * ShipperName : 韵达快递
         */

        private String ShipperCode;
        private String ShipperName;

        public String getShipperCode() {
            return ShipperCode;
        }

        public void setShipperCode(String ShipperCode) {
            this.ShipperCode = ShipperCode;
        }

        public String getShipperName() {
            return ShipperName;
        }

        public void setShipperName(String ShipperName) {
            this.ShipperName = ShipperName;
        }
    }
}
