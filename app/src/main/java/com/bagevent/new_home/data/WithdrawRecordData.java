package com.bagevent.new_home.data;

import java.util.List;

/**
 * Created by ZWJ on 2017/12/11 0011.
 */

public class WithdrawRecordData {

    /**
     * respObject : {"objects":[{"outcome_time":"2016-04-23 10:31:35","amount":0,"total_amount":0,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2016-04-23 10:36:22","amount":0,"total_amount":0,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2016-09-27 10:59:53","amount":98,"total_amount":100,"outcome_type":0,"pay_type":1,"type":2,"account":"622787777777777777777","handle_time":""},{"outcome_time":"2016-09-27 11:00:41","amount":98,"total_amount":100,"outcome_type":0,"pay_type":1,"type":2,"account":"622787777777777777777","handle_time":""},{"outcome_time":"2017-05-18 18:21:35","amount":4900,"total_amount":5000,"outcome_type":0,"pay_type":1,"type":2,"account":"622787777777777777777","handle_time":""},{"outcome_time":"2017-12-06 11:24:26","amount":555.66,"total_amount":567,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 11:33:15","amount":192.08,"total_amount":196,"outcome_type":0,"pay_type":1,"type":1,"account":"23666","handle_time":""},{"outcome_time":"2017-12-06 11:33:40","amount":192.08,"total_amount":196,"outcome_type":0,"pay_type":1,"type":1,"account":"4588","handle_time":""},{"outcome_time":"2017-12-06 11:34:49","amount":192.08,"total_amount":196,"outcome_type":0,"pay_type":1,"type":1,"account":"23666","handle_time":""},{"outcome_time":"2017-12-06 11:36:04","amount":192.08,"total_amount":196,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 11:38:00","amount":192.08,"total_amount":196,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 11:50:56","amount":196,"total_amount":200,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 11:51:17","amount":196,"total_amount":200,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 11:51:49","amount":196,"total_amount":200,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 14:16:50","amount":196,"total_amount":200,"outcome_type":0,"pay_type":1,"type":1,"account":"23666","handle_time":""},{"outcome_time":"2017-12-08 14:23:27","amount":98,"total_amount":100,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-08 15:58:34","amount":98,"total_amount":100,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-08 16:03:17","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:03:17"},{"outcome_time":"2017-12-08 16:05:00","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:05:00"},{"outcome_time":"2017-12-08 16:08:01","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:08:01"},{"outcome_time":"2017-12-08 16:14:28","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:14:28"},{"outcome_time":"2017-12-08 16:17:40","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:17:40"},{"outcome_time":"2017-12-08 16:37:14","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:37:14"},{"outcome_time":"2017-12-08 16:37:47","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:37:47"},{"outcome_time":"2017-12-08 16:51:47","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:51:47"},{"outcome_time":"2017-12-08 16:58:04","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:58:04"},{"outcome_time":"2017-12-08 17:00:29","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 17:00:29"},{"outcome_time":"2017-12-08 17:15:28","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 17:15:28"},{"outcome_time":"2017-12-08 17:18:28","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 17:18:28"},{"outcome_time":"2017-12-08 17:22:37","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 17:22:37"},{"outcome_time":"2017-12-08 17:22:53","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 17:22:53"}],"pagination":{"currentTime":1512977755023,"objectCount":31,"pageCount":1,"pageNumber":1,"pageSize":50},"syncTime":0}
     * respType : 1
     * retStatus : 200
     */

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
         * objects : [{"outcome_time":"2016-04-23 10:31:35","amount":0,"total_amount":0,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2016-04-23 10:36:22","amount":0,"total_amount":0,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2016-09-27 10:59:53","amount":98,"total_amount":100,"outcome_type":0,"pay_type":1,"type":2,"account":"622787777777777777777","handle_time":""},{"outcome_time":"2016-09-27 11:00:41","amount":98,"total_amount":100,"outcome_type":0,"pay_type":1,"type":2,"account":"622787777777777777777","handle_time":""},{"outcome_time":"2017-05-18 18:21:35","amount":4900,"total_amount":5000,"outcome_type":0,"pay_type":1,"type":2,"account":"622787777777777777777","handle_time":""},{"outcome_time":"2017-12-06 11:24:26","amount":555.66,"total_amount":567,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 11:33:15","amount":192.08,"total_amount":196,"outcome_type":0,"pay_type":1,"type":1,"account":"23666","handle_time":""},{"outcome_time":"2017-12-06 11:33:40","amount":192.08,"total_amount":196,"outcome_type":0,"pay_type":1,"type":1,"account":"4588","handle_time":""},{"outcome_time":"2017-12-06 11:34:49","amount":192.08,"total_amount":196,"outcome_type":0,"pay_type":1,"type":1,"account":"23666","handle_time":""},{"outcome_time":"2017-12-06 11:36:04","amount":192.08,"total_amount":196,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 11:38:00","amount":192.08,"total_amount":196,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 11:50:56","amount":196,"total_amount":200,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 11:51:17","amount":196,"total_amount":200,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 11:51:49","amount":196,"total_amount":200,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-06 14:16:50","amount":196,"total_amount":200,"outcome_type":0,"pay_type":1,"type":1,"account":"23666","handle_time":""},{"outcome_time":"2017-12-08 14:23:27","amount":98,"total_amount":100,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-08 15:58:34","amount":98,"total_amount":100,"outcome_type":0,"pay_type":1,"type":1,"account":"11223345","handle_time":""},{"outcome_time":"2017-12-08 16:03:17","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:03:17"},{"outcome_time":"2017-12-08 16:05:00","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:05:00"},{"outcome_time":"2017-12-08 16:08:01","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:08:01"},{"outcome_time":"2017-12-08 16:14:28","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:14:28"},{"outcome_time":"2017-12-08 16:17:40","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:17:40"},{"outcome_time":"2017-12-08 16:37:14","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:37:14"},{"outcome_time":"2017-12-08 16:37:47","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:37:47"},{"outcome_time":"2017-12-08 16:51:47","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:51:47"},{"outcome_time":"2017-12-08 16:58:04","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 16:58:04"},{"outcome_time":"2017-12-08 17:00:29","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 17:00:29"},{"outcome_time":"2017-12-08 17:15:28","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 17:15:28"},{"outcome_time":"2017-12-08 17:18:28","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 17:18:28"},{"outcome_time":"2017-12-08 17:22:37","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 17:22:37"},{"outcome_time":"2017-12-08 17:22:53","amount":10,"total_amount":10,"outcome_type":1,"pay_type":25,"handle_time":"2017-12-08 17:22:53"}]
         * pagination : {"currentTime":1512977755023,"objectCount":31,"pageCount":1,"pageNumber":1,"pageSize":50}
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
             * currentTime : 1512977755023
             * objectCount : 31
             * pageCount : 1
             * pageNumber : 1
             * pageSize : 50
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
             * outcome_time : 2016-04-23 10:31:35
             * amount : 0
             * total_amount : 0
             * outcome_type : 0
             * pay_type : 1
             * type : 1
             * account : 11223345
             * handle_time :
             */

            private String outcome_time;
            private float amount;
            private float total_amount;
            private int outcome_type;
            private int pay_type;
            private int type;
            private String account;
            private String handle_time;
            private String pay_type_desc;
            private String type_desc;

            public String getOutcome_time() {
                return outcome_time;
            }

            public void setOutcome_time(String outcome_time) {
                this.outcome_time = outcome_time;
            }

            public float getAmount() {
                return amount;
            }

            public void setAmount(float amount) {
                this.amount = amount;
            }

            public float getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(float total_amount) {
                this.total_amount = total_amount;
            }

            public int getOutcome_type() {
                return outcome_type;
            }

            public String getPay_type_desc() {
                return pay_type_desc;
            }

            public void setPay_type_desc(String pay_type_desc) {
                this.pay_type_desc = pay_type_desc;
            }

            public void setOutcome_type(int outcome_type) {
                this.outcome_type = outcome_type;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getType_desc() {
                return type_desc;
            }

            public void setType_desc(String type_desc) {
                this.type_desc = type_desc;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getHandle_time() {
                return handle_time;
            }

            public void setHandle_time(String handle_time) {
                this.handle_time = handle_time;
            }
        }
    }
}
