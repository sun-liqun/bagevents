package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.RechargeMsgData;
import com.bagevent.new_home.new_interface.RechargeMessageInterface;
import com.bagevent.new_home.new_interface.impl.RechargeMessageImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnRechargeMessageListener;
import com.bagevent.new_home.new_interface.new_view.RechargeMessageView;

/**
 * Created by zwj on 2017/10/11.
 */

public class RechargeMessagePresenter {
    private RechargeMessageInterface rechargeMessageInterface;
    private RechargeMessageView rechargeMessageView;

    public RechargeMessagePresenter(RechargeMessageView rechargeMessageView) {
        this.rechargeMessageInterface = new RechargeMessageImpl();
        this.rechargeMessageView = rechargeMessageView;
    }

    public void thirdRecharge() {
        rechargeMessageInterface.thirdRechargeMessage(rechargeMessageView.mContext(), rechargeMessageView.userId(),
                rechargeMessageView.count(), rechargeMessageView.payway(), rechargeMessageView.accountType(), new OnRechargeMessageListener() {
                    @Override
                    public void rechargeSuccess(RechargeMsgData response) {
                        rechargeMessageView.thirdRechargeSuccess(response);
                    }

                    @Override
                    public void rechargeFailed(String errInfo) {
                        rechargeMessageView.thirdRechargeFailed(errInfo);
                    }
                });
    }

    public void accountRecharge() {
        rechargeMessageInterface.accountRechargeMessage(rechargeMessageView.mContext(), rechargeMessageView.userId(),
                rechargeMessageView.count(), rechargeMessageView.payway(), rechargeMessageView.drawPassword(), new OnRechargeMessageListener() {
                    @Override
                    public void rechargeSuccess(RechargeMsgData response) {
                        rechargeMessageView.rechargeSuccess(response);
                    }

                    @Override
                    public void rechargeFailed(String errInfo) {
                        rechargeMessageView.rechargeFailed(errInfo);
                    }
                });
    }
}
