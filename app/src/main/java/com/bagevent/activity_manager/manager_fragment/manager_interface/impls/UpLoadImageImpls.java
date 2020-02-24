package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.callback.StringDataCallback;
import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.UpLoadImageInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnUpLoadImageListener;
import com.bagevent.common.Constants;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zwj on 2016/8/16.
 */
public class UpLoadImageImpls implements UpLoadImageInterface {
    @Override
    public void upload(Context mContext, File file, String userId, final OnUpLoadImageListener listener) {
        OkHttpUtil.okPost(mContext)
                .url(Constants.URL + "/uploadImgFile" + "?userId=" + userId + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .addFile("uploadImgFile",file.getName(),file)
                .tag("getAvatar")
                .build()
                .execute(new StringDataCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.i("---------------e"+e);
                    }

                    @Override
                    public void onResponse(StringData response, int id) {
                        LogUtil.i("---------------respnse"+response.getRespObject());
                        if(response.getRetStatus() == 200) {
                            listener.upLoadSuccess(response.getRespObject());

                        }else {
                            listener.upLoadFailed(response.getRespObject());
                        }
                    }
                });
    }
}
