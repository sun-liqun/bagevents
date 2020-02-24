package com.bagevent.new_home.data;

import java.util.List;

/**
 * Created by zwj on 2017/10/23.
 */

public class MsgRecordData {


    private RespObjectBean respObject;
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
        /**
         * objects : [{"pay_status":1,"create_time":"2017-10-16 14:37:41","order_number":"123456789","fee":10,"pay_way":0}]
         * pagination : {"currentTime":1508726569249,"objectCount":1,"pageCount":1,"pageNumber":1,"pageSize":2}
         * syncTime : 0
         */

        private PaginationBean pagination;
        private int syncTime;
        private List<ObjectsBean> objects;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public int getSyncTime() {
            return syncTime;
        }

        public void setSyncTime(int syncTime) {
            this.syncTime = syncTime;
        }

        public List<ObjectsBean> getObjects() {
            return objects;
        }

        public void setObjects(List<ObjectsBean> objects) {
            this.objects = objects;
        }

        public static class PaginationBean {
            /**
             * currentTime : 1508726569249
             * objectCount : 1
             * pageCount : 1
             * pageNumber : 1
             * pageSize : 2
             */

            private long currentTime;
            private int objectCount;
            private int pageCount;
            private int pageNumber;
            private int pageSize;

            public long getCurrentTime() {
                return currentTime;
            }

            public void setCurrentTime(long currentTime) {
                this.currentTime = currentTime;
            }

            public int getObjectCount() {
                return objectCount;
            }

            public void setObjectCount(int objectCount) {
                this.objectCount = objectCount;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }
        }

        public static class ObjectsBean {
            /**
             * pay_status : 1
             * create_time : 2017-10-16 14:37:41
             * order_number : 123456789
             * fee : 10
             * pay_way : 0
             */

            private int pay_status;
            private String create_time;
            private String order_number;
            private double fee;
            private int pay_way;

            public int getPay_status() {
                return pay_status;
            }

            public void setPay_status(int pay_status) {
                this.pay_status = pay_status;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getOrder_number() {
                return order_number;
            }

            public void setOrder_number(String order_number) {
                this.order_number = order_number;
            }

            public double getFee() {
                return fee;
            }

            public void setFee(double fee) {
                this.fee = fee;
            }

            public int getPay_way() {
                return pay_way;
            }

            public void setPay_way(int pay_way) {
                this.pay_way = pay_way;
            }
        }
    }
}
