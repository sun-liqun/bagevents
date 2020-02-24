package com.bagevent.login.interfaces;

import android.content.Context;

/**
 * Created by zwj on 2016/5/25.
 */
public interface LoginInterface  {

    void Login(Context mContext,String userName, String password, OnLoginInterface onLoginInterface);

    void autoLogin(Context mContext,String autoToken,OnLoginInterface onLoginInterface);

}
