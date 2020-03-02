package com.bagevent.login.presenter;

import com.bagevent.login.impls.ModifyPwImpl;
import com.bagevent.login.interfaces.ModifyPasswordInterface;
import com.bagevent.login.interfaces.OnModifyPwListener;
import com.bagevent.login.loginview.ModifyPwView;

/**
 * Created by ZWJ on 2018/1/11 0011.
 */

public class ModifyPwPresenter {
    private ModifyPasswordInterface modifyPassword;
    private ModifyPwView modifyPwView;

    public ModifyPwPresenter(ModifyPwView modifyPwView) {
        this.modifyPassword = new ModifyPwImpl();
        this.modifyPwView = modifyPwView;
    }

    public void modifyPassword() {
        modifyPassword.modifyPassword(modifyPwView.mContext(), modifyPwView.phoneNum(), modifyPwView.smsCode(), modifyPwView.password(), modifyPwView.passwordAgaing(), new OnModifyPwListener() {
            @Override
            public void modifySuccess(String response) {
                modifyPwView.showModifySuccess(response);
            }

            @Override
            public void modifyFailed(String errInfo) {
                modifyPwView.showModifyFailed(errInfo);
            }
        });
    }
}