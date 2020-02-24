package com.bagevent.login.impls;

import android.content.Context;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.login.callback.LoginCallback;
import com.bagevent.login.interfaces.LoginInterface;
import com.bagevent.login.interfaces.OnLoginInterface;
import com.bagevent.login.model.UserInfo;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;

/**
 * Created by zwj on 2016/5/25.
 * 请求登录
 */
public class LoginImpl implements LoginInterface {

    @Override
    public void Login(final Context mcontext, String userName, String password, final OnLoginInterface onLoginInterface) {
        // Log.e("TAG",userName+"---"+password);
        OkHttpUtils.post()
                .url(Constants.URL + Constants.logins)
                .addParams("account", userName)
                .addParams("password", password)
                .build()
                .writeTimeOut(3_1000L)
                .readTimeOut(3_1000L)
                .connTimeOut(3_1000L)
                .execute(new LoginCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //        Log.e("", "login failed" + e.getMessage());
//                        request failed , reponse's code is : 502
                        if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
                            onLoginInterface.loginFailed(mcontext.getResources().getString(R.string.connect_overtime));
                        } else if (e instanceof IOException) {
                            onLoginInterface.loginFailed(mcontext.getResources().getString(R.string.system_login_abnormal));
                        } else {
                            onLoginInterface.loginFailed(String.valueOf(mcontext.getResources().getString(R.string.error_username_pwd)));
                        }
                    }

                    @Override
                    public void onResponse(UserInfo response, int id) {
                        if (response.getCode() == 200) {
                            onLoginInterface.loginSuccess(response);
                        } else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(mcontext);
                            onLoginInterface.loginFailed(codeUtil.ErrCode(response.getCode()));
                        }

                    }
                });

    }

    @Override
    public void autoLogin(final Context mContext, String autoToken, final OnLoginInterface onLoginInterface) {
        OkHttpUtils.post()
                .url(Constants.URL + Constants.AUTO_LOGIN)
                .tag("autoLogin")
                .addParams("autoLoginToken", autoToken)
                .build()
                .writeTimeOut(3_1000L)
                .readTimeOut(3_1000L)
                .connTimeOut(3_1000L)
                .execute(new LoginCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("autoLogin请求取消");
                        }else {
                            onLoginInterface.loginFailed(mContext.getResources().getString(R.string.error_auto_login));
                        }

                    }

                    @Override
                    public void onResponse(UserInfo response, int id) {
                        if (response.getCode() == 200) {
                            onLoginInterface.loginSuccess(response);
                        } else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(mContext);
                            onLoginInterface.loginFailed(codeUtil.ErrCode(response.getCode()));
                        }
                    }
                });
    }
}
