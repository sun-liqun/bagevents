package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

import com.bagevent.new_home.data.AddFormItemData;

/**
 * Created by zwj on 2016/9/26.
 */
public interface AddFormItemView {
    Context mContext();
    String detailEventId();
    String userId();
    int formFieldId();

    void addFormItemSuccess(AddFormItemData response);
    void addFormItemFailed(String errInfo);
}
