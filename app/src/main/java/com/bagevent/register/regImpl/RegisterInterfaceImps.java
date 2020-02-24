package com.bagevent.register.regImpl;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.register.callback.RegisterCallback;
import com.bagevent.register.data.RegisterData;
import com.bagevent.register.reginterface.RegisterInterface;
import com.bagevent.register.reginterface.clicklistener.RegisterOnClickInterface;
import com.bagevent.util.ErrCodeUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/5/27.
 * 注册功能的实现类
 */
public class RegisterInterfaceImps implements RegisterInterface {
    @Override
    public void register(final Context context, String phoneNum, String password, String identifyCode, final RegisterOnClickInterface registerOnClickInterface) {
        OkHttpUtils.post()
                .url(Constants.URL + Constants.RRGISTER + Constants.REGISTERSOURCE + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .addParams("account", phoneNum)
                .addParams("nickName", phoneNum)
                .addParams("password", password)
                .addParams("randomCode", identifyCode)
                .build()
                .execute(new RegisterCallback() {
                    @Override
                    public void onError(Call call, Exception e,int id) {

                    }

                    @Override
                    public void onResponse(RegisterData response,int id) {
                        if(response.getCode() == 200 ) {
                            registerOnClickInterface.registerSuccess(response);
                        }else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(context);
                            registerOnClickInterface.registerFailed(codeUtil.ErrCode(response.getCode()));
                        }
                    }
                });

    }
}
