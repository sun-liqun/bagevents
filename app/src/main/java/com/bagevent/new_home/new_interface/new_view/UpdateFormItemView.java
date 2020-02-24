package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/26.
 */
public interface UpdateFormItemView {
    Context mContext();
    String detailEventId();
    String userId();
    int formFieldId();
    String itemName();
    String itemValue();

    void upDateItemSuccess();
    void upDateItemFailed(String errInfo);
}
