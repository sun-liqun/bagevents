package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.AcManager;
import com.bagevent.activity_manager.detail.ExhibitorDetailActivity;
//import com.bagevent.activity_manager.manager_fragment.ExhibitorManageFragment;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.adapter.ExhibitorManagerAdapter;
import com.bagevent.activity_manager.manager_fragment.data.ExhibitorData;
import com.bagevent.common.Constants;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonParser;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

public class AuditExhibitorActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener,View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, TextWatcher{

    @BindView(R.id.ll_common_title)
    AutoLinearLayout llCommonTitle;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.rv_exhibitor)
    RecyclerView rvExhibitor;
    Unbinder unbinder;
    @BindView(R.id.spl_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.ll_view_search)
    AutoLinearLayout llViewSearch;
    @BindView(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @BindView(R.id.btn_search_cancle)
    Button btnSearchCancle;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.fl_search)
    AutoFrameLayout flSearch;
    @BindView(R.id.fm_exhibitor)
    AutoFrameLayout fmExhibitor;
    @BindView(R.id.v_transparent)
    View vTransparent;
    @BindView(R.id.rv_search_exhibitor)
    RecyclerView rvSearchExhibitor;

    private int eventId;
    private int auditCount;
    private String audit = "";
    private String condition = "";
    private String startTime = "";
    private String result = "";
    private int page;
    private int pageSize = 20;
    private boolean isSearch = false;

    private final byte ACTION_LAOD = 0;
    private final byte ACTION_REFRESH = 1;
    private final byte ACTION_LOADMORE = 2;

    private NoDataViewBind noDataViewBind;
    private ExhibitorManagerAdapter exhibitorManagerAdapter;
    private InputMethodManager inputMethodManager;//软键盘
    ArrayList<ExhibitorData.ExhibitorList> searchFData = new ArrayList<>();

    @OnClick({R.id.ll_right_click, R.id.ll_title_back, R.id.ll_search, R.id.iv_search_clear, R.id.btn_search_cancle})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_search:
                isSearch = true;
                etSearch.requestFocus();
                etSearch.setFocusable(true);
                vTransparent.setVisibility(View.VISIBLE);
                vTransparent.setBackgroundColor(Color.parseColor("#50000000"));
                tvResult.setVisibility(View.GONE);
                llCommonTitle.setVisibility(View.GONE);
                llViewSearch.setVisibility(View.VISIBLE);
                inputMethodManager.showSoftInput(etSearch, 0);
                refreshLayout.setEnabled(false);
                if (exhibitorManagerAdapter != null) {
                    exhibitorManagerAdapter.setEnableLoadMore(false);
                }
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                if (isSearch) {
                    isSearch = false;
                    rvSearchExhibitor.setVisibility(View.GONE);
                }
                rvExhibitor.setVisibility(View.VISIBLE);
                vTransparent.setVisibility(View.VISIBLE);
                vTransparent.setBackgroundColor(Color.parseColor("#50000000"));
                if (searchFData.size() > 0) {
                    tvResult.setVisibility(View.GONE);
                } else {
                    tvResult.setVisibility(View.VISIBLE);
                }
                ivSearchClear.setVisibility(View.GONE);
                break;
            case R.id.btn_search_cancle:
                searchFData.clear();
                rvSearchExhibitor.setVisibility(View.GONE);
                rvExhibitor.setVisibility(View.VISIBLE);
                etSearch.setText("");
                refreshLayout.setEnabled(true);
                vTransparent.setVisibility(View.GONE);
                llViewSearch.setVisibility(View.GONE);
                llCommonTitle.setVisibility(View.VISIBLE);
                fmExhibitor.setVisibility(View.VISIBLE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                }
                break;
        }
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_audit_exhibitor);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        inputMethodManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        initView();
        getIntentValue();
        initData();
        setListener();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event.getAction()==MessageAction.REGRESH_EXHIBITOR_AUDIT) {
            isSearch=false;
            vTransparent.setVisibility(View.GONE);
            llViewSearch.setVisibility(View.GONE);
            llCommonTitle.setVisibility(View.VISIBLE);
            fmExhibitor.setVisibility(View.VISIBLE);
            rvSearchExhibitor.setVisibility(View.GONE);
            page=1;
            result="";
            loadAuditExhibitor(ACTION_LAOD, false);
        }
    }
    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        llViewSearch.setVisibility(View.GONE);
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", eventId);
        auditCount = intent.getIntExtra("auditCount", auditCount);
        tvTitle.setText(getString(R.string.audit_exhibitor1) + "(" + auditCount + ")");
    }

    private void initData() {
        loadAuditExhibitor(ACTION_LAOD, false);
    }

    private void loadAuditExhibitor(final byte action, final boolean isSearch) {
        if (!NetUtil.isConnected(this)) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Get(this)
                .url(Constants.NEW_URL + Constants.QUERY_EXHIBITORS)
                .addParams("eventId", eventId + "")
                .addParams("pagingPage", page + "")
                .addParams("audit", "0")
                .addParams("condition", condition)
                .addParams("startTime", startTime)
                .addParams("search", result)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showNoData();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadingFinished();
                        if (response.contains("\"retStatus\":200")) {
                            parserSuccess(response, action,isSearch);
                        } else {
                            showNoData();
                        }
                    }
                });
    }


    private void parserSuccess(String response, byte action, boolean isSearch) {
        switch (action) {
            case ACTION_LAOD:
                parserLoadData(response,isSearch);
                break;
            case ACTION_LOADMORE:
                parserLoadMoreData(response);
                break;
            case ACTION_REFRESH:
                parserRefreshData(response);
                break;
        }
    }

    private void parserLoadData(String response, boolean isSearch) {
        if (isSearch) {
            searchFData = getListData(response);
            if (searchFData.size() == 0) {
                rvSearchExhibitor.setVisibility(View.GONE);
                vTransparent.setVisibility(View.VISIBLE);
                vTransparent.setBackgroundColor(Color.parseColor("#50000000"));
                tvResult.setVisibility(View.VISIBLE);
                tvResult.setText(R.string.no_result);
            }
            initRecyclerView(searchFData);
        } else {
            ArrayList<ExhibitorData.ExhibitorList> exhibitorData = getListData(response);
            int attendeeSize = exhibitorData.size();
            tvTitle.setText(getString(R.string.audit_exhibitor1) + "(" + attendeeSize + ")");
            initRecyclerView(exhibitorData);
            if (attendeeSize == 0) {
                showNoData();
            } else {
                page++;
            }
        }
    }

    private void parserLoadMoreData(String response) {
        ArrayList<ExhibitorData.ExhibitorList> exhibitorLists = getListData(response);
        if (exhibitorLists == null) {
            exhibitorManagerAdapter.loadMoreFail();
            return;
        } else {
            page++;
        }
        if (exhibitorLists.size() < pageSize) {
            exhibitorManagerAdapter.loadMoreComplete();
            exhibitorManagerAdapter.loadMoreEnd();
        } else {
            exhibitorManagerAdapter.loadMoreComplete();
        }
        exhibitorManagerAdapter.addData(exhibitorLists);
    }

    private void parserRefreshData(String response) {
        ArrayList<ExhibitorData.ExhibitorList> exhibitorData = getListData(response);
        refreshLayout.setRefreshing(false);
        exhibitorManagerAdapter.setNewData(exhibitorData);
        if (exhibitorData.size()==0) {
            showNoData();
        } else {
            page++;
            rvExhibitor.setVisibility(View.VISIBLE);
        }
        tvTitle.setText(getString(R.string.audit_exhibitor1) + "(" + exhibitorData.size() + ")");
    }

    private void initRecyclerView(final ArrayList<ExhibitorData.ExhibitorList> exhibitorData) {
        if (isSearch && exhibitorData.size() > 0) {
            rvSearchExhibitor.setVisibility(View.VISIBLE);
            rvExhibitor.setVisibility(View.GONE);
            vTransparent.setBackgroundColor(Color.parseColor("#ffffff"));
            exhibitorManagerAdapter = new ExhibitorManagerAdapter(exhibitorData);
            exhibitorManagerAdapter.setHasStableIds(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rvSearchExhibitor.setLayoutManager(linearLayoutManager);
            exhibitorManagerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    int exhibitor_id=exhibitorData.get(position).getExhibitor_id();
                    int audit=exhibitorData.get(position).getAudit();
                    Intent intent = new Intent(AuditExhibitorActivity.this, ExhibitorDetailActivity.class);
                    intent.putExtra("eventId", eventId);
                    intent.putExtra("exhibitorId", exhibitor_id);
                    intent.putExtra("audit",audit);
                    startActivity(intent);
//
                }
            });
            rvSearchExhibitor.setAdapter(exhibitorManagerAdapter);
        } else if (!isSearch) {
            rvExhibitor.setVisibility(View.VISIBLE);
            rvSearchExhibitor.setVisibility(View.GONE);
            exhibitorManagerAdapter = new ExhibitorManagerAdapter(exhibitorData);
            exhibitorManagerAdapter.setHasStableIds(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rvExhibitor.setLayoutManager(linearLayoutManager);
//            exhibitorManagerAdapter.setOnItemClickListener(this);
            rvExhibitor.setAdapter(exhibitorManagerAdapter);
            exhibitorManagerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ExhibitorData.ExhibitorList exhibitorList = exhibitorManagerAdapter.getData().get(position);
//                    int exhibitor_id=exhibitorData.get(position).getExhibitor_id();
//                    int audit=exhibitorData.get(position).getAudit();
                    int exhibitor_id=exhibitorList.getExhibitor_id();
                    int audit=exhibitorList.getAudit();
                    Intent intent = new Intent(AuditExhibitorActivity.this, ExhibitorDetailActivity.class);
                    intent.putExtra("eventId", eventId);
                    intent.putExtra("exhibitorId", exhibitor_id);
                    intent.putExtra("audit",audit);
                    startActivity(intent);
                }
            });
//        exhibitorManagerAdapter.setItemClickListener(this);
//        attendeeTagAdapter.setTagClickListener(this);

            if (exhibitorData.size() < 20) {
                exhibitorManagerAdapter.loadMoreEnd();
            }

            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    page = 1;
                    result="";
                    isSearch=false;
                    loadAuditExhibitor(ACTION_REFRESH, false);
                }
            });

//            exhibitorManagerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//                @Override
//                public void onLoadMoreRequested() {
//                    loadAuditExhibitor(ACTION_LOADMORE, false);
//                    Log.i("-----------","2");
//                }
//            }, rvExhibitor);
        }

    }

    private ArrayList<ExhibitorData.ExhibitorList> getListData(String response) {
        ExhibitorData exhibitorData =
                new ExhibitorData(new JsonParser().parse(response).getAsJsonObject());

        ArrayList<ExhibitorData.ExhibitorList> exhibitorList = exhibitorData.getRespObject().getExhibitorList();
        if (exhibitorData.getRespObject() == null || exhibitorList == null) {
            return new ArrayList<>();
        } else {
            return exhibitorList;
        }
    }



    private void loadingFinished() {
        llLoading.setVisibility(View.GONE);
    }

    private void showNoData() {
        if (exhibitorManagerAdapter != null) {
            if (exhibitorManagerAdapter.getData() != null) {
                exhibitorManagerAdapter.getData().clear();
                exhibitorManagerAdapter.notifyDataSetChanged();
            }
            noDataViewBind = new NoDataViewBind(LayoutInflater.from(this).inflate(R.layout.layout_no_attendee, null));
            if (isSearch) {
                vTransparent.setVisibility(View.VISIBLE);
                vTransparent.setBackgroundColor(Color.parseColor("#50000000"));
                noDataViewBind.ivPageStatus.setVisibility(View.GONE);
                noDataViewBind.tvPageStatus.setVisibility(View.VISIBLE);
                noDataViewBind.tvPageStatus.setText(R.string.no_result);
//                tvResult.setVisibility(View.VISIBLE);
//                tvResult.setText("无结果");
            } else {
                noDataViewBind.tvPageStatus.setText(R.string.no_exhibitor);
            }

            exhibitorManagerAdapter.setEmptyView(noDataViewBind.itemView);
        }
    }

    private void setListener() {
        etSearch.addTextChangedListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (!TextUtils.isEmpty(etSearch.getText().toString())) {
                    searchData();
                }

//              InputMethodUtil.showOrHide(getContext());
                return true;
            }
        });
    }

    private void searchData() {
        page = 1;
        if (!NetUtil.isConnected(this)) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }
        isSearch = true;
        result = etSearch.getText().toString().trim();
        loadAuditExhibitor(ACTION_LAOD, true);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View view) {

    }



    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        result = editable.toString();
        if (!TextUtils.isEmpty(result)) {
            ivSearchClear.setVisibility(View.VISIBLE);
        } else {
            ivSearchClear.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        ExhibitorData.ExhibitorList exhibitorList = exhibitorManagerAdapter.getData().get(position);
//        int exhibitor_id = exhibitorList.getExhibitor_id();
//        Intent intent = new Intent(AuditExhibitorActivity.this, ExhibitorDetailActivity.class);
//        intent.putExtra("eventId", eventId);
//        intent.putExtra("exhibitorId", exhibitor_id);
//        intent.putExtra("audit",exhibitorList.getAudit());
//        startActivity(intent);
    }



    class NoDataViewBind {
        @BindView(R.id.iv_page_status)
        ImageView ivPageStatus;
        @BindView(R.id.tv_page_status)
        TextView tvPageStatus;
        @BindView(R.id.ll_page_status)
        AutoLinearLayout llPageStatus;
        View itemView;

        public NoDataViewBind(View view) {
            ButterKnife.bind(this, view);
            itemView = view;
        }

        @OnClick(R.id.ll_page_status)
        public void onClicked() {
//            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
