package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.UserInfoData;
import com.bagevent.new_home.new_interface.GetUserInfoInterface;
import com.bagevent.new_home.new_interface.callback.GetUserInfoCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetUserInfoListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/10/11.
 */
public class GetUserInfoImpl implements GetUserInfoInterface {
    @Override
    public void userInfoo(final Context mContext, String userId, final OnGetUserInfoListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.USER_INFO + "userId=" + userId + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .tag("GetUser")
                .build()
                .execute(new GetUserInfoCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("GetUser请求取消");
                        }else {
                            Log.e("err userInfo",e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(UserInfoData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.userInfoSuccess(response);
                        }else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(mContext);
                            listener.userInfoFailed(codeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
