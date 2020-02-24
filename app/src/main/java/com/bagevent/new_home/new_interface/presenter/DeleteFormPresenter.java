package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.DeleteFormInterface;
import com.bagevent.new_home.new_interface.impl.DeleteFormImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnDeleteFormFieldIdListener;
import com.bagevent.new_home.new_interface.new_view.DeleteFormView;

/**
 * Created by zwj on 2016/9/22.
 */
public class DeleteFormPresenter {
    private DeleteFormInterface deleteFormInterface;
    private DeleteFormView deleteFormView;

    public DeleteFormPresenter(DeleteFormView deleteFormView) {
        this.deleteFormInterface = new DeleteFormImpl();
        this.deleteFormView = deleteFormView;
    }

    public void delete() {
        deleteFormInterface.deleteForm(deleteFormView.mContext(),deleteFormView.detailEventId(), deleteFormView.userId(), deleteFormView.formFieldId(), new OnDeleteFormFieldIdListener() {
            @Override
            public void deleteFormSuccess() {
                deleteFormView.deleteSuccess();
            }

            @Override
            public void deleteFormFailed(String errInfo) {
                deleteFormView.deleteFailed(errInfo);
            }
        });
    }
}
