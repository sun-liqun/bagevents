package com.bagevent.jpush;

import android.content.Context;
import android.text.TextUtils;

import com.badoo.mobile.util.WeakHandler;
import com.bagevent.util.LogUtil;
import com.bagevent.util.SharedPreferencesUtil;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/12/18
 * <p>
 * desp 自定义JPush message 接收器,包括操作tag/alias的结果返回(仅仅包含tag/alias新接口部分)
 * <p>
 * <p>
 * =============================================
 */
public class MyJPushMessageReceiver extends JPushMessageReceiver {
    @Override
    public void onAliasOperatorResult(final Context context, JPushMessage jPushMessage) {

        int errorCode = jPushMessage.getErrorCode();
        final String alias = jPushMessage.getAlias();
        if (TextUtils.isEmpty(alias))
            return;

        LogUtil.e("错误码" + errorCode + ",alias=" + alias);

        switch (errorCode) {
            case 0:
                SharedPreferencesUtil.put(context, "alias" + alias, alias);
                break;
            case 6002: //遇到 6002 超时，则稍延迟重试。
                new WeakHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        JPushInterface.setAlias(context, 0, alias);
                    }
                }, 30_1000L);
                break;
        }

    }
}
