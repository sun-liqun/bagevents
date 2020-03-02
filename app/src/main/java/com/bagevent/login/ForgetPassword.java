package com.bagevent.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.login.loginview.ModifyPwView;
import com.bagevent.login.presenter.ModifyPwPresenter;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.new_home.new_activity.SetWithDrawPassword;
import com.bagevent.new_home.new_interface.new_view.GetRequireSmsCodeView;
import com.bagevent.new_home.new_interface.presenter.GetRequireSmsCodePresenter;
import com.bagevent.new_home.new_interface.presenter.GetWithdrawValicodePresenter;
import com.bagevent.register.data.GetSMSData;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.xw.repo.XEditText;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZWJ on 2018/1/11 0011.
 */

public class ForgetPassword extends BaseActivity implements GetRequireSmsCodeView,ModifyPwView {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.ll_common_title)
    AutoLinearLayout llCommonTitle;
    @BindView(R.id.et_cellphone)
    XEditText etCellphone;
    @BindView(R.id.et_valid_code)
    EditText etValidCode;
    @BindView(R.id.tv_get_valid_code)
    TextView tvGetValidCode;
    @BindView(R.id.et_first_password)
    XEditText etFirstPassword;
    @BindView(R.id.et_second_password)
    XEditText etSecondPassword;
    @BindView(R.id.tv_modify_password)
    TextView tvModifyPassword;
    private TimeCount timeCount;
    private GetRequireSmsCodePresenter getRequireSmsCodePresenter;
    private ModifyPwPresenter modifyPwPresenter;

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
        setContentView(R.layout.layout_forget_password);
        ButterKnife.bind(this);
        getIntentValue();
        initView();
    }

    @OnClick({R.id.ll_title_back, R.id.tv_get_valid_code, R.id.tv_modify_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_get_valid_code:
                if (NetUtil.isConnected(this)) {
                    timeCount = new TimeCount(this, tvGetValidCode, 60000);
                    timeCount.start();
                    getRequireSmsCodePresenter = new GetRequireSmsCodePresenter(this);
                    getRequireSmsCodePresenter.getSms();
                } else {
                    Toast.makeText(this, R.string.check_network1, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_modify_password:
                if(!TextUtils.isEmpty(etCellphone.getText().toString())) {
                    if(!TextUtils.isEmpty(etValidCode.getText().toString())) {
                        if(!TextUtils.isEmpty(etFirstPassword.getText().toString())) {
                            if(!TextUtils.isEmpty(etSecondPassword.getText().toString())) {
                                modifyPwPresenter = new ModifyPwPresenter(this);
                                modifyPwPresenter.modifyPassword();
                            }else {
                                Toast.makeText(this, R.string.et_second_password, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(this, R.string.et_first_password, Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, R.string.login_user_code, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, R.string.input_phone, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getIntentValue() {
        String cellphone = getIntent().getStringExtra("cellphone");
        etCellphone.setText(cellphone);
    }

    private void initView() {
        llCommonTitle.setBackgroundColor(ContextCompat.getColor(this,R.color.transparent));
        //llCommonTitle.setBackgroundResource(R.color.transparent);
        Glide.with(this).load(R.drawable.ic_back_white).into(ivTitleBack);
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String phoneNum() {
        return etCellphone.getText().toString();
    }

    @Override
    public String smsCode() {
        return etValidCode.getText().toString();
    }

    @Override
    public String password() {
        return etFirstPassword.getText().toString();
    }

    @Override
    public String passwordAgaing() {
        return etSecondPassword.getText().toString();
    }

    @Override
    public void showModifySuccess(String response) {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void showModifyFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int source() {
        return 1;
    }

    @Override
    public void showSmsCode(GetSMSData response) {
        Toast.makeText(this, R.string.send_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSmsFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    class TimeCount extends CountDownTimer {
        private Context context;
        private TextView tvTime;

        public TimeCount(Context context, TextView textView, long millisInFuture) {
            super(millisInFuture, 1000);
            this.context = context;
            this.tvTime = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //开始倒计时,将textview设为不可点击
            tvTime.setClickable(false);
            String s = millisUntilFinished / 1000 + "s";
            tvTime.setText(s + getString(R.string.second1));
        }

        @Override
        public void onFinish() {
            //倒计时结束将textview设为可点击
            tvTime.setClickable(true);
            tvTime.setText(R.string.send_verification_code);
        }
    }
}
