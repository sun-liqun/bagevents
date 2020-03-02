package com.bagevent;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;

import com.bagevent.common.Constants;
import com.bagevent.util.AppUtils;
import com.bagevent.util.SharedPreferencesUtil;
import com.facebook.stetho.Stetho;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.UIKitOptions;
import com.netease.nim.uikit.business.contact.core.query.PinYin;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.NIMPushClient;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;


/**
 * Created by zwj on 2016/6/2.
 */
public class MyApplication extends Application {

    public static IWXAPI iwxapi;
    private static MyApplication application;

    public static MyApplication getInstance() {
        if (application==null){
            try {
                throw new IllegalAccessException("Application is not created.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return application;
    }

    private SparseBooleanArray synacAttends;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public SparseBooleanArray getSynacAttends() {
        return this.synacAttends;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        SdcardConfig.getInstance().initSdcard();
        // 捕捉异常
        AppUncaughtExceptionHandler crashHandler = AppUncaughtExceptionHandler.getInstance();
        crashHandler.init(getApplicationContext());

        synacAttends = new SparseBooleanArray();
        //   LeakCanary.install(this);
        // initHotFix();
        //     SophixManager.getInstance().queryAndLoadNewPatch();
        FlowManager.init(new FlowConfig.Builder(this).build());
        Stetho.initializeWithDefaults(this);
        JPushInterface.init(this);
        regToWX(this);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        //  CrashHandler.getInstance().init(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(30_1000L, TimeUnit.SECONDS)
                .readTimeout(30_1000L, TimeUnit.SECONDS)
                .writeTimeout(30_1000L, TimeUnit.SECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
        BGASwipeBackHelper.init(this, null);

        //配置腾讯bugly
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(this);
        //设置SDK处理延时，毫秒
        userStrategy.setAppReportDelay(5000);
        if (AppUtils.isApkInDebug(this)) {
            userStrategy.setAppVersion(AppUtils.getVersioncode(this) + "_debug");
        }
//        CrashReport.initCrashReport(getApplicationContext(), "e7a18e7d38", false, userStrategy);
        CrashReport.initCrashReport(getApplicationContext(), "1948bec648", false, userStrategy);
        disableAPIDialog();

        NIMClient.init(this,loginInfo(),NimSDKOptionConfig.getSDKOptions(this));


        // 以下逻辑只在主进程初始化时执行
        if (NIMUtil.isMainProcess(this)) {
            NIMPushClient.registerMixPushMessageHandler(new DemoMixPushMessageHandler());
            // init pinyin
            PinYin.init(this);
            PinYin.validate();
            // 初始化UIKit模块
            initUIKit();
            // 初始化消息提醒
            NIMClient.toggleNotification(true);
            // 云信sdk相关业务初始化
            NIMInitManager.getInstance().init(true);
        }
        Log.i("----------application","onCreate");
    }

    private void initUIKit() {
        // 初始化
        NimUIKit.init(this, buildUIKitOptions());
    }

    private UIKitOptions buildUIKitOptions() {
        UIKitOptions options = new UIKitOptions();
        // 设置app图片/音频/日志等缓存目录
        options.appCacheDir = NimSDKOptionConfig.getAppCacheDir(this) + "/app";
        return options;
    }


    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        String account =SharedPreferencesUtil.get(this, "accid", "");
        String token = SharedPreferencesUtil.get(this,"yunxin_token","");
        if (!TextUtils.isEmpty(account)&&!TextUtils.isEmpty(token)){
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account.toLowerCase(),token);
        }else {
            return null;
        }
    }

//    private void initHotFix() {
//        String appVersion;
//        try {
//            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
//        } catch (Exception e) {
//            appVersion = "2.0.4";
//        }
//
//        SophixManager.getInstance()
//                .setContext(this)
//                .setAppVersion(appVersion)
//                .setEnableDebug(true)
//                .setSecretMetaData(null,null,"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCi8EnEVgxceTgqbkAqvfPvjg6XyD4g2o5bdxE/TlAHJpzg6Ialmrx0zh7GoOcnnkX5dFvQ6h0S6aT0HsyuulP5Vh9UNyzQgVYvlZYOMgKx3B+RsczPprVGYSFt6oh9CNYWLeiRb9jN+Mj/8kT99zo7Q6d3jk5PsZpkJhgQ4c2FfTehHXMHOjycMHEo0WNjtpzMhgR53WuYLNWMGmlIOGgHi7XxlXEsDL38khuTjGuSPMscsmyHJZLHyAw3Yz72+df30M6cMWpfvge6OXN2RxMvfxJizgUV0AIdkHgbKo1eGrsFKnDKnzjoa/YpR194fTrDx6fqUevcdwvv87SVGv8/AgMBAAECggEBAJG/vUyZ6B7D9ADIdC+HkO/FM7qDzUdYGSUqAcyOAboTyRrKBL+YBWG6iv8EKSFgS5mTgxoCgRVM2FfiQRc+5yTO6+FKJZUxTGXuObbi91Qf4kFSOTG6Hscg1WVb+NFXmSFn2XRXBylflHSWQy6l+nnOz7t7GQPii4dwJ/DZ+xye8aFA71Z3SR0PDRMuMGlJlnyaCHflJJnmIZqqfQp77LVb8QDUoqjvB0SRH36Tee9djlUdy5Y/Q1+MmBbmTsnT3prc8R/B9ZYG/XKoK3ZnC8XwEbeeLaSEPFTeoU/iUqv0qWxW1o5S1jUuWRIsyP1y8lfpZB4vlXLC/4Dxu8mPGsECgYEA4IK2huOQBAQdtHq//jiuN3QbGTR2JoUuXs8tA+/Kn/BeXMH364mH5rpRSNVbLHPIcAYvK8+vnghThsBNOkzPw7hIhms8XupoORizpKb8sslDGqOagcRoD0/0LEoHy+nXGERqRNasqL8I4qf9QyoQY3xVL0FSMPF6P9Absl0wG2ECgYEAucrE8pif+UyDIaHz+0uTKLgUrVTdJtnNU05TqBNw311jtK+19hn9U6Wf14+ZCnWZFApFkmLPL7FjulZYgWQhNmGEdyQVUIZrmIkWVpLgdhk2N9Pz//RjNOicdPoaZj8DT1LUo8Z0uGFwHVdirqy03oh5wnDH7EVd82DrCDDovp8CgYA1Itq+bCeOu8aoC5FbQuEA4Or0+TSpg0XpTJFz6BIF7qb2vzXFOmS+2AQa/9w3ViPvV5dcgCgpeAEE2IiurrVRtlD7929va31FXjJOWvNeAUJvkJ8l7W1PinXY8aCMw4iUBsU8C68LtUMOGgJYHiGI2klzCLvCN7TeW4vimCmloQKBgQCbWFSWd1EDNJtMKssCqe0wlcIML7H+AwywBATkuuScjsyIToa5cH+lSg29Qj73RN6qp/AF9wI/YXX+XqYAAkXW33DP4WbE/q/GSZFY79QoSupPgbQ5phCxM3FmSZx7M2LEF4DeM1hytl302pUTciqFCt2r23r+Lf3hOn8qSSLCBwKBgDPNV5VgitP8WS50hTlMcsD6llL+lo5MmYDoDWqNyhvtrqlLM8Y6rrgm1AvXw1jmGy4hEw1BfXGJYMbxFyggdRE6tdHX0D8UBG6Z7190sG4dedBlQ4VOAeiCH5Qaq3yXHrl6oQaP60kR7exXEB6S3bsj9v+sm6DH1sQI9xLUOm+8")
//                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
//                    @Override
//                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
//                        Log.e("Application",code+"");
//                    }
//                })
//                .initialize();
//    }


    private void regToWX(Context context) {
        iwxapi = WXAPIFactory.createWXAPI(context, Constants.WX_APPID, true);
        iwxapi.registerApp(Constants.WX_APPID);
    }

    private void disableAPIDialog(){
        if (Build.VERSION.SDK_INT < 28)return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取自身App安装包信息
     *
     * @return
     */

    public PackageInfo getLocalPackageInfo() {
        return getPackageInfo(getPackageName());
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */

    public PackageInfo getPackageInfo(String packageName) {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }
}
