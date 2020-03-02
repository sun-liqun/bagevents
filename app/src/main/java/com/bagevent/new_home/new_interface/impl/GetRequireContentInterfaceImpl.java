package com.bagevent.new_home.new_interface.impl;

import android.content.Context;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.RequireContentData;
import com.bagevent.new_home.new_interface.GetRequireContentInterface;
import com.bagevent.new_home.new_interface.callback.GetRequireContentCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetRequireContentListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/12.
 */
public class GetRequireContentInterfaceImpl implements GetRequireContentInterface {
    @Override
    public void getRequireContent(final Context mContext, String userId, final OnGetRequireContentListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.REQUIRE_CONTENT + "userId="+userId + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .build()
                .execute(new GetRequireContentCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(RequireContentData response, int id) {
                        if (response.getRetStatus() == 200) {
                            listener.showRequireContent(response);
                        }else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(mContext);
                            listener.showRequireContentFailed(codeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
