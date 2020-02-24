package com.bagevent;

import android.app.Activity;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/04/09
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class PatchBaseActivity extends Activity {
    @Override final protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
