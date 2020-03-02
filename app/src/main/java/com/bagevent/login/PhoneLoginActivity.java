package com.bagevent.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.login.loginview.PhoneLoginViewInterface;
import com.bagevent.login.model.UserInfo;
import com.bagevent.login.presenter.PhoneLoginPresenter;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.data.YunXinToken;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;
import okhttp3.Response;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/8
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class PhoneLoginActivity extends BaseActivity implements PhoneLoginViewInterface, View.OnClickListener {
    private EditText txt_login_username;
    private EditText txt_login_code;
    private TextView tv_auth_code;
    private Button btn_login;
    private PhoneLoginPresenter phoneLoginPresenter;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.phone_login_layout);
//        StatusBarUtil.setTransparent(this);
//        initView();
//        setListener();
//        phoneLoginPresenter = new PhoneLoginPresenter(this);
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.phone_login_layout);
        initView();
        setListener();
        phoneLoginPresenter = new PhoneLoginPresenter(this);
    }

    private void setListener() {
        tv_auth_code.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    }

    private void initView() {
        txt_login_username = findViewById(R.id.txt_login_username);
        txt_login_code = findViewById(R.id.txt_login_code);
        tv_auth_code = findViewById(R.id.tv_auth_code);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    public String getUserName() {
        return txt_login_username.getText().toString().trim();
    }

    @Override
    public String getAuthCode() {
        return txt_login_code.getText().toString().trim();
    }

    @Override
    public void updateCountDown(String msg) {
        tv_auth_code.setText(msg);
    }

    @Override
    public void setAuthCodeEnable(boolean enable) {
        tv_auth_code.setEnabled(enable);
    }

    @Override
    public void clearUserName() {
        txt_login_username.setText("");
    }

    @Override
    public void clearAuthCode() {
        txt_login_code.setText("");
    }

    @Override
    public void toMainActivity(UserInfo userInfo) {
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
        SharedPreferencesUtil.putAccount(this, "account_info",getUserName());

        String token=userInfo.getReturnObj().getToken();
        OkHttpUtil.Get(this)
                .url(Constants.NEW_URL + Constants.YUNXIN_TOKEN)
                .addParams("action","")
                .addParams("userToken",token)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.e("云信获取token失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            YunXinToken yunXin=new Gson().fromJson(response,YunXinToken.class);
                            String accid=yunXin.getRespObject().getAccid();
                            String yunxin_token=yunXin.getRespObject().getToken();
                            LogUtil.e("云信获取token成功"+yunxin_token);
                            LoginInfo loginInfo=new LoginInfo(accid.toLowerCase(),yunxin_token);
                            RequestCallback<LoginInfo> callback =
                                    new RequestCallback<LoginInfo>() {
                                        @Override
                                        public void onSuccess(LoginInfo param) {
                                            putYunXinInfo(accid.toLowerCase(),yunxin_token);
                                            LogUtil.e("云信登录成功");
                                            NimUIKit.loginSuccess(accid.toLowerCase());
                                            // 初始化消息提醒配置
                                            initNotificationConfig();
                                            EventBus.getDefault().postSticky(new MessageEvent(MessageAction.REFRESH_YUNXIN_MESSAGE));
                                        }

                                        @Override
                                        public void onFailed(int code) {
                                            LogUtil.e("云信登录失败");
                                            CrashReport.postCatchedException(new Throwable("云信登录失败"+code+""));
                                        }

                                        @Override
                                        public void onException(Throwable exception) {
                                            LogUtil.e("云信登录异常"+exception+"");
                                            CrashReport.postCatchedException(new Throwable("云信登录异常"+exception));
                                        }
                                    };
                            NIMClient.getService(AuthService.class).login(loginInfo)
                                    .setCallback(callback);
                        } else {
                            LogUtil.e("云信获取token失败");
                        }
                    }
                });

        Intent intent = new Intent(this, HomePage.class);//这边修改跳转 MainActivity-->Homepage
        startActivity(intent);
        AppManager.getAppManager().finishActivity();

    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(true);
    }

    private void putYunXinInfo(String accid, String yunxin_token) {
        SharedPreferencesUtil.put(this, "accid", accid);
        SharedPreferencesUtil.put(this, "yunxin_token", yunxin_token);
    }

    @Override
    public void showFailedErr(String errInfo) {
        TosUtil.show(errInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                phoneLoginPresenter.login(this);
                break;
            case R.id.tv_auth_code:
                if (phoneLoginPresenter.checkPhone(this)) {
                    phoneLoginPresenter.sendAuthCode(this,getUserName());
                }
                break;
        }
    }
}
