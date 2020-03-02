package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.SaveSponsorInterface;
import com.bagevent.new_home.new_interface.impl.SaveSponsorImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnSaveSponsorListener;
import com.bagevent.new_home.new_interface.new_view.SaveSponsorView;

/**
 * Created by zwj on 2016/9/20.
 */
public class SaveSponsorPresenter {
    private SaveSponsorInterface saveSponsorInterface;
    private SaveSponsorView saveSponsorView;

    public SaveSponsorPresenter(SaveSponsorView saveSponsorView) {
        this.saveSponsorInterface = new SaveSponsorImpl();
        this.saveSponsorView = saveSponsorView;
    }

    public void saveEventSponsor() {
        saveSponsorInterface.saveSponsor(saveSponsorView.mContext(),saveSponsorView.eventId(), saveSponsorView.userId(), saveSponsorView.textName(), saveSponsorView.organizerIds(), new OnSaveSponsorListener() {
            @Override
            public void showSaveSponsorSuccess() {
                saveSponsorView.saveSponsorsuccess();
            }

            @Override
            public void showSaveSponsorFailed(String errInfo) {
                saveSponsorView.saveSponsorFailed(errInfo);
            }
        });
    }
}
