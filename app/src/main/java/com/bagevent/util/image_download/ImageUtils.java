package com.bagevent.util.image_download;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
import com.bagevent.db.Attends_Table;
import com.bagevent.util.OkHttpUtil;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;

/**
 * Created by zwj on 2016/8/8.
 */
public class ImageUtils {
    private static final int REQUECT_CODE_SDCARD = 1;
    public static final File APP_DIR = new File(Environment.getExternalStorageDirectory(), "BagEvent");

    public static final File IMG_DIR = new File(Environment.getExternalStorageDirectory() + "/BagEvent","ImageCache");
    private static final ExecutorService fixExecutorService = Executors.newFixedThreadPool(5);

    public static void saveImageToLocal(String fileName, Bitmap bitmap) {
        if( !APP_DIR.exists()) {
            APP_DIR.mkdir();
        }
        if(!IMG_DIR.exists()) {
            IMG_DIR.mkdir();
        }
        File file = new File(IMG_DIR,fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
            fos.close();
            bitmap.recycle();
        } catch (IOException e) {
            Log.e("ImageUtil","save image is err");
            //e.printStackTrace();
        } catch (NullPointerException e) {
            Log.e("ImageUtil","IMG_DIR is Null");
        }

    }

    public static boolean isFileExists(String fileName) {
        return new File(IMG_DIR, fileName).exists();
    }

    public static void loadImage(Context mContext,int eventId) {
        List<Attends> attende = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).queryList();
        //  Log.e("attendee size-->",attende.size()+"");
        if (attende.size() != 0) {
            for (int i = 0; i < attende.size(); i++) {
            //    Log.e("attendee size-->",attende.get(i).attendeeAvatar+"AAA");
                if (!TextUtils.isEmpty(attende.get(i).attendeeAvatar)) {
                   final String fileName = attende.get(i).attendeeAvatar.replace("/", "_");
                    if(!ImageUtils.isFileExists(fileName)) {
                        String url = Constants.imgsURL + attende.get(i).attendeeAvatar;
                        OkHttpUtil.okGet(mContext)
                                .url(url)
                                .build()
                                .execute(new BitmapCallback() {
                                    @Override
                                    public void onError(Call call, Exception e,int id) {

                                    }

                                    @Override
                                    public void onResponse(final Bitmap bitmap,int id) {
                                        fixExecutorService.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                ImageUtils.saveImageToLocal(fileName, bitmap);
                                            }
                                        });
                                    }
                                });
                    }
                }
            }
        }
    }
}
