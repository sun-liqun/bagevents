package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.SaveEventDetailInterface;
import com.bagevent.new_home.new_interface.impl.SaveEventDetailImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnSaveEventDetailListener;
import com.bagevent.new_home.new_interface.new_view.SaveEventDetailView;

/**
 * Created by zwj on 2016/10/10.
 */
public class SaveEventDetailPresenter {
    private SaveEventDetailInterface saveEventDetailInterface;
    private SaveEventDetailView saveEventDetailView;

    public SaveEventDetailPresenter(SaveEventDetailView saveEventDetailView) {
        this.saveEventDetailInterface = new SaveEventDetailImpl();
        this.saveEventDetailView = saveEventDetailView;
    }

    public void saveEventDetail() {
        saveEventDetailInterface.saveEventDetail(saveEventDetailView.mContext(),saveEventDetailView.eventId(), saveEventDetailView.userId(),
                saveEventDetailView.textName(), saveEventDetailView.eventContent(), saveEventDetailView.eventBrief(), new OnSaveEventDetailListener() {
            @Override
            public void saveDetailSuccess() {
                saveEventDetailView.saveDetailSuccess();
            }

            @Override
            public void saveDetailFailed(String errInfo) {
                saveEventDetailView.saveDetailFailed(errInfo);
            }
        });
    }
}
