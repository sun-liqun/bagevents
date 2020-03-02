package com.bagevent.activity_manager.manager_fragment.data;

/**
 * Created by ZWJ on 2017/12/27 0027.
 */

public class OrderServiceData {

    /**
     * EBusinessID : 1237100
     * Order : {"OrderCode":"012657700387","ShipperCode":"HTKY","LogisticCode":"50002498503427","MarkDestination":"京-朝阳(京-1)","OriginCode":"200000","OriginName":"上海分拨中心","PackageCode":"北京"}
     * PrintTemplate : 此处省略打印模板HTML内容
     * EstimatedDeliveryTime : 2016-03-06
     * Callback : 调用时传入的Callback
     * Success : true
     * ResultCode : 100
     * Reason : 成功
     */

    private String EBusinessID;
    private OrderBean Order;
    private String PrintTemplate;
    private String EstimatedDeliveryTime;
    private String Callback;
    private boolean Success;
    private String ResultCode;
    private String Reason;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public OrderBean getOrder() {
        return Order;
    }

    public void setOrder(OrderBean Order) {
        this.Order = Order;
    }

    public String getPrintTemplate() {
        return PrintTemplate;
    }

    public void setPrintTemplate(String PrintTemplate) {
        this.PrintTemplate = PrintTemplate;
    }

    public String getEstimatedDeliveryTime() {
        return EstimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String EstimatedDeliveryTime) {
        this.EstimatedDeliveryTime = EstimatedDeliveryTime;
    }

    public String getCallback() {
        return Callback;
    }

    public void setCallback(String Callback) {
        this.Callback = Callback;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String ResultCode) {
        this.ResultCode = ResultCode;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public static class OrderBean {
        /**
         * OrderCode : 012657700387
         * ShipperCode : HTKY
         * LogisticCode : 50002498503427
         * MarkDestination : 京-朝阳(京-1)
         * OriginCode : 200000
         * OriginName : 上海分拨中心
         * PackageCode : 北京
         */

        private String OrderCode;
        private String ShipperCode;
        private String LogisticCode;
        private String MarkDestination;
        private String OriginCode;
        private String OriginName;
        private String PackageCode;

        public String getOrderCode() {
            return OrderCode;
        }

        public void setOrderCode(String OrderCode) {
            this.OrderCode = OrderCode;
        }

        public String getShipperCode() {
            return ShipperCode;
        }

        public void setShipperCode(String ShipperCode) {
            this.ShipperCode = ShipperCode;
        }

        public String getLogisticCode() {
            return LogisticCode;
        }

        public void setLogisticCode(String LogisticCode) {
            this.LogisticCode = LogisticCode;
        }

        public String getMarkDestination() {
            return MarkDestination;
        }

        public void setMarkDestination(String MarkDestination) {
            this.MarkDestination = MarkDestination;
        }

        public String getOriginCode() {
            return OriginCode;
        }

        public void setOriginCode(String OriginCode) {
            this.OriginCode = OriginCode;
        }

        public String getOriginName() {
            return OriginName;
        }

        public void setOriginName(String OriginName) {
            this.OriginName = OriginName;
        }

        public String getPackageCode() {
            return PackageCode;
        }

        public void setPackageCode(String PackageCode) {
            this.PackageCode = PackageCode;
        }
    }
}
