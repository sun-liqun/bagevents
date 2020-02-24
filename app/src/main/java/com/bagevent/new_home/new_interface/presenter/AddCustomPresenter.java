package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.AddFormResponse;
import com.bagevent.new_home.new_interface.AddFormInterface;
import com.bagevent.new_home.new_interface.impl.AddCustomImpl;
import com.bagevent.new_home.new_interface.impl.AddFormImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormListener;
import com.bagevent.new_home.new_interface.new_view.AddFormView;

/**
 * Created by zwj on 2016/9/23.
 */
public class AddCustomPresenter {
    private AddFormInterface addFormInterface;
    private AddFormView addFormView;

    public AddCustomPresenter(AddFormView addFormView) {
        this.addFormInterface = new AddCustomImpl();
        this.addFormView = addFormView;
    }

    public void addEventForm() {
        addFormInterface.addForm(addFormView.mContext(),addFormView.detailEventId(), addFormView.userId(), addFormView.filedName(),addFormView.showName() ,addFormView.filedType(), new OnAddFormListener() {
            @Override
            public void addFormSuccess(AddFormResponse response) {
                addFormView.eventAddCustomFormSuccess(response);
            }

            @Override
            public void addFormFailed(String errInfo) {
               addFormView.eventAddCustomFormFailed(errInfo);
            }
        });
    }

    public void addMultiForm() {
        addFormInterface.addMutiForm(addFormView.mContext(),addFormView.detailEventId(), addFormView.userId(), addFormView.filedName(),addFormView.showName(),addFormView.items(),addFormView.filedType(), new OnAddFormListener() {
            @Override
            public void addFormSuccess(AddFormResponse response) {
                addFormView.eventAddMultiFormSuccess(response);
            }

            @Override
            public void addFormFailed(String errInfo) {
                addFormView.eventAddMultiFormFailed(errInfo);
            }
        });
    }
}
