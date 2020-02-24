package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.FormType;

/**
 * Created by zwj on 2016/6/27.
 */
public interface GetFormTypeView {
    Context mContext();
    String detailEventId();
    int sType();

    Context getContext();

    void showDetailInfo(FormType formType);
    void showDetailErrInfo(String errInfo);
    void showBadgeFormInfo(FormType formType);
    void showBadgeFormErrInfo(String errInfo);
}
