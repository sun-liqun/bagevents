package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.AddWithdrawAccount;
import com.bagevent.new_home.new_interface.impl.AddWithDrawAccountImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddWithDrawAccount;
import com.bagevent.new_home.new_interface.new_view.AddWithDrawAccountView;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public class AddWithDrawAccountPresenter {

    private AddWithdrawAccount addWithdrawAccount;
    private AddWithDrawAccountView addWithDrawAccountView;

    public AddWithDrawAccountPresenter(AddWithDrawAccountView addWithDrawAccountView) {
        this.addWithdrawAccount = new AddWithDrawAccountImpl();
        this.addWithDrawAccountView = addWithDrawAccountView;
    }

    public void addWithdraw() {
        addWithdrawAccount.addWithDrawAccount(addWithDrawAccountView.mContext(), addWithDrawAccountView.userId(),addWithDrawAccountView.type() ,addWithDrawAccountView.accountName(), addWithDrawAccountView.account(), addWithDrawAccountView.bankName(), new OnAddWithDrawAccount() {
            @Override
            public void addWithdrawAccountSuccess(String response) {
                addWithDrawAccountView.showAddAccountSucess(response);
            }

            @Override
            public void addWithdrawAccountFailed(String errInfo) {
                addWithDrawAccountView.showAddAccountFailed(errInfo);
            }
        });
    }
}
