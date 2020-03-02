package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.IncomeData;
import com.bagevent.new_home.new_interface.GetIncomeInterface;
import com.bagevent.new_home.new_interface.impl.GetIncomeImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetIncomeListener;
import com.bagevent.new_home.new_interface.new_view.GetIncomeView;

/**
 * Created by zwj on 2016/9/2.
 */
public class GetIncomePresenter {
    private GetIncomeInterface getIncome;
    private GetIncomeView getIncomeView;

    public GetIncomePresenter(GetIncomeView getIncomeView) {
        this.getIncome = new GetIncomeImpl();
        this.getIncomeView = getIncomeView;
    }

    public void income() {
        getIncome.getIncome(getIncomeView.mContext(),getIncomeView.userId(), new OnGetIncomeListener() {
            @Override
            public void showGetSuccess(IncomeData response) {
                getIncomeView.showGetIncomeSuccess(response);
            }

            @Override
            public void showGetFailed() {
                getIncomeView.showGetIncomeFailed();
            }
        });
    }
}
