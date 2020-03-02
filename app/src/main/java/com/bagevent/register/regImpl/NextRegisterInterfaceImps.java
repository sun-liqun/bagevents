package com.bagevent.register.regImpl;

import android.content.Context;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.register.callback.NextRegisterPhoneIsExistCallback;
import com.bagevent.register.data.PhoneIsExistData;
import com.bagevent.register.reginterface.clicklistener.NextOnClickListener;
import com.bagevent.register.reginterface.RegisterNextInterface;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.IsPhoneNum;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

/**
 * Created by zwj on 2016/5/27.
 * 验证是否为手机号，进入下一步的实现类
 */
public class NextRegisterInterfaceImps implements RegisterNextInterface {

    @Override
    public void checkPhone(final Context mcontext, String phone, final NextOnClickListener nextOnClickListener) {
        if(phone.isEmpty()){
            nextOnClickListener.checkFail(mcontext.getResources().getString(R.string.input_phone));
        }else{
            if (!IsPhoneNum.isPhone(phone)) {
                //手机号不合法
                nextOnClickListener.checkFail(mcontext.getResources().getString(R.string.phone_err2));
            } else {
                OkHttpUtils.post()
                        .url(Constants.URL + Constants.PHONEISEXIST + Constants.SOURCE + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                        .addParams("account", phone)
                        .build()
                        .execute(new NextRegisterPhoneIsExistCallback() {
                            @Override
                            public void onError(Call call, Exception e,int id) {
                                //服务器响应错误
                                ErrCodeUtil codeUtil=new ErrCodeUtil(mcontext);
                                nextOnClickListener.checkFail(codeUtil.ErrCode(101));
                            }

                            @Override
                            public void onResponse(PhoneIsExistData response,int id) {
                                if(response.getCode() != 200){
                                    ErrCodeUtil errCodeUtil=new ErrCodeUtil(mcontext);
                                    String errInfo = errCodeUtil.ErrCode(response.getCode());
                                    nextOnClickListener.checkIsExist(errInfo);
                                }else {
                                    //进行下一步
                                    nextOnClickListener.checkSuccess();
                                }

                            }
                        });
            }
        }

    }



}
