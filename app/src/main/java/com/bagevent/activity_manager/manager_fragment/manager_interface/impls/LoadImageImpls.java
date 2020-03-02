package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.graphics.Bitmap;

import com.bagevent.activity_manager.manager_fragment.manager_interface.LoadImageInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnLoadImageListener;
import com.bagevent.util.OkHttpUtil;
import com.zhy.http.okhttp.callback.BitmapCallback;

import okhttp3.Call;

/**
 * Created by zwj on 2016/8/8.
 */
public class LoadImageImpls implements LoadImageInterface{
    @Override
    public void loadImage(Context mContext, String url, final OnLoadImageListener listener) {
        OkHttpUtil.okGet(mContext)
                .url(url)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e,int id) {

                    }

                    @Override
                    public void onResponse(Bitmap bitmap,int id) {
                        listener.loadSuccess(bitmap);
                    }
                });
    }
}
