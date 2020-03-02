package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.os.Environment;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetAttendeeJsonFileInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetAttendeeJsonFile;
import com.bagevent.common.Constants;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;


/**
 * Created by zwj on 2017/9/7.
 */

public class GetAttendeeJsonFile implements GetAttendeeJsonFileInterface {
    @Override
    public void getAttendeeJsonFile(final Context mContext, String eventId, final OnGetAttendeeJsonFile listener) {
        final long startTime = System.currentTimeMillis();
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.ATTENDEE_FILE + eventId + Constants.ACCESS_TOKENS + Constants.ACCESS_SERCRET + "&sync_all=1&with_jdbc=1")
                .tag("MeetingPersonFragment")
                .build()
                .connTimeOut(160000)
                .readTimeOut(160000)
                .writeTimeOut(160000)
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "AttendeeFile.zip") {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()) {
                            LogUtil.e("取消请求");
                        } else {
                            CrashReport.postCatchedException(new Throwable("参会者文件下载失败"+e));
                            listener.onGetAttendeeJsonFileFailed(mContext.getResources().getString(R.string.attendee_file_failed));
                        }
                    }

                    @Override
                    public void onResponse(File response, int id) {

                        long entTime = System.currentTimeMillis();
                        int dTime = (int) ((entTime - startTime) / 1000);//94
                        LogUtil.e("下载文件时长为" + dTime);
                        listener.onGetAttendeeJsonFile(response);
                    }
                });
    }
}
