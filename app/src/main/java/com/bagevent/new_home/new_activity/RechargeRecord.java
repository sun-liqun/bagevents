package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.new_home.adapter.MsgRecordAdapter;
import com.bagevent.new_home.data.MsgRecordData;
import com.bagevent.new_home.new_interface.new_view.RechargeListView;
import com.bagevent.new_home.new_interface.presenter.RechargeListPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.gudong.loading.LoadingView;

/**
 * Created by zwj on 2017/10/16.
 */

public class RechargeRecord extends BaseActivity implements RechargeListView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_msg_record)
    RecyclerView rvMsgRecord;
    @BindView(R.id.sfl_record)
    SwipeRefreshLayout sflRecord;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;
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
    private int pageNum;
    private int pageSize = 50;//每页请求数目
    private int pageCount;//总页数
    private MsgRecordAdapter msgRecordAdapter;
    private List<MsgRecordData.RespObjectBean.ObjectsBean> data = new ArrayList<MsgRecordData.RespObjectBean.ObjectsBean>();

    private boolean isRefresh = false;//是否为下拉刷新


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.recharge_record);
        ButterKnife.bind(this);
        initView();
        isLoading();
        initData();
        initAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        return pageNum;
    }

    @Override
    public int pageSize() {
        return pageSize;
    }

    @OnClick({R.id.ll_title_back,R.id.ll_page_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_page_status:
                isLoading();
                initData();
                break;
        }
    }

    @Override
    public void showRechargeList(MsgRecordData response) {
      //  Log.e("fds",isRefresh +"");
        if (isRefresh) {
            data.clear();
            sflRecord.setRefreshing(false);
            msgRecordAdapter.setEnableLoadMore(true);
        } else {
            msgRecordAdapter.loadMoreComplete();
        }
        if (response.getRespObject().getObjects().size() > 0) {
            for (int i = 0; i < response.getRespObject().getObjects().size(); i++) {
                MsgRecordData.RespObjectBean.ObjectsBean object = response.getRespObject().getObjects().get(i);
                MsgRecordData.RespObjectBean.ObjectsBean bean = new MsgRecordData.RespObjectBean.ObjectsBean();
                bean.setOrder_number(object.getOrder_number());
                bean.setCreate_time(object.getCreate_time());
                bean.setFee(object.getFee());
                bean.setPay_status(object.getPay_status());
                bean.setPay_way(object.getPay_way());
                data.add(bean);
            }
            if (msgRecordAdapter != null) {
                loadingFinished();
                msgRecordAdapter.setNewData(data);
            }
            pageCount = response.getRespObject().getPagination().getPageCount();
            pageNum = response.getRespObject().getPagination().getPageNumber();
            pageSize = response.getRespObject().getPagination().getPageSize();
        }
    }

    @Override
    public void showRechargeListErrInfo(String errInfo) {
        if (isRefresh) {
            sflRecord.setRefreshing(false);
        }
        if (msgRecordAdapter != null && !isRefresh) {
            msgRecordAdapter.loadMoreFail();
        }
        loadingFinished();
        llPageStatus.setVisibility(View.VISIBLE);
        sflRecord.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
        tvPageStatus.setText(R.string.error_server);
        //Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    private void initAdapter() {
        loadingFinished();
        msgRecordAdapter = new MsgRecordAdapter(R.layout.recharge_item, data);
        msgRecordAdapter.openLoadAnimation();
        msgRecordAdapter.setOnLoadMoreListener(this, rvMsgRecord);
        rvMsgRecord.setAdapter(msgRecordAdapter);
    }

    private void initData() {
        userId = SharedPreferencesUtil.get(this, "userId", "");
        pageNum = 1;
        if (NetUtil.isConnected(this)) {
            llPageStatus.setVisibility(View.GONE);
            sflRecord.setVisibility(View.VISIBLE);
            RechargeListPresenter presenter = new RechargeListPresenter(this);
            presenter.getRechargeList();
        } else {
            loadingFinished();
            llPageStatus.setVisibility(View.VISIBLE);
            sflRecord.setVisibility(View.GONE);
        }

    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        sflRecord.setVisibility(View.GONE);
    }

    private void loadingFinished() {
        llLoading.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        sflRecord.setVisibility(View.VISIBLE);
    }

    private void initView() {
        tvTitle.setText(R.string.recharge_record);
        ivRight.setVisibility(View.GONE);
        ivRight2.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        rvMsgRecord.setLayoutManager(new LinearLayoutManager(this));
        sflRecord.setOnRefreshListener(this);
    }

    @Override
    public void onLoadMoreRequested() {
        //Log.e("fds","fdsf");
        if (NetUtil.isConnected(this)) {
            sflRecord.setEnabled(false);
            isRefresh = false;
            if (pageNum < pageCount) {
                pageNum = pageNum + 1;
                RechargeListPresenter presenter = new RechargeListPresenter(this);
                presenter.getRechargeList();
            } else {
                msgRecordAdapter.loadMoreEnd();
            }
            sflRecord.setEnabled(true);
        } else {
            Toast.makeText(this, R.string.check_your_net, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRefresh() {
        if (NetUtil.isConnected(this)) {
            msgRecordAdapter.setEnableLoadMore(false);
            isRefresh = true;
            pageNum = 1;
            RechargeListPresenter presenter = new RechargeListPresenter(this);
            presenter.getRechargeList();
        } else {
            Toast.makeText(this, R.string.check_your_net, Toast.LENGTH_SHORT).show();
        }
    }

}
