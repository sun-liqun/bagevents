package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import android.text.TextUtils;

import com.bagevent.activity_manager.manager_fragment.data.StringData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetAttendeeJsonFileInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.GetAttendeeJsonFile;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetAttendeeJsonFile;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetAttendeeJsonFileView;
import com.bagevent.util.LogUtil;
import com.bagevent.util.UnZIPUtil;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zwj on 2017/9/7.
 */

public class GetAttendeeFilePresenter {
    private GetAttendeeJsonFileInterface getAttendeeJsonFile;
    private GetAttendeeJsonFileView attendeeJsonFileView;
    private final String LINE_SEP = System.getProperty("line.separator");
    private File attendFile = null;

    public GetAttendeeFilePresenter(GetAttendeeJsonFileView attendeeJsonFileView) {
        this.attendeeJsonFileView = attendeeJsonFileView;
        this.getAttendeeJsonFile = new GetAttendeeJsonFile();
    }

    public void attendeeJsonFile() {
        getAttendeeJsonFile.getAttendeeJsonFile(attendeeJsonFileView.mContext(), attendeeJsonFileView.getEventId(), new OnGetAttendeeJsonFile() {
            @Override
            public void onGetAttendeeJsonFile(File attendeeFile) {
                attendFile = attendeeFile;
                UnZipFileThread thread = new UnZipFileThread();
                thread.start();
            }

            @Override
            public void onGetAttendeeJsonFileFailed(String errInfo) {
                attendeeJsonFileView.attendeeJsonFileFailed(errInfo);
            }
        });

    }


    private class UnZipFileThread extends Thread {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            String filePath = UnZIPUtil.unZIPFile(attendFile);
            deleteFile(attendFile);
            if (!TextUtils.isEmpty(filePath)) {
                System.gc();
                String s = readFile2String(new File(filePath), "utf-8");
                deleteFile(new File(filePath));
                if (!TextUtils.isEmpty(s)) {
                    if (s.contains("\"retStatus\":200")) {
                        long endTime = System.currentTimeMillis();
                        int dTime = (int) ((endTime - startTime) / 1000);//21
                        LogUtil.e("解压文件并读取字符串时长" + dTime);
                        attendeeJsonFileView.attendeeJsonFile(s);
                    } else {
                        StringData data = new Gson().fromJson(s, StringData.class);
                        attendeeJsonFileView.attendeeJsonFileFailed(data.getRespObject());
                    }
                } else {
                    attendeeJsonFileView.attendeeJsonFileFailed("同步参会人员失败");
                }
            } else {
                attendeeJsonFileView.attendeeJsonFileFailed("同步参会人员失败");
            }
        }


        private void deleteFile(File file) {
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 读取解压文件
     *
     * @param file
     * @param charsetName
     * @return
     */
    private String readFile2String(final File file, final String charsetName) {
        if (!isFileExists(file)) return null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            if ((line = reader.readLine()) != null) {
                sb.append(line);
                while ((line = reader.readLine()) != null) {
                    sb.append(LINE_SEP).append(line);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIO(reader);
        }
    }

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void closeIO(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
