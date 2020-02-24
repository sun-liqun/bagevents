package com.bagevent.util;

import android.util.Log;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/6
 * <p>
 * desp 打印日志封装类
 * <p>
 * <p>
 * =============================================
 */
public class LogUtil {
    private static final String TAG = "bagevents";

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    /**
     * 打印超长字符
     *
     * @param msg
     */
    public static void iL(String msg) {
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - TAG.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            i(msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        i(msg);
    }

    public static void eL(String msg) {
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - TAG.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            e(msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        e(msg);
    }

    public static void dL(String msg) {
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - TAG.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            d(msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        d(msg);
    }
}
