package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/10/10.
 */
public interface SaveEventDetailView {
    Context mContext();

    int eventId();
    String userId();
    String textName();
    String eventContent();
    String eventBrief();
    void saveDetailSuccess();
    void saveDetailFailed(String errInfo);
}
