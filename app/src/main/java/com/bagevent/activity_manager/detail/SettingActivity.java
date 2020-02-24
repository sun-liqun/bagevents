package com.bagevent.activity_manager.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bagevent.R;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by zwj on 2016/10/14.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private AutoRelativeLayout rl_login_qrcode;
    private AutoLinearLayout ll_set_back;
    private int eventId = -1;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.parameter_set);
//        Intent intent = getIntent();
//        eventId = intent.getIntExtra("eventId",-1);
//        initView();
//        setListener();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.parameter_set);
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId",-1);
        initView();
        setListener();
    }

    private void setListener() {
        rl_login_qrcode.setOnClickListener(this);
        ll_set_back.setOnClickListener(this);
    }

    private void initView() {
        rl_login_qrcode = (AutoRelativeLayout)findViewById(R.id.rl_login_qrcode);
        ll_set_back = (AutoLinearLayout)findViewById(R.id.ll_set_back);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_login_qrcode:
                Intent intent = new Intent(this,LoginQrCode.class);
                intent.putExtra("eventId",eventId);
                startActivity(intent);
                break;
            case R.id.ll_set_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }
}
