package com.bagevent.register.regImpl;

import android.content.Context;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.register.callback.GetSMSCallback;
import com.bagevent.register.data.GetSMSData;
import com.bagevent.register.reginterface.GetSMSInterface;
import com.bagevent.register.reginterface.clicklistener.GetSMSClickListener;
import com.bagevent.util.CompareRex;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.IsPhoneNum;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/5/27.
 *
 * 获得验证码的实现类
 */
public class GetSMSInterfaceImps implements GetSMSInterface {
    @Override
    public void getSMS(final Context mContext, String phoneNum, int source , final GetSMSClickListener getSMSClickListener) {
        //发送验证码前判断是否为手机号,如果是手机号则发送验证码
        if (CompareRex.isCellPhone(phoneNum)) {
            OkHttpUtils.post()
                    .url(Constants.URL + Constants.GETSMS + "source=" + source + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                    .addParams("account", phoneNum)
                    .build()
                    .execute(new GetSMSCallback() {
                        @Override
                        public void onError(Call call, Exception e,int id) {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(mContext);
                            getSMSClickListener.errInfo(codeUtil.ErrCode(101));
                        }

                        @Override
                        public void onResponse(GetSMSData response,int id) {
                            if (response.getCode() == 200) {
                                getSMSClickListener.doCountDown();
                                getSMSClickListener.setToastMsg();
                            } else {
                                ErrCodeUtil codeUtil=new ErrCodeUtil(mContext);
                                getSMSClickListener.errInfo( codeUtil.ErrCode(response.getCode()));
                            }
                        }
                    });
        }else {
            getSMSClickListener.errInfo(mContext.getResources().getString(R.string.phone_err2));
        }
    }
}
