package com.bagevent.util;

import android.content.Context;
import android.util.Log;

import com.bagevent.common.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;

/**
 * Created by zwj on 2016/10/12.
 * 同意添加请求header
 */
public class OkHttpUtil {

    public static PostFormBuilder okPost(Context mContext) {
        //  Log.e("post header--->",SharedPreferencesUtil.get(mContext,"autoLoginToken",""));
        PostFormBuilder p = OkHttpUtils.post()
                .addHeader(Constants.USER_TOKEN, SharedPreferencesUtil.get(mContext, "autoLoginToken", ""));
        return p;
    }

    public static GetBuilder okGet(Context mContext) {
        // Log.e("get header--->",SharedPreferencesUtil.get(mContext,"autoLoginToken",""));
        GetBuilder g = OkHttpUtils.get()
                .addHeader(Constants.USER_TOKEN, SharedPreferencesUtil.get(mContext, "autoLoginToken", ""));
        return g;
    }


    public static PostFormBuilder Post(Context mContext) {
        PostFormBuilder formBuilder = okPost(mContext);
        formBuilder.addParams("SOURCE", "1");
        formBuilder.addParams("access_token", "ipad");
        formBuilder.addParams("access_secret", "ipad_secret");
        formBuilder.addParams("userId",SharedPreferencesUtil.get(mContext,KeyUtil.KEY_USER_ID,""));
        Log.i("----------USER",SharedPreferencesUtil.get(mContext,KeyUtil.KEY_USER_ID,""));
        return formBuilder;
    }

    public static GetBuilder Get(Context mContext) {
        GetBuilder getBuilder = okGet(mContext);
        getBuilder.addParams("SOURCE", "1");
        getBuilder.addParams("access_token", "ipad");
        getBuilder.addParams("access_secret", "ipad_secret");
        return getBuilder;
    }

}
