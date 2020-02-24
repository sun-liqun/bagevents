package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.SetWithdrawPasswordInterface;
import com.bagevent.new_home.new_interface.impl.SetWithdrawpasswordImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetWithdrawValidCodeListener;
import com.bagevent.new_home.new_interface.listenerInterface.OnSetWithdrawpasswordListener;
import com.bagevent.new_home.new_interface.new_view.GetWithDrawValiCodeView;
import com.bagevent.new_home.new_interface.new_view.SetWithDrawPasswordView;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public class SetWithdrawPasswordPresenter {
    private SetWithdrawPasswordInterface setWithdrawPassword;
    private SetWithDrawPasswordView setWithDrawPasswordView;

    public SetWithdrawPasswordPresenter(SetWithDrawPasswordView setWithDrawPasswordView) {
        this.setWithdrawPassword = new SetWithdrawpasswordImpl();
        this.setWithDrawPasswordView = setWithDrawPasswordView;
    }


    public void setPassword() {
        setWithdrawPassword.setWithdrawPassword(setWithDrawPasswordView.mContext(), setWithDrawPasswordView.userId(), setWithDrawPasswordView.password(), setWithDrawPasswordView.confirmPassword(), setWithDrawPasswordView.validCode(), new OnSetWithdrawpasswordListener() {
            @Override
            public void setPasswordSuccess(String response) {
                setWithDrawPasswordView.showSetWithdrawPasswordSuccess(response);
            }

            @Override
            public void setPasswordFailed(String errInfo) {
                setWithDrawPasswordView.showSetWithdrawPasswordFailed(errInfo);
            }
        });
    }


}
