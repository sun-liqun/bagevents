package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.WithdrawApplyInterface;
import com.bagevent.new_home.new_interface.impl.WithdrawApplyImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnWithdrawApplyListener;
import com.bagevent.new_home.new_interface.new_view.WithdrawApplyView;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public class WithdrawApplyPresenter {
    private WithdrawApplyInterface withdrawApplyInterface;
    private WithdrawApplyView withdrawApplyView;

    public WithdrawApplyPresenter(WithdrawApplyView withdrawApplyView) {
        this.withdrawApplyInterface = new WithdrawApplyImpl();
        this.withdrawApplyView = withdrawApplyView;
    }

    public void applyWithdraw() {
        withdrawApplyInterface.applyWithdraw(withdrawApplyView.mContext(), withdrawApplyView.userId(), withdrawApplyView.applyAmount(), withdrawApplyView.account(), withdrawApplyView.accountName(), withdrawApplyView.password(),
                withdrawApplyView.bankName(), withdrawApplyView.type(), new OnWithdrawApplyListener() {
                    @Override
                    public void applyWithdrawSuccess(String resoponse) {
                        withdrawApplyView.showApplySuccess(resoponse);
                    }

                    @Override
                    public void applyWithdrawFailed(String errInfo) {
                        withdrawApplyView.showApplyFailed(errInfo);
                    }
                });
    }
}
