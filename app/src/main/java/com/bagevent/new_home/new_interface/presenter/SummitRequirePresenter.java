package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.SummitRequireInterface;
import com.bagevent.new_home.new_interface.impl.SummitRequireImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnSummitRequireListener;
import com.bagevent.new_home.new_interface.new_view.SummitRequireView;

/**
 * Created by zwj on 2016/9/13.
 */
public class SummitRequirePresenter {
    private SummitRequireInterface summitRequire;
    private SummitRequireView summitRequireView;

    public SummitRequirePresenter(SummitRequireView summitRequireView) {
        this.summitRequireView = summitRequireView;
        this.summitRequire = new SummitRequireImpl();
    }

    public void summitRequire() {
        summitRequire.summitRequire(summitRequireView.mContext(),summitRequireView.userId(), summitRequireView.cellPhone(),summitRequireView.randomCode(),summitRequireView.demandStartTime(), summitRequireView.demandEndTime(), summitRequireView.cityName(), summitRequireView.demandOfPerson(),
                summitRequireView.demandOtherRequire(), summitRequireView.type(), new OnSummitRequireListener() {
                    @Override
                    public void summitRequireSuccess() {
                        summitRequireView.summitRequireSuccess();
                    }

                    @Override
                    public void summitRequireFailed(String errInfo) {
                        summitRequireView.summitRequireFailed(errInfo);
                    }
                });
    }
}
