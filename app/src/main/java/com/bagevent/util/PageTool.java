package com.bagevent.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/19
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class PageTool {


    public static void go(Activity activity, Class clzz, Bundle bundle) {
        Intent intent = new Intent(activity, clzz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public static void go(Activity activity, Class clzz) {
        go(activity, clzz, null);
    }
}
