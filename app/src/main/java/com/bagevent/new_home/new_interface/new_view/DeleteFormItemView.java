package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/26.
 */
public interface DeleteFormItemView {
    Context mContext();

    String detailEventId();
    String userId();
    int formFieldId();
    String itemName();

    void deleteFormItemSuccess();
    void deleteFormItemFailed(String errInfo);
}
