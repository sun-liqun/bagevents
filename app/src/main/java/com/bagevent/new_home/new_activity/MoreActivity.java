package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.detail.LoginQrCode;
import com.bagevent.activity_manager.manager_fragment.CollectManagerFragment;
import com.bagevent.activity_manager.manager_fragment.SignInActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.PageTool;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreActivity extends BaseActivity {

    private Bundle bundle = null;
    private int eventId=-1;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @OnClick({R.id.rl_check_in,R.id.rl_collection_manager,R.id.ll_title_back,R.id.rl_login_in,R.id.rl_login_pc})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_check_in:
                Intent intent=new Intent(this,SignInActivity.class);
                intent.putExtra("eventId",eventId);
                startActivity(intent);
                break;
            case R.id.rl_collection_manager:
                PageTool.go(this, CollectorManagerActivity.class,bundle);
                break;
            case R.id.rl_login_in:
                Intent intent1=new Intent(this,LoginQrCode.class);
                intent1.putExtra("eventId",eventId);
                startActivity(intent1);
                break;
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.rl_login_pc:
                Intent intentPc=new Intent(this,BarcodeLoginPcActivity.class);
                intentPc.putExtra("eventId",eventId);
                startActivity(intentPc);
                break;
        }
    }
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        initView();
        intentValue();
    }

    private void intentValue() {
        Intent intent = getIntent();
        bundle = intent.getExtras();
        eventId = bundle.getInt("eventId");
    }

    private void initView() {
        tvTitle.setText(R.string.more);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
    }

}
