package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/10/13.
 */
public interface UpdateUserInfoView {
    Context mContext();
    String userId();
    String formName();
    String formValue();

    void updateUserInfoSuccess();
    void updateUserInfoFailed(String errInfo);
}
