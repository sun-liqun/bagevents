package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/12.
 */
public interface BindUserCellPhoneView {
    Context mContext();

    String cellPhone();
    String randomCode();
    String userId();
    void bindSuccess();
    void bindFailed(String errInfo);
}
