package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.new_home.data.WithDrawAccountData;
import com.bagevent.new_home.new_interface.new_view.WithDrawAccountView;
import com.bagevent.new_home.new_interface.new_view.WithdrawApplyView;
import com.bagevent.new_home.new_interface.presenter.WithDrawAccountPresenter;
import com.bagevent.new_home.new_interface.presenter.WithdrawApplyPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.view.listener.ConfirmListener;
import com.bagevent.view.mydialog.MyPayDialog;
import com.bumptech.glide.Glide;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WenJie on 2017/11/16.
 */

public class WithDrawActivity extends BaseActivity implements TextWatcher, WithDrawAccountView, WithdrawApplyView {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.iv_bank_icon)
    ImageView ivBankIcon;
    @BindView(R.id.et_account_banlance)
    EditText etAccountBanlance;
    @BindView(R.id.tv_all_money)
    TextView tvAllMoney;
    @BindView(R.id.tv_actual_arrival)
    TextView tvActualArrival;
    @BindView(R.id.tv_all_banlance)
    TextView tvAllBanlance;
    @BindView(R.id.tv_apply_withdraw)
    TextView tvApplyWithdraw;

    private String accountBanlance;
    private int outcomeAccountId;
    private WithDrawAccountData drawAccountData;
    private String userId="";
    private WithDrawAccountPresenter withDrawAccountPresenter;
    private String applyAmount="";
    private String account="";
    private String accountName="";
    private String password="";
    private String bankName="";
    private String withDrawAmount;
    private int type;
    private WithdrawApplyPresenter withdrawApplyPresenter;
    private MyPayDialog payDialog;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_withdraw);
        ButterKnife.bind(this);
        initView();
        initData();
        payDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().postSticky(new MsgEvent(Common.WITHDRAW_APPLY));
        if ((payDialog != null)) {
            if (payDialog.isShowing()) {
                payDialog.dismiss();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals(Common.SELECT_ACCOUNT)) {
            if (NetUtil.isConnected(this)) {
                outcomeAccountId = event.pos;
                withDrawAccountPresenter = new WithDrawAccountPresenter(this);
                withDrawAccountPresenter.withdrawAccount();
                EventBus.getDefault().removeStickyEvent(event);
            } else {
                if (drawAccountData != null) {
                    for (int i = 0; i < drawAccountData.getRespObject().getAccount().size(); i++) {
                        if (drawAccountData.getRespObject().getAccount().get(i).getOutcomeAccountId() == event.pos) {
                            getAccountInfo(drawAccountData.getRespObject().getAccount().get(i));
                            return;
                        }

                    }
                }
            }
        }
    }

    private void getAccountInfo(WithDrawAccountData.RespObjectBean.AccountBean bean) {
        account = bean.getAccount();
        accountName = bean.getAccountName();
        bankName = bean.getBankName();
        type = bean.getType();
        if (bean.getType() == 1) {
            Glide.with(this).load(R.drawable.ic_alipay).into(ivBankIcon);
            tvAccountName.setText(getString(R.string.alipay) + "(" + bean.getAccount() + ")");
        } else {
            Glide.with(this).load(bean.getBankIcon()).into(ivBankIcon);
            tvAccountName.setText(bean.getBankName());
        }
        outcomeAccountId = bean.getOutcomeAccountId();
    }

    private void payDialog() {
        payDialog = new MyPayDialog(this);
        payDialog.setCancelable(false);
        payDialog.setConfirmListener(new ConfirmListener() {
            @Override
            public void confirm(View view) {
                password = payDialog.getPassword();
                withDrawAmount = etAccountBanlance.getText().toString();
                applyAmount = etAccountBanlance.getText().toString();
                if (NetUtil.isConnected(WithDrawActivity.this)) {
                    if (!TextUtils.isEmpty(payDialog.getPassword())) {
                        withdrawApplyPresenter = new WithdrawApplyPresenter(WithDrawActivity.this);
                        withdrawApplyPresenter.applyWithdraw();
                        payDialog.setPassword();
                        payDialog.dismiss();
                    } else {
                        Toast.makeText(WithDrawActivity.this, R.string.et_pay, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WithDrawActivity.this, R.string.check_network1, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = SharedPreferencesUtil.get(this, "userId", "");
        if (bundle != null) {
            accountBanlance = bundle.getString("account", "0").trim();
            etAccountBanlance.setText(accountBanlance);
            tvAllBanlance.setText(accountBanlance);
            drawAccountData = (WithDrawAccountData) bundle.getSerializable("WithDrawAccountData");
            if (drawAccountData != null) {
                int size=drawAccountData.getRespObject().getAccount().size();
                if (drawAccountData.getRespObject().getAccount().size() > 0) {
                    for(int i=0;i<size;i++){
                        if (drawAccountData.getRespObject().getAccount().get(i).getHasSubmittedValidationInfo()==1){
                            getAccountInfo(drawAccountData.getRespObject().getAccount().get(i));
                            return;
                        }
                    }
                }
            } else {
                Log.e("WithdDrawActivity", "withdrawAccount is null");
            }
        }

    }

    private void initView() {
        tvTitle.setText(R.string.cash_withdrawal);
        ivRight2.setVisibility(View.GONE);
        ivRight.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        Glide.with(this).load(R.drawable.jilu).into(ivRight);
        etAccountBanlance.addTextChangedListener(this);
    }

    @OnClick({R.id.ll_title_back, R.id.ll_right_click, R.id.tv_account_name, R.id.tv_all_money, R.id.tv_apply_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_right_click:
                Intent intents = new Intent(this, WithdrawReord.class);
                startActivity(intents);
                break;
            case R.id.tv_account_name:
                Intent intent = new Intent(this, WithDrawAccount.class);
                intent.putExtra("outcomeAccountId", outcomeAccountId);
                startActivity(intent);
                break;
            case R.id.tv_all_money:
                etAccountBanlance.setText(accountBanlance);
                break;
            case R.id.tv_apply_withdraw:
                if (!TextUtils.isEmpty(etAccountBanlance.getText().toString())){
                    if(Double.parseDouble(etAccountBanlance.getText().toString()) >= 100) {
                        payDialog.show();
                    }else {
                        Toast.makeText(this,R.string.cash_withdrawal_money,Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,R.string.withdrawal_money_null,Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public String mulReduce(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return b1.subtract(b2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public String mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        // BigDecimal b3 = new BigDecimal(b1.multiply(b2).setScale(2, BigDecimal.ROUND_DOWN).toString());
        return b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        CrashReport.postCatchedException(new Throwable("提现金额输入异常"+s));
        String key = s.toString();
        if (!TextUtils.isEmpty(key)) {
            if (!key.substring(0, 1).equals(".")) {
                if (Double.parseDouble(key.replaceAll(" ","")) > Double.parseDouble(accountBanlance.replaceAll(" ",""))) {
                    etAccountBanlance.setText(accountBanlance);
                    key = accountBanlance;
                }
                tvActualArrival.setText(mul(key, "0.98"));
            } else {
                tvActualArrival.setText("0");
            }

        } else {
            tvActualArrival.setText("0");
        }
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
    public String applyAmount() {
        return applyAmount;
    }

    @Override
    public String account() {
         return account;
    }

    @Override
    public String accountName() {
       return accountName;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String bankName() {
        return bankName;
    }

    @Override
    public int type() {
        return type;
    }

    @Override
    public void showApplySuccess(String response) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        String banlance = mulReduce(tvAllBanlance.getText().toString(), withDrawAmount);
        String banlanceArrival = mul(banlance, "0.98");
        tvAllBanlance.setText(banlance);
        etAccountBanlance.setText(banlance);
        tvActualArrival.setText(banlanceArrival);
    }

    @Override
    public void showApplyFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void withDrawAccountSuccess(WithDrawAccountData response) {
        for (int i = 0; i < response.getRespObject().getAccount().size(); i++) {
            if (outcomeAccountId == response.getRespObject().getAccount().get(i).getOutcomeAccountId()) {
                if(response.getRespObject().getAccount().get(i).getHasSubmittedValidationInfo()==1){
                    getAccountInfo(response.getRespObject().getAccount().get(i));
                    return;
                }
            }
        }
    }

    @Override
    public void withDrawAccoountFailed(String errInfo) {

    }
}
