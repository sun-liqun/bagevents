package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.adapter.AuditOrderAdapter;
import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;
import com.bagevent.common.Common;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.Order;
//import com.bagevent.db.Order_Table;
import com.bagevent.db.Order_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WenJie on 2017/11/3.
 */

public class AuditOrder extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, TextWatcher {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.ll_common_title)
    AutoLinearLayout llCommonTitle;
    @BindView(R.id.ll_search)
    AutoLinearLayout llSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @BindView(R.id.btn_search_cancle)
    Button btnSearchCancle;
    @BindView(R.id.fl_search)
    AutoFrameLayout flSearch;
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.sfl_order)
    SwipeRefreshLayout sflOrder;
    @BindView(R.id.v_transparent)
    View vTransparent;
    @BindView(R.id.fm_order)
    AutoFrameLayout fmOrder;
    //    @BindView(R.id.tv_no_order)
//    TextView tvNoOrder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;
    @BindView(R.id.iv_page_status)
    ImageView ivPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;

    private List<OrderInfo.RespObjectBean.ObjectsBean> auditList = new ArrayList<OrderInfo.RespObjectBean.ObjectsBean>();
    private List<OrderInfo.RespObjectBean.ObjectsBean> auditSearchList = new ArrayList<OrderInfo.RespObjectBean.ObjectsBean>();
    private AuditOrderAdapter auditOrderAdapter;
    private int eventId;
    private int offset = 0;
    private int limit = 50;
    private boolean isSearch = false;
    private String result;
    private InputMethodManager inputMethodManager;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initView();
        initData();
        initList(0);
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

    @OnClick({R.id.ll_search, R.id.iv_search_clear, R.id.btn_search_cancle, R.id.ll_title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_search:
                isSearch = true;
                etSearch.requestFocus();
                etSearch.setFocusable(true);
                inputMethodManager.showSoftInput(etSearch, 0);
                vTransparent.setVisibility(View.VISIBLE);
                llCommonTitle.setVisibility(View.GONE);
                llSearch.setVisibility(View.GONE);
                flSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                auditSearchList.clear();
                vTransparent.setVisibility(View.VISIBLE);
                ivSearchClear.setVisibility(View.GONE);
                auditOrderAdapter.setNewData(auditList);
                break;
            case R.id.btn_search_cancle:
                isSearch = false;
                cancelSearch();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MsgEvent event) {
     //   Log.e("AuditOrder",event.mMsg+"F");
        if(event.mMsg.equals(Common.AUDIT_ORDER)) {
            if(auditSearchList.size() == 0) {
                auditList.remove(event.pos);
                auditOrderAdapter.notifyDataSetChanged();
            }else {
                auditSearchList.remove(event.pos);
                auditOrderAdapter.notifyDataSetChanged();
            }
        }if (event.mMsg.equals(Common.MODIFY_ORDER)) {//修改参会人员成功需更新列表项
            if (auditSearchList.size() == 0) {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(auditList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = auditList.get(event.pos);
                    bean.setBuyerName(order.buyerName);
                    auditOrderAdapter.notifyItemChanged(event.pos);
                }
            } else {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(auditSearchList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = auditSearchList.get(event.pos);
                    bean.setBuyerName(order.buyerName);
                    auditOrderAdapter.notifyItemChanged(event.pos);
                }
            }
        } else if (event.mMsg.equals(Common.CONFIRM_ORDER)) {//确认收款or退票，更新订单列表的状态信息
            if (auditSearchList.size() == 0) {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(auditList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = auditList.get(event.pos);
                    bean.setPayStatus(order.payStatus);
                    auditOrderAdapter.notifyItemChanged(event.pos);
                }
            } else {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(auditSearchList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = auditSearchList.get(event.pos);
                    bean.setPayStatus(order.payStatus);
                    auditOrderAdapter.notifyItemChanged(event.pos);
                }
            }
        } else if (event.mMsg.equals(Common.UPDATE_NOTES)) {
            if (auditSearchList.size() == 0) {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(auditList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = auditList.get(event.pos);
                    bean.setNotes(order.notes);
                    auditOrderAdapter.notifyItemChanged(event.pos);
                }
            } else {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(auditSearchList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = auditSearchList.get(event.pos);
                    bean.setNotes(order.notes);
                    auditOrderAdapter.notifyItemChanged(event.pos);
                }
            }
        }
    }

    private void cancelSearch() {
        etSearch.setText("");
        auditSearchList.clear();
        auditOrderAdapter.setNewData(auditList);
        vTransparent.setVisibility(View.GONE);
        flSearch.setVisibility(View.GONE);
        llCommonTitle.setVisibility(View.VISIBLE);
        llSearch.setVisibility(View.VISIBLE);
        fmOrder.setVisibility(View.VISIBLE);
        llPageStatus.setVisibility(View.GONE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        }
    }


    private void initList(int offset) {
        List<Order> auditOrder = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.payStatus.is(12)).offset(offset).limit(limit).queryList();
        for (int i = 0; i < auditOrder.size(); i++) {
            OrderInfo.RespObjectBean.ObjectsBean info = new OrderInfo.RespObjectBean.ObjectsBean();
            Order data = auditOrder.get(i);
            info.setAccountType(data.accountType);
            info.setOrderNumber(data.orderNumber);
            info.setBuyerName(data.buyerName);
            info.setOrderTime(data.orderTime);
            info.setTicketOrderTotalPrice(data.ticketOrderTotalPrice);
            info.setPayStatus(data.payStatus);
            info.setOrderNumber(data.orderNumber);
            info.setOrderId(data.orderId);
            info.setBuyerEmail(data.buyerEmail);
            info.setBuyerCellphone(data.buyerCellphone);
            info.setPayWay(data.payWay);
            info.setNotes(data.notes);
            info.setAudit(data.audit);
            auditList.add(info);
        }

        if (auditOrderAdapter == null) {
            initAdapter();
        } else {
            if (auditOrder.size() > 0) {
                auditOrderAdapter.setNewData(auditList);
                if (auditOrder.size() < limit) {
                    auditOrderAdapter.loadMoreComplete();
                    auditOrderAdapter.loadMoreEnd();
                } else {
                    auditOrderAdapter.loadMoreComplete();
                }
            } else {
                auditOrderAdapter.loadMoreEnd();
            }
        }
    }

    private void initAdapter() {
        auditOrderAdapter = new AuditOrderAdapter(R.layout.activity_order_item, auditList);
        auditOrderAdapter.openLoadAnimation();
        auditOrderAdapter.setOnLoadMoreListener(this, rvOrder);
        auditOrderAdapter.setOnItemClickListener(this);
        rvOrder.setAdapter(auditOrderAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", -1);
    }

    private void initView() {
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        tvTitle.setText(R.string.audit_wait_order);
        sflOrder.setEnabled(false);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        etSearch.addTextChangedListener(this);
    }


    @Override
    public void onLoadMoreRequested() {
        offset = offset + limit;
        initList(offset);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OrderInfo.RespObjectBean.ObjectsBean bean = null;
        if (isSearch) {
            if (auditSearchList.size() > 0) {
                bean = auditSearchList.get(position);
            }
        } else {
            bean = auditList.get(position);
        }
        if (bean != null) {
            Intent intent = new Intent(this, ActivityOrderDetail.class);
            intent.putExtra("eventId", eventId);
            intent.putExtra("orderId", bean.getOrderId());
            intent.putExtra("position", position);
            intent.putExtra("enterStatus",1);
            startActivity(intent);
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
        auditSearchList.clear();
        result = s.toString();
        if (TextUtils.isEmpty(result)) {
            ivSearchClear.setVisibility(View.GONE);
        } else {
            ivSearchClear.setVisibility(View.VISIBLE);
        }

        List<Attends> attendList = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId))
                .and(OperatorGroup.clause()
                        .and(Attends_Table.gsonUser.like("%" + result + "%"))
                        .or(Attends_Table.pinyinNames.like("%" + result + "%"))
                        .or(Attends_Table.weixinIds.like("%" + result + "%"))
                        .or(Attends_Table.cellphones.like("%" + result + "%"))
                        .or(Attends_Table.emailAddrs.like("%" + result + "%"))
                        .or(Attends_Table.names.like("%" + result + "%"))
                ).queryList();

    //    Log.e("aa", attendList.size() + "F");

        List<Order> lists = new Select().from(Order.class).where(Order_Table.eventId.is(eventId))
                .and(OperatorGroup.clause().and(Order_Table.buyerName.like("%" + result + "%"))
                        .or(Order_Table.buyerCellphone.like("%" + result + "%"))
                        .or(Order_Table.buyerEmail.like("%" + result + "%"))
                        .or(Order_Table.orderNumber.like("%" + result + "%"))
                )
                .and(Order_Table.audit.is(1)).queryList();

        for (int i = 0; i < attendList.size(); i++) {
            OrderInfo.RespObjectBean.ObjectsBean info = new OrderInfo.RespObjectBean.ObjectsBean();
            Order order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(attendList.get(i).orderIds))
                    .and(Order_Table.audit.is(1)).querySingle();
            if (order != null) {
                info.setAccountType(order.accountType);
                info.setOrderNumber(order.orderNumber);
                info.setBuyerName(order.buyerName);
                info.setOrderTime(order.orderTime);
                info.setTicketOrderTotalPrice(order.ticketOrderTotalPrice);
                info.setPayStatus(order.payStatus);
                info.setOrderNumber(order.orderNumber);
                info.setOrderId(order.orderId);
                info.setBuyerEmail(order.buyerEmail);
                info.setBuyerCellphone(order.buyerCellphone);
                info.setPayWay(order.payWay);
                info.setNotes(order.notes);
                info.setAudit(order.audit);
                auditSearchList.add(info);
            }
        }

        for (int i = 0; i < lists.size(); i++) {
            OrderInfo.RespObjectBean.ObjectsBean info = new OrderInfo.RespObjectBean.ObjectsBean();
            Order order = lists.get(i);
            info.setAccountType(order.accountType);
            info.setOrderNumber(order.orderNumber);
            info.setBuyerName(order.buyerName);
            info.setOrderTime(order.orderTime);
            info.setTicketOrderTotalPrice(order.ticketOrderTotalPrice);
            info.setPayStatus(order.payStatus);
            info.setOrderNumber(order.orderNumber);
            info.setOrderId(order.orderId);
            info.setBuyerEmail(order.buyerEmail);
            info.setBuyerCellphone(order.buyerCellphone);
            info.setPayWay(order.payWay);
            info.setNotes(order.notes);
            info.setAudit(order.audit);
            auditSearchList.add(info);
        }

        auditSearchList = removeDuplicteOrders(auditSearchList);

        if (auditSearchList.size() > 0) {
            vTransparent.setVisibility(View.GONE);
            llPageStatus.setVisibility(View.GONE);
            fmOrder.setVisibility(View.VISIBLE);
            auditOrderAdapter.setNewData(auditSearchList);
            sflOrder.setEnabled(false);
            auditOrderAdapter.setEnableLoadMore(false);
        } else {
            vTransparent.setVisibility(View.GONE);
            fmOrder.setVisibility(View.GONE);
            llPageStatus.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
            tvPageStatus.setText(R.string.no_order_record);
        }
    }

    private List<OrderInfo.RespObjectBean.ObjectsBean> removeDuplicteOrders(List<OrderInfo.RespObjectBean.ObjectsBean> orderInfos) {
        Set<OrderInfo.RespObjectBean.ObjectsBean> s = new TreeSet<OrderInfo.RespObjectBean.ObjectsBean>(new Comparator<OrderInfo.RespObjectBean.ObjectsBean>() {
            @Override
            public int compare(OrderInfo.RespObjectBean.ObjectsBean o1, OrderInfo.RespObjectBean.ObjectsBean o2) {

                return o1.getBuyerName().compareTo(o2.getBuyerName());
            }
        });
        s.addAll(orderInfos);
        return new ArrayList<OrderInfo.RespObjectBean.ObjectsBean>(s);
    }
}
