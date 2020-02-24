package com.bagevent;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.SysInfoUtil;
import com.alibaba.fastjson.JSON;
import com.bagevent.home.MainActivity;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.SharedPreferencesUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.mixpush.MixPushService;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.Map;
import java.util.prefs.Preferences;

public class NotificationActivity extends BaseActivity {

    private static final String TAG = "WelcomeActivity";

    private boolean customSplash = false;

    private static boolean firstEnter = true; // 是否首次进入
    String account;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notification);
//        account =SharedPreferencesUtil.get(this, "accid", "");
//        DemoCache.setMainTaskLaunching(true);
//
//        if (savedInstanceState != null) {
//            setIntent(new Intent()); // 从堆栈恢复，不再重复解析之前的intent
//        }
//
////        if (!firstEnter) {
////            onIntent(); // APP进程还在，Activity被重新调度起来
////        } else {
////            showSplashView(); // APP进程重新起来
////        }
//        onIntent();
//
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_notification);
        account =SharedPreferencesUtil.get(this, "accid", "");
        DemoCache.setMainTaskLaunching(true);

        if (savedInstanceState != null) {
            setIntent(new Intent()); // 从堆栈恢复，不再重复解析之前的intent
        }

//        if (!firstEnter) {
//            onIntent(); // APP进程还在，Activity被重新调度起来
//        } else {
//            showSplashView(); // APP进程重新起来
//        }
        onIntent();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        /*
         * 如果Activity在，不会走到onCreate，而是onNewIntent，这时候需要setIntent
         * 场景：点击通知栏跳转到此，会收到Intent
         */
        setIntent(intent);
        if (!customSplash) {
            onIntent();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (firstEnter) {
//            firstEnter = false;
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    if (!NimUIKit.isInitComplete()) {
//                        LogUtil.i(TAG, "wait for uikit cache!");
//                        new Handler().postDelayed(this, 100);
//                        return;
//                    }
//
//                    customSplash = false;
//                    if (canAutoLogin()) {
//                        onIntent();
//                    } else {
//                        finish();
//                    }
//                }
//            };
//            if (customSplash) {
//                new Handler().postDelayed(runnable, 1000);
//            } else {
//                runnable.run();
//            }
//        }
//    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DemoCache.setMainTaskLaunching(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }

    // 处理收到的Intent
    private void onIntent() {
        LogUtil.i(TAG, "onIntent...");

        if (TextUtils.isEmpty(account)) {
            // 判断当前app是否正在运行
            if (!SysInfoUtil.stackResumed(this)) {
//                LoginActivity.start(this);
            }
            finish();
        } else {
            // 已经登录过了，处理过来的请求
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
                    parseNotifyIntent(intent);
                    return;
                }
            }

            if (!firstEnter && intent == null) {
                finish();
            } else {
                showMainActivity();
            }
        }
    }

//    /**
//     * 已经登陆过，自动登陆
//     */
//    private boolean canAutoLogin() {
//        String account = "";
//        String token = "";
//
////        Log.i(TAG, "get local sdk token =" + token);
//        return !TextUtils.isEmpty(account) && !TextUtils.isEmpty(token);
//    }

    private void parseNotifyIntent(Intent intent) {
        ArrayList<IMMessage> messages = (ArrayList<IMMessage>) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
        if (messages == null || messages.size() > 1) {
            showMainActivity(null);
        } else {
            showMainActivity(new Intent().putExtra(NimIntent.EXTRA_NOTIFY_CONTENT, messages.get(0)));
        }
    }


    private void showMainActivity() {
        showMainActivity(null);
    }

    private void showMainActivity(Intent intent) {
        HomePage.start(NotificationActivity.this, intent);
        finish();
    }
}
