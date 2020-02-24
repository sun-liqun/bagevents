package com.bagevent.activity_manager.manager_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.bagevent.BaseFragment;
import com.bagevent.R;
import com.bagevent.activity_manager.detail.ActivityOrderDetail;
import com.bagevent.activity_manager.detail.AuditOrder;
import com.bagevent.activity_manager.manager_fragment.adapter.ActivityOrderAdapter;
import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ActivityOrderPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ActivityOrderView;
import com.bagevent.common.Common;
import com.bagevent.db.Attends;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.Order;
import com.bagevent.db.Order_Table;
import com.bagevent.new_home.HomePage;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.dbutil.SyncOrderUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;

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
import butterknife.Unbinder;

/**
 * Created by zwj on 2016/5/30.
 * <p>
 * 活动通知
 */
public class ActivityOrderFragment extends BaseFragment implements ActivityOrderView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, TextWatcher, BaseQuickAdapter.OnItemClickListener, FragmentBackHandler {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    Unbinder unbinder;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.sfl_order)
    SwipeRefreshLayout sflOrder;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @BindView(R.id.btn_search_cancle)
    Button btnSearchCancle;
    @BindView(R.id.fl_search)
    AutoFrameLayout flSearch;
    @BindView(R.id.ll_search)
    AutoLinearLayout llSearch;
    //    @BindView(R.id.tv_no_order)
//    TextView tvNoOrder;
    @BindView(R.id.ll_common_title)
    LinearLayout llCommonTitle;
    @BindView(R.id.v_transparent)
    View vTransparent;
    @BindView(R.id.fm_order)
    AutoFrameLayout fmOrder;
    @BindView(R.id.tv_audit_order_num)
    TextView tvAuditOrderNum;
    @BindView(R.id.ll_audit_order)
    AutoLinearLayout llAuditOrder;
    @BindView(R.id.iv_page_status)
    ImageView ivPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;
    @BindView(R.id.tv_mark)
    TextView tvMark;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.tv_retry)
    TextView tvRetry;
    @BindView(R.id.tv_loading_msg)
    TextView tv_loading_msg;

    private ActivityOrderPresenter orderPresenter;
    private String eventId;
    private String userId;
    private String fromTime;
    private int pageNum = 1;
    private List<OrderInfo.RespObjectBean.ObjectsBean> orderList = new ArrayList<OrderInfo.RespObjectBean.ObjectsBean>();
    private List<OrderInfo.RespObjectBean.ObjectsBean> orderAllList = new ArrayList<OrderInfo.RespObjectBean.ObjectsBean>();
    private List<Order> auditOrder = new ArrayList<Order>();
    private int offset = 0;//数据库查询偏移量
    private int limit = 50;//数据库每次查询的数量
    private ActivityOrderAdapter orderAdapter;

    private InputMethodManager inputMethodManager;
    private boolean isSwipe = false;
    private boolean isSearch = false;
    private String result;

    private boolean mViewBound=false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        mViewBound=true;
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        eventId = Integer.toString(bundle.getInt("eventId"));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isLoading();
        getOrder();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSearch) {
            if (orderAdapter != null) {
                orderAdapter.replaceData(orderAllList);
                //  orderAdapter.setNewData(orderAllList);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkHttpUtils.getInstance().cancelTag("ActivityOrderFragment");
        if (unbinder!=null){
            unbinder.unbind();
        }
        mViewBound=false;
        EventBus.getDefault().removeAllStickyEvents();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MsgEvent event) {
        //  Log.e("ActivityOrderFragment",event.mMsg);
        if (event.mMsg.equals(Common.SYNC_INFO_SUCCESS)) {
            // Log.e("fdsa", "fds");
            if (!isSearch) {
                orderList.clear();
                if (!isSwipe) {
                    initData(0);
                } else {
                    isSwipe = false;
                    offset = 0;
                    orderAdapter.setEnableLoadMore(true);
                    sflOrder.setRefreshing(false);
                    initData(0);
                }
            }
        } else if (event.mMsg.equals(Common.SYNC_INFO_MORE)) {
            sflOrder.setEnabled(true);
//            setMoreData(offset);
        } else if (event.mMsg.equals(Common.ORDER_PAGE)) {//下拉刷新的人数超出一页，采用分页获取
            pageNum = event.pageNumber;
            getOrder();
        } else if (event.mMsg.equals(Common.MODIFY_ORDER)) {//修改参会人员成功需更新列表项
            if (orderAllList.size() == 0) {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId))).and(Order_Table.orderId.is(orderList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = orderList.get(event.pos);
                    bean.setBuyerName(order.buyerName);
                    orderAdapter.notifyItemChanged(event.pos);
                }
            } else {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId))).and(Order_Table.orderId.is(orderAllList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = orderAllList.get(event.pos);
                    bean.setBuyerName(order.buyerName);
                    orderAdapter.notifyItemChanged(event.pos);
                }
            }
        } else if (event.mMsg.equals(Common.CONFIRM_ORDER)) {//确认收款or退票，更新订单列表的状态信息
            if (orderAllList.size() == 0) {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId))).and(Order_Table.orderId.is(orderList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = orderList.get(event.pos);
                    bean.setPayStatus(order.payStatus);
                    orderAdapter.notifyItemChanged(event.pos);
                }
            } else {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId))).and(Order_Table.orderId.is(orderAllList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = orderAllList.get(event.pos);
                    bean.setPayStatus(order.payStatus);
                    orderAdapter.notifyItemChanged(event.pos);
                }
            }
        } else if (event.mMsg.equals(Common.UPDATE_NOTES)) {
            if (orderAllList.size() == 0) {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId))).and(Order_Table.orderId.is(orderList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = orderList.get(event.pos);
                    bean.setNotes(order.notes);
                    orderAdapter.notifyItemChanged(event.pos);
                }
            } else {
                Order order = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId))).and(Order_Table.orderId.is(orderAllList.get(event.pos).getOrderId())).querySingle();
                if (order != null) {
                    OrderInfo.RespObjectBean.ObjectsBean bean = orderAllList.get(event.pos);
                    bean.setNotes(order.notes);
                    orderAdapter.notifyItemChanged(event.pos);
                }
            }
        } else if (event.mMsg.equals(Common.AUDIT_ORDER)) {
            getAuditOrder();
            // Log.e("aaa","dsfsd");
        } else if (event.mMsg.equals("orderSearch")) {
            llLoading.setVisibility(View.GONE);
            if (orderAllList.size() > 0) {
                vTransparent.setVisibility(View.GONE);
                llPageStatus.setVisibility(View.GONE);
                fmOrder.setVisibility(View.VISIBLE);
                orderAdapter.setNewData(orderAllList);
                sflOrder.setEnabled(false);
                orderAdapter.setEnableLoadMore(false);
            } else {
                vTransparent.setVisibility(View.GONE);
                fmOrder.setVisibility(View.GONE);
                llPageStatus.setVisibility(View.VISIBLE);
                llPageStatus.setClickable(false);
                tvRetry.setVisibility(View.GONE);
                Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
                tvPageStatus.setText(R.string.no_order_record);
            }
        }
        if (!event.mMsg.equals(Common.SYNC_INFO_SUCCESS)) {
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    private void getAuditOrder() {
        auditOrder = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId))).and(Order_Table.payStatus.is(12)).orderBy(Order_Table.orderTime, false).queryList();
        if (auditOrder.size() > 0) {
            if (!isSearch) {
                llAuditOrder.setVisibility(View.VISIBLE);
            }
            if (auditOrder.size() > 99) {
                tvMark.setText(R.string.audit_wait_order1);
                tvAuditOrderNum.setText(R.string.num_more);
            } else {
                tvMark.setText(R.string.audit_wait_order1);
                tvAuditOrderNum.setText(auditOrder.size() + "");
            }
        } else {
            llAuditOrder.setVisibility(View.GONE);
        }
    }

    private void getOrder() {
     if (mViewBound){
        if (NetUtil.isConnected(getActivity())) {
            if (TextUtils.isEmpty(currentTime()) || pageNum > 1) {
                orderPresenter = new ActivityOrderPresenter(this);
                orderPresenter.getOrderFirsst();
            } else {
                orderPresenter = new ActivityOrderPresenter(this);
                orderPresenter.getOrderFromTime();
            }
        } else {
            orderList.clear();
            offset = 0;
            initData(0);
        }
     }
    }

    private String currentTime() {
        fromTime = SharedPreferencesUtil.get(getActivity(), "orderTime" + eventId, "");
        return fromTime;
    }

    @Override
    public Context mContext() {
        return getActivity();
    }

    @Override
    public String eventId() {
        return eventId;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public String fromTime() {
        return currentTime();
    }

    @Override
    public int page() {
        return pageNum;
    }

    /**
     * 第一次加载数据成功
     *
     * @param response
     */
    @Override
    public void showOrderSuccess(String response) {
        SyncOrderUtil util = new SyncOrderUtil(getActivity(), Integer.parseInt(eventId), response);
        util.syncOrder();

    }

    @Override
    public void showOrderSuccess2(String response) {
        SyncOrderUtil util = new SyncOrderUtil(getActivity(), Integer.parseInt(eventId), response);
        util.setMore(true);
        util.syncOrder();

    }

    @Override
    public void showOrderFailed(String errInfo) {
        loadingFinished();
        Toast.makeText(getActivity(), errInfo, Toast.LENGTH_SHORT).show();

    }

    private void setMoreData(int offset) {
        List<Order> order = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId))).offset(offset).limit(limit).queryList();
        orderAllList.clear();
        for (int i = 0; i < order.size(); i++) {
            OrderInfo.RespObjectBean.ObjectsBean info = new OrderInfo.RespObjectBean.ObjectsBean();
            Order data = order.get(i);
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
            orderAllList.add(info);
        }

        orderAdapter.addData(orderAllList);
        if (order.size() < limit) {
            orderAdapter.loadMoreComplete();
            orderAdapter.loadMoreEnd();
        } else {
            orderAdapter.loadMoreComplete();
        }
    }

    private void initData(int offset) {
        loadingFinished();
        auditOrder.clear();
        getAuditOrder();
        List<Order> order = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId))).offset(offset).limit(limit).queryList();
        for (int i = 0; i < order.size(); i++) {
            OrderInfo.RespObjectBean.ObjectsBean info = new OrderInfo.RespObjectBean.ObjectsBean();
            Order data = order.get(i);
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
            orderList.add(info);
        }

        if (orderAdapter == null) {
            initAdapter();
        } else {
            if (order.size() > 0) {
                orderAdapter.setNewData(orderList);
                if (order.size() < limit) {
                    orderAdapter.loadMoreComplete();
                    orderAdapter.loadMoreEnd();
                } else {
                    orderAdapter.loadMoreComplete();
                }
            } else {
                orderPresenter.getOrderFromPage();
            }
        }
    }

    private void initAdapter() {
        if (orderList.size() > 0) {
            llPageStatus.setVisibility(View.GONE);
            orderAdapter = new ActivityOrderAdapter(R.layout.activity_order_item, orderList);
            // orderAdapter.openLoadAnimation();
            orderAdapter.setOnLoadMoreListener(this, rvOrder);
            orderAdapter.setOnItemClickListener(this);
            rvOrder.setAdapter(orderAdapter);
            if (orderList.size() < limit) {
                orderAdapter.loadMoreEnd();
            }
        } else {
            llPageStatus.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(R.drawable.no_record).into(ivPageStatus);
            tvPageStatus.setText(R.string.no_order);
        }
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        sflOrder.setVisibility(View.GONE);
    }

    private void loadingFinished() {
        llLoading.setVisibility(View.GONE);
        sflOrder.setVisibility(View.VISIBLE);
    }

    private void initView() {
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Glide.with(getActivity()).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.activity_order);
        ivRight.setVisibility(View.GONE);
        tv_loading_msg.setVisibility(View.VISIBLE);
        Glide.with(getActivity()).load(R.drawable.jilu).into(ivRight);
        ((SimpleItemAnimator) rvOrder.getItemAnimator()).setSupportsChangeAnimations(false);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        sflOrder.setOnRefreshListener(this);
        etSearch.addTextChangedListener(this);
        userId = SharedPreferencesUtil.get(getActivity(), "userId", "");
    }

    @Override
    public void onLoadMoreRequested() {
        sflOrder.setEnabled(false);
        pageNum++;
        offset = offset + limit;
        initData(offset);
    }

    @Override
    public void onRefresh() {
        isSwipe = true;
        pageNum = 1;
        orderAdapter.setEnableLoadMore(false);
        if (NetUtil.isConnected(getActivity())) {
            orderPresenter = new ActivityOrderPresenter(this);
            orderPresenter.getOrderFromTime();
        }
    }

    @OnClick({R.id.ll_search, R.id.iv_search_clear, R.id.btn_search_cancle, R.id.ll_title_back, R.id.ll_audit_order, R.id.ll_page_status, R.id.ll_right_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_search:
                isSearch = true;
                etSearch.requestFocus();
                etSearch.setFocusable(true);
                llAuditOrder.setVisibility(View.GONE);
                vTransparent.setVisibility(View.VISIBLE);
                llCommonTitle.setVisibility(View.GONE);
                llSearch.setVisibility(View.GONE);
                flSearch.setVisibility(View.VISIBLE);
                inputMethodManager.showSoftInput(etSearch, 0);
                sflOrder.setEnabled(false);
//                if (orderAdapter != null) {
//                    orderAdapter.setEnableLoadMore(false);
//                }
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                orderAllList.clear();
                vTransparent.setVisibility(View.VISIBLE);
                ivSearchClear.setVisibility(View.GONE);
                if (orderAdapter != null) {
                    orderAdapter.setNewData(orderList);
                }
                break;
            case R.id.btn_search_cancle:
                cancelSearch();
                break;
            case R.id.ll_audit_order:
                Intent intent = new Intent(getActivity(), AuditOrder.class);
                intent.putExtra("eventId", Integer.parseInt(eventId));
                startActivity(intent);
                //    Toast.makeText(getActivity(), "待审核订单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_page_status:
                isLoading();
                getOrder();
                break;
            case R.id.ll_right_click:
                // Intent intent1 = new Intent(getActivity(), AllInvoiceActivity.class);
                //    intent1.putExtra("eventId",Integer.parseInt(eventId));
                //    startActivity(intent1);
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
        orderAllList.clear();
        result = s.toString();
        if (TextUtils.isEmpty(result)) {
            ivSearchClear.setVisibility(View.GONE);
            if (orderAdapter != null) {
                orderAdapter.setNewData(orderAllList);
            }
        } else {
            ivSearchClear.setVisibility(View.VISIBLE);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<OrderInfo.RespObjectBean.ObjectsBean> list = new ArrayList<OrderInfo.RespObjectBean.ObjectsBean>();

                    List<Attends> attendList = new Select().from(Attends.class).where(Attends_Table.eventId.is(Integer.parseInt(eventId)))
                            .and(OperatorGroup.clause()
                                    .and(Attends_Table.gsonUser.like("%" + result + "%"))
                                    .or(Attends_Table.pinyinNames.like("%" + result + "%"))
                                    .or(Attends_Table.weixinIds.like("%" + result + "%"))
                                    .or(Attends_Table.cellphones.like("%" + result + "%"))
                                    .or(Attends_Table.emailAddrs.like("%" + result + "%"))
                                    .or(Attends_Table.names.like("%" + result + "%"))
                            )
                            .and(OperatorGroup.clause()
                                    .and(Attends_Table.audits.is(0))
                                    .or(Attends_Table.audits.is(2))
                                    .or(Attends_Table.audits.is(3))).queryList();

                    List<Order> lists = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId)))
                            .and(OperatorGroup.clause().and(Order_Table.buyerName.like("%" + result + "%"))
                                    .or(Order_Table.buyerCellphone.like("%" + result + "%"))
                                    .or(Order_Table.buyerEmail.like("%" + result + "%"))
                                    .or(Order_Table.orderNumber.like("%" + result + "%"))
                            )
                            .and(OperatorGroup.clause()
                                    .and(Order_Table.audit.is(0))
                                    .or(Order_Table.audit.is(2))
                                    .or(Order_Table.audit.is(3))).queryList();


                    for (int i = 0; i < attendList.size(); i++) {
                        OrderInfo.RespObjectBean.ObjectsBean info = new OrderInfo.RespObjectBean.ObjectsBean();
                        Order order = new Select().from(Order.class).where(Order_Table.eventId.is(Integer.parseInt(eventId))).and(Order_Table.orderId.is(attendList.get(i).orderIds)).and(OperatorGroup.clause()
                                .and(Order_Table.audit.is(0))
                                .or(Order_Table.audit.is(2))
                                .or(Order_Table.audit.is(3))).querySingle();
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
                            list.add(info);
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
                        list.add(info);
                    }
                    list = removeDuplicteOrders(list);
                    orderAllList.addAll(list);
                    EventBus.getDefault().post(new MsgEvent("orderSearch"));
                }
            });
            thread.start();
        }


    }

    /**
     * remove搜索的重复项
     *
     * @param orderInfos
     * @return
     */
    private List<OrderInfo.RespObjectBean.ObjectsBean> removeDuplicteOrders(List<OrderInfo.RespObjectBean.ObjectsBean> orderInfos) {
        Set<OrderInfo.RespObjectBean.ObjectsBean> s = new TreeSet<OrderInfo.RespObjectBean.ObjectsBean>(new Comparator<OrderInfo.RespObjectBean.ObjectsBean>() {
            @Override
            public int compare(OrderInfo.RespObjectBean.ObjectsBean o1, OrderInfo.RespObjectBean.ObjectsBean o2) {

                return o1.getOrderNumber().compareTo(o2.getOrderNumber());
            }
        });
//        for (int i = 0; i < orderInfos.size(); i++) {
//            s.add(orderInfos.get(i));
//        }
        s.addAll(orderInfos);
        return new ArrayList<OrderInfo.RespObjectBean.ObjectsBean>(s);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OrderInfo.RespObjectBean.ObjectsBean bean = null;
        if (isSearch) {
            if (orderAllList.size() > 0) {
                bean = orderAllList.get(position);
            }
        } else {
            bean = orderList.get(position);
        }
        if (bean != null) {
            Intent intent = new Intent(getActivity(), ActivityOrderDetail.class);
            intent.putExtra("accountType", bean.getAccountType());
            intent.putExtra("eventId", Integer.parseInt(eventId));
            intent.putExtra("orderId", bean.getOrderId());
            intent.putExtra("position", position);
            intent.putExtra("enterStatus", 0);
            startActivity(intent);
        }

    }

    private void cancelSearch() {
        isSearch = false;
        etSearch.setText("");
        orderAllList.clear();
        sflOrder.setEnabled(true);
        if (orderAdapter != null) {
            orderAdapter.setEnableLoadMore(true);
            orderAdapter.setNewData(orderList);
        }
        vTransparent.setVisibility(View.GONE);
        flSearch.setVisibility(View.GONE);
        llCommonTitle.setVisibility(View.VISIBLE);
        llSearch.setVisibility(View.VISIBLE);
        fmOrder.setVisibility(View.VISIBLE);
        llPageStatus.setVisibility(View.GONE);
        getAuditOrder();
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onBackPressed() {
        if (llCommonTitle.getVisibility() == View.GONE) {
            cancelSearch();
            return true;
        } else {
            return BackHandlerHelper.handleBackPress(this);
        }
    }
}
