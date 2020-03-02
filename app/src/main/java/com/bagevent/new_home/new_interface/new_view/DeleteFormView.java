package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/22.
 */
public interface DeleteFormView {
    Context mContext();
    String detailEventId();
    String userId();
    int formFieldId();

    void deleteSuccess();
    void deleteFailed(String errInfo);
}
