package com.bagevent.synchro_data;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.bagevent.R;
import com.bagevent.util.AppUtils;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;

import java.io.File;

/**
 * Created by zwj on 2016/12/6.
 */
public class UpdateVersionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            TosUtil.show(context.getResources().getString(R.string.download_finish1));
            long downLoadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
            long cacheDownLoadId = SharedPreferencesUtil.getLongValue(context, DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
            if (downLoadId == cacheDownLoadId) {
                File apkFile = AppUtils.getApkFile(context);
                TosUtil.show(apkFile.getAbsolutePath());
                AppUtils.installApk(context, apkFile);
            }
        }
    }


}
