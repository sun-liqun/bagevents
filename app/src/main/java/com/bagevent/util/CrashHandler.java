package com.bagevent.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;


import com.bagevent.R;
import com.bagevent.util.image_download.ImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by zwj on 2016/8/18.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    /**
     * 保存crash日志的文件目录
     */
    public static final File CRASH_LOG = new File(Environment.getExternalStorageDirectory() + "/BagEvent","Log");

    /**
     * 系统默认UncaughtExceptionHandler
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * context
     */
    private Context mContext;

    /**
     * 存储异常和参数信息
     */
    private Map<String,String> paramsMap = new HashMap<>();

    /**
     * 格式化时间
     */
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",Locale.getDefault());

    private String TAG = this.getClass().getSimpleName();

    private static CrashHandler mInstance;

    private CrashHandler() {

    }

    /**
     * 获取CrashHandler实例
     */
    public static synchronized CrashHandler getInstance(){
        if(null == mInstance){
            mInstance = new CrashHandler();
        }
        return mInstance;
    }

    public void init(Context context){
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * uncaughtException 回调函数
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex) && mDefaultHandler != null){
            mDefaultHandler.uncaughtException(thread,ex);
        }else{
            Log.e(TAG,ex.getStackTrace().toString());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            AppManager.getAppManager().AppExit(mContext);
        }

    }

    /**
     * 收集错误信息.发送到服务器
     * @return 处理了该异常返回true,否则false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        collectDeviceInfo(mContext);

        addCustomInfo();

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast toast = Toast.makeText(mContext, R.string.anr, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                Looper.loop();
            }
        }.start();
        saveCrashInfo2File(ex);
        return true;
    }


    /**
     * 收集设备参数信息
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {

        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                paramsMap.put("versionName", versionName);
                paramsMap.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                paramsMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 添加自定义参数
     */
    private void addCustomInfo() {

    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        Log.e(TAG,result);
        sb.append(result);
        try {
            if(!ImageUtils.APP_DIR.exists()) {
                ImageUtils.APP_DIR.mkdir();
            }
            if(!CRASH_LOG.exists()) {
                CRASH_LOG.mkdir();
            }
            long timestamp = System.currentTimeMillis();
            String time = format.format(new Date());
            String fileName = "crash_" + time + "_" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
           //     String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crash/";
                File dir = new File(CRASH_LOG,fileName);
                FileOutputStream fos = new FileOutputStream(dir);
                fos.write(sb.toString().getBytes());
                Log.e(TAG,ex.getMessage());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
}
