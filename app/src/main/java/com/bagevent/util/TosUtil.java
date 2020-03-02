package com.bagevent.util;

import android.view.Gravity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/12
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class TosUtil {
    public static void showCenter(String msg) {
        Toast toast = Toast.makeText(AppUtils.getContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void show(String msg) {
        Toast.makeText(AppUtils.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
