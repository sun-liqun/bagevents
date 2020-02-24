package com.bagevent.home.home_interface;

import com.bagevent.home.data.CollectDetailData;

/**
 * Created by zwj on 2016/7/13.
 */
public interface OnGetCollectChildInterface {
    void showSuccess(CollectDetailData data);
    void showFailed();
}
