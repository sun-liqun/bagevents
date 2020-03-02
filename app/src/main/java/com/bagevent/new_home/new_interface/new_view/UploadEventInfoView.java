package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by zwj on 2016/9/21.
 */
public interface UploadEventInfoView {
    Context mContext();
    int eventId();
    String userIds();
    String textName();
    String textValue();

    void upEventLogoSuccess();
    void upEventLogoFailed(String errInfo);
}
