package com.bagevent.login.model;

import java.io.Serializable;

/**
 * Created by zwj on 2016/5/25.
 */
public class UserInfo {

    private int code;


    private ReturnObjBean returnObj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ReturnObjBean getReturnObj() {
        return returnObj;
    }

    public void setReturnObj(ReturnObjBean returnObj) {
        this.returnObj = returnObj;
    }

    public static class ReturnObjBean {
        private int userId;
        private String email;
        private String cellphone;
        private String userName;
        private String avatar;
        private int source;
        private String token;
        private int state;
        private String autoLoginToken;
        private long autoLoginExpireTime;

        private RuleMapBean ruleMap;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAutoLoginToken() {
            return autoLoginToken;
        }

        public void setAutoLoginToken(String autoLoginToken) {
            this.autoLoginToken = autoLoginToken;
        }

        public long getAutoLoginExpireTime() {
            return autoLoginExpireTime;
        }

        public void setAutoLoginExpireTime(long autoLoginExpireTime) {
            this.autoLoginExpireTime = autoLoginExpireTime;
        }

        public RuleMapBean getRuleMap() {
            return ruleMap;
        }

        public void setRuleMap(RuleMapBean ruleMap) {
            this.ruleMap = ruleMap;
        }

        public static class RuleMapBean {
            /**
             * enterprise : {"functionRuleId":7,"functionCode":"enterprise","createTime":"2016-03-08 00:00:00","expireTime":"2016-05-31 23:59:59"}
             */

            private UserRoleBean userRole;

            public UserRoleBean getUserRole() {
                return userRole;
            }

            public void setUserRole(UserRoleBean userRole) {
                this.userRole = userRole;
            }

            public static class UserRoleBean  {
                /**
                 * functionRuleId : 7
                 * functionCode : enterprise
                 * createTime : 2016-03-08 00:00:00
                 * expireTime : 2016-05-31 23:59:59
                 */

                private EnterpriseBean enterprise;

                public EnterpriseBean getEnterprise() {
                    return enterprise;
                }

                public void setEnterprise(EnterpriseBean enterprise) {
                    this.enterprise = enterprise;
                }

                public static class EnterpriseBean {
                    private int functionRuleId;
                    private String functionCode;
                    private String createTime;
                    private String expireTime;

                    public int getFunctionRuleId() {
                        return functionRuleId;
                    }

                    public void setFunctionRuleId(int functionRuleId) {
                        this.functionRuleId = functionRuleId;
                    }

                    public String getFunctionCode() {
                        return functionCode;
                    }

                    public void setFunctionCode(String functionCode) {
                        this.functionCode = functionCode;
                    }

                    public String getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(String createTime) {
                        this.createTime = createTime;
                    }

                    public String getExpireTime() {
                        return expireTime;
                    }

                    public void setExpireTime(String expireTime) {
                        this.expireTime = expireTime;
                    }
                }
            }
        }
    }
}
