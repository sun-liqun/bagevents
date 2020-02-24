package com.bagevent.home.home_interface.view;

import android.content.Context;

import com.bagevent.home.data.ExercisingData;

/**
 * Created by zwj on 2016/5/31.
 */
public interface GetExercisingView {
    String getUserId();
    int getPage();
    int getPageSize();
    String getStatus();
    int getShowType();
    Context getContext();
//    void showSuccess(String data);
    void showSuccess(ExercisingData data);
    void showFailed(String errInfo);
    void showHideView();
    void hideView();
}
