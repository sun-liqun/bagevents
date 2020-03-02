package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.AddFormResponse;
import com.bagevent.new_home.new_interface.AddFormInterface;
import com.bagevent.new_home.new_interface.impl.AddFormImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormListener;
import com.bagevent.new_home.new_interface.new_view.AddFormView;

/**
 * Created by zwj on 2016/9/23.
 */
public class AddFormPresenter {
    private AddFormInterface addFormInterface;
    private AddFormView addFormView;

    public AddFormPresenter(AddFormView addFormView) {
        this.addFormInterface = new AddFormImpl();
        this.addFormView = addFormView;
    }

    public void addEventForm() {
        addFormInterface.addForm(addFormView.mContext(),addFormView.detailEventId(), addFormView.userId(), addFormView.filedName(),addFormView.showName() ,addFormView.filedType(), new OnAddFormListener() {
            @Override
            public void addFormSuccess(AddFormResponse response) {
                addFormView.eventAddFormSuccess(response);
            }

            @Override
            public void addFormFailed(String errInfo) {
                addFormView.eventAddFormFailed(errInfo);
            }
        });
    }
}
