package com.bagevent.new_home.new_interface.new_view;

import android.content.Context;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public interface GetWithDrawValiCodeView {
    Context mContext();
    String userId();
    int type();

    void showValicodeSuccess(String response);
    void showValicodeFailed(String errInfo);
}
