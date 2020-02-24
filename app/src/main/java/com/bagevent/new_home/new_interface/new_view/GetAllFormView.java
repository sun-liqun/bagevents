package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.GetAllFormData;

/**
 * Created by zwj on 2016/9/22.
 */
public interface GetAllFormView {
    Context mContext();
    String detailEventId();
    String userId();

    void showAllForm(GetAllFormData response);
    void showAllFormFailed(String errInfo);
}
