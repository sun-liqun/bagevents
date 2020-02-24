package com.bagevent.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.bagevent.MyApplication;

import java.io.File;

/**
 * Created by zwj on 2016/12/7.
 */
public class AppUtils {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static Context getContext() {
        return MyApplication.getInstance();
    }

    /**
     * 安装apk
     *
     * @param context
     * @param apkFile
     */
    public static void installApk(Context context, File apkFile) {
        if (apkFile != null) {
            Intent install = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //参数1 上下文, 参数2 在AndroidManifest中的android:authorities值, 参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(context, "com.bagevent.fileprovider", apkFile);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                //由于没有在Activity环境下启动Activity,设置下面的标签
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(install);
        }
    }

    /**
     * 获得apk文件
     *
     * @param context
     * @return
     */
    public static File getApkFile(Context context) {
        File apkFile = null;
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long id = SharedPreferencesUtil.getLongValue(context, DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
        if (id != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(id);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cursor = manager.query(query);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uri)) {
                        apkFile = new File(Uri.parse(uri).getPath());
                    }
                }
                cursor.close();
            }
        }
        return apkFile;
    }

    /**
     * 获得版本号
     */

    public static String getVersioncode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void runOnUI(Runnable runnable) {
        handler.post(runnable);
    }

    //判断当前应用是否是debug状态
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

}
