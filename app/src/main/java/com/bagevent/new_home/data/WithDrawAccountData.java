package com.bagevent.new_home.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WenJie on 2017/11/16.
 */

public class WithDrawAccountData implements Serializable {

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

    public static class RespObjectBean implements Serializable {
        /**
         * firstWithdrawal : 0
         * account : [{"account":"15555555555","accountName":"steven","bankName":"","outcomeAccountId":9,"type":1},{"account":"17301486665","accountName":"allen","bankName":"","outcomeAccountId":5,"type":1},{"account":"17301486665","accountName":"ss","bankName":"","outcomeAccountId":7,"type":2},{"account":"allen@qq.com","accountName":"scas","bankName":"","outcomeAccountId":8,"type":1}]
         */

        private int firstWithdrawal;
        private List<AccountBean> account;

        public int getFirstWithdrawal() {
            return firstWithdrawal;
        }

        public void setFirstWithdrawal(int firstWithdrawal) {
            this.firstWithdrawal = firstWithdrawal;
        }

        public List<AccountBean> getAccount() {
            return account;
        }

        public void setAccount(List<AccountBean> account) {
            this.account = account;
        }

        public static class AccountBean implements Serializable {
            /**
             * account : 15555555555
             * accountName : steven
             * bankName :
             * outcomeAccountId : 9
             * type : 1
             * hasSubmittedValidationInfo : 1
             */

            private String account;
            private String accountName;
            private String bankName;
            private int outcomeAccountId;
            private int type;
            private String bankIcon;
            private int hasSubmittedValidationInfo;

            public int getHasSubmittedValidationInfo() {
                return hasSubmittedValidationInfo;
            }

            public void setHasSubmittedValidationInfo(int hasSubmittedValidationInfo) {
                this.hasSubmittedValidationInfo = hasSubmittedValidationInfo;
            }

            public String getBankIcon() {
                return bankIcon;
            }

            public void setBankIcon(String bankIcon) {
                this.bankIcon = bankIcon;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public int getOutcomeAccountId() {
                return outcomeAccountId;
            }

            public void setOutcomeAccountId(int outcomeAccountId) {
                this.outcomeAccountId = outcomeAccountId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
