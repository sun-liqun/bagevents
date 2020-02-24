package com.bagevent.new_home.data;

/**
 * Created by zwj on 2016/9/2.
 */
public class IncomeData {

    /**
     * rmbIncome : 10
     * dollarIncome : 0
     * newAttendees : 3
     */

    private RespObjectBean respObject;
    /**
     * respObject : {"rmbIncome":10,"dollarIncome":0,"newAttendees":3}
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
        private double rmbIncome;
        private double dollarIncome;
        private double todayRmbIncome;
        private double todayDollarIncome;
        private int newAttendees;
        private int todayAttendees;

        public double getTodayRmbIncome() {
            return todayRmbIncome;
        }

        public void setTodayRmbIncome(double todayRmbIncome) {
            this.todayRmbIncome = todayRmbIncome;
        }

        public double getTodayDollarIncome() {
            return todayDollarIncome;
        }

        public void setTodayDollarIncome(double todayDollarIncome) {
            this.todayDollarIncome = todayDollarIncome;
        }

        public int getTodayAttendees() {
            return todayAttendees;
        }

        public void setTodayAttendees(int todayAttendees) {
            this.todayAttendees = todayAttendees;
        }

        public double getRmbIncome() {
            return rmbIncome;
        }

        public void setRmbIncome(double rmbIncome) {
            this.rmbIncome = rmbIncome;
        }

        public double getDollarIncome() {
            return dollarIncome;
        }

        public void setDollarIncome(double dollarIncome) {
            this.dollarIncome = dollarIncome;
        }

        public int getNewAttendees() {
            return newAttendees;
        }

        public void setNewAttendees(int newAttendees) {
            this.newAttendees = newAttendees;
        }
    }
}
