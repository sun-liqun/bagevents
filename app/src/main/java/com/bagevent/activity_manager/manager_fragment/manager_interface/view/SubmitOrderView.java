package com.bagevent.activity_manager.manager_fragment.manager_interface.view;

import android.content.Context;

import com.bagevent.activity_manager.manager_fragment.data.FormData;
import com.bagevent.activity_manager.manager_fragment.data.ModifyData;

/**
 * Created by zwj on 2016/7/5.
 */
public interface SubmitOrderView {
    Context mContext();
    int eventId();
    String map();
    String buyWay();
    void showSuccess(ModifyData submitData,String ref_code);
    void showFailed(ModifyData submitData);
}
