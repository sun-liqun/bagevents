package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.WithDrawAccountData;
import com.bagevent.new_home.new_interface.WithdrawAccountInterface;
import com.bagevent.new_home.new_interface.impl.WithDrawAccountImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnWithDrawListener;
import com.bagevent.new_home.new_interface.new_view.WithDrawAccountView;

/**
 * Created by WenJie on 2017/11/16.
 */

public class WithDrawAccountPresenter {
    private WithdrawAccountInterface withdraw;
    private WithDrawAccountView withDrawAccountView;

    public WithDrawAccountPresenter(WithDrawAccountView withDrawAccountView) {
        this.withdraw = new WithDrawAccountImpl();
        this.withDrawAccountView = withDrawAccountView;
    }

    public void withdrawAccount() {
        withdraw.withdrawAccount(withDrawAccountView.mContext(), withDrawAccountView.userId(), new OnWithDrawListener() {
            @Override
            public void withdrawSuccess(WithDrawAccountData response) {
                withDrawAccountView.withDrawAccountSuccess(response);
            }

            @Override
            public void withdrawFailed(String errInfo) {
                withDrawAccountView.withDrawAccoountFailed(errInfo);
            }
        });
    }
}
