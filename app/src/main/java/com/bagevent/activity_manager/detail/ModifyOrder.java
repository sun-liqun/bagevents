package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ModifyOrderPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ModifyOrderView;
import com.bagevent.common.Common;
//import com.bagevent.db.Order_Table;
import com.bagevent.db.Order;
import com.bagevent.db.Order_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.NetUtil;
import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WenJie on 2017/10/30.
 */

public class ModifyOrder extends BaseActivity implements ModifyOrderView{
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.et_buer_name)
    EditText etBuerName;
    @BindView(R.id.et_buer_email)
    EditText etBuerEmail;
    @BindView(R.id.et_buyer_cellphone)
    TextView etBuyerCellphone;

    private int eventId = -1;
    private int orderId = -1;
    private String orderNumber;
    private String name;
    private String cellphone;
    private String email;
    private String areaCode;
    private int position;
    private Order order;
    private ModifyOrderPresenter modifyOrderPresenter;
    private InputMethodManager inputMethodManager;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_modify_order);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        eventId = intent.getIntExtra("eventId", -1);
        orderId = intent.getIntExtra("orderId", -1);
        order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(orderId)).querySingle();
        if (order != null) {
            etBuerName.setText(order.buyerName);
            etBuerEmail.setText(order.buyerEmail);
            etBuyerCellphone.setText(order.buyerCellphone);
        }
    }

    private void initView() {
        tvTitle.setText(R.string.change_order_info);
        ivRight2.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        Glide.with(this).load(R.mipmap.confirm).into(ivRight);
    }

    @OnClick({R.id.ll_title_back, R.id.ll_right_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_right_click:

             //   Log.e("name","name===="+etBuerName.getText().toString());
             //   Log.e("email","email====" + etBuerEmail.getText().toString());
             //   Log.e("cellphone","cellphone" + etBuyerCellphone.getText().toString());
                if(!TextUtils.isEmpty(etBuerName.getText().toString())) {
                    if(!CompareRex.isContainsNum(etBuerName.getText().toString())) {
                        name = etBuerName.getText().toString();
                        if(!TextUtils.isEmpty(etBuerEmail.getText().toString())) {
                            if(CompareRex.checkEmail(etBuerEmail.getText().toString())) {
                                email = etBuerEmail.getText().toString();
                                if(!TextUtils.isEmpty(etBuyerCellphone.getText().toString())) {
                                        if(CompareRex.isCellPhone(etBuyerCellphone.getText().toString())) {
                                            cellphone = etBuyerCellphone.getText().toString();
                                            areaCode = order.areaCode;
                                            orderNumber = order.orderNumber;
                                            if(NetUtil.isConnected(this)) {
                                                modifyOrderPresenter = new ModifyOrderPresenter(this);
                                                modifyOrderPresenter.modifyOrder();
                                            }else {
                                                Toast.makeText(this,R.string.check_network,Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(this,R.string.phone_format_error,Toast.LENGTH_SHORT).show();
                                        }
                                }else {
                                    Toast.makeText(this,R.string.input_buyer_phone,Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(this,R.string.email_format_error,Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(this,R.string.input_buyer_email,Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this,R.string.input_name,Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,R.string.input_name_buyer,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public String orderNumber() {
        return orderNumber;
    }

    @Override
    public String buyerName() {
        return name;
    }

    @Override
    public String buyerEmail() {
        return email;
    }

    @Override
    public String buyerCellphone() {
        return cellphone;
    }

    @Override
    public String areaCode() {
        return areaCode;
    }

    @Override
    public void showModifySuccess(String response) {
        Toast.makeText(this,R.string.change_success,Toast.LENGTH_SHORT).show();
        SQLite.update(Order.class).set(Order_Table.buyerName.is(name),Order_Table.buyerEmail.is(email),Order_Table.buyerCellphone.is(cellphone))
                .where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(orderId)).execute();
        EventBus.getDefault().postSticky(new MsgEvent(position,"change position",Common.MODIFY_ORDER));
        AppManager.getAppManager().finishActivity();
}

    @Override
    public void showModifyFailed(String errInfo) {
        Toast.makeText(this,errInfo,Toast.LENGTH_SHORT).show();
    }
}
