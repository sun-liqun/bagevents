package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.adapter.AllInvoiceAdapter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetInvoicePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetInvoiceListView;
import com.bagevent.common.Common;
import com.bagevent.db.Invoice;
//import com.bagevent.db.Invoice_Table;
import com.bagevent.db.Invoice_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.dbutil.SyncInvoiceUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZWJ on 2017/12/27 0027.
 */

public class AllInvoiceActivity extends BaseActivity implements GetInvoiceListView,BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_invoice)
    RecyclerView rvInvoice;

    private String userId;
    private int eventId;
    private int pageNum = 1;
    private String fromTime;
    private AllInvoiceAdapter allInvoiceAdapter;
    private List<Invoice> invoiceList = new ArrayList<Invoice>();
    private GetInvoicePresenter getInvoicePresenter;

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
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_all_invoice);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if(event.mMsg.equals(Common.SYNC_INFO_SUCCESS)) {
            invoiceList.clear();
            getInvoice();
        }else if(event.mMsg.equals(Common.ORDER_PAGE)){
            pageNum = event.pageNumber;
            getInvoiceList();
        }
    }


    @OnClick({R.id.ll_title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    private void getInvoice() {
        List<Invoice> invoices = new Select().from(Invoice.class).where(Invoice_Table.userId.is(Integer.parseInt(userId)))
                .and(Invoice_Table.eventId.is(eventId +""))
                .queryList();
        invoiceList.addAll(invoices);
        if(allInvoiceAdapter == null) {
            initAdapter();
        }else {
            allInvoiceAdapter.addData(invoiceList);
        }
    }

    private void initAdapter() {
        allInvoiceAdapter = new AllInvoiceAdapter(invoiceList);
        allInvoiceAdapter.setOnItemClickListener(this);
        allInvoiceAdapter.setEnableLoadMore(true);
        rvInvoice.setAdapter(allInvoiceAdapter);
    }

    private String currentTime() {
        fromTime = SharedPreferencesUtil.get(this, "invoiceTime" + eventId, "");
        return fromTime;
    }

    private void getInvoiceList() {
        if (NetUtil.isConnected(this)) {
            if (TextUtils.isEmpty(currentTime()) || pageNum > 1) {
                getInvoicePresenter = new GetInvoicePresenter(this);
                getInvoicePresenter.getInvoiceList();
            } else {
                getInvoicePresenter = new GetInvoicePresenter(this);
                getInvoicePresenter.getInvoiceListFromTime();
            }
        }
    }

    private void initData() {
        userId = SharedPreferencesUtil.get(this,"userId","");
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId",-1);
        if(NetUtil.isConnected(this)) {
            getInvoiceList();
        }
    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.invoice_list);
        rvInvoice.setLayoutManager(new LinearLayoutManager(this));
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
    public int eventId() {
        return eventId;
    }

    @Override
    public int page() {
        return pageNum;
    }

    @Override
    public String fromTiem() {
        return fromTime;
    }

    @Override
    public void showInvoiceListSuccess(String response) {
        SyncInvoiceUtil util = new SyncInvoiceUtil(this,userId, eventId +"",response);
        util.syncInvoice();
    }

    @Override
    public void showInvoiceListFailed(String errInfo) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this,InvoiceDetailActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("eventId",eventId);
        intent.putExtra("invoiceId",invoiceList.get(position).orderInvoiceId);
        startActivity(intent);
    }
}
