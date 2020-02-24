package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.new_home.data.GetAllFormData;
import com.bagevent.new_home.new_interface.GetAllFormInterface;
import com.bagevent.new_home.new_interface.impl.GetAllFormImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetAllFormListener;
import com.bagevent.new_home.new_interface.new_view.GetAllFormView;

/**
 * Created by zwj on 2016/9/22.
 */
public class GetAllFormPresenter {
    private GetAllFormInterface getAllFormInterface;
    private GetAllFormView getAllFormView;

    public GetAllFormPresenter(GetAllFormView getAllFormView) {
        this.getAllFormInterface = new GetAllFormImpl();
        this.getAllFormView = getAllFormView;
    }

    public void allForm() {
        getAllFormInterface.getAllForm(getAllFormView.mContext(),getAllFormView.detailEventId(), getAllFormView.userId(), new OnGetAllFormListener() {
            @Override
            public void getAllFormSuccess(GetAllFormData formType) {
                getAllFormView.showAllForm(formType);
            }

            @Override
            public void getAllFormFailed(String errInfo) {
                getAllFormView.showAllFormFailed(errInfo);
            }
        });
    }
}
