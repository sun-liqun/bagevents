package com.bagevent.register;

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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.home.MainActivity;
import com.bagevent.login.ForgetPassword;
import com.bagevent.login.LoginActivity;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.register.data.RegisterData;
import com.bagevent.register.presenter.GetSMSCoutDownPresenter;
import com.bagevent.register.presenter.RegisterNextPresenter;
import com.bagevent.register.presenter.RegisterPresenter;
import com.bagevent.register.registerview.GetSMSCountDown;
import com.bagevent.register.registerview.NextRegisterViewInterface;
import com.bagevent.register.registerview.RegisterViewInterface;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by zwj on 2016/5/26.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener,NextRegisterViewInterface,RegisterViewInterface,GetSMSCountDown {
    /**
     * 用户名、密码、短信
     */
    private EditText userName_register,password_register,message_register;
    private FrameLayout register_fmlayout;
    /**
     * 下一步，注册按钮
     */
    private Button btn_next_register,btn_register;

    /**
     *用户协议、点击登录、获取验证码、重新输入手机号
     */
    private TextView agreement_text,login_text,text_get_message,reset_phone_text;

    /**
     *检查手机号是否合法,执行下一步操作
     * 验证码倒计时
     *注册
     */
    private RegisterNextPresenter nextPresenter;
    private GetSMSCoutDownPresenter getSMSCoutDown;
    private RegisterPresenter register;
    private boolean isClickRegister = true;
    private int type;//判断是忘记密码还是注册

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
        setContentView(R.layout.register_layout);
        getIntentValue();
        initView();
        setClickListener();
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type",2);
    }

    private void setClickListener(){
        register_fmlayout.setOnClickListener(this);
        btn_next_register.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        agreement_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
        text_get_message.setOnClickListener(this);
    }
    private void initView(){
        register_fmlayout = (FrameLayout)findViewById(R.id.register_fmlayout);

        userName_register = (EditText)findViewById(R.id.edit_register_phone);
        password_register = (EditText)findViewById(R.id.edit_register_password);
        message_register = (EditText)findViewById(R.id.edit_register_message);

        btn_next_register = (Button)findViewById(R.id.btn_register_next);
        btn_register = (Button)findViewById(R.id.id_btn_register);

        agreement_text = (TextView)findViewById(R.id.text_register_agreement);
        login_text = (TextView)findViewById(R.id.text_have_num);
        text_get_message = (TextView)findViewById(R.id.text_get_message);
        reset_phone_text = (TextView)findViewById(R.id.reset_phone_text);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register_next://下一步
                if(type == 2) {
                    if(NetUtil.isConnected(this)) {
                        nextPresenter = new RegisterNextPresenter(this);
                        nextPresenter.registerNext();
                    }else {
                        Toast toast = Toast.makeText(this,R.string.check_your_net,Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                }else {
                    if(!TextUtils.isEmpty(userName_register.getText().toString())) {
                        if(CompareRex.isCellPhone(userName_register.getText().toString())) {
                            Intent intent = new Intent(this, ForgetPassword.class);
                            intent.putExtra("cellphone",userName_register.getText().toString());
                            startActivity(intent);
                        }else {
                            Toast.makeText(this,R.string.phone_err1,Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this,R.string.input_phone,Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.id_btn_register://注册
                if(userName_register.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(this,R.string.input_username,Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else if(password_register.getText().toString().isEmpty()){
                    Toast toast = Toast.makeText(this,R.string.et_first_password,Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else if(message_register.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(this,R.string.login_user_code,Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else {
                    if(NetUtil.isConnected(this)) {
                        if(isClickRegister) {
                            isClickRegister = false;
                            register = new RegisterPresenter(this);
                            register.register();
                        }
                    }else {
                        Toast toast = Toast.makeText(this,R.string.check_your_net,Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                }

                break;
            case R.id.text_get_message://获取验证码
                getSMSCoutDown = new GetSMSCoutDownPresenter(this);
                getSMSCoutDown.startCountDown();
                break;
            case R.id.text_register_agreement://用户协议
                Intent argument = new Intent(this,UserArgument.class);
                startActivity(argument);
                break;
            case R.id.text_have_num://已有账号,跳转到登录
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                AppManager.getAppManager().finishActivity();
                break;
            default:
                break;
        }

    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public String getPhoneNum() {
        return userName_register.getText().toString();
    }

    @Override
    public int getSource() {
        return 0;
    }

    @Override
    public void startCutDown(Long mills) {
        String s = mills / 1000 + "s";
        text_get_message.setText(s);
    }


    @Override
    public void stopCutDown() {
        text_get_message.setText(R.string.get_identify_code);
    }

    @Override
    public void showErrInfo(String errInfo) {
        Toast.makeText(this,errInfo,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastMsg() {

    }

    @Override
    public void textClickEnable() {
        text_get_message.setClickable(true);
    }

    @Override
    public void textClickUnable() {
        text_get_message.setClickable(false);
    }

    @Override
    public String getPhonePassword() {
        return password_register.getText().toString();
    }

    @Override
    public String getIdentifyCode() {
        return message_register.getText().toString();
    }

    @Override
    public void showRegisterFail(String err) {
        Toast.makeText(RegisterActivity.this,err,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toLoginActivity(RegisterData response) {
        //清除sharePreference中的内容
        SharedPreferencesUtil.clear(this);
        //使用Sharepreference保存用户信息
        SharedPreferencesUtil.put(this, "userId", response.getReturnObj().getUserId() + "");
        SharedPreferencesUtil.put(this, "email", response.getReturnObj().getEmail());
        SharedPreferencesUtil.put(this, "cellphone", response.getReturnObj().getCellphone());
        SharedPreferencesUtil.put(this, "userName", response.getReturnObj().getUserName());
        SharedPreferencesUtil.put(this, "avatar", response.getReturnObj().getAvatar());
        SharedPreferencesUtil.put(this, "source", response.getReturnObj().getSource() + "");
        SharedPreferencesUtil.put(this, "token", response.getReturnObj().getToken());
        SharedPreferencesUtil.put(this, "state", response.getReturnObj().getState() + "");
        SharedPreferencesUtil.put(this, "autoLoginToken", response.getReturnObj().getAutoLoginToken());
        SharedPreferencesUtil.put(this, "autoLoginExpireTime", response.getReturnObj().getAutoLoginExpireTime() + "");
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void showFailInfo(String errInfo) {
        isClickRegister = true;
        Toast.makeText(RegisterActivity.this,errInfo,Toast.LENGTH_SHORT).show();
        //reset_phone_text.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPhoneNumIsExist(String errInfo) {
        Toast.makeText(RegisterActivity.this,errInfo,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doSuccess() {
        btn_next_register.setVisibility(View.GONE);
        register_fmlayout.setVisibility(View.VISIBLE);
        getSMSCoutDown = new GetSMSCoutDownPresenter(this);
        getSMSCoutDown.startCountDown();

    }
}
