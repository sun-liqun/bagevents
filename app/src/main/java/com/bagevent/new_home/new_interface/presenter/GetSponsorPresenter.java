package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.SponsorData;
import com.bagevent.new_home.new_interface.GetSponsorInterface;
import com.bagevent.new_home.new_interface.impl.GetSponsorImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetSponsorListener;
import com.bagevent.new_home.new_interface.new_view.GetSponsorView;

/**
 * Created by zwj on 2016/9/18.
 */
public class GetSponsorPresenter {
    private GetSponsorView getSponsorView;
    private GetSponsorInterface getSponsor;

    public GetSponsorPresenter(GetSponsorView getSponsorView) {
        this.getSponsorView = getSponsorView;
        this.getSponsor = new GetSponsorImpl();
    }

    public void sponsor() {
        getSponsor.getSponsor(getSponsorView.mContext(),getSponsorView.userId(), new OnGetSponsorListener() {
            @Override
            public void getSuccess(SponsorData reponse) {
                getSponsorView.getSponsor(reponse);
            }

            @Override
            public void getFailed(String errInfo) {
                getSponsorView.getSponsorFailed(errInfo);
            }
        });
    }
}
