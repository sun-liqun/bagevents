package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/20.
 */
public interface SaveEventLocationView {
    Context mContext();

    int eventId();
    String userIds();
    String textName();
    int addrType();
    String address();

    void showSaveLocationSuccess();
    void showSaveLocationFailed(String errInfo);
}
