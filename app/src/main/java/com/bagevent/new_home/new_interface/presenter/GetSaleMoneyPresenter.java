package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.SaleMoneyData;
import com.bagevent.new_home.new_interface.GetSaleMoneyInterface;
import com.bagevent.new_home.new_interface.impl.GetSaleMoneyImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetSaleMoneyListener;
import com.bagevent.new_home.new_interface.new_view.GetSaleMoneyView;

/**
 * Created by zwj on 2016/9/2.
 */
public class GetSaleMoneyPresenter {
    private GetSaleMoneyInterface getSaleMoney;
    private GetSaleMoneyView getSaleMoneyView;

    public GetSaleMoneyPresenter(GetSaleMoneyView getSaleMoneyView) {
        this.getSaleMoneyView = getSaleMoneyView;
        this.getSaleMoney = new GetSaleMoneyImpl();
    }

    public void saleMoney() {
        getSaleMoney.getSaleMoney(getSaleMoneyView.mContext(),getSaleMoneyView.userId(), getSaleMoneyView.size(), new OnGetSaleMoneyListener() {
            @Override
            public void showGetSuccess(SaleMoneyData response) {
                getSaleMoneyView.showGetSaleMoneySuccess(response);
            }

            @Override
            public void showGetFailed() {
                getSaleMoneyView.showGetSaleMoneyFailed();
            }
        });
    }
}
