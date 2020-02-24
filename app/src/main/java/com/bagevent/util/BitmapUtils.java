package com.bagevent.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * =============================================
 * <p>
 * 作者：lshsh
 * 时间：2019/1/10 09:23
 * 描述：
 * <p>
 * =============================================
 */
public class BitmapUtils {

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
