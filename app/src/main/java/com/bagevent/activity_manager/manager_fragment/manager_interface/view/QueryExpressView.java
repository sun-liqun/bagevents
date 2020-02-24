package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

/**
 * Created by ZWJ on 2017/12/26 0026.
 */

public interface QueryExpressView {
    Context mContext();
    String requestData();
    String eBusinessID();
    String requestType();
    String dataSign();

    void querySuccess();
    void queryFailed();
}
