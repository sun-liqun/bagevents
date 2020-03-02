package com.bagevent.new_home.data;

import java.util.List;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/04/04
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class Test {

    /**
     * failureRetStatus : 200
     * nextPage :
     * respData : null
     * respObject : {"exhibitorAttendeeDTO":{"attendeeId":2316673,"createTime":"2019-04-03 18:05","exhibitorAttendeeId":31,"exhibitorId":121,"type":0,"updateTime":"2019-04-04 11:06"},"attendeeName":"测试","exhibitorAttendeeTagList":[{"createTime":"","exhibitorAttendeeId":31,"exhibitorAttendeeTagId":93,"remark":"","remarkId":142,"tagName":"潜在客户","updateTime":""},{"createTime":"","exhibitorAttendeeId":31,"exhibitorAttendeeTagId":94,"remark":"","remarkId":143,"tagName":"意向客户","updateTime":""}],"company":null}
     * respType : 0
     * responseType : 0
     * resultObject :
     * retStatus : 200
     * success : true
     */

    private int failureRetStatus;
    private String nextPage;
    private Object respData;
    private RespObjectBean respObject;
    private int respType;
    private int responseType;
    private String resultObject;
    private int retStatus;
    private boolean success;

    public int getFailureRetStatus() {
        return failureRetStatus;
    }

    public void setFailureRetStatus(int failureRetStatus) {
        this.failureRetStatus = failureRetStatus;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public Object getRespData() {
        return respData;
    }

    public void setRespData(Object respData) {
        this.respData = respData;
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

    public int getResponseType() {
        return responseType;
    }

    public void setResponseType(int responseType) {
        this.responseType = responseType;
    }

    public String getResultObject() {
        return resultObject;
    }

    public void setResultObject(String resultObject) {
        this.resultObject = resultObject;
    }

    public int getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(int retStatus) {
        this.retStatus = retStatus;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class RespObjectBean {
        /**
         * exhibitorAttendeeDTO : {"attendeeId":2316673,"createTime":"2019-04-03 18:05","exhibitorAttendeeId":31,"exhibitorId":121,"type":0,"updateTime":"2019-04-04 11:06"}
         * attendeeName : 测试
         * exhibitorAttendeeTagList : [{"createTime":"","exhibitorAttendeeId":31,"exhibitorAttendeeTagId":93,"remark":"","remarkId":142,"tagName":"潜在客户","updateTime":""},{"createTime":"","exhibitorAttendeeId":31,"exhibitorAttendeeTagId":94,"remark":"","remarkId":143,"tagName":"意向客户","updateTime":""}]
         * company : null
         */

        private ExhibitorAttendeeDTOBean exhibitorAttendeeDTO;
        private String attendeeName;
        private Object company;
        private List<ExhibitorAttendeeTagListBean> exhibitorAttendeeTagList;

        public ExhibitorAttendeeDTOBean getExhibitorAttendeeDTO() {
            return exhibitorAttendeeDTO;
        }

        public void setExhibitorAttendeeDTO(ExhibitorAttendeeDTOBean exhibitorAttendeeDTO) {
            this.exhibitorAttendeeDTO = exhibitorAttendeeDTO;
        }

        public String getAttendeeName() {
            return attendeeName;
        }

        public void setAttendeeName(String attendeeName) {
            this.attendeeName = attendeeName;
        }

        public Object getCompany() {
            return company;
        }

        public void setCompany(Object company) {
            this.company = company;
        }

        public List<ExhibitorAttendeeTagListBean> getExhibitorAttendeeTagList() {
            return exhibitorAttendeeTagList;
        }

        public void setExhibitorAttendeeTagList(List<ExhibitorAttendeeTagListBean> exhibitorAttendeeTagList) {
            this.exhibitorAttendeeTagList = exhibitorAttendeeTagList;
        }

        public static class ExhibitorAttendeeDTOBean {
            /**
             * attendeeId : 2316673
             * createTime : 2019-04-03 18:05
             * exhibitorAttendeeId : 31
             * exhibitorId : 121
             * type : 0
             * updateTime : 2019-04-04 11:06
             */

            private int attendeeId;
            private String createTime;
            private int exhibitorAttendeeId;
            private int exhibitorId;
            private int type;
            private String updateTime;

            public int getAttendeeId() {
                return attendeeId;
            }

            public void setAttendeeId(int attendeeId) {
                this.attendeeId = attendeeId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getExhibitorAttendeeId() {
                return exhibitorAttendeeId;
            }

            public void setExhibitorAttendeeId(int exhibitorAttendeeId) {
                this.exhibitorAttendeeId = exhibitorAttendeeId;
            }

            public int getExhibitorId() {
                return exhibitorId;
            }

            public void setExhibitorId(int exhibitorId) {
                this.exhibitorId = exhibitorId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }

        public static class ExhibitorAttendeeTagListBean {
            /**
             * createTime :
             * exhibitorAttendeeId : 31
             * exhibitorAttendeeTagId : 93
             * remark :
             * remarkId : 142
             * tagName : 潜在客户
             * updateTime :
             */

            private String createTime;
            private int exhibitorAttendeeId;
            private int exhibitorAttendeeTagId;
            private String remark;
            private int remarkId;
            private String tagName;
            private String updateTime;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getExhibitorAttendeeId() {
                return exhibitorAttendeeId;
            }

            public void setExhibitorAttendeeId(int exhibitorAttendeeId) {
                this.exhibitorAttendeeId = exhibitorAttendeeId;
            }

            public int getExhibitorAttendeeTagId() {
                return exhibitorAttendeeTagId;
            }

            public void setExhibitorAttendeeTagId(int exhibitorAttendeeTagId) {
                this.exhibitorAttendeeTagId = exhibitorAttendeeTagId;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getRemarkId() {
                return remarkId;
            }

            public void setRemarkId(int remarkId) {
                this.remarkId = remarkId;
            }

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }

    @Override
    public String toString() {
        return "Test{" +
                "failureRetStatus=" + failureRetStatus +
                ", nextPage='" + nextPage + '\'' +
                ", respData=" + respData +
                ", respObject=" + respObject +
                ", respType=" + respType +
                ", responseType=" + responseType +
                ", resultObject='" + resultObject + '\'' +
                ", retStatus=" + retStatus +
                ", success=" + success +
                '}';
    }
}
