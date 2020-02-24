package com.bagevent.util;

import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by zwj on 2017/9/7.
 */

public class   UnZIPUtil {
    public static String unZIPFile(File file) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry;
            while ((zipEntry = zin.getNextEntry()) != null) {
                if (!zipEntry.isDirectory()) {
                    String fileName = filePath + "/" + zipEntry.getName();
                    FileOutputStream fo = new FileOutputStream(fileName);
                    BufferedOutputStream bo = new BufferedOutputStream(fo);
                    byte[] buffer = new byte[4096];
                    int read = 0;
                    while ((read = zin.read(buffer)) != -1) {
                        bo.write(buffer, 0, read);
                    }
                    bo.flush();
                    bo.close();
                    zin.close();
                    return fileName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
