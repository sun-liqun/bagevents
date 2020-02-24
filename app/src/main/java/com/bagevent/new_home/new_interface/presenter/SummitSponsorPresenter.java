package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.SummitSponsorInterface;
import com.bagevent.new_home.new_interface.impl.SummitSponsorImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnSummitSponsorListener;
import com.bagevent.new_home.new_interface.new_view.SummitSponsorView;

/**
 * Created by zwj on 2016/9/18.
 */
public class SummitSponsorPresenter {

    private SummitSponsorInterface summitSponsor;
    private SummitSponsorView summitSponsorView;

    public SummitSponsorPresenter(SummitSponsorView summitSponsorView) {
        this.summitSponsorView = summitSponsorView;
        this.summitSponsor = new SummitSponsorImpl();
    }

    public void summit() {
        summitSponsor.summitSponsor(summitSponsorView.mContext(),summitSponsorView.userId(), summitSponsorView.organizerName(), summitSponsorView.contactPhone(), summitSponsorView.contactMail(), summitSponsorView.officialHomePage(),
                summitSponsorView.brief(), summitSponsorView.logoUrl(), new OnSummitSponsorListener() {
                    @Override
                    public void summitSponsorSuccess() {
                        summitSponsorView.summitSponsorSuccess();
                    }

                    @Override
                    public void summitSponsorFailed(String errInfo) {
                        summitSponsorView.summitSponsorFailed(errInfo);
                    }
                });
    }
}
