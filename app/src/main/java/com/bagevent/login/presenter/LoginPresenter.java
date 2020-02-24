package com.bagevent.login.presenter;

import android.content.Context;
import android.util.Log;

import com.bagevent.login.impls.LoginImpl;
import com.bagevent.login.interfaces.LoginInterface;
import com.bagevent.login.loginview.LoginViewInterface;
import com.bagevent.login.interfaces.OnLoginInterface;
import com.bagevent.login.model.UserInfo;

/**
 * Created by zwj on 2016/5/25.
 */
public class LoginPresenter {
    private LoginInterface loginInterface;
    private LoginViewInterface loginViewInterface;


    public LoginPresenter(LoginViewInterface loginViewInterface) {
        this.loginViewInterface = loginViewInterface;
        this.loginInterface = new LoginImpl();
    }

    public void login(){
        loginInterface.Login(loginViewInterface.mContext(),loginViewInterface.getUserName(), loginViewInterface.getPassword(), new OnLoginInterface() {
            @Override
            public void loginSuccess(UserInfo userInfo) {
                loginViewInterface.toMainActivity(userInfo);
            }

            @Override
            public void loginFailed(String errInfo) {
              //  Log.e("tassss",errInfo);
                loginViewInterface.showFailedErr(errInfo);
            }
        });
    }

    public void autoLogin(Context context,String autoToken) {
        loginInterface.autoLogin(context,autoToken, new OnLoginInterface() {
            @Override
            public void loginSuccess(UserInfo userInfo) {
                loginViewInterface.toMainActivity(userInfo);
            }

            @Override
            public void loginFailed(String errInfo) {
                loginViewInterface.showFailedErr(errInfo);
            }
        });
    }

    public void clearName(){
        loginViewInterface.clearUserName();
    }
    public void clearword(){
        loginViewInterface.clearPassword();
    }
}
