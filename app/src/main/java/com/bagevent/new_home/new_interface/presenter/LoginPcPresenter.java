package com.bagevent.new_home.new_interface.presenter;


import com.bagevent.new_home.new_interface.LoginPcInterface;
import com.bagevent.new_home.new_interface.impl.LoginPcImpl;
import com.bagevent.new_home.new_interface.impl.PostMessageImpl;
import com.bagevent.new_home.new_interface.listenerInterface.LoginPcListener;
import com.bagevent.new_home.new_interface.listenerInterface.OnPublishEventListener;
import com.bagevent.new_home.new_interface.new_view.LoginPcView;
import com.bagevent.new_home.new_interface.new_view.PostMessageView;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2020/01/13
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class LoginPcPresenter {
    private LoginPcInterface loginPcInterface;
    private LoginPcView loginPcView;

    public LoginPcPresenter(LoginPcView loginPcView) {
        this.loginPcInterface = new LoginPcImpl();
        this.loginPcView = loginPcView;
    }

    public void loginPc() {
        loginPcInterface.getLoginPcInfo(loginPcView.mContext(), loginPcView.confirmQrCode(), loginPcView.eventId(), loginPcView.functionTag(), new LoginPcListener() {
            @Override
            public void loginPcSuccess() {
                loginPcView.loginPcSuccess();
            }

            @Override
            public void loginPcFailed(String info) {
                loginPcView.loginPcFailed(info);
            }
        });
    }
}
