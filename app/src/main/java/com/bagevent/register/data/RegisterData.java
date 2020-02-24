package com.bagevent.register.data;

/**
 * Created by zwj on 2016/5/30.
 */
public class RegisterData {

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
        private long autoLoginExpireTime;
        private String autoLoginToken;
        private String avatar;
        private String cellphone;
        private String company;
        private String email;
        private String nickName;
        private int source;
        private int state;
        private String title;
        private String token;
        private int userId;
        private String userName;

        public long getAutoLoginExpireTime() {
            return autoLoginExpireTime;
        }

        public void setAutoLoginExpireTime(long autoLoginExpireTime) {
            this.autoLoginExpireTime = autoLoginExpireTime;
        }

        public String getAutoLoginToken() {
            return autoLoginToken;
        }

        public void setAutoLoginToken(String autoLoginToken) {
            this.autoLoginToken = autoLoginToken;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
