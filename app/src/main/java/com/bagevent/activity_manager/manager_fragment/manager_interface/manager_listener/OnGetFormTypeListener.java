package com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener;

import com.bagevent.activity_manager.manager_fragment.data.FormType;

/**
 * Created by zwj on 2016/6/27.
 */
public interface OnGetFormTypeListener {
    void getFormTypeSuccess(FormType formType);

    void getFormTypeFailed(String errInfo);

    void getBadgeFormTypeSuccess(FormType formType);
    void getBadgeFormTypeFailed(String errInfo);
}
