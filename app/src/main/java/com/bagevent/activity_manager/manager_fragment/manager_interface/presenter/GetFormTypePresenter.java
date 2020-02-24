package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetFormTypeInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.GetFormTypeIms;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetFormTypeListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetFormTypeView;

/**
 * Created by zwj on 2016/6/27.
 */
public class GetFormTypePresenter {
    private GetFormTypeInterface getFormType;
    private GetFormTypeView getFormTypeView;

    public GetFormTypePresenter( GetFormTypeView getFormTypeView) {
        this.getFormType = new GetFormTypeIms();
        this.getFormTypeView = getFormTypeView;
    }

    public void getFormType(){
        getFormType.getFormType(getFormTypeView.getContext(),getFormTypeView.detailEventId(), new OnGetFormTypeListener() {
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

    public void getBadgeFormType() {
        getFormType.getBadgeFormType(getFormTypeView.getContext(), getFormTypeView.detailEventId(), getFormTypeView.sType(), new OnGetFormTypeListener() {
            @Override
            public void getFormTypeSuccess(FormType formType) {
            }

            @Override
            public void getFormTypeFailed(String errInfo) {
            }

            @Override
            public void getBadgeFormTypeSuccess(FormType formType) {
                getFormTypeView.showBadgeFormInfo(formType);

            }

            @Override
            public void getBadgeFormTypeFailed(String errInfo) {
                getFormTypeView.showBadgeFormErrInfo(errInfo);

            }
        });
    }
}
