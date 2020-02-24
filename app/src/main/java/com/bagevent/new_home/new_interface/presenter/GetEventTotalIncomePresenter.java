package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.EventTotalIncome;
import com.bagevent.new_home.new_interface.GetEventTotalIncomeInterface;
import com.bagevent.new_home.new_interface.impl.GetEventTotalIncomeImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetEventTotalIncomeListener;
import com.bagevent.new_home.new_interface.new_view.GetEventTotalIncomeView;

/**
 * Created by zwj on 2016/10/11.
 */
public class GetEventTotalIncomePresenter {
    private GetEventTotalIncomeInterface getEventTotalIncomeInterface;
    private GetEventTotalIncomeView getEventTotalIncomeView;

    public GetEventTotalIncomePresenter(GetEventTotalIncomeView getEventTotalIncomeView) {
        this.getEventTotalIncomeInterface = new GetEventTotalIncomeImpl();
        this.getEventTotalIncomeView = getEventTotalIncomeView;
    }

    public void eventTotalIncome() {
        getEventTotalIncomeInterface.eventTotalincome(getEventTotalIncomeView.mContext(),getEventTotalIncomeView.userId(), new OnGetEventTotalIncomeListener() {
            @Override
            public void totalIncomeSuccess(EventTotalIncome response) {
                getEventTotalIncomeView.getEventTotalIncomeSuccess(response);
            }

            @Override
            public void totalIncomeFailed(String errInfo) {
                getEventTotalIncomeView.getEventTotalIncomeFailed(errInfo);
            }
        });
    }
}
