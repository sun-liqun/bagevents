package com.bagevent.new_home.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.BaseFragment;
import com.bagevent.R;
import com.bagevent.activity_manager.AcManager;
import com.bagevent.activity_manager.detail.CollectionBarcode;
import com.bagevent.activity_manager.detail.CollectionDetail;
import com.bagevent.activity_manager.manager_fragment.BarCodeCheckIn;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.EventList;
import com.bagevent.db.EventList_Table;
import com.bagevent.home.data.ExercisingData;
import com.bagevent.home.home_interface.presenter.GetExercisingPresenter;
import com.bagevent.home.home_interface.view.GetExercisingView;
import com.bagevent.new_home.adapter.EventPandectAdapter;
import com.bagevent.new_home.adapter.ExhibitorAdapter;
import com.bagevent.new_home.data.ExhibitionListData;
import com.bagevent.new_home.data.IncomeData;
import com.bagevent.new_home.data.NewAttendeeData;
import com.bagevent.new_home.data.SaleMoneyData;
import com.bagevent.new_home.data.UpdateVersionData;
import com.bagevent.new_home.data.UserInfoData;
import com.bagevent.new_home.data.WithDrawAccountData;
import com.bagevent.new_home.new_activity.RechargeMessage;
import com.bagevent.new_home.new_activity.SetWithDrawPassword;
import com.bagevent.new_home.new_activity.WithDrawActivity;
import com.bagevent.new_home.new_interface.new_view.GetIncomeView;
import com.bagevent.new_home.new_interface.new_view.GetNewAttendeeView;
import com.bagevent.new_home.new_interface.new_view.GetSaleMoneyView;
import com.bagevent.new_home.new_interface.new_view.GetUserInfoView;
import com.bagevent.new_home.new_interface.new_view.UpdateVersionView;
import com.bagevent.new_home.new_interface.new_view.WithDrawAccountView;
import com.bagevent.new_home.new_interface.presenter.GetIncomePresenter;
import com.bagevent.new_home.new_interface.presenter.GetNewAttendeePresenter;
import com.bagevent.new_home.new_interface.presenter.GetSaleMoneyPresenter;
import com.bagevent.new_home.new_interface.presenter.GetUserInfoPresenter;
import com.bagevent.new_home.new_interface.presenter.UpDateVersionPresenter;
import com.bagevent.new_home.new_interface.presenter.WithDrawAccountPresenter;
import com.bagevent.synchro_data.UpdateVersionService;
import com.bagevent.util.AppUtils;
import com.bagevent.util.CompareDate;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.PageTool;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.util.dbutil.SyncEventUtil;
import com.bagevent.view.CustomMarkView;
import com.bagevent.view.DownloadDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.JsonParser;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by zwj on 2016/8/25.
 */
public class EventPandectFragment extends BaseFragment implements GetExercisingView, GetIncomeView, GetUserInfoView, UpdateVersionView, BaseQuickAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, OnChartValueSelectedListener, WithDrawAccountView, GetSaleMoneyView, GetNewAttendeeView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.refresh_event)
    SwipeRefreshLayout refreshEvent;
    Unbinder unbinder;

    @BindView(R.id.rv_home_event)
    RecyclerView rvHomeEvent;

    @BindView(R.id.rv_home_exhibition)
    RecyclerView rvHomeExhibition;

    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.iv_barcode_checkin)
    ImageView iv_barcode_checkin;
    private View headerView;
    private View footerView;
    private HeaderViewBind headerViewBind;
    private FooterViewBind footerViewBind;

    private EventPandectAdapter eventPandectAdapter;
    private ExhibitorAdapter exhibitorAdapter;

    private ArrayList<EventList> eventLists = new ArrayList<EventList>();
    private String[] x;//x轴
    private float[] y1;//左边y轴 收入
    private float[] y2;//右边y轴 人数
    private float leftMax;
    private float rightMax;

    /**
     * 加载数据
     */
    private String userId = "";
    private GetExercisingPresenter getExPresenter;
    private GetUserInfoPresenter userInfoPresenter;
    private GetSaleMoneyPresenter getSaleMoneyPresenter;
    private GetNewAttendeePresenter getNewAttendeePresenter;

    private GetIncomePresenter incomePresenter;
    /**
     * 版本更新
     */
    private UpDateVersionPresenter upDateVersionPresenter;
    private WithDrawAccountPresenter withDrawPresenter;
    private DownloadDialog downloadDialog;
    private NormalAlertDialog forceUpdateDialog;
    private NormalAlertDialog updateDialog;
    private NormalAlertDialog requestDialog;
    private int state = -1;
    private String apkUrl = "";
    private String versinCode = "";
    private String userCellphone;
    private CustomMarkView customMarkView;

//    int page=1;
//    int pageSize=50;
//    int pageCount=0;
//    private int offset = 0;
//    private int limit = 50;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_pandect, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getHeaderView();
        getFooterView();
        headerViewBind = new HeaderViewBind(headerView);
        footerViewBind = new FooterViewBind(footerView);
//        initLineChart();
        isLoading();
        getEvent();

        getExhibition();

        setListener();
        //更新app

        if (!AppUtils.isApkInDebug(getContext())) {
            updateVersion();
        }
//
//      Toast.makeText(getActivity(),"版本修复成功~",Toast.LENGTH_SHORT).show();

    }

    /**
     * 显示快速签到按钮
     */
    public void showBarcodeCheckin() {
        if (iv_barcode_checkin != null) {
            iv_barcode_checkin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int pos = (int) e.getX();
        if (pos < y1.length && pos < y2.length) {
            customMarkView.setValue(y1[pos], y2[pos]);
        }
    }

    @Override
    public void onNothingSelected() {

    }

    public void hideBarcode() {
        iv_barcode_checkin.setVisibility(View.GONE);
    }

    class FooterViewBind {
        @BindView(R.id.iv_page_status)
        ImageView ivPageStatus;
        @BindView(R.id.tv_page_status)
        TextView tvPageStatus;
        @BindView(R.id.tv_retry)
        TextView tvRetry;
        @BindView(R.id.ll_page_status)
        AutoLinearLayout llPageStatus;

        public FooterViewBind(View footerView) {
            ButterKnife.bind(this, footerView);
        }

        @OnClick(R.id.ll_page_status)
        public void onViewClicked() {
            EventPandectFragment.this.reTryGetEvent();
        }

    }

    class HeaderViewBind {
        @BindView(R.id.tv_new_add_attendee)
        TextView tvNewAddAttendee;
        @BindView(R.id.tv_new_add_income)
        TextView tvNewAddIncome;
        @BindView(R.id.tv_account_balance)
        TextView tvAccountBalance;
        @BindView(R.id.ll_withdraw)
        AutoLinearLayout llWithdraw;
        @BindView(R.id.tv_msg_balance)
        TextView tvMsgBalance;
        @BindView(R.id.ll_recharge)
        AutoLinearLayout llRecharge;
        @BindView(R.id.line_chart)
        LineChart lineChart;
        @BindView(R.id.tv_yesterday_attendee)
        TextView tvYesterdayAttendee;
        @BindView(R.id.tv_yesterday_sale)
        TextView tvYesterdaySale;
        View itemView;


        public HeaderViewBind(View headerView) {
            ButterKnife.bind(this, headerView);
            itemView = headerView;
        }

        @OnClick({R.id.ll_withdraw, R.id.ll_recharge})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.ll_withdraw:
                    EventPandectFragment.this.withDraw();
                    break;
                case R.id.ll_recharge:
                    Intent rechargeIntent = new Intent(getActivity(), RechargeMessage.class);
                    rechargeIntent.putExtra("banlance", tvAccountBalance.getText().toString());
                    startActivity(rechargeIntent);
                    break;
            }
        }

    }

    public void reTryGetEvent() {
        getEvent();
    }

    public void withDraw() {
        if (NetUtil.isConnected(getActivity())) {
            withDrawPresenter = new WithDrawAccountPresenter(this);
            withDrawPresenter.withdrawAccount();
        } else {
            Toast.makeText(getActivity(), R.string.check_network1, Toast.LENGTH_SHORT).show();
        }
    }

    public void initLineChart() {
        //设置数值选择监听
        headerViewBind.lineChart.setOnChartValueSelectedListener(this);
        // 没有描述的文本
        headerViewBind.lineChart.getDescription().setEnabled(false);
        // 支持触控手势
        headerViewBind.lineChart.setTouchEnabled(true);
        headerViewBind.lineChart.setDragDecelerationFrictionCoef(0.9f);
        headerViewBind.lineChart.setDrawMarkers(true);
        customMarkView.setChartView(headerViewBind.lineChart);
        headerViewBind.lineChart.setMarker(customMarkView);
        //设置是否可以用手指移动图表
        headerViewBind.lineChart.setDragEnabled(false);
        //设置是否可以缩放图表
        headerViewBind.lineChart.setScaleEnabled(false);
        //设置是否绘制背景
        headerViewBind.lineChart.setDrawGridBackground(false);
        headerViewBind.lineChart.setHighlightPerDragEnabled(true);
        // 如果禁用,扩展可以在x轴和y轴分别完成
        headerViewBind.lineChart.setPinchZoom(false);
        // 设置背景颜色(灰色)
        headerViewBind.lineChart.setBackgroundColor(Color.WHITE);
        //默认x动画
        headerViewBind.lineChart.animateX(1000);

        setData();

        //获得数据
        Legend legend = headerViewBind.lineChart.getLegend();
        //图例显示的位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        //图例的排列方式
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(true);
        legend.setEnabled(true);

        //x轴
        XAxis xAxis = headerViewBind.lineChart.getXAxis();
        xAxis.setEnabled(true);
        //设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置x轴文字的大小
        xAxis.setTextSize(9f);
//        xAxis.setLabelCount(2);
        xAxis.setLabelCount(x.length, true);
        //设置x轴文字的颜色
        xAxis.setTextColor(Color.BLACK);
        //设置是否绘制x方向网络线
        xAxis.setDrawGridLines(false);
        //设置是否显示x轴线
        xAxis.setDrawAxisLine(false);
        //显示x轴标签
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int val = value < 0 ? 0 : (int) value;
                int realVal = x.length;

                if (val < realVal)
                    return x[val];
                else
                    return "";
            }
        });


        //左边y轴 收入
        YAxis leftAxis = headerViewBind.lineChart.getAxisLeft();
        leftAxis.setEnabled(false);
        //设置y轴的最大值
        leftAxis.setAxisMaximum(leftMax);
        //设置y轴的最小值
        leftAxis.setAxisMinimum(0f);
        //设置y轴发出横向直线
        leftAxis.setDrawGridLines(false);
        //是否显示y轴坐标线
        leftAxis.setDrawZeroLine(true);


        //右边 人数
        YAxis rightAxis = headerViewBind.lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setAxisMaximum(rightMax);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(true);

    }


    //设置数据
    private void setData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < y1.length; i++) {
            yVals1.add(new Entry(i, y1[i]));
        }

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < y2.length; i++) {
            yVals2.add(new Entry(i, y2[i]));
        }

        LineDataSet set1, set2;

        if (headerViewBind.lineChart.getData() != null &&
                headerViewBind.lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) headerViewBind.lineChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) headerViewBind.lineChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            headerViewBind.lineChart.getData().notifyDataChanged();
            headerViewBind.lineChart.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            if (isAdded()){
                set1 = new LineDataSet(yVals1, getResources().getString(R.string.income));
                set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                set1.setDrawValues(false);
                set1.setColor(ContextCompat.getColor(getActivity(), R.color.red));
                set1.setCircleColor(ContextCompat.getColor(getActivity(), R.color.red));
                set1.setCircleColorHole(ContextCompat.getColor(getActivity(), R.color.red));
                set1.setLineWidth(1f);
                set1.setCircleRadius(4f);
                set1.setDrawCircles(false);
                set1.setDrawHighlightIndicators(false);
                set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                set1.setFillColor(ContextCompat.getColor(getActivity(), R.color.alpha_red));
                set1.setDrawFilled(true);


                //创建一个数据集,并给它一个类型
                set2 = new LineDataSet(yVals2, getResources().getString(R.string.number));
                set2.setDrawValues(false);
                set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
                set2.setColor(ContextCompat.getColor(getActivity(), R.color.ff9a3b));
                set2.setDrawCircles(false);
                set2.setCircleColor(ContextCompat.getColor(getActivity(), R.color.ff9a3b));
                set2.setLineWidth(1f);
                set2.setCircleRadius(3f);
                set2.setDrawCircleHole(false);
                set2.setDrawHighlightIndicators(false);
                set2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                set2.setFillColor(ContextCompat.getColor(getActivity(), R.color.ff9a3b));
                set2.setDrawFilled(true);
                set2.setHighLightColor(Color.rgb(244, 117, 117));

                // 创建一个数据集的数据对象
                ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
                dataSets.add(set1);
                dataSets.add(set2);
                LineData data = new LineData(set1, set2);
                headerViewBind.lineChart.setData(data);
                headerViewBind.lineChart.setNoDataText(getResources().getString(R.string.no_data));
                headerViewBind.lineChart.invalidate();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.contains("newEventList")) {
//            if (y1 != null && y2 != null) {
//                if (y1.length > 0 && y2.length > 0) {
//                    initLineChart();
//                }
//            }
            getEventList();

        }
//        else if(event.mMsg.contains(Common.SYNC_EVENT)){
//            page=event.pageNumber;
//            getExPresenter.getExercise();
//            pageCount=event.pageCount;
//            getEventList();
//            LogUtil.i("-----------------------SYNC_EVENT$"+event.pageNumber);
//        }
        else if (event.mMsg.equals("STATUS_SUCCESSFUL")) {
            downloadDialog.setProgress(100);
            cancleDialog();
        } else if (event.mMsg.equals("STATUS_FAILED")) {
            cancleDialog();
        } else if (event.mMsg.equals("STATUS_PAUSED")) {
            showDialog();
        } else if (event.mMsg.equals("STATUS_PENDING")) {
            showDialog();
        } else if (event.mMsg.equals("STATUS_RUNNING")) {
            downloadDialog.setProgress(event.pos);
        } else if (event.mMsg.contains(Common.WITHDRAW_APPLY)) {
            if (NetUtil.isConnected(getActivity())) {
                userInfoPresenter = new GetUserInfoPresenter(this);
                userInfoPresenter.getUserInfo();
                eventPandectAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 签到
     */
    @OnClick(R.id.iv_barcode_checkin)
    public void signIn() {
        Bundle signBundle = new Bundle();
        signBundle.putInt(KeyUtil.KEY_GO_SIGN, 1);
        PageTool.go(getActivity(), BarCodeCheckIn.class, signBundle);
    }

    private void updateVersion() {
        if (NetUtil.isConnected(getActivity())) {
            upDateVersionPresenter = new UpDateVersionPresenter(this);
            upDateVersionPresenter.version();
        }
    }

    private void isForceUpdate() {
        if (state == Constants.FORCE_UPDATE) {
            showDialog();
            startLoadApk();
        } else if (state == Constants.NOT_FORCE_UPDATE) {
            showUpdateDialog();
        }
    }

    private void startLoadApk() {
        Intent intent = new Intent(getActivity(), UpdateVersionService.class);
        intent.putExtra("url", apkUrl);
        intent.putExtra("state", state);
        intent.putExtra("appName", "百格活动");
        intent.putExtra("description", "正在下载");
        getActivity().startService(intent);
    }

    private void getEvent() {
        if (NetUtil.isConnected(getActivity())) {
            getNewAttendeePresenter = new GetNewAttendeePresenter(this);
            getNewAttendeePresenter.newAttendee();
            getSaleMoneyPresenter = new GetSaleMoneyPresenter(this);
            getSaleMoneyPresenter.saleMoney();
            incomePresenter = new GetIncomePresenter(this);
            incomePresenter.income();//获得收入数据
            userInfoPresenter = new GetUserInfoPresenter(this);
            userInfoPresenter.getUserInfo();
            getExPresenter = new GetExercisingPresenter(this);
            getExPresenter.getExercise();//获得活动信息和采集点信息
        } else {
            getEventList();
        }
    }

    private void getExhibition() {
        if (!NetUtil.isConnected(getActivity())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Post(
                getContext()).url(Constants.NEW_URL + Constants.EXHIBITOR_LIST)
                .tag("GetExhibition")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("GetExhibition取消请求");
                        }else {
                            parserError();
                        }

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            parserSuccess(response);
                        } else {
                            TosUtil.show(getString(R.string.send_error));
                        }
                    }
                });
    }

    private void parserError() {
        if (exhibitorAdapter != null) {
            if (exhibitorAdapter.getData() != null) {
                exhibitorAdapter.getData().clear();
                exhibitorAdapter.notifyDataSetChanged();
            }
        } else {
            ArrayList<ExhibitionListData.ExhibitionList> data = new ArrayList<>();
            exhibitorAdapter = new ExhibitorAdapter(data);
            rvHomeExhibition.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
            rvHomeExhibition.setAdapter(exhibitorAdapter);
        }
        if (exhibitorAdapter != null && headerViewBind != null) {
            exhibitorAdapter.removeHeaderView(headerViewBind.itemView);
        }
        exhibitorAdapter.addHeaderView(headerView);
    }

    private void parserSuccess(String response) {
        ArrayList<ExhibitionListData.ExhibitionList> showListData=new ArrayList<>();
        ArrayList<ExhibitionListData.ExhibitionList> listData = getListData(response);
        for(int i=0;i<listData.size();i++){
            if(CompareDate.compareEndTime(listData.get(i).getEvent().getEndTime())){
                    showListData.add(listData.get(i));
            }
        }
        initRecyclerView(showListData);
    }

    private void initRecyclerView(ArrayList<ExhibitionListData.ExhibitionList> data) {
        if (exhibitorAdapter != null && headerViewBind != null) {
            exhibitorAdapter.removeHeaderView(headerViewBind.itemView);
            exhibitorAdapter.setNewData(data);
        }
//            if (data.size()>0){
        exhibitorAdapter = new ExhibitorAdapter(data);
        rvHomeExhibition.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        rvHomeExhibition.setHasFixedSize(true);

        rvHomeExhibition.setAdapter(exhibitorAdapter);
        rvHomeExhibition.setNestedScrollingEnabled(false);
//            }
        exhibitorAdapter.addHeaderView(headerView);

//          exhibitorAdapter.addHeaderView(headerView);
//        exhibitorAdapter.addFooterView(footerView);
    }

    private ArrayList<ExhibitionListData.ExhibitionList> getListData(String response) {
        ExhibitionListData exhibitionListData = new ExhibitionListData(new JsonParser().parse(response).getAsJsonObject());
        if (exhibitionListData.getRespObject() == null || exhibitionListData.getRespObject().getExhibitorList() == null) {
            return new ArrayList<>();
        } else {
            return exhibitionListData.getRespObject().getExhibitorList();
        }
    }

    private void getEventList() {
        loadFinish();
        eventLists.clear();
        List<EventList> queryEventList = new Select().from(EventList.class)
                .where(EventList_Table.userId.is(Integer.parseInt(userId)))
                .and(OperatorGroup.clause().and(EventList_Table.status.is(1))
                        .or(EventList_Table.status.is(2))
                        .or(EventList_Table.status.is(4)))
                .and(EventList_Table.mark.is("event")).queryList();
//        List<EventList> queryEventList = new Select().from(EventList.class)
//                .where(EventList_Table.userId.is(Integer.parseInt(userId)))
//                .and(OperatorGroup.clause().and(EventList_Table.status.is(1))
//                        .or(EventList_Table.status.is(2))
//                        .or(EventList_Table.status.is(4)))
//                .and(EventList_Table.mark.is("event")).offset(offset).limit(limit).queryList();
        eventLists.addAll(queryEventList);
        if (eventPandectAdapter == null) {
            initAdapter();
        } else {
            eventPandectAdapter.setNewData(eventLists);
        }
        if (!SharedPreferencesUtil.getSettingBoolean(getContext(), KeyUtil.KEY_GUIDE_SIGN, false)) {
            iv_barcode_checkin.setVisibility(View.GONE);
        }
    }


    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public int getPage() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return 0;
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public int getShowType() {
        return 0;
    }

//    @Override
//    public void showSuccess(String data) {
//        SyncEventUtil util = new SyncEventUtil(getActivity(), Integer.parseInt(userId), data);
//        util.startSyncEventUtil();
//    }

    @Override
    public void showSuccess(ExercisingData data) {

        SyncEventUtil util = new SyncEventUtil(getActivity(), Integer.parseInt(userId), data);
        util.startSyncEventUtil();
    }

    @Override
    public void showFailed(String errInfo) {
        getEventList();
    }

    @Override
    public void showHideView() {

    }

    @Override
    public void hideView() {

    }

    @Override
    public void onRefresh() {
//        page=1;
//        offset=0;
//        eventLists.clear();
        getEvent();
        getExhibition();
        refreshEvent.setRefreshing(false);
    }

    /**
     * 非wifi网络对话框
     */
    private void requestDialog() {
        requestDialog = new NormalAlertDialog.Builder(getActivity())
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black_light)
                .setContentText(getString(R.string.no_wifi))
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.fe6900)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        requestDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        startLoadApk();
                        requestDialog.dismiss();
                    }
                })
                .build();
        requestDialog.show();
    }

    /**
     * 弹出更新对话框
     */
    private void showUpdateDialog() {
        updateDialog = new NormalAlertDialog.Builder(getActivity())
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black_light)
                .setContentText(getString(R.string.weather_update))
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.fe6900)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        updateDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        if (NetUtil.isWifi(getActivity())) {
                            startLoadApk();
                            updateDialog.dismiss();
                        } else {
                            requestDialog();
                            updateDialog.dismiss();
                        }
                    }
                })
                .build();
        updateDialog.show();
    }


    /**
     * 显示下载进度的弹窗
     */
    private void showDialog() {
        if (downloadDialog == null) {
            downloadDialog = new DownloadDialog(getActivity());
        }
        if (!downloadDialog.isShowing()) {
            downloadDialog.show();
        }
    }

    public void cancleDialog() {
        if (downloadDialog != null && downloadDialog.isShowing()) {
            downloadDialog.dismiss();
        }
    }

    private void setListener() {
        refreshEvent.setOnRefreshListener(this);
    }

    private void initAdapter() {
        if (eventLists.size() > 0) {
            footerViewBind.llPageStatus.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(R.drawable.no_record).into(footerViewBind.ivPageStatus);
            footerViewBind.tvPageStatus.setText(R.string.no_activity_ing);
            footerViewBind.llPageStatus.setVisibility(View.VISIBLE);
        }
        eventPandectAdapter = new EventPandectAdapter(eventLists);
        eventPandectAdapter.openLoadAnimation();
//        eventPandectAdapter.addHeaderView(headerView);
        eventPandectAdapter.addFooterView(footerView);
        eventPandectAdapter.setOnItemClickListener(this);
        rvHomeEvent.setAdapter(eventPandectAdapter);
        rvHomeEvent.setNestedScrollingEnabled(false);
//        eventPandectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                page++;
//                if(page<=pageCount&&pageCount>1){
//                    getExPresenter.getExercise();
//                    offset=offset+limit;
//                }else{
//                    eventPandectAdapter.loadMoreComplete();
//                    eventPandectAdapter.loadMoreEnd();
//                }
//            }
//        }, rvHomeEvent);
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        refreshEvent.setVisibility(View.GONE);
    }

    private void loadFinish() {
        llLoading.setVisibility(View.GONE);
        refreshEvent.setVisibility(View.VISIBLE);
    }

    private void getFooterView() {
        footerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_page_status, (ViewGroup) rvHomeEvent.getParent(), false);
    }

    private void getHeaderView() {
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.event_pandect_type, (ViewGroup) rvHomeEvent.getParent(), false);
    }

    private void initView() {
        tvTitle.setText(R.string.tv_event_pandect);
        customMarkView = new CustomMarkView(getActivity(), R.layout.layout_custom_markview);
        rvHomeEvent.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHomeExhibition.setLayoutManager(new LinearLayoutManager(getActivity()));
        userId = SharedPreferencesUtil.get(getActivity(), "userId", "");
    }


    @Override
    public Context mContext() {
        return getActivity();
    }


    @Override
    public String userId() {
        return userId;
    }

    @Override
    public int size() {
        return 7;
    }



    /**
     * 显示人数
     *
     * @param response
     */
    @Override
    public void showGetNewAttendeeSuccess(NewAttendeeData response) {
        if (response.getRespObject().size() > 0) {
            y2 = new float[response.getRespObject().size()];
//            x = new String[response.getRespObject().size()];
            List<NewAttendeeData.RespObjectBean> list = new ArrayList<NewAttendeeData.RespObjectBean>();
            list.addAll(response.getRespObject());
            Collections.reverse(list);
            for (int i = 0; i < list.size(); i++) {
                NewAttendeeData.RespObjectBean bean = list.get(i);
                y2[i] = bean.getAttendeeCount();
//                x[i] = bean.getSignDate();
            }
            if (y2.length > 0) {
                rightMax = y2[0];
                for (int i = 1; i < y2.length; i++) {
                    if (y2[i] > rightMax) {
                        rightMax = y2[i];
                    }
                }
            }
            if (y1 != null && y2 != null) {
                if (y1.length > 0 && y2.length > 0) {
                    initLineChart();
                }
            }
        }
    }

    @Override
    public void showGetNewAttendeeFailed() {
        if (isAdded()){
            TosUtil.show(getResources().getString(R.string.gain_people_failed));
        }
    }

    /**
     * 显示收入
     *
     * @param response
     */
    @Override
    public void showGetSaleMoneySuccess(SaleMoneyData response) {
        if (response.getRespObject().size() > 0) {
            y1 = new float[response.getRespObject().size()];
            x = new String[response.getRespObject().size()];
            List<SaleMoneyData.RespObjectBean> list = new ArrayList<SaleMoneyData.RespObjectBean>();
            list.addAll(response.getRespObject());
            Collections.reverse(list);
            for (int i = 0; i < list.size(); i++) {
                SaleMoneyData.RespObjectBean bean = list.get(i);
                y1[i] = (float) bean.getRmbSaleMoney();
                x[i] = bean.getOrderTime();
            }
            if (y1.length > 0) {
                leftMax = y1[0];
                for (int i = 1; i < y1.length; i++) {
                    if (y1[i] > leftMax) {
                        leftMax = y1[i];
                    }
                }
            }

            if (y1 != null && y2 != null) {
                if (y1.length > 0 && y2.length > 0) {
                    initLineChart();
                }
            }
        }
    }

    @Override
    public void showGetSaleMoneyFailed() {
        if (isAdded()){
            TosUtil.show(getResources().getString(R.string.gain_people_failed));
        }

    }

    @Override
    public void withDrawAccountSuccess(WithDrawAccountData response) {
        if (response.getRespObject().getFirstWithdrawal() == 0) {
            Intent intent = new Intent(getActivity(), WithDrawActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account", headerViewBind.tvAccountBalance.getText().toString());
            bundle.putSerializable("WithDrawAccountData", (Serializable) response);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), SetWithDrawPassword.class);
            Bundle bundle = new Bundle();
            bundle.putString("account", headerViewBind.tvAccountBalance.getText().toString());
            bundle.putSerializable("WithDrawAccountData", (Serializable) response);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void withDrawAccoountFailed(String errInfo) {
        Toast.makeText(getActivity(), errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getUserInfoSuccess(UserInfoData response) {
        UserInfoData.RespObjectBean bean = response.getRespObject();
        DecimalFormat format = new DecimalFormat("0.00 ");
        headerViewBind.tvAccountBalance.setText(format.format(bean.getBalance()) + "");
        headerViewBind.tvMsgBalance.setText(bean.getSmsBalance() + "");
        userCellphone = bean.getCellphone();
    }

    @Override
    public void getUserInfoFailed(String errInfo) {

    }

    @Override
    public void showGetIncomeSuccess(IncomeData response) {
        //  incomeData = response;
        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int apm = calendar.get(Calendar.AM_PM);

        if (apm == 0) {//上午
            IncomeData.RespObjectBean bean = response.getRespObject();
            if (bean.getRmbIncome() == 0 && bean.getDollarIncome() == 0) {
                headerViewBind.tvNewAddIncome.setText("￥ 0");
            } else if (bean.getRmbIncome() != 0 && bean.getDollarIncome() == 0) {
                headerViewBind.tvNewAddIncome.setText("￥" + setBigDecimal(bean.getRmbIncome()));
            } else if (bean.getRmbIncome() == 0 && bean.getDollarIncome() != 0) {
                headerViewBind.tvNewAddIncome.setText("$" + setBigDecimal(bean.getDollarIncome()));
            } else {
                headerViewBind.tvNewAddIncome.setText("￥" + setBigDecimal(bean.getRmbIncome()) + "  $" + setBigDecimal(bean.getDollarIncome()));
            }
            headerViewBind.tvYesterdayAttendee.setText(R.string.tv_yesterday_attendee);
            headerViewBind.tvYesterdaySale.setText(R.string.tv_yesterday_sale);
            headerViewBind.tvNewAddAttendee.setText(bean.getNewAttendees() + "");
        } else {
            IncomeData.RespObjectBean bean = response.getRespObject();
            if (bean.getTodayRmbIncome() == 0 && bean.getTodayDollarIncome() == 0) {
                headerViewBind.tvNewAddIncome.setText("￥ 0");
            } else if (bean.getTodayRmbIncome() != 0 && bean.getTodayDollarIncome() == 0) {
                headerViewBind.tvNewAddIncome.setText("￥" + setBigDecimal(bean.getTodayRmbIncome()));
            } else if (bean.getTodayRmbIncome() == 0 && bean.getTodayDollarIncome() != 0) {
                headerViewBind.tvNewAddIncome.setText("$" + setBigDecimal(bean.getTodayDollarIncome()));
            } else {
                headerViewBind.tvNewAddIncome.setText("￥" + setBigDecimal(bean.getTodayRmbIncome()) + "  $" + setBigDecimal(bean.getTodayDollarIncome()));
            }
            headerViewBind.tvYesterdayAttendee.setText(R.string.tv_today_attendee);
            headerViewBind.tvYesterdaySale.setText(R.string.tv_today_sale);
            headerViewBind.tvNewAddAttendee.setText(bean.getTodayAttendees() + "");
        }

    }

    private String setBigDecimal(double income) {
        BigDecimal bigDecimal = new BigDecimal(income);
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString();
    }

    @Override
    public void showGetIncomeFailed() {

    }

    @Override
    public void versionInfo(UpdateVersionData response) {

        if (response.getRespObject() != null) {
            UpdateVersionData.RespObjectBean res = response.getRespObject();
            state = res.getUpdateWay();
            apkUrl = res.getDownloadUrl();
            String versionCode = res.getVersion();
            String localVersionCode = AppUtils.getVersioncode(getActivity());
            SharedPreferencesUtil.put(getActivity(), "versionCode", versionCode);
            SharedPreferencesUtil.put(getActivity(), "apkUrl", apkUrl);
            if (!versionCode.equals(localVersionCode)) {
                isForceUpdate();
            }
        }
    }

    @Override
    public void errVersionInfo(String errInfo) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag("NewAttendee");
        OkHttpUtils.getInstance().cancelTag("GetSaleMoney");
        OkHttpUtils.getInstance().cancelTag("GetIncome");
        OkHttpUtils.getInstance().cancelTag("GetUser");
        OkHttpUtils.getInstance().cancelTag("GetExercising");
        OkHttpUtils.getInstance().cancelTag("GetExhibition");
        OkHttpUtils.getInstance().cancelTag("GetVersion");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        EventList eventList = eventLists.get(position);

        if (eventList.mark.contains("event")) { //模拟活动，
            Intent intent = new Intent(getActivity(), AcManager.class);
            Bundle bundle = new Bundle();
            bundle.putString("eventName", eventList.eventName);
            bundle.putInt("eventId", eventList.eventId);
            bundle.putInt("stType", eventList.sType);
            bundle.putInt("exType", eventList.exType);
            bundle.putInt("hasLiveModule",eventList.hasLiveModule);
            bundle.putString(Common.BARCODE_LOGIN, "no");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (eventLists.get(position).mark.contains("collect")) {
            if (eventList.export == 1) {
                Intent intent1 = new Intent(getActivity(), CollectionDetail.class);
                intent1.putExtra("eventId", eventList.eventId);
                intent1.putExtra("collectionId", eventList.collectPointId);
                intent1.putExtra("collectionName", eventList.collectionName);
                intent1.putExtra(Common.COLLECT, Common.COLLECT_CHILD);//用于判断是否是采集点管理页面进入详情页
                startActivity(intent1);
            } else {
                Intent intent2 = new Intent(getActivity(), CollectionBarcode.class);
                intent2.putExtra("collectionId", eventList.collectPointId);
                intent2.putExtra("eventId", eventList.eventId);
                intent2.putExtra(Common.COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_HOMEPAGE);
                startActivity(intent2);
            }
        }
    }
}
