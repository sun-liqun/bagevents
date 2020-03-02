package com.bagevent.synchro_data;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * Created by zwj on 2016/12/7.
 */
public class UpdateVersionService extends IntentService {

    private String url;
    private int state;
    private String appName;
    private String description;
    private DownloadManager downloadManager;
    private Uri uri;

    public UpdateVersionService() {
        super("UpdateVersionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        url = intent.getStringExtra("url");

//        if (Build.VERSION.SDK_INT>=24){
//            uri = FileProvider.getUriForFile(this, "com.bagevent.fileprovider", new File(url));
//        }else {
//            uri= Uri.parse(url);
//        }
//        Log.i("------------111-",FileProvider.getUriForFile(this, "com.bagevent.fileprovider", new File(url)).toString());

        if (url != null) {
            state = intent.getIntExtra("state", -1);
            appName = intent.getStringExtra("appName");
            description = intent.getStringExtra("description");
            long cacheDownLoadId = SharedPreferencesUtil.getLongValue(this, DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
            if (cacheDownLoadId != -1L && downloadManager != null) {
                //  Log.e("aa","ds");
                downloadManager.remove(cacheDownLoadId);
            }
            startDownload();
        }
    }

    /**
     * 通过DownloadManager开始下载
     *
     * @return
     */
    public long startDownload() {
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request rq = new DownloadManager.Request(Uri.parse(url));
        rq.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        if (state == Constants.FORCE_UPDATE) {
            rq.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
        } else {
            rq.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        //  rq.setVisibleInDownloadsUi(true);
        rq.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, appName + ".apk");
        rq.setTitle(appName);
        rq.setDescription(description);
        long requestId = downloadManager.enqueue(rq);
        SharedPreferencesUtil.putLong(this, DownloadManager.EXTRA_DOWNLOAD_ID, requestId);
        if (state == Constants.FORCE_UPDATE) {//强制升级，显示自定义进度的弹窗
            // Log.e("aa","ddsa");
            getDownLoadMangerStatus(requestId);
        }
        return requestId;
    }

    /**
     * 获得当前的下载状态
     *
     * @param downLoadId
     * @return
     */
    public void getDownLoadMangerStatus(long downLoadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downLoadId);
        try {
            boolean isGoing = true;
            while (isGoing) {
                //   Log.e("aaa","dds");
                Cursor cursor = downloadManager.query(query);
                if (cursor != null && cursor.moveToFirst()) {
                    int state = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                    switch (state) {
                        case DownloadManager.STATUS_SUCCESSFUL:
                            isGoing = false;
                            EventBus.getDefault().postSticky(new MsgEvent("STATUS_SUCCESSFUL"));
                            break;
                        case DownloadManager.STATUS_FAILED:
                            isGoing = false;
                            EventBus.getDefault().postSticky(new MsgEvent("STATUS_FAILED"));
                            break;
                        case DownloadManager.STATUS_RUNNING:
                            /**
                             * 计算下载下载率；
                             */
                            int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                            int currentSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                            int progress = (int) (((float) currentSize) / ((float) totalSize) * 100);
                            EventBus.getDefault().postSticky(new MsgEvent(progress, "", "STATUS_RUNNING"));
                            break;
                        case DownloadManager.STATUS_PAUSED:
                            EventBus.getDefault().postSticky(new MsgEvent("STATUS_PAUSED"));
                            break;
                        case DownloadManager.STATUS_PENDING:
                            EventBus.getDefault().postSticky(new MsgEvent("STATUS_PENDING"));
                            break;
                    }
                }

                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            Log.e("downLoadManager", "exception--->" + e.getMessage());
        }
    }

    public int removeDownLoad(long downLoadId) {
        return downloadManager.remove(downLoadId);
    }

}
