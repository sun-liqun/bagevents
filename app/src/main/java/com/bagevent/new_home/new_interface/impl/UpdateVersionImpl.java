package com.bagevent.new_home.new_interface.impl;

import android.content.Context;
import android.util.Log;

import com.bagevent.common.Constants;
import com.bagevent.new_home.data.UpdateVersionData;
import com.bagevent.new_home.new_interface.UpdateVersionInterface;
import com.bagevent.new_home.new_interface.callback.UpdateVersionCallback;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateVersionListener;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;

import okhttp3.Call;

/**
 * Created by zwj on 2016/12/7.
 */
public class UpdateVersionImpl implements UpdateVersionInterface {
    @Override
    public void updateVersion(Context mContext, final OnUpdateVersionListener listener) {
        String url = Constants.URL + Constants.UPDATE_VERSION_INFO + Constants.ACCESS_SERCRET + Constants.ACCESS_TOKEN;
       /// String url = "http://192.168.0.18:9988/api/third/getAppVersionName?access_token=b89abe3a0600e57288bee9aa63af5967ec5a3dc9&access_secret=b17395040318072087";
        OkHttpUtil.okGet(mContext)
                .tag("GetVersion")
                .url(url)
                .build()
                .execute(new UpdateVersionCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("GetVersion");
                        }else {
                            Log.e("updateVersion","version" + e.getMessage());
                        }

                    }


                    @Override
                    public void onResponse(UpdateVersionData response, int id) {
                        if(response.getRetStatus() == 200) {
                            listener.getVersionInfo(response);
                        }else {
                            listener.getVersionInfoFailed("获得版本信息出错");
                        }
                    }
                });
    }
}
