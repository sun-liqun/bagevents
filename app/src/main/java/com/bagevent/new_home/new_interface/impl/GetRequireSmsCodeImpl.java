package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.GetReqireSmsCodeInterface;
import com.bagevent.new_home.new_interface.listenerInterface.GetRequireSmsCodeListener;
import com.bagevent.register.callback.GetSMSCallback;
import com.bagevent.register.data.GetSMSData;
import com.bagevent.util.CompareRex;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.IsPhoneNum;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/11/9.
 */
public class GetRequireSmsCodeImpl implements GetReqireSmsCodeInterface {
    @Override
    public void getSmsCode(final Context mContext, String phoneNum, int source, final GetRequireSmsCodeListener listener) {
        if (CompareRex.isCellPhone(phoneNum)) {
            OkHttpUtil.okPost(mContext)
                    .url(Constants.URL + Constants.GETSMS + "source=" + source + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                    .addParams("account", phoneNum)
                    .build()
                    .execute(new GetSMSCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(GetSMSData response, int id) {
                            if (response.getCode() == 200) {
                                listener.getSmsCodeSuccess(response);
                            } else {
                                ErrCodeUtil codeUtil=new ErrCodeUtil(mContext);
                                listener.getSmsCodeFailed(codeUtil.ErrCode(response.getCode()));
                            }
                        }
                    });
        }else {
            listener.getSmsCodeFailed(mContext.getResources().getString(R.string.phone_err2));
        }
    }
}
