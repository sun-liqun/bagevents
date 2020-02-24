package com.bagevent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.common.Constants;
import com.bagevent.util.FileUtils;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.TosUtil;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/04/09
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class AppUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    //程序的Context对象

    private Context applicationContext;
    private volatile boolean crashing;
    private String time;
    private String fileName;
    /**
     * 日期格式器
     */
    private DateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * 单例
     */
    private static AppUncaughtExceptionHandler sAppUncaughtExceptionHandler;
    public static synchronized AppUncaughtExceptionHandler getInstance() {
        if (sAppUncaughtExceptionHandler == null) {
            synchronized (AppUncaughtExceptionHandler.class) {
                if (sAppUncaughtExceptionHandler == null) {
                    sAppUncaughtExceptionHandler = new AppUncaughtExceptionHandler();
                }
            }
        }
        return sAppUncaughtExceptionHandler;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        applicationContext = context.getApplicationContext();
        crashing = false;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (crashing) {
            return;
        }
        crashing = true;
        // 打印异常信息
        throwable.printStackTrace();
        // 我们没有处理异常 并且默认异常处理不为空 则交给系统处理
        if (!handlelException(throwable) && mDefaultHandler != null) {
            // 系统处理
            mDefaultHandler.uncaughtException(thread, throwable);

        }
        exit();
    }

    private void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    private boolean handlelException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        try {
            // 异常信息
            String crashReport = getCrashReport(ex);
            // 保存到sd卡
            saveExceptionToSdcard(crashReport);
            sendToServer(crashReport);
            // 提示对话框
            showPatchDialog();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void sendToServer(String crashReport) {
        File logFile = new File("/sdcard/crashdemo/log/"+fileName);
        if (!NetUtil.isConnected(applicationContext)) {
            TosUtil.show(applicationContext.getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Post(applicationContext)
                .url(Constants.NEW_URL + Constants.COMMON_FILEUPLOAD)
                .addFile("bagEvent", fileName, logFile.getAbsoluteFile())
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("-------------",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                         Log.i("-------------",response);
                    }
                });
    }

    private void showPatchDialog() {
        Intent intent = PatchDialogActivity.newIntent(applicationContext, getApplicationName(applicationContext), null);
        applicationContext.startActivity(intent);
    }

    private String getApplicationName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        String name = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(
                    context.getApplicationInfo().packageName, 0);
            name = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (final PackageManager.NameNotFoundException e) {
            String[] packages = context.getPackageName().split(".");
            name = packages[packages.length - 1];
        }
        return name;
    }

    /**
     * 获取异常信息
     * @param ex
     * @return
     */

    private String getCrashReport(Throwable ex) {
        StringBuffer exceptionStr = new StringBuffer();
        PackageInfo pinfo = MyApplication.getInstance().getLocalPackageInfo();
        if (pinfo != null) {
            if (ex != null) {
                //app版本信息
                exceptionStr.append("App Version：" + pinfo.versionName);
                exceptionStr.append("_" + pinfo.versionCode + "\n");
                //手机系统信息
                exceptionStr.append("OS Version：" + Build.VERSION.RELEASE);
                exceptionStr.append("_");
                exceptionStr.append(Build.VERSION.SDK_INT + "\n");
                //手机制造商
                exceptionStr.append("Vendor: " + Build.MANUFACTURER+ "\n");
                //手机型号
                exceptionStr.append("Model: " + Build.MODEL+ "\n");
                String errorStr = ex.getLocalizedMessage();
                if (TextUtils.isEmpty(errorStr)) {
                    errorStr = ex.getMessage();
                }
                if (TextUtils.isEmpty(errorStr)) {
                    errorStr = ex.toString();
                }
                exceptionStr.append("Exception: " + errorStr + "\n");
                StackTraceElement[] elements = ex.getStackTrace();
                if (elements != null) {
                    for (int i = 0; i < elements.length; i++) {
                        exceptionStr.append(elements[i].toString() + "\n");
                    }
                }
            } else {
                exceptionStr.append("no exception. Throwable is null\n");
            }
            return exceptionStr.toString();
        } else {
            return "";
        }
    }

    /**
     * 保存错误报告到sd卡
     * @param errorReason
     */

    private void saveExceptionToSdcard(String errorReason) {
        try {
            Log.e("CrashDemo", "AppUncaughtExceptionHandler执行了一次");
            time = mFormatter.format(new Date());
            fileName = "Crash-" + time + ".db";
            if (SdcardConfig.getInstance().hasSDCard()) {
                String path = SdcardConfig.LOG_FOLDER;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(errorReason.getBytes());
                fos.close();
            }
        } catch (Exception e) {
            Log.e("CrashDemo", "an error occured while writing file..." + e.getMessage());
        }
    }

}
