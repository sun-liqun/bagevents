package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.SaveEventTimeInterface;
import com.bagevent.new_home.new_interface.impl.SaveEventTimeImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnSaveEventTimeListener;
import com.bagevent.new_home.new_interface.new_view.SaveEventTimeView;

/**
 * Created by zwj on 2016/9/20.
 */
public class SaveEventTimePresenter {
    private SaveEventTimeInterface saveEventTimeInterface;
    private SaveEventTimeView saveEventInfoView;

    public SaveEventTimePresenter(SaveEventTimeView saveEventInfoView) {
        this.saveEventTimeInterface = new SaveEventTimeImpl();
        this.saveEventInfoView = saveEventInfoView;
    }

    public void saveEventTime() {
        saveEventTimeInterface.saveEventInfo(saveEventInfoView.mContext(),saveEventInfoView.eventId(), saveEventInfoView.userId(), saveEventInfoView.textName(), saveEventInfoView.startTime(), saveEventInfoView.endTime(), new OnSaveEventTimeListener() {
            @Override
            public void showSaveSuccess() {
                saveEventInfoView.showSaveTimeSuccess();
            }

            @Override
            public void showSaveFailed(String errInfo) {
                saveEventInfoView.showSaveTimeFailed(errInfo);
            }
        });
    }

}
