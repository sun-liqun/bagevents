package com.bagevent.new_home.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by zwj on 2016/10/11.
 */
public class EventTotalIncome {

    /**
     * eventList : [{"currencySign":"￥","eventId":5,"eventName":"发行路演","offlineIncome":6200,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":22,"eventName":"百格活动2016首期创业沙龙","offlineIncome":100000.06999999998,"onlineIncome":0.15000000000000002,"refundPrice":30000.01,"status":2},{"currencySign":"￥","eventId":11496,"eventName":"2015年度（第七届）中国数据中心行业颁奖大会","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":46942,"eventName":"摄影收集大赛","offlineIncome":20,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":87920,"eventName":"架构高峰论坛","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":140496,"eventName":"","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":140876,"eventName":"2015年度（第七届）中国数据中心行业颁奖大会-copy","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":0},{"currencySign":"￥","eventId":155030,"eventName":"跨境电商大会","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":155053,"eventName":"让人太多让人","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":10},{"currencySign":"￥","eventId":155199,"eventName":"Android技术前沿","offlineIncome":200,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":197125,"eventName":"dhh","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":10},{"currencySign":"￥","eventId":234497,"eventName":"allen的学术会议","offlineIncome":0,"onlineIncome":0.01,"refundPrice":0,"status":2},{"currencySign":"￥","eventId":234621,"eventName":"打广告过分","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":10},{"currencySign":"￥","eventId":234705,"eventName":"dhh-copy","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":0},{"currencySign":"￥","eventId":234780,"eventName":"关于好胡","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":10}]
     * totalOnlineDollar : 0
     * totalOffline : 106420.06999999998
     * totalOnline : 0.16000000000000003
     * totalOfflineDollar : 0
     */

    private RespObjectBean respObject;
    /**
     * respObject : {"eventList":[{"currencySign":"￥","eventId":5,"eventName":"发行路演","offlineIncome":6200,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":22,"eventName":"百格活动2016首期创业沙龙","offlineIncome":100000.06999999998,"onlineIncome":0.15000000000000002,"refundPrice":30000.01,"status":2},{"currencySign":"￥","eventId":11496,"eventName":"2015年度（第七届）中国数据中心行业颁奖大会","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":46942,"eventName":"摄影收集大赛","offlineIncome":20,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":87920,"eventName":"架构高峰论坛","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":140496,"eventName":"","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":140876,"eventName":"2015年度（第七届）中国数据中心行业颁奖大会-copy","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":0},{"currencySign":"￥","eventId":155030,"eventName":"跨境电商大会","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":155053,"eventName":"让人太多让人","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":10},{"currencySign":"￥","eventId":155199,"eventName":"Android技术前沿","offlineIncome":200,"onlineIncome":0,"refundPrice":0,"status":3},{"currencySign":"￥","eventId":197125,"eventName":"dhh","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":10},{"currencySign":"￥","eventId":234497,"eventName":"allen的学术会议","offlineIncome":0,"onlineIncome":0.01,"refundPrice":0,"status":2},{"currencySign":"￥","eventId":234621,"eventName":"打广告过分","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":10},{"currencySign":"￥","eventId":234705,"eventName":"dhh-copy","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":0},{"currencySign":"￥","eventId":234780,"eventName":"关于好胡","offlineIncome":0,"onlineIncome":0,"refundPrice":0,"status":10}],"totalOnlineDollar":0,"totalOffline":106420.06999999998,"totalOnline":0.16000000000000003,"totalOfflineDollar":0}
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
        private double totalOnlineDollar;
        private double totalOffline;
        private double totalOnline;
        private double totalOfflineDollar;
        /**
         * currencySign : ￥
         * eventId : 5
         * eventName : 发行路演
         * offlineIncome : 6200
         * onlineIncome : 0
         * refundPrice : 0
         * status : 3
         */

        private List<EventListBean> eventList;

        public double getTotalOnlineDollar() {
            return totalOnlineDollar;
        }

        public void setTotalOnlineDollar(double totalOnlineDollar) {
            this.totalOnlineDollar = totalOnlineDollar;
        }

        public double getTotalOffline() {
            return totalOffline;
        }

        public void setTotalOffline(double totalOffline) {
            this.totalOffline = totalOffline;
        }

        public double getTotalOnline() {
            return totalOnline;
        }

        public void setTotalOnline(double totalOnline) {
            this.totalOnline = totalOnline;
        }

        public double getTotalOfflineDollar() {
            return totalOfflineDollar;
        }

        public void setTotalOfflineDollar(double totalOfflineDollar) {
            this.totalOfflineDollar = totalOfflineDollar;
        }

        public List<EventListBean> getEventList() {
            return eventList;
        }

        public void setEventList(List<EventListBean> eventList) {
            this.eventList = eventList;
        }

        public static class EventListBean {
            private String currencySign;
            private int eventId;
            private String eventName;
            private double offlineIncome;
            private double onlineIncome;
            private double refundPrice;
            private int status;

            public String getCurrencySign() {
                return currencySign;
            }

            public void setCurrencySign(String currencySign) {
                this.currencySign = currencySign;
            }

            public int getEventId() {
                return eventId;
            }

            public void setEventId(int eventId) {
                this.eventId = eventId;
            }

            public String getEventName() {
                return eventName;
            }

            public void setEventName(String eventName) {
                this.eventName = eventName;
            }

            public double getOfflineIncome() {
                return offlineIncome;
            }

            public void setOfflineIncome(double offlineIncome) {
                this.offlineIncome = offlineIncome;
            }

            public double getOnlineIncome() {
                return onlineIncome;
            }

            public void setOnlineIncome(double onlineIncome) {
                this.onlineIncome = onlineIncome;
            }

            public double getRefundPrice() {
                return refundPrice;
            }

            public void setRefundPrice(double refundPrice) {
                this.refundPrice = refundPrice;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

        }
    }
}
