package com.bagevent.new_home.new_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.RechargeMsgData;
import com.bagevent.new_home.data.WXPayData;
import com.bagevent.new_home.new_interface.new_view.RechargeMessageView;
import com.bagevent.new_home.new_interface.new_view.ThirdPayView;
import com.bagevent.new_home.new_interface.presenter.RechargeMessagePresenter;
import com.bagevent.new_home.new_interface.presenter.ThirdPayPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.PayResult;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.view.MyRadioGroup;
import com.bagevent.view.listener.ConfirmListener;
import com.bagevent.view.mydialog.MyPayDialog;
import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.autolayout.AutoLinearLayout;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zwj on 2017/10/9.
 */

public class RechargeMessage extends BaseActivity implements MyRadioGroup.OnCheckedChangeListener, TextWatcher, RechargeMessageView, ThirdPayView {


    @BindView(R.id.tv_account_banlance)
    TextView tvAccountBanlance;
    private String TAG = "RechargeMessage";
    private MyPayDialog payDialog;
    private InputMethodManager inputMethodManager;
    private IWXAPI iwxapi;
    private double price = 10.00;
    private String payTime = "";
    private String nonceStr = "";
    private String prepayId = "";
    private RechargeMessagePresenter rechargePresenter;
    private String userId = "";
    private String count = "";
    private int payway = 0;
    private String account_type = "";
    private String drawPassword = "";

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.rg_message_num)
    MyRadioGroup rgMessageNum;
    @BindView(R.id.et_recharge_num)
    EditText etRechargeNum;
    @BindView(R.id.iv_alipay)
    ImageView ivAlipay;
    @BindView(R.id.ll_alipay)
    AutoLinearLayout llAlipay;
    @BindView(R.id.iv_wxpay)
    ImageView ivWxpay;
    @BindView(R.id.ll_wxpay)
    AutoLinearLayout llWxpay;
    @BindView(R.id.iv_accountpay)
    ImageView ivAccountpay;
    @BindView(R.id.ll_accountpay)
    AutoLinearLayout llAccountpay;
    @BindView(R.id.tv_pay_num)
    TextView tvPayNum;
    @BindView(R.id.ll_pay)
    AutoLinearLayout llPay;

    private String orderNum;
    private ThirdPayPresenter thirdPayPresenter;
    private String banlance;
    private String aliPayOrderInfo;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 2: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Intent intent = new Intent(RechargeMessage.this, RechargeResult.class);
                        intent.putExtra(RechargeResult.RECHARGE, RechargeResult.RECHARGE_SUCCESS);
//                        intent.putExtra(RechargeResult.ORDERNUM, orderNum);
//                        intent.putExtra(RechargeResult.PRODUCT, "短信充值");
//                        intent.putExtra(RechargeResult.FEE, "10");
                        startActivity(intent);
                    //    Toast.makeText(RechargeMessage.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeMessage.this, R.string.error_pay, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.recharge_message);
        ButterKnife.bind(this);
        init();
        payDialog();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (payDialog.isShowing()) {
            payDialog.dismiss();
        }
    }

    @OnClick({R.id.ll_alipay, R.id.ll_wxpay, R.id.ll_accountpay, R.id.ll_pay, R.id.iv_right, R.id.ll_title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_alipay:
                setIvPaySelect(ivAlipay, ivWxpay, ivAccountpay, 0, 0);
                break;
            case R.id.ll_wxpay:
                setIvPaySelect(ivWxpay, ivAlipay, ivAccountpay, 0, 1);
                break;
            case R.id.ll_accountpay:
                setIvPaySelect(ivAccountpay, ivAlipay, ivWxpay, 25, -1);//账户余额支付不需要type
                break;
            case R.id.iv_right:
                Intent intent = new Intent(this, RechargeRecord.class);
                startActivity(intent);
                break;
            case R.id.ll_pay:
                if (Integer.parseInt(count) >= 100) {
                    if (Integer.parseInt(account_type) == 0) {
                        aliPay();
                    } else if (Integer.parseInt(account_type) == 1) {
                        wxPay();
                    } else if (payway == 25) {
                        payDialog.show();
                    }
                } else {
                    Toast.makeText(this, R.string.recharge_num, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    @Override
    public void onCheckedChanged(MyRadioGroup group, int checkedId) {
        setEditTextEmpty();
        switch (checkedId) {
            case R.id.rb_message1:
                etRechargeNum.setHint(getString(R.string.other));
                etRechargeNum.setHintTextColor(ContextCompat.getColor(this,R.color.black));
                etRechargeNum.setBackgroundColor(ContextCompat.getColor(this,R.color.f3f3f3));
                setTvPayNumText(100, 10.00);
                break;
            case R.id.rb_message2:
                etRechargeNum.setHint(getString(R.string.other));
                etRechargeNum.setHintTextColor(ContextCompat.getColor(this,R.color.black));
                etRechargeNum.setBackgroundColor(ContextCompat.getColor(this,R.color.f3f3f3));
                setTvPayNumText(200, 20.00);
                break;
            case R.id.rb_message3:
                etRechargeNum.setHint(getString(R.string.other));
                etRechargeNum.setHintTextColor(ContextCompat.getColor(this,R.color.black));
                etRechargeNum.setBackgroundColor(ContextCompat.getColor(this,R.color.f3f3f3));
                setTvPayNumText(500, 50.00);
                break;
            case R.id.rb_message4:
                etRechargeNum.setHint(getString(R.string.other));
                etRechargeNum.setHintTextColor(ContextCompat.getColor(this,R.color.black));
                etRechargeNum.setBackgroundColor(ContextCompat.getColor(this,R.color.f3f3f3));
                setTvPayNumText(1000, 100.00);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s.toString())) {
            price = 0.00;
            DecimalFormat format = new DecimalFormat( "0.00 ");
            BigDecimal b1 = new BigDecimal(Double.valueOf(s.toString()));
            BigDecimal b2 = new BigDecimal(10.00);
            price = b1.divide(b2).doubleValue();
            tvPayNum.setText(format.format(price));
            count = s.toString();
        }
    }

    /**
     * 支付宝
     */

    private void aliPay() {
        rechargePresenter = new RechargeMessagePresenter(RechargeMessage.this);
        rechargePresenter.thirdRecharge();
    }

    /**
     * 微信支付
     */
    private void wxPay() {
        rechargePresenter = new RechargeMessagePresenter(RechargeMessage.this);
        rechargePresenter.thirdRecharge();
    }

    private void init() {
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        DecimalFormat format = new DecimalFormat( "0.00 ");
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        ivRight2.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.jilu).into(ivRight);
        tvTitle.setText(R.string.recharge);
        userId = SharedPreferencesUtil.get(this, "userId", "");
        banlance = getIntent().getStringExtra("banlance");
        rgMessageNum.check(R.id.rb_message1);
        payway = 0;
        account_type = Integer.toString(0);
        tvPayNum.setText(format.format(price));
        tvAccountBanlance.setText("(￥ " + banlance + ")");
        count = "100";
        iwxapi = WXAPIFactory.createWXAPI(this, null);
        iwxapi.registerApp(Constants.WX_APPID);
    }

    private void initListener() {
        rgMessageNum.setOnCheckedChangeListener(this);
        etRechargeNum.addTextChangedListener(this);
        etRechargeNum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.et_recharge_num) {
                    rgMessageNum.clearCheck();
                    etRechargeNum.requestFocus();
                    etRechargeNum.setHint("");
                    //etRechargeNum.setHintTextColor(ContextCompat.getColor(RechargeMessage.this,R.color.white));
                    etRechargeNum.setBackgroundResource(R.drawable.text_border_orange);
                    inputMethodManager.showSoftInputFromInputMethod(etRechargeNum.getWindowToken(), 0);
                    setTvPayNumText(0,0.00);
                }
                return false;
            }
        });
    }

    private void payDialog() {
        payDialog = new MyPayDialog(this);
        payDialog.setCancelable(false);
        payDialog.setConfirmListener(new ConfirmListener() {
            @Override
            public void confirm(View view) {
                drawPassword = payDialog.getPassword();
                if (!TextUtils.isEmpty(payDialog.getPassword())) {
                    if (NetUtil.isConnected(RechargeMessage.this)) {
                        rechargePresenter = new RechargeMessagePresenter(RechargeMessage.this);
                        rechargePresenter.accountRecharge();
                    }
                }
            }
        });
    }

    private void setIvPaySelect(ImageView select, ImageView unselect1, ImageView unselect2, int payways, int type) {
        Glide.with(this).load(R.drawable.xuanzhong).into(select);
        Glide.with(this).load(R.drawable.ic_weixuanzhong).into(unselect1);
        Glide.with(this).load(R.drawable.ic_weixuanzhong).into(unselect2);
        etRechargeNum.setHint(getString(R.string.other));
        payway = payways;
        account_type = Integer.toString(type);
    }


    private void setEditTextEmpty() {
        price = 0.00;
        etRechargeNum.setText("");
    }

    private void setTvPayNumText(int num, double p) {
        count = num +"";
        price = p;
        DecimalFormat format = new DecimalFormat( "0.00 ");
        inputMethodManager.hideSoftInputFromWindow(etRechargeNum.getWindowToken(), 0);
        tvPayNum.setText(format.format(p));
    }

    private void setOrderToShare() {
        DecimalFormat format = new DecimalFormat( "0.00 ");
        SharedPreferencesUtil.put(this,"rechargeOrderNum",orderNum);
        SharedPreferencesUtil.put(this,"rechargeFee",format.format(price));
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String orderNum() {
        return orderNum;
    }

    @Override
    public String userId() {
     //   Log.e(TAG, "userId====" + userId);
        return userId;
    }

    @Override
    public String count() {
      //  Log.e(TAG, "count====" + count);
        return count;
    }

    @Override
    public String payway() {
     //   Log.e(TAG, "payway====" + payway);
        return Integer.toString(payway);
    }

    @Override
    public void showAliPaySuccess(final String orderStr) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask task = new PayTask(RechargeMessage.this);
                Map<String, String> result = task.payV2(orderStr,true);
                Message msg = new Message();
                msg.what = 2;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(runnable);
        payThread.start();
        setOrderToShare();
    }

    @Override
    public void showAliPayFailed(String errInfo) {

    }

    @Override
    public void showThirdPaySuccess(WXPayData response) {
        setOrderToShare();
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this,Constants.WX_APPID);
        iwxapi.registerApp(Constants.WX_APPID);
        PayReq request = new PayReq();
        request.appId = Constants.WX_APPID;
        request.partnerId = response.getRespObject().getPartnerid();
        request.prepayId = response.getRespObject().getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = response.getRespObject().getNoncestr();
        request.timeStamp = response.getRespObject().getTimestamp();
        request.sign = response.getRespObject().getPaySign();
       // Log.e("request",request.timeStamp + " ");
        boolean is = iwxapi.sendReq(request);
        //    Toast.makeText(this," " + is,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showThirdPayFailed(String errInfo) {

    }

    @Override
    public String accountType() {
        return account_type;
    }

    @Override
    public String drawPassword() {
  //      Log.e(TAG, "drawPassword====" + drawPassword);
        return drawPassword;
    }

    @Override
    public void thirdRechargeSuccess(RechargeMsgData response) {
        if(account_type.equals("0")) {
            payway = 0;
        }else {
            payway = 1;
        }
        orderNum = response.getRespObject().getOrderNumber();
        if (NetUtil.isConnected(this)) {
            thirdPayPresenter = new ThirdPayPresenter(this);
            thirdPayPresenter.getThirdPay();
        } else {
            Toast.makeText(this, R.string.error_net, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void thirdRechargeFailed(String errInfo) {

    }

    @Override
    public void rechargeSuccess(RechargeMsgData response) {
        if (payDialog.isShowing()) {
            payDialog.setPassword();
            payDialog.dismiss();
        }
        setOrderToShare();
        Intent intent = new Intent(this, RechargeResult.class);
        intent.putExtra(RechargeResult.RECHARGE, RechargeResult.RECHARGE_SUCCESS);
        startActivity(intent);
    }

    @Override
    public void rechargeFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

}
