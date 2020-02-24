package com.bagevent.new_home.data;

/**
 * Created by ZWJ on 2018/1/8 0008.
 */

public class AlipayData {

    /**
     * respObject : {"orderStr":"app_id=2017092608938337&biz_content=%7B%22out_trade_no%22%3A%22118010883585586%22%2C%22total_amount%22%3A%2210.0%22%2C%22subject%22%3A%22%E7%9F%AD%E4%BF%A1%E5%85%85%E5%80%BC%22%2C%22body%22%3A%22%E7%99%BE%E6%A0%BC%E6%B4%BB%E5%8A%A8%20-%20%E7%9F%AD%E4%BF%A1%E5%85%85%E5%80%BC%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fwww.bagevent.com%2Fpay%2Fnotify&sign_type=RSA2&timestamp=2017-12-29%2011%3A20%3A58&version=1.0&signKP%2FYrC0lgwY7kWgp%2FIoA0fkUrxiJM1csNccFM3l51SRDnuv%2BJnDqq2RuM%2B6Ywp03%2Bh1zg7UIIsiZ6fPRvdy%2FTqO%2FHq45P9JJHK1k7%2B8U9y2SDjKmR%2BkkPwrcDYDuqZqo9tVLR8TYcd6cD2ijzp5FAYWOACybyfm%2BPp2b4CtQZgFCpPjBFMGCT6vKS7xOwpQpic9scx1%2B2F7%2BLt3UPZmI08z6aMg5cV8ZWFOpu0qC%2BYn5elRglrgzZEk%2Bts0e1MjcOHUqbtmMvvoCmZXZByTqPZWcL6FrmvKaUEk%2FJZLOMfAeTlMaLRIw2ZR1VoY0ZJ8Suq00FYAUbVoqEP5sWhuc8Q%3D%3D"}
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
         * orderStr : app_id=2017092608938337&biz_content=%7B%22out_trade_no%22%3A%22118010883585586%22%2C%22total_amount%22%3A%2210.0%22%2C%22subject%22%3A%22%E7%9F%AD%E4%BF%A1%E5%85%85%E5%80%BC%22%2C%22body%22%3A%22%E7%99%BE%E6%A0%BC%E6%B4%BB%E5%8A%A8%20-%20%E7%9F%AD%E4%BF%A1%E5%85%85%E5%80%BC%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fwww.bagevent.com%2Fpay%2Fnotify&sign_type=RSA2&timestamp=2017-12-29%2011%3A20%3A58&version=1.0&signKP%2FYrC0lgwY7kWgp%2FIoA0fkUrxiJM1csNccFM3l51SRDnuv%2BJnDqq2RuM%2B6Ywp03%2Bh1zg7UIIsiZ6fPRvdy%2FTqO%2FHq45P9JJHK1k7%2B8U9y2SDjKmR%2BkkPwrcDYDuqZqo9tVLR8TYcd6cD2ijzp5FAYWOACybyfm%2BPp2b4CtQZgFCpPjBFMGCT6vKS7xOwpQpic9scx1%2B2F7%2BLt3UPZmI08z6aMg5cV8ZWFOpu0qC%2BYn5elRglrgzZEk%2Bts0e1MjcOHUqbtmMvvoCmZXZByTqPZWcL6FrmvKaUEk%2FJZLOMfAeTlMaLRIw2ZR1VoY0ZJ8Suq00FYAUbVoqEP5sWhuc8Q%3D%3D
         */

        private String orderStr;

        public String getOrderStr() {
            return orderStr;
        }

        public void setOrderStr(String orderStr) {
            this.orderStr = orderStr;
        }
    }
}
