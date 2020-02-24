package com.bagevent.activity_manager.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by zwj on 2017/4/25.
 */

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    static final String ORDER_STATUS = "ORDER_STATUS";
    static final String ORDER_NUM = "ORDER_NUM";
    static final String ORDER_NAME = "ORDER_NAME";
    static final String ORDER_EMAIL = "ORDER_EMAIL";
    static final String ORDER_CELLPHONE = "ORDER_CELLPHONE";
    static final String ORDER_DATE = "ORDER_DATE";
    private TextView orderNum;
    private TextView orderStatus;
    private TextView orderName;
    private TextView orderEmail;
    private TextView orderCellphone;
    private TextView orderDate;
    private AutoLinearLayout orderBack;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.order_detail);
//        initView();
//        setListener();
//        setData();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.order_detail);
        initView();
        setListener();
        setData();
    }

    private void setData() {
        Intent intent = getIntent();
        if(intent != null) {
            orderNum.setText(intent.getStringExtra(ORDER_NUM));
            orderName.setText(intent.getStringExtra(ORDER_NAME));
            orderEmail.setText(intent.getStringExtra(ORDER_EMAIL));
            orderCellphone.setText(intent.getStringExtra(ORDER_CELLPHONE));
            orderDate.setText(intent.getStringExtra(ORDER_DATE));
            int status = intent.getIntExtra(ORDER_STATUS,-1);
            if(status == 0) {
                orderStatus.setText(R.string.unPaid);
                orderStatus.setTextColor(ContextCompat.getColor(this,R.color.aFF1D00));
            }else if(status == 1) {
                orderStatus.setText(R.string.paid);
                orderStatus.setTextColor(ContextCompat.getColor(this,R.color.gray));
            }else if(status == 2){
                orderStatus.setText(R.string.overtimeUnpaid);
                orderStatus.setTextColor(ContextCompat.getColor(this,R.color.aFF1D00));
            }else if(status == 4) {
                orderStatus.setText(R.string.paying);
                orderStatus.setTextColor(ContextCompat.getColor(this,R.color.a59c709));
            }else if(status == 5) {
                orderStatus.setText(R.string.cancelPaid);
                orderStatus.setTextColor(ContextCompat.getColor(this,R.color.gray));
            }else if(status == 6) {
                orderStatus.setText(R.string.offLinePay);
                orderStatus.setTextColor(ContextCompat.getColor(this,R.color.gray));
            }
        }
    }

    private void setListener() {
        orderBack.setOnClickListener(this);
    }

    private void initView() {
        orderNum = (TextView)findViewById(R.id.tv_order_detail_num);
        orderStatus = (TextView)findViewById(R.id.tv_order_detail_status);
        orderName = (TextView)findViewById(R.id.tv_order_detail_name);
        orderEmail = (TextView)findViewById(R.id.tv_order_detail_email);
        orderCellphone = (TextView)findViewById(R.id.tv_order_detail_cellphone);
        orderDate = (TextView)findViewById(R.id.tv_order_detail_date);
        orderBack = (AutoLinearLayout)findViewById(R.id.ll_order_detail_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_order_detail_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }
}
