package com.bagevent.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/12/4
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class InputMethodUtil {
    //如果输入法在窗口上已经显示，则隐藏，反之则显示
    public static void showOrHide(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //view为接受软键盘输入的视图，SHOW_FORCED表示强制显示
    public static void show(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);//SHOW_FORCED表示强制显示
    }

    public static void close(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    //调用隐藏系统默认的输入法
    public static void showOrHide(Context context, Activity activity) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //获取输入法打开的状态
    public static boolean isShowing(Context context,View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive(view);//isOpen若返回true，则表示输入法打开
    }

    public static boolean isSoftShowing(Activity activity) {
         //获取当屏幕内容的高度
         int screenHeight = activity.getWindow().getDecorView().getHeight();
         //获取View可见区域的bottom
         Rect rect = new Rect();
         //DecorView即为activity的顶级view
         activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
         // 考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
         // 选取screenHeight*2/3进行判断
         return screenHeight*2/3 > rect.bottom+getSoftButtonsBarHeight(activity);
    }

    /**     * 底部虚拟按键栏的高度     * @return     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
         activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
         int usableHeight = metrics.heightPixels;
         //获取当前屏幕的真实高度
         activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
         int realHeight = metrics.heightPixels;
         if (realHeight > usableHeight) {
             return realHeight - usableHeight;
         } else {
             return 0;
         }
    }

}
