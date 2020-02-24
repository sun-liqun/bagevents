package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.provider.MediaStore;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.SponsorData;
import com.bagevent.new_home.new_interface.GetSponsorInterface;
import com.bagevent.new_home.new_interface.callback.GetSponsorCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetSponsorListener;
import com.bagevent.util.ErrCodeUtil;
import com.bagevent.util.OkHttpUtil;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * Created by zwj on 2016/9/18.
 */
public class GetSponsorImpl implements GetSponsorInterface {
    @Override
    public void getSponsor(final Context mContext, String userId, final OnGetSponsorListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.ORGANIZER + "userId=" + userId + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN)
                .build()
                .execute(new GetSponsorCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(SponsorData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.getSuccess(response);
                        }else {
                            ErrCodeUtil codeUtil=new ErrCodeUtil(mContext);
                            listener.getFailed(codeUtil.ErrCode(response.getRetStatus()));
                        }
                    }
                });
    }
}
