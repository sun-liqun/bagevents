package com.bagevent.register.registerview;

import android.content.Context;

/**
 * Created by zwj on 2016/5/27.
 */
public interface NextRegisterViewInterface {
    Context mContext();
    String getPhoneNum();
    void showFailInfo(String errInfo);
    void showPhoneNumIsExist(String errInfo);
    void doSuccess();
}
