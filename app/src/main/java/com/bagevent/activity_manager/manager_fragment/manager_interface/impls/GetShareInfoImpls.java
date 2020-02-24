package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.callback.GetShareInfoCallback;
import com.bagevent.activity_manager.manager_fragment.data.ShareInfoData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetShareInfoInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetShareInfoListener;
import com.bagevent.common.Constants;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2016/12/5.
 */
public class GetShareInfoImpls implements GetShareInfoInterface {
    @Override
    public void shareInfo(final Context mContext, String eventId, String userId, final OnGetShareInfoListener listener) {
       // String url = "http://192.168.0.18:9988/api/getEventShareInfo/15677?access_token=b89abe3a0600e57288bee9aa63af5967ec5a3dc9&access_secret=b17395040318072087&userId=146";
        String url = Constants.URL + Constants.GET_SHARE_INFO + eventId + Constants.ACCESS_TOKENS + Constants.ACCESS_SERCRET + "&userId=" + userId;
        OkHttpUtil.okGet(mContext)
      //      OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new GetShareInfoCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("GetShareInfoImpls",e.getMessage());
                    }

                    @Override
                    public void onResponse(ShareInfoData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.shareInfo(response);
                        }else {
                            listener.shareErr(mContext.getResources().getString(R.string.gain_share_error));
                        }
                    }
                });
    }
}
