package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.new_home.adapter.WithdrawRecordAdapter;
import com.bagevent.new_home.data.WithdrawRecordData;
import com.bagevent.new_home.new_interface.new_view.WithdrawRecordView;
import com.bagevent.new_home.new_interface.presenter.WithdrawRecordPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.gudong.loading.LoadingView;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public class WithdrawReord extends BaseActivity implements WithdrawRecordView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_withdraw_record)
    RecyclerView rvWithdrawRecord;
    @BindView(R.id.refresh_withdraw)
    SwipeRefreshLayout refreshWithdraw;
    @BindView(R.id.loading)
    LoadingView loading;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.iv_page_status)
    ImageView ivPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;
    private String userId;
    private int page = 1;
    private int pageCount;
    private int pageSize = 50;
    private WithdrawRecordPresenter withdrawRecordPresenter;
    private List<WithdrawRecordData.RespObjectBean.ObjectsBean> data = new ArrayList<WithdrawRecordData.RespObjectBean.ObjectsBean>();
    private boolean isRefresh = false;//是否为下拉刷新
    private WithdrawRecordAdapter withdrawRecordAdapter;
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_withdraw_record);
        ButterKnife.bind(this);
        initView();
        isLoading();
        getWithDrawRecord();
    }

    private void getWithDrawRecord() {
        if (NetUtil.isConnected(this)) {
            withdrawRecordPresenter = new WithdrawRecordPresenter(this);
            withdrawRecordPresenter.getWithdrawRecord();
        } else {
            llPageStatus.setVisibility(View.VISIBLE);
            refreshWithdraw.setVisibility(View.GONE);
        }
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        refreshWithdraw.setVisibility(View.GONE);
    }

    private void loadingFinished() {
        llLoading.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        refreshWithdraw.setVisibility(View.VISIBLE);
    }

    private void initAdapter() {
        withdrawRecordAdapter = new WithdrawRecordAdapter(data);
        withdrawRecordAdapter.openLoadAnimation();
        withdrawRecordAdapter.setOnLoadMoreListener(this, rvWithdrawRecord);
        rvWithdrawRecord.setAdapter(withdrawRecordAdapter);
    }

    private void initView() {
        userId = SharedPreferencesUtil.get(this, "userId", "");
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.cash_withdrawal_record);
        refreshWithdraw.setOnRefreshListener(this);
        rvWithdrawRecord.setLayoutManager(new LinearLayoutManager(this));
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
    public int page() {
        return page;
    }

    @Override
    public int pageSize() {
        return pageSize;
    }

    @Override
    public void showWithDrawRecordSuccess(WithdrawRecordData response) {
        loadingFinished();
        if (isRefresh) {
            data.clear();
            refreshWithdraw.setRefreshing(false);
        } else {
            if (withdrawRecordAdapter != null) {
                withdrawRecordAdapter.loadMoreComplete();
            }
        }
        if (response.getRespObject().getObjects().size() > 0) {
            llPageStatus.setVisibility(View.GONE);
            refreshWithdraw.setVisibility(View.VISIBLE);
            for (int i = 0; i < response.getRespObject().getObjects().size(); i++) {
                WithdrawRecordData.RespObjectBean.ObjectsBean object = response.getRespObject().getObjects().get(i);
                WithdrawRecordData.RespObjectBean.ObjectsBean bean = new WithdrawRecordData.RespObjectBean.ObjectsBean();
                bean.setAccount(object.getAccount());
                bean.setOutcome_time(object.getOutcome_time());
                bean.setType(object.getType());
                bean.setTotal_amount(object.getTotal_amount());
                bean.setOutcome_type(object.getOutcome_type());
                bean.setPay_type_desc(object.getPay_type_desc());
                bean.setAmount(object.getAmount());
                bean.setType_desc(object.getType_desc());
                data.add(bean);
            }
            Collections.reverse(data);
            if (withdrawRecordAdapter != null) {
                withdrawRecordAdapter.setNewData(data);
            } else {
                initAdapter();
            }
            pageCount = response.getRespObject().getPagination().getPageCount();
            page = response.getRespObject().getPagination().getPageNumber();
            pageSize = response.getRespObject().getPagination().getPageSize();
        } else {
            llPageStatus.setVisibility(View.VISIBLE);
            refreshWithdraw.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
            tvPageStatus.setText(R.string.no_cash_withdrawal_record);
        }
    }

    @Override
    public void showWithDrawRecordFailed(String errInfo) {
        loadingFinished();
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRefresh() {
        if (NetUtil.isConnected(this)) {
            withdrawRecordAdapter.setEnableLoadMore(false);
            isRefresh = true;
            page = 1;
            WithdrawRecordPresenter presenter = new WithdrawRecordPresenter(this);
            presenter.getWithdrawRecord();
            withdrawRecordAdapter.setEnableLoadMore(true);
        } else {
            Toast.makeText(this, getString(R.string.check_your_net), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (NetUtil.isConnected(this)) {
            refreshWithdraw.setEnabled(false);
//            isRefresh = false;
            if (page < pageCount) {
                //page = page + 1;
                WithdrawRecordPresenter presenter = new WithdrawRecordPresenter(this);
                presenter.getWithdrawRecord();
            } else {
                withdrawRecordAdapter.loadMoreEnd();
            }
            refreshWithdraw.setEnabled(true);
        } else {
            Toast.makeText(this, getString(R.string.check_your_net), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick({R.id.ll_title_back, R.id.ll_page_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_page_status:
                isLoading();
                getWithDrawRecord();
                break;
        }
    }
}
