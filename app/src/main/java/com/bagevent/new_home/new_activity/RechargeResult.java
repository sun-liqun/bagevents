package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.AcManager;
import com.bagevent.new_home.HomePage;
import com.bagevent.util.AppManager;
import com.bagevent.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zwj on 2017/10/13.
 */

public class RechargeResult extends BaseActivity {
    public static String RECHARGE = "result";
    public static String ORDERNUM = "orderNum";
    public static String PRODUCT = "product";
    public static String FEE = "fee";//充值金额
    public static int RECHARGE_SUCCESS = 0;
    public static int RECHARGE_FAILED = 1;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.tv_paynum)
    TextView tvPaynum;
    @BindView(R.id.btn_retry)
    TextView btnRetry;
    @BindView(R.id.btn_return_home)
    TextView btnReturnHome;
    @BindView(R.id.iv_recharge_status)
    ImageView ivRechargeStatus;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.recharge_result);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        int flag = intent.getIntExtra(RECHARGE, -1);
        if (flag == RECHARGE_SUCCESS) {
            String orderNum = SharedPreferencesUtil.get(this,"rechargeOrderNum","");
            String orderFee = SharedPreferencesUtil.get(this,"rechargeFee","");
            tvTitle.setText(R.string.recharge_success);
            tvOrderNum.setText(orderNum);
            tvProduct.setText(R.string.sms_pay);
            tvPaynum.setText(orderFee);
            Glide.with(this).load(R.drawable.chenggong).into(ivRechargeStatus);
            btnRetry.setVisibility(View.GONE);
        } else {
            tvTitle.setText(R.string.recharge_failed);
            Glide.with(this).load(R.drawable.shibai).into(ivRechargeStatus);
            btnRetry.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.btn_retry, R.id.btn_return_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_retry:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.btn_return_home:
                Intent intent = new Intent(this, HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                AppManager.getAppManager().finishAllActivity();
                break;
        }
    }
}
