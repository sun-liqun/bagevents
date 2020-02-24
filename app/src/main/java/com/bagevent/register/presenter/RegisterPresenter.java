package com.bagevent.register.presenter;

import com.bagevent.register.data.RegisterData;
import com.bagevent.register.regImpl.RegisterInterfaceImps;
import com.bagevent.register.reginterface.RegisterInterface;
import com.bagevent.register.reginterface.clicklistener.RegisterOnClickInterface;
import com.bagevent.register.registerview.RegisterViewInterface;

/**
 * Created by zwj on 2016/5/27.
 */
public class RegisterPresenter {
    private RegisterInterface register;
    private RegisterViewInterface registerView;

    public RegisterPresenter(RegisterViewInterface registerView) {
        this.registerView = registerView;
        this.register = new RegisterInterfaceImps();
    }
    public void register(){
        register.register(registerView.context(),registerView.getPhoneNum(), registerView.getPhonePassword(), registerView.getIdentifyCode(), new RegisterOnClickInterface() {
            @Override
            public void registerSuccess(RegisterData registerData) {
                registerView.toLoginActivity(registerData);
            }

            @Override
            public void registerFailed(String err) {
                registerView.showRegisterFail(err);
            }
        });
    }
}
