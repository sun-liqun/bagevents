package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.detail.ActivityOrderDetail;
import com.bagevent.activity_manager.detail.ModifyOrder;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.UpdateNotesPresenter;
import com.bagevent.common.Common;
import com.bagevent.login.LoginActivity;
import com.bagevent.new_home.adapter.WithDrawAccountAdapter;
import com.bagevent.new_home.data.WithDrawAccountData;
import com.bagevent.new_home.new_interface.new_view.WithDrawAccountView;
import com.bagevent.new_home.new_interface.presenter.WithDrawAccountPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.view.listener.ConfirmListener;
import com.bagevent.view.mydialog.CommenDialog;
import com.bagevent.view.mydialog.EditDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WenJie on 2017/11/16.
 */

public class WithDrawAccount extends BaseActivity implements WithDrawAccountView, BaseQuickAdapter.OnItemClickListener, PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.rv_withdraw_account)
    RecyclerView rvWithdrawAccount;
    private PopupMenu menu;
    private String userId;
    private WithDrawAccountAdapter accountAdapter;
    private WithDrawAccountPresenter withDrawAccountPresenter;
    private int defaultPosition = 0;
    private int outcomeAccountId;
    private NormalAlertDialog dialog;

    private List<WithDrawAccountData.RespObjectBean.AccountBean> mWithdrawAccount = new ArrayList<WithDrawAccountData.RespObjectBean.AccountBean>();

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_withdraw_account);
        ButterKnife.bind(this);
        initView();
        initData();
//        showPopMenu(llRightClick);
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
        EventBus.getDefault().postSticky(new MsgEvent(outcomeAccountId, Common.SELECT_ACCOUNT, Common.SELECT_ACCOUNT));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals(Common.ADD_ACCOUNT_SUCCESS)) {
            if (NetUtil.isConnected(this)) {
                //    Log.e("fdsf","fds");
                withDrawAccountPresenter = new WithDrawAccountPresenter(this);
                withDrawAccountPresenter.withdrawAccount();
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //  defaultPosition = position;
        if (mWithdrawAccount.get(position).getHasSubmittedValidationInfo() == 1) {
            outcomeAccountId = mWithdrawAccount.get(position).getOutcomeAccountId();
            accountAdapter.setSelectPosition(position);
            accountAdapter.notifyDataSetChanged();
            AppManager.getAppManager().finishActivity();
        } else {
            final CommenDialog dialog = new CommenDialog(WithDrawAccount.this);
            dialog.setText(getString(R.string.go_name));
            dialog.setConfirmListener(new ConfirmListener() {
                @Override
                public void confirm(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    @OnClick({R.id.ll_title_back, R.id.ll_right_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_right_click:
//                menu.show();
                showRemindDialog();
                break;
        }
    }

    private void showRemindDialog() {
        final CommenDialog dialog = new CommenDialog(WithDrawAccount.this);
        dialog.setConfirmListener(new ConfirmListener() {
            @Override
            public void confirm(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_alipay:
                Intent intent = new Intent(this, AddWithDrawAccount.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.menu_add_bankcard:
                Intent bankIntent = new Intent(this, AddWithDrawAccount.class);
                bankIntent.putExtra("type", 2);
                startActivity(bankIntent);
                break;
        }
        return false;
    }

    private void showPopMenu(View v) {
        menu = new PopupMenu(this, v);
        menu.getMenuInflater().inflate(R.menu.add_account, menu.getMenu());
        menu.setOnMenuItemClickListener(this);
        setForceShowIcon(menu);
    }

    public void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        accountAdapter = new WithDrawAccountAdapter(mWithdrawAccount);
        accountAdapter.openLoadAnimation();
        accountAdapter.setOnItemClickListener(this);
        accountAdapter.setSelectPosition(defaultPosition);
        rvWithdrawAccount.setAdapter(accountAdapter);
    }

    private void initData() {
        userId = SharedPreferencesUtil.get(this, "userId", "");
        Intent intent = getIntent();
        outcomeAccountId = intent.getIntExtra("outcomeAccountId", 0);
        if (NetUtil.isConnected(this)) {
            withDrawAccountPresenter = new WithDrawAccountPresenter(this);
            withDrawAccountPresenter.withdrawAccount();
        }
    }

    private void initView() {
        tvTitle.setText(R.string.select_withdrawal_account);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        ivRight2.setVisibility(View.GONE);
        ivRight.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.add_img).into(ivRight);
        rvWithdrawAccount.setLayoutManager(new LinearLayoutManager(this));
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
    public void withDrawAccountSuccess(WithDrawAccountData response) {
        mWithdrawAccount.clear();
        if (response.getRespObject().getAccount().size() > 0) {
            for (int i = 0; i < response.getRespObject().getAccount().size(); i++) {
                //       Log.e("size",i+"");
                WithDrawAccountData.RespObjectBean.AccountBean bean = new WithDrawAccountData.RespObjectBean.AccountBean();
                WithDrawAccountData.RespObjectBean.AccountBean temp = response.getRespObject().getAccount().get(i);
                if (temp.getOutcomeAccountId() == outcomeAccountId) {
                    defaultPosition = i;
                }
                bean.setType(temp.getType());
                bean.setOutcomeAccountId(temp.getOutcomeAccountId());
                bean.setBankName(temp.getBankName());
                bean.setAccountName(temp.getAccountName());
                bean.setBankIcon(temp.getBankIcon());
                bean.setAccount(temp.getAccount());
                bean.setHasSubmittedValidationInfo(temp.getHasSubmittedValidationInfo());
                mWithdrawAccount.add(bean);
            }
//            mWithdrawAccount.addAll(response.getRespObject().getAccount());
            if (mWithdrawAccount.size() > 0) {
                if (accountAdapter == null) {
                    initAdapter();
                } else {
                    //    Log.e("account","fdsf");
                    for (int i = 0; i < mWithdrawAccount.size(); i++) {
                        if (outcomeAccountId == mWithdrawAccount.get(i).getOutcomeAccountId()) {
                            accountAdapter.setSelectPosition(i);
                        }
                    }
                    accountAdapter.replaceData(mWithdrawAccount);
                }
            }
        }

    }

    @Override
    public void withDrawAccoountFailed(String errInfo) {

    }


}
