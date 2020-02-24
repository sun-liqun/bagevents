package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.new_home.data.WithDrawAccountData;
import com.bagevent.new_home.new_interface.new_view.GetWithDrawValiCodeView;
import com.bagevent.new_home.new_interface.new_view.SetWithDrawPasswordView;
import com.bagevent.new_home.new_interface.presenter.GetWithdrawValicodePresenter;
import com.bagevent.new_home.new_interface.presenter.SetWithdrawPasswordPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public class SetWithDrawPassword extends BaseActivity implements GetWithDrawValiCodeView, SetWithDrawPasswordView {
    private static final int TIME_TASK = 1000;

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_password)
    ShowHidePasswordEditText etPassword;
    @BindView(R.id.et_again_password)
    ShowHidePasswordEditText etAgiainPassword;
    @BindView(R.id.tv_confirm_add_account)
    TextView tvConfirmAddAccount;
    @BindView(R.id.tv_send_valicode)
    TextView tvSendValicode;
    @BindView(R.id.et_valicode)
    EditText etValicode;
    private String userId;
    private String valicode;
    private TimeCount timeCount;
    private SetWithdrawPasswordPresenter setWithdrawPasswordPresenter;
    private GetWithdrawValicodePresenter getWithrawValicode;
    private String accountBanlance;
    private WithDrawAccountData drawAccountData;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_withdraw_password);
        ButterKnife.bind(this);
        getIntentValue();
        initVirwe();
    }

    @OnClick({R.id.ll_title_back, R.id.tv_confirm_add_account, R.id.tv_send_valicode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_confirm_add_account:
                if (NetUtil.isConnected(this)) {
                    if (!TextUtils.isEmpty(etPassword.getText().toString())) {
                        if (!TextUtils.isEmpty(etAgiainPassword.getText().toString())) {
                            if(!TextUtils.isEmpty(etValicode.getText().toString())) {
                                setWithdrawPasswordPresenter = new SetWithdrawPasswordPresenter(this);
                                setWithdrawPasswordPresenter.setPassword();
                            }else {
                                Toast.makeText(this, R.string.login_user_code, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, R.string.confirm_password, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, R.string.et_first_password, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_send_valicode:
                if (NetUtil.isConnected(this)) {
                    timeCount = new TimeCount(this, tvSendValicode, 60000);
                    timeCount.start();
                    getWithrawValicode = new GetWithdrawValicodePresenter(this);
                    getWithrawValicode.getValiCode();
                } else {
                    Toast.makeText(this, R.string.check_network1, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = SharedPreferencesUtil.get(this, "userId", "");
        if (bundle != null) {
            accountBanlance = bundle.getString("account", "0");
            drawAccountData = (WithDrawAccountData) bundle.getSerializable("WithDrawAccountData");
        }
    }

    private void initVirwe() {
        tvTitle.setText(R.string.set_withdrawal_pwd);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public String password() {
        return etPassword.getText().toString();
    }

    @Override
    public String confirmPassword() {
        return etAgiainPassword.getText().toString();
    }

    @Override
    public String validCode() {
        return etValicode.getText().toString();
    }

    @Override
    public void showSetWithdrawPasswordSuccess(String response) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WithDrawActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("account", accountBanlance);
        bundle.putSerializable("WithDrawAccountData", drawAccountData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showSetWithdrawPasswordFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();

    }

    @Override
    public int type() {
        return 1;
    }

    @Override
    public void showValicodeSuccess(String response) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showValicodeFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }


    class TimeCount extends CountDownTimer {
        private Context context;
        private TextView tvTime;

        public TimeCount(Context context, TextView textView, long millisInFuture) {
            super(millisInFuture, TIME_TASK);
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
