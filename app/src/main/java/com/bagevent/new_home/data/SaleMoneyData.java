package com.bagevent.new_home.data;

import java.util.List;

/**
 * Created by zwj on 2016/9/2.
 */
public class SaleMoneyData {

    /**
     * respObject : [{"rmbSaleMoney":0,"dollarSaleMoney":0,"orderTime":"08-24"},{"rmbSaleMoney":10,"dollarSaleMoney":0,"orderTime":"08-23"},{"rmbSaleMoney":10,"dollarSaleMoney":0,"orderTime":"08-16"},{"rmbSaleMoney":15000,"dollarSaleMoney":0,"orderTime":"08-15"},{"rmbSaleMoney":0,"dollarSaleMoney":0,"orderTime":"08-10"},{"rmbSaleMoney":10000.01,"dollarSaleMoney":0,"orderTime":"08-09"},{"rmbSaleMoney":0,"dollarSaleMoney":0,"orderTime":"08-08"}]
     * respType : 1
     * retStatus : 200
     */

    private int respType;
    private int retStatus;
    /**
     * rmbSaleMoney : 0
     * dollarSaleMoney : 0
     * orderTime : 08-24
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
        private double rmbSaleMoney;
        private double dollarSaleMoney;
        private String orderTime;

        public double getRmbSaleMoney() {
            return rmbSaleMoney;
        }

        public void setRmbSaleMoney(double rmbSaleMoney) {
            this.rmbSaleMoney = rmbSaleMoney;
        }

        public double getDollarSaleMoney() {
            return dollarSaleMoney;
        }

        public void setDollarSaleMoney(double dollarSaleMoney) {
            this.dollarSaleMoney = dollarSaleMoney;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }
    }
}
