package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.new_home.new_interface.new_view.LoginPcView;
import com.bagevent.new_home.new_interface.presenter.LoginPcPresenter;
import com.bagevent.util.AppManager;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectDeviceFunctionActivity extends BaseActivity implements LoginPcView {

    int eventId;
    int code;
    String confirmQrCode="";
    String functionTag="";
    LoginPcPresenter loginPcPresenter;

    @OnClick({R.id.ll_speaker_manage,R.id.ll_check_in_entry,R.id.ll_title_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_check_in_entry:
                functionTag="F_T_301";
                loginPcPresenter = new LoginPcPresenter(this);
                loginPcPresenter.loginPc();
                break;
            case R.id.ll_speaker_manage:
                functionTag="F_T_302";
                loginPcPresenter = new LoginPcPresenter(this);
                loginPcPresenter.loginPc();
                break;
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void initUI(Bundle savedInstanceState) {
       setContentView(R.layout.activity_select_device_function);
       ButterKnife.bind(this);
       initView();
       Intent intent=getIntent();
       eventId=intent.getIntExtra("eventId",-1);
       confirmQrCode=intent.getStringExtra("confirmQrCode");
    }

    private void initView() {
        tvTitle.setText(R.string.login_pc);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String confirmQrCode() {
        return confirmQrCode;
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public String functionTag() {
        return functionTag;
    }

    @Override
    public void loginPcSuccess() {
      AppManager.getAppManager().finishActivity();
    }

    @Override
    public void loginPcFailed(String errInfo) {
        Toast.makeText(SelectDeviceFunctionActivity.this, errInfo, Toast.LENGTH_SHORT).show();
    }
}
