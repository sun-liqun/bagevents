package com.bagevent.new_home.data;

/**
 * Created by zwj on 2016/10/11.
 */
public class UserInfoData {

    /**
     * balance : 0.16
     * cellphone :
     * company : 南京弟齐
     * dollarBalance : 0
     * email : allen@eventslack.com
     * emailBalance : 1999
     * nickName : allen
     * smsBalance : 0
     * title : 开发
     * userId : 87
     * userName :
     */

    private RespObjectBean respObject;
    /**
     * respObject : {"balance":0.16,"cellphone":"","company":"南京弟齐","dollarBalance":0,"email":"allen@eventslack.com","emailBalance":1999,"nickName":"allen","smsBalance":0,"title":"开发","userId":87,"userName":""}
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
        private double balance;
        private String cellphone;
        private String company;
        private double dollarBalance;
        private String email;
        private int emailBalance;
        private String nickName;
        private int smsBalance;
        private String title;
        private int userId;
        private String userName;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
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

        public double getDollarBalance() {
            return dollarBalance;
        }

        public void setDollarBalance(double dollarBalance) {
            this.dollarBalance = dollarBalance;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getEmailBalance() {
            return emailBalance;
        }

        public void setEmailBalance(int emailBalance) {
            this.emailBalance = emailBalance;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getSmsBalance() {
            return smsBalance;
        }

        public void setSmsBalance(int smsBalance) {
            this.smsBalance = smsBalance;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
