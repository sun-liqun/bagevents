package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.SaveEventLocationInterface;
import com.bagevent.new_home.new_interface.impl.SaveEventLocationImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnSaveEventLocation;
import com.bagevent.new_home.new_interface.new_view.SaveEventLocationView;

/**
 * Created by zwj on 2016/9/20.
 */
public class SaveLocationPresenter {
    private SaveEventLocationInterface saveEventLocationInterface;
    private SaveEventLocationView saveEventLocationView;

    public SaveLocationPresenter(SaveEventLocationView saveEventLocationView) {
        this.saveEventLocationInterface = new SaveEventLocationImpl();
        this.saveEventLocationView = saveEventLocationView;
    }

    public void saveLocation() {
        saveEventLocationInterface.saveLocation(saveEventLocationView.mContext(),saveEventLocationView.eventId(), saveEventLocationView.userIds(), saveEventLocationView.textName(), saveEventLocationView.addrType(),
                saveEventLocationView.address(), new OnSaveEventLocation() {
                    @Override
                    public void showSaveLocationSuccess() {
                        saveEventLocationView.showSaveLocationSuccess();
                    }

                    @Override
                    public void showSaveLoactionFailed(String errInfo) {
                        saveEventLocationView.showSaveLocationFailed(errInfo);
                    }
                });
    }
}
