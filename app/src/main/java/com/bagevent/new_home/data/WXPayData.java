package com.bagevent.new_home.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZWJ on 2017/12/15 0015.
 */

public class WXPayData {

    /**
     * respObject : {"package":"Sign=WXPay","paySign":"541B2EECEE4D2590150369B1B1C3E10A","appid":"wx88a7404458473ec0","partnerid":"1489827162","prepayid":"wx201712191836567306103a220569389265","wxReturnData":{"appid":"wx88a7404458473ec0","code_url":"","device_info":"APP","err_code":"","err_code_des":"","mch_id":"1489827162","nonce_str":"BIFd8TiXv8O4fRT2","prepay_id":"wx201712191836567306103a220569389265","result_code":"SUCCESS","return_code":"SUCCESS","return_msg":"OK","sign":"9661C30542C55320964249D61F7BBBE7","trade_type":"APP"},"noncestr":"mg9txfj2va1yt1it54epi57bens6madz","timestamp":"1513679816"}
     * respType : 0
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
         * package : Sign=WXPay
         * paySign : 541B2EECEE4D2590150369B1B1C3E10A
         * appid : wx88a7404458473ec0
         * partnerid : 1489827162
         * prepayid : wx201712191836567306103a220569389265
         * wxReturnData : {"appid":"wx88a7404458473ec0","code_url":"","device_info":"APP","err_code":"","err_code_des":"","mch_id":"1489827162","nonce_str":"BIFd8TiXv8O4fRT2","prepay_id":"wx201712191836567306103a220569389265","result_code":"SUCCESS","return_code":"SUCCESS","return_msg":"OK","sign":"9661C30542C55320964249D61F7BBBE7","trade_type":"APP"}
         * noncestr : mg9txfj2va1yt1it54epi57bens6madz
         * timestamp : 1513679816
         */

        @SerializedName("package")
        private String packageX;
        private String paySign;
        private String appid;
        private String partnerid;
        private String prepayid;
        private WxReturnDataBean wxReturnData;
        private String noncestr;
        private String timestamp;

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPaySign() {
            return paySign;
        }

        public void setPaySign(String paySign) {
            this.paySign = paySign;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public WxReturnDataBean getWxReturnData() {
            return wxReturnData;
        }

        public void setWxReturnData(WxReturnDataBean wxReturnData) {
            this.wxReturnData = wxReturnData;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public static class WxReturnDataBean {
            /**
             * appid : wx88a7404458473ec0
             * code_url :
             * device_info : APP
             * err_code :
             * err_code_des :
             * mch_id : 1489827162
             * nonce_str : BIFd8TiXv8O4fRT2
             * prepay_id : wx201712191836567306103a220569389265
             * result_code : SUCCESS
             * return_code : SUCCESS
             * return_msg : OK
             * sign : 9661C30542C55320964249D61F7BBBE7
             * trade_type : APP
             */

            private String appid;
            private String code_url;
            private String device_info;
            private String err_code;
            private String err_code_des;
            private String mch_id;
            private String nonce_str;
            private String prepay_id;
            private String result_code;
            private String return_code;
            private String return_msg;
            private String sign;
            private String trade_type;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getCode_url() {
                return code_url;
            }

            public void setCode_url(String code_url) {
                this.code_url = code_url;
            }

            public String getDevice_info() {
                return device_info;
            }

            public void setDevice_info(String device_info) {
                this.device_info = device_info;
            }

            public String getErr_code() {
                return err_code;
            }

            public void setErr_code(String err_code) {
                this.err_code = err_code;
            }

            public String getErr_code_des() {
                return err_code_des;
            }

            public void setErr_code_des(String err_code_des) {
                this.err_code_des = err_code_des;
            }

            public String getMch_id() {
                return mch_id;
            }

            public void setMch_id(String mch_id) {
                this.mch_id = mch_id;
            }

            public String getNonce_str() {
                return nonce_str;
            }

            public void setNonce_str(String nonce_str) {
                this.nonce_str = nonce_str;
            }

            public String getPrepay_id() {
                return prepay_id;
            }

            public void setPrepay_id(String prepay_id) {
                this.prepay_id = prepay_id;
            }

            public String getResult_code() {
                return result_code;
            }

            public void setResult_code(String result_code) {
                this.result_code = result_code;
            }

            public String getReturn_code() {
                return return_code;
            }

            public void setReturn_code(String return_code) {
                this.return_code = return_code;
            }

            public String getReturn_msg() {
                return return_msg;
            }

            public void setReturn_msg(String return_msg) {
                this.return_msg = return_msg;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getTrade_type() {
                return trade_type;
            }

            public void setTrade_type(String trade_type) {
                this.trade_type = trade_type;
            }
        }
    }
}
