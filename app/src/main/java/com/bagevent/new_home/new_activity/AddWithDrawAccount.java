package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.new_home.new_interface.new_view.AddWithDrawAccountView;
import com.bagevent.new_home.new_interface.presenter.AddWithDrawAccountPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.xw.repo.XEditText;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public class AddWithDrawAccount extends BaseActivity implements AddWithDrawAccountView {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_account_name)
    XEditText etAccountName;
    @BindView(R.id.et_account_bank_name)
    XEditText etAccountBankName;
    @BindView(R.id.et_account_bank_card)
    XEditText etAccountBankCard;
    @BindView(R.id.ll_bank)
    AutoLinearLayout llBank;
    @BindView(R.id.et_account_number)
    XEditText etAccountNumber;
    @BindView(R.id.ll_account)
    AutoLinearLayout llAccount;
    @BindView(R.id.tv_account_type)
    TextView tvAccountType;
    @BindView(R.id.tv_account_type2)
    TextView tvAccountType2;
    @BindView(R.id.tv_account_type3)
    TextView tvAccountType3;
    @BindView(R.id.tv_account_type4)
    TextView tvAccountType4;
    private int type = 1;
    private String userId;
    private AddWithDrawAccountPresenter addWithDrawAccountPresenter;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_add_withdraw);
        ButterKnife.bind(this);
        getIntentValue();
        initView();
    }

    @OnClick({R.id.ll_title_back, R.id.tv_confirm_add_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_confirm_add_account:
                    if (type == 2) {
                        if (!TextUtils.isEmpty(etAccountName.getText().toString())) {
                            if (!TextUtils.isEmpty(etAccountBankName.getText().toString())) {
                                if (!TextUtils.isEmpty(etAccountBankCard.getText().toString())) {
                                    if (NetUtil.isConnected(this)) {
                                        type = 2;
                                        addWithDrawAccountPresenter = new AddWithDrawAccountPresenter(this);
                                        addWithDrawAccountPresenter.addWithdraw();
                                    } else {
                                        Toast.makeText(this, R.string.check_network1, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, R.string.bank_card_hint, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, R.string.opening_bank, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(this, R.string.card_holding, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!TextUtils.isEmpty(etAccountName.getText().toString())) {
                            if (!TextUtils.isEmpty(etAccountNumber.getText().toString())) {
                                if (NetUtil.isConnected(this)) {
                                    type = 1;
                                    addWithDrawAccountPresenter = new AddWithDrawAccountPresenter(this);
                                    addWithDrawAccountPresenter.addWithdraw();
                                } else {
                                    Toast.makeText(this, R.string.check_network1, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, R.string.please_et_account, Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(this, R.string.gain_money_hint, Toast.LENGTH_SHORT).show();
                        }

                }
                break;
        }
    }


    private void getIntentValue() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 1);
        userId = SharedPreferencesUtil.get(this, "userId", "");
    }

    private void initView() {
        if (type == 1) {
            tvTitle.setText(R.string.add_alipay);
            tvAccountType.setText(R.string.gain_money);
            tvAccountType4.setText(R.string.accounts1);
            llAccount.setVisibility(View.VISIBLE);
            llBank.setVisibility(View.GONE);
        } else {
            tvTitle.setText(R.string.add_card);
            tvAccountType.setText(R.string.card_holder);
            etAccountName.setHint(getString(R.string.card_holding));
            tvAccountType2.setText(R.string.open_bank);
            etAccountBankName.setHint(getString(R.string.opening_bank));
            tvAccountType3.setText(R.string.card);
            etAccountBankCard.setHint(getString(R.string.et_card));
            llAccount.setVisibility(View.GONE);
            llBank.setVisibility(View.VISIBLE);
        }
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
    public int type() {
        return type;
    }

    @Override
    public String accountName() {
        return etAccountName.getText().toString();
    }

    @Override
    public String account() {
        if (type == 1) {
            return etAccountNumber.getText().toString();
        } else {
            return etAccountBankCard.getText().toString();
        }
    }

    @Override
    public String bankName() {
        return etAccountBankName.getText().toString();
    }

    @Override
    public void showAddAccountSucess(String response) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().postSticky(new MsgEvent(Common.ADD_ACCOUNT_SUCCESS));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void showAddAccountFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }
}
