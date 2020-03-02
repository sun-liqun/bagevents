package com.bagevent;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.SysInfoUtil;
import com.badoo.mobile.util.WeakHandler;
import com.bagevent.activity_manager.AcManager;
import com.bagevent.activity_manager.detail.CollectionBarcode;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.db.CollectList;
import com.bagevent.db.CollectList_Table;
import com.bagevent.home.MainActivity;
import com.bagevent.login.LoginActivity;
import com.bagevent.login.loginview.LoginViewInterface;
import com.bagevent.login.model.UserInfo;
import com.bagevent.login.presenter.LoginPresenter;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.mixpush.MixPushService;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zwj on 2016/5/26.
 */
public class WelcomeActivity extends BaseActivity implements LoginViewInterface {
    private static final String TAG = "WelcomeActivity";
    public static final String KEY = "eventId";
    private WeakHandler mHandler;
    private String eventId = "";
    private String collectionId = "";
    private String autoToken = "";
    private String userId = "";

    private static boolean firstEnter = true; // 是否首次进入
    private boolean customSplash = false;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.welcome);
        StatusBarUtil.setTransparent(this);
        Bundle jpushBundle = getIntent().getExtras();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO},
                    1);
        }

//        DemoCache.setMainTaskLaunching(true);
//        customSplash = true;
//        if (savedInstanceState != null) {
//            setIntent(new Intent()); // 从堆栈恢复，不再重复解析之前的intent
//        }
//        firstEnter=SharedPreferencesUtil.get(WelcomeActivity.this, "autoLoginToken", "").isEmpty();
//        if (!firstEnter) {
//           // APP进程还在，Activity被重新调度起来
//        } else {
////            showSplashView(); // APP进程重新起来
//        }


        if (jpushBundle != null) {
            eventId = jpushBundle.getString(KEY);
        }

        mHandler = new WeakHandler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String autoCollect = SharedPreferencesUtil.get(WelcomeActivity.this, "autoCollect", "");
                if (!TextUtils.isEmpty(autoCollect)) {
                    String[] s1 = autoCollect.split(":");
                    eventId = s1[1];
                    collectionId = s1[2];
                    userId = s1[3];
                    autoToken = s1[4];
                    CollectList tempList = new Select().from(CollectList.class).where(CollectList_Table.eventId.is(Integer.parseInt(eventId))).and(CollectList_Table.collectPointId.is(Integer.parseInt(collectionId)))
                            .querySingle();
                    if (tempList != null) {
                        String startTime = tempList.startTime;
                        String endTime = tempList.endTime;
                        if (CompareRex.dateCompare(startTime, endTime).contains(R.string.start + "") || CompareRex.dateCompare(startTime, endTime).contains(R.string.not_start + "")) {
                            goToCollection();
                        } else if (CompareRex.dateCompare(startTime, endTime).contains(R.string.stop + "")) {
                            Log.e(TAG, "该采集点已结束!");
                            SharedPreferencesUtil.clear(WelcomeActivity.this);//增加了采集点自动登录,所以采集结束之后需要清除保存的采集点登录码
                            goToMain();
                        }
                    } else {
                        goToMain();
                    }
                } else {
                    goToMain();
                }

            }

            private void goToMain() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loginToMain();
                    }
                });

            }

            private void goToCollection() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(WelcomeActivity.this, CollectionBarcode.class);
                        if (!TextUtils.isEmpty(eventId)) {
                            intent.putExtra(KeyUtil.KEY_EVENT_ID, Integer.parseInt(eventId));
                        }
                        intent.putExtra(KeyUtil.KEY_COLLECTION_ID, Integer.parseInt(collectionId));
                        intent.putExtra(KeyUtil.KEY_COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_AUTOLOGIN);
                        startActivity(intent);
                        AppManager.getAppManager().finishActivity();
                    }
                });
            }
        }, 1000);
    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.welcome);
//        StatusBarUtil.setTransparent(this);
//        Bundle jpushBundle = getIntent().getExtras();
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
//                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.RECORD_AUDIO},
//                    1);
//        }
//
////        DemoCache.setMainTaskLaunching(true);
////        customSplash = true;
////        if (savedInstanceState != null) {
////            setIntent(new Intent()); // 从堆栈恢复，不再重复解析之前的intent
////        }
////        firstEnter=SharedPreferencesUtil.get(WelcomeActivity.this, "autoLoginToken", "").isEmpty();
////        if (!firstEnter) {
////           // APP进程还在，Activity被重新调度起来
////        } else {
//////            showSplashView(); // APP进程重新起来
////        }
//
//
//        if (jpushBundle != null) {
//            eventId = jpushBundle.getString(KEY);
//        }
//
//        mHandler = new WeakHandler();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String autoCollect = SharedPreferencesUtil.get(WelcomeActivity.this, "autoCollect", "");
//                if (!TextUtils.isEmpty(autoCollect)) {
//                    String[] s1 = autoCollect.split(":");
//                    eventId = s1[1];
//                    collectionId = s1[2];
//                    userId = s1[3];
//                    autoToken = s1[4];
//                    CollectList tempList = new Select().from(CollectList.class).where(CollectList_Table.eventId.is(Integer.parseInt(eventId))).and(CollectList_Table.collectPointId.is(Integer.parseInt(collectionId)))
//                            .querySingle();
//                    if (tempList != null) {
//                        String startTime = tempList.startTime;
//                        String endTime = tempList.endTime;
//                        if (CompareRex.dateCompare(startTime, endTime).contains(R.string.start + "") || CompareRex.dateCompare(startTime, endTime).contains(R.string.not_start + "")) {
//                            goToCollection();
//                        } else if (CompareRex.dateCompare(startTime, endTime).contains(R.string.stop + "")) {
//                            Log.e(TAG, "该采集点已结束!");
//                            SharedPreferencesUtil.clear(WelcomeActivity.this);//增加了采集点自动登录,所以采集结束之后需要清除保存的采集点登录码
//                            goToMain();
//                        }
//                    } else {
//                        goToMain();
//                    }
//                } else {
//                    goToMain();
//                }
//
//            }
//
//            private void goToMain() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        loginToMain();
//                    }
//                });
//
//            }
//
//            private void goToCollection() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(WelcomeActivity.this, CollectionBarcode.class);
//                        if (!TextUtils.isEmpty(eventId)) {
//                            intent.putExtra(KeyUtil.KEY_EVENT_ID, Integer.parseInt(eventId));
//                        }
//                        intent.putExtra(KeyUtil.KEY_COLLECTION_ID, Integer.parseInt(collectionId));
//                        intent.putExtra(KeyUtil.KEY_COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_AUTOLOGIN);
//                        startActivity(intent);
//                        AppManager.getAppManager().finishActivity();
//                    }
//                });
//            }
//        }, 1000);
//    }


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        /*
//         * 如果Activity在，不会走到onCreate，而是onNewIntent，这时候需要setIntent
//         * 场景：点击通知栏跳转到此，会收到Intent
//         */
//        setIntent(intent);
//        if (!customSplash) {
//            onIntent();
//        }
//    }
//    // 处理收到的Intent
//    private void onIntent() {
//        LogUtil.i(TAG, "onIntent...");
//
//        if (TextUtils.isEmpty(DemoCache.getAccount())) {
//            // 判断当前app是否正在运行
//            if (!SysInfoUtil.stackResumed(this)) {
////                LoginActivity.start(this);
//            }
//            finish();
//        } else {
//            // 已经登录过了，处理过来的请求
//            Intent intent = getIntent();
//            if (intent != null) {
//                if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
//                    parseNotifyIntent(intent);
//                    return;
//                }
//            }
//
//            if (!firstEnter && intent == null) {
//                finish();
//            } else {
//                showMainActivity(null);
//            }
//        }
//    }

//    private void parseNotifyIntent(Intent intent) {
//        ArrayList<IMMessage> messages = (ArrayList<IMMessage>)
//                intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
//        if (messages == null || messages.size() > 1) {
//            showMainActivity(null);
//        } else {
//            showMainActivity(new Intent().putExtra(NimIntent.EXTRA_NOTIFY_CONTENT, messages.get(0)));
//        }
//    }
//
//    private void showMainActivity(Intent intent) {
//        MainActivity.start(WelcomeActivity.this, intent);
//        finish();
//    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
//        if (event.mMsg.equals(Common.COLLECT_LOGIN_TYPE_AUTOLOGIN)) {
//            Intent intent = new Intent(this, CollectionBarcode.class);
//            intent.putExtra(KeyUtil.KEY_EVENT_ID, Integer.parseInt(eventId));
//            intent.putExtra(KeyUtil.KEY_COLLECTION_ID, Integer.parseInt(collectionId));
//            intent.putExtra(KeyUtil.KEY_COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_AUTOLOGIN);
//            startActivity(intent);
//            AppManager.getAppManager().finishActivity();
//        } else if (event.mMsg.equals("loginToMain")) {
//            loginToMain();
//        }
    }

    @Override
    protected void onDestroy() {
        mHandler = null;
        super.onDestroy();
    }


    private void loginToMain() {
        if (SharedPreferencesUtil.get(WelcomeActivity.this, "autoLoginToken", "").isEmpty()) {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            AppManager.getAppManager().finishActivity();
        } else if (SharedPreferencesUtil.get(WelcomeActivity.this,"loginType","").contains("barcode")){
            int eventId = Integer.parseInt(SharedPreferencesUtil.get(WelcomeActivity.this, "eventId", "-1"));
            Intent intent = new Intent(this, AcManager.class);
            Bundle bundle = new Bundle();
            bundle.putInt(KeyUtil.KEY_EVENT_ID, eventId);
            bundle.putString(Common.BARCODE_LOGIN, Common.BARCODE_LOGIN);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            AppManager.getAppManager().finishActivity();
        } else {
            if (NetUtil.isConnected(WelcomeActivity.this)) {
                LoginPresenter presenter = new LoginPresenter(WelcomeActivity.this);
                presenter.autoLogin(this,SharedPreferencesUtil.get(WelcomeActivity.this, "autoLoginToken", ""));
            } else {
                Intent intent = new Intent(WelcomeActivity.this, HomePage.class);//这边修改跳转 MainActivity-->Homepage
                startActivity(intent);
                AppManager.getAppManager().finishActivity();
            }
        }
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearPassword() {

    }

    @Override
    public void toMainActivity(UserInfo userInfo) {
        //使用Sharepreference保存用户信息
        SharedPreferencesUtil.put(this, "userId", userInfo.getReturnObj().getUserId() + "");
        SharedPreferencesUtil.put(this, "email", userInfo.getReturnObj().getEmail());
        SharedPreferencesUtil.put(this, "cellphone", userInfo.getReturnObj().getCellphone());
        SharedPreferencesUtil.put(this, "userName", userInfo.getReturnObj().getUserName());
        SharedPreferencesUtil.put(this, "avatar", userInfo.getReturnObj().getAvatar());
        SharedPreferencesUtil.put(this, "source", userInfo.getReturnObj().getSource() + "");
        SharedPreferencesUtil.put(this, "token", userInfo.getReturnObj().getToken());
        SharedPreferencesUtil.put(this, "state", userInfo.getReturnObj().getState() + "");
        SharedPreferencesUtil.put(this, "autoLoginToken", userInfo.getReturnObj().getAutoLoginToken());
        SharedPreferencesUtil.put(this, "autoLoginExpireTime", userInfo.getReturnObj().getAutoLoginExpireTime() + "");
        Intent intent = new Intent(this, HomePage.class);//这边修改跳转 MainActivity-->Homepage
        startActivity(intent);
        AppManager.getAppManager().finishActivity();

    }

    @Override
    public void showFailedErr(String errInfo) {
        if (SharedPreferencesUtil.get(this, "autoLoginToken", "").isEmpty()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            AppManager.getAppManager().finishActivity();
        } else {
            Intent intent = new Intent(this, HomePage.class);//这边修改跳转 MainActivity-->Homepage
            startActivity(intent);
            AppManager.getAppManager().finishActivity();
        }
    }


}
