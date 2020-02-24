package com.bagevent.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.MyApplication;
import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.login.loginview.LoginViewInterface;
import com.bagevent.login.model.UserInfo;
import com.bagevent.login.presenter.LoginPresenter;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.data.YunXinToken;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.register.RegisterActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.PageTool;
import com.bagevent.util.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zhy.http.okhttp.callback.Callback;
import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by zwj on 2016/5/25.
 */
public class LoginActivity extends BaseActivity implements LoginViewInterface, View.OnClickListener {
    private EditText txtLoginUsername;//用户名
    private EditText txtLoginPassword;//密码
    private TextView idTextRegister;//注册
    private TextView tvForgetPassword;
    private LoginPresenter presenter;
    private Button idBtnLogin;//登录
    private ImageView barcode_login;//扫描登录
    private ImageView phone_login;//手机号登录
    private ImageView wechat_login; //微信登录

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
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
//        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.login_layout);
//        StatusBarUtil.setTransparent(this);
        initView();
        setListener();

    }

    private void setListener() {
        idBtnLogin.setOnClickListener(this);//登录
        idTextRegister.setOnClickListener(this);//注册
        barcode_login.setOnClickListener(this);//扫描登录
        tvForgetPassword.setOnClickListener(this);
        phone_login.setOnClickListener(this);
        wechat_login.setOnClickListener(this);
    }

    private void initView() {
        txtLoginUsername = findViewById(R.id.txt_login_username);
        txtLoginPassword = findViewById(R.id.txt_login_password);
        idTextRegister = findViewById(R.id.id_text_register);
        idBtnLogin =  findViewById(R.id.id_btn_login);
        barcode_login = findViewById(R.id.barcode_login);
        phone_login = findViewById(R.id.iv_phone_login);
        wechat_login = findViewById(R.id.iv_wechat_login);
        tvForgetPassword = findViewById(R.id.tv_forget_password);
        String userAccount = SharedPreferencesUtil.getAccount(this, "account_info", "");
        if (TextUtils.isEmpty(userAccount)) {
            txtLoginUsername.setText("");
        } else {
            txtLoginUsername.setText(userAccount);
        }

        if (!MyApplication.iwxapi.isWXAppInstalled()) {
            wechat_login.setVisibility(View.GONE);
        }

    }


    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String getUserName() {
        return txtLoginUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return txtLoginPassword.getText().toString();
    }

    @Override
    public void clearUserName() {
        txtLoginUsername.setText("");
    }

    @Override
    public void clearPassword() {
        txtLoginPassword.setText("");
    }

    @Override
    public void toMainActivity(UserInfo userInfo) {
        idBtnLogin.setText(R.string.login);
        //清除sharePreference中的内容
        // SharedPreferencesUtil.clear(this);
        //使用Sharepreference保存用户信息
        SharedPreferencesUtil.put(this, "loginType", "btnLogin");
        SharedPreferencesUtil.put(this, "userId", userInfo.getReturnObj().getUserId() + "");
        SharedPreferencesUtil.put(this, "email", userInfo.getReturnObj().getEmail());
        SharedPreferencesUtil.put(this, "cellphone", userInfo.getReturnObj().getCellphone());
        SharedPreferencesUtil.put(this, "userName", userInfo.getReturnObj().getUserName());
        SharedPreferencesUtil.put(this, "avatar", userInfo.getReturnObj().getAvatar());
        SharedPreferencesUtil.put(this, "source", userInfo.getReturnObj().getSource() + "");
        SharedPreferencesUtil.put(this, "token", userInfo.getReturnObj().getToken());
        SharedPreferencesUtil.put(this, "state", userInfo.getReturnObj().getState() + "");
        SharedPreferencesUtil.put(this, "autoLoginToken", userInfo.getReturnObj().getAutoLoginToken());
        SharedPreferencesUtil.put(this, "autoLoginExpireTime",userInfo.getReturnObj().getAutoLoginExpireTime()+"");
        SharedPreferencesUtil.putAccount(this, "account_info", txtLoginUsername.getText().toString());
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
                                            Toast.makeText(LoginActivity.this, "IM："+code, Toast.LENGTH_SHORT).show();
                                            CrashReport.postCatchedException(new Throwable("云信登录失败"+code+""));
                                        }

                                        @Override
                                        public void onException(Throwable exception) {
                                            LogUtil.e("云信登录异常"+exception+"");
                                            Toast.makeText(LoginActivity.this, "登录异常："+exception, Toast.LENGTH_SHORT).show();
                                            CrashReport.postCatchedException(new Throwable("云信登录异常"+exception+""));
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
        idBtnLogin.setText(R.string.login);
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_login:
                if (NetUtil.isConnected(this)) {
                    if (txtLoginUsername.getText().toString().isEmpty() || txtLoginPassword.getText().toString().isEmpty()) {
                        Toast toast = Toast.makeText(this, R.string.name_password_null, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        idBtnLogin.setText(R.string.is_login);
                        presenter = new LoginPresenter(this);
                        presenter.login();
                    }
                } else {
                    Toast toast = Toast.makeText(this, R.string.check_your_net, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            case R.id.id_text_register:
                Bundle registerBundle = new Bundle();
                registerBundle.putInt(KeyUtil.KEY_TYPE, 2);
                PageTool.go(this, RegisterActivity.class, registerBundle);
                break;
            case R.id.barcode_login: //扫描登录
                //intent1.putExtra(Common.BARCODE_LOGIN,Common.BARCODE_LOGIN);
                PageTool.go(this, BarCodeLoginActivity.class);
                break;
            case R.id.tv_forget_password:
                Bundle registerBundle2 = new Bundle();
                registerBundle2.putInt(KeyUtil.KEY_TYPE, 1);
                PageTool.go(this, RegisterActivity.class, registerBundle2);
                break;
            case R.id.iv_phone_login: //手机号登录
                PageTool.go(this, PhoneLoginActivity.class);
                break;
            case R.id.iv_wechat_login: //微信登录
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "bagevent_sdk";
                MyApplication.iwxapi.sendReq(req);
                break;
        }
    }
}
