package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.UpdateFormInterface;
import com.bagevent.new_home.new_interface.impl.UpdateFormImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateFormListener;
import com.bagevent.new_home.new_interface.new_view.UpdateFormView;

/**
 * Created by zwj on 2016/9/26.
 */
public class UpdateFormPresenter {
    private UpdateFormInterface updateFormInterface;
    private UpdateFormView updateFormView;

    public UpdateFormPresenter(UpdateFormView updateFormView) {
        this.updateFormInterface = new UpdateFormImpl();
        this.updateFormView = updateFormView;
    }

    public void updateForm() {
        updateFormInterface.updateForm(updateFormView.mContext(),updateFormView.detailEventId(), updateFormView.userId(), updateFormView.formFieldId(), updateFormView.type(), updateFormView.formFieldValue(), new OnUpdateFormListener() {
            @Override
            public void updateFormSuceecss() {
                updateFormView.upDateSuccess();
            }

            @Override
            public void updateFormFailed(String errInfo) {
                updateFormView.upDateFailed(errInfo);
            }
        });
    }
}
