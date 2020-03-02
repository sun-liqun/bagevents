package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetFormTypeInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetFormTypeListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetFormTypeView;
import com.bagevent.new_home.new_interface.impl.GetFormContentImpl;

/**
 * Created by zwj on 2016/9/22.
 */
public class GetFormContentPresenter {
    private GetFormTypeInterface getFormTypeInterface;
    private GetFormTypeView getFormTypeView;

    public GetFormContentPresenter(GetFormTypeView getFormTypeView) {
        this.getFormTypeInterface = new GetFormContentImpl();
        this.getFormTypeView = getFormTypeView;
    }

    public void getForm() {
        getFormTypeInterface.getFormType(getFormTypeView.getContext(), getFormTypeView.detailEventId(), new OnGetFormTypeListener() {
            @Override
            public void getFormTypeSuccess(FormType formType) {
                getFormTypeView.showDetailInfo(formType);
            }

            @Override
            public void getFormTypeFailed(String errInfo) {
                getFormTypeView.showDetailErrInfo(errInfo);
            }

            @Override
            public void getBadgeFormTypeSuccess(FormType formType) {

            }

            @Override
            public void getBadgeFormTypeFailed(String errInfo) {

            }
        });
    }
}
