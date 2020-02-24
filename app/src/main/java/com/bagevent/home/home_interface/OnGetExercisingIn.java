package com.bagevent.home.home_interface;

import com.bagevent.home.data.ExercisingData;

/**
 * Created by zwj on 2016/5/31.
 */
public interface OnGetExercisingIn {
    void getSuccess(ExercisingData data);
//    void getSuccess(String response);
    void getFailed(String errInfo);
}
