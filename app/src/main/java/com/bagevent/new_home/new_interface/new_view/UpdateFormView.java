package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/26.
 */
public interface UpdateFormView {
    Context mContext();
    String detailEventId();
    String userId();
    int formFieldId();
    int type();
    String formFieldValue();

    void upDateSuccess();
    void upDateFailed(String errInfo);
}
