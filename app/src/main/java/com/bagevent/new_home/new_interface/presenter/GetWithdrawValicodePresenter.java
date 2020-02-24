package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.SetWithdrawPasswordInterface;
import com.bagevent.new_home.new_interface.impl.SetWithdrawpasswordImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetWithdrawValidCodeListener;
import com.bagevent.new_home.new_interface.new_view.GetWithDrawValiCodeView;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public class GetWithdrawValicodePresenter {
    private SetWithdrawPasswordInterface setWithdrawPassword;
    private GetWithDrawValiCodeView getWithDrawValiCodeView;

    public GetWithdrawValicodePresenter(GetWithDrawValiCodeView getWithDrawValiCodeView) {
        this.setWithdrawPassword = new SetWithdrawpasswordImpl();
        this.getWithDrawValiCodeView = getWithDrawValiCodeView;
    }

    public void getValiCode() {
        setWithdrawPassword.getWithdrawValidCode(getWithDrawValiCodeView.mContext(), getWithDrawValiCodeView.userId(), getWithDrawValiCodeView.type(), new OnGetWithdrawValidCodeListener() {
            @Override
            public void showValidCodeSuccess(String response) {
                getWithDrawValiCodeView.showValicodeSuccess(response);
            }

            @Override
            public void showValidCodeFailed(String errInfo) {
                getWithDrawValiCodeView.showValicodeFailed(errInfo);
            }
        });
    }

}
