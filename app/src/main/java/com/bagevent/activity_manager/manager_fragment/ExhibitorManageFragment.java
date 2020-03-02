package com.bagevent.activity_manager.manager_fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.badoo.mobile.util.WeakHandler;
import com.bagevent.BaseFragment;
import com.bagevent.R;
import com.bagevent.activity_manager.AcManager;
import com.bagevent.activity_manager.detail.ExhibitorDetailActivity;
import com.bagevent.activity_manager.manager_fragment.adapter.ExhibitorManagerAdapter;
import com.bagevent.activity_manager.manager_fragment.data.ExhibitorData;
import com.bagevent.common.Constants;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.adapter.SelectedTagsAdapter;
import com.bagevent.new_home.new_activity.AuditExhibitorActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.google.gson.JsonParser;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;
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


public class ExhibitorManageFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, TextWatcher, FragmentBackHandler, BaseQuickAdapter.OnItemClickListener {

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
    @BindView(R.id.tag_filter)
    ImageView tag_filter;
    @BindView(R.id.spl_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.ll_search_filter)
    AutoLinearLayout llSearchFilter;
    @BindView(R.id.ll_view_search)
    AutoLinearLayout llViewSearch;
    @BindView(R.id.tv_result)
    TextView tvResult;
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
    @BindView(R.id.fm_exhibitor)
    AutoFrameLayout fmExhibitor;
    @BindView(R.id.v_transparent)
    View vTransparent;
    @BindView(R.id.rv_search_exhibitor)
    RecyclerView rvSearchExhibitor;
    @BindView(R.id.tv_audit_count)
    TextView tvAudit;
    @BindView(R.id.select_tags)
    AutoLinearLayout selectTags;
    @BindView(R.id.rv_select_tag)
    RecyclerView rvSelectTag;


    private View view;

    private NoDataViewBind noDataViewBind;
    private ExhibitorManagerAdapter exhibitorManagerAdapter;
    private int eventId;

    private String condition = "";
    private String startTime = "";
    private String search = "";
    private int page;
    private int pageSize = 20;
    private int auditCount = 0;


    private final byte ACTION_LAOD = 0;
    private final byte ACTION_REFRESH = 1;
    private final byte ACTION_LOADMORE = 2;

    private boolean isSearch = false;
    private String result = "";//搜索关键词
    private InputMethodManager inputMethodManager;//软键盘
    private ArrayList<ExhibitorData.ExhibitorList> searchFData = new ArrayList<>();
    private ArrayList<String> selectStatus=new ArrayList<>();
    private SelectedTagsAdapter selectedTagsAdapter;
    private ArrayList<String> selectStatusId=new ArrayList<>();
    private String status="";
    private int type=-1;
    private int filter=-1;

    ArrayList<ExhibitorData.ExhibitorList> showExhibitorList=new ArrayList<>();

    @OnClick({R.id.ll_right_click, R.id.tag_filter, R.id.ll_title_back, R.id.ll_search, R.id.iv_search_clear, R.id.btn_search_cancle, R.id.tv_audit_count})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tag_filter:
                filter=0;
                ((AcManager) getActivity()).showRightMenu();
                break;
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
                if (isSearch){
                    isSearch = false;
                    rvSearchExhibitor.setVisibility(View.GONE);
                }
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
                isSearch=false;
                searchFData.clear();
                rvSearchExhibitor.setVisibility(View.GONE);
                etSearch.setText("");
                refreshLayout.setEnabled(true);
                vTransparent.setVisibility(View.GONE);
                llViewSearch.setVisibility(View.GONE);
                llSearchFilter.setVisibility(View.VISIBLE);
                llCommonTitle.setVisibility(View.VISIBLE);
                fmExhibitor.setVisibility(View.VISIBLE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                }
                break;
            case R.id.tv_audit_count:
                Intent intent = new Intent(getActivity(), AuditExhibitorActivity.class);
                intent.putExtra("eventId", eventId);
                intent.putExtra("auditCount", auditCount);
                startActivity(intent);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.exhibitor_manager, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        eventId = bundle.getInt("eventId");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        llViewSearch.setVisibility(View.GONE);
        initView();
        initData(1,false);
        initAuditData(ACTION_LAOD);
        isLoading();
        setListener();
    }

    public void initAuditData(final byte action) {
        if (!NetUtil.isConnected(getActivity())) {
            TosUtil.show(getString(R.string.check_network));
            return;
        }

        OkHttpUtil.Get(getContext())
                .url(Constants.NEW_URL + Constants.QUERY_EXHIBITORS)
                .addParams("eventId", eventId + "")
                .addParams("pagingPage", page + "")
                .addParams("audit", "0")
                .addParams("condition","")
                .addParams("startTime", startTime)
                .addParams("search", result)
                .tag("ExhibitorManageQuery")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("auditExhibitor请求取消");
                        }else {
                            showNoAudit();
                        }

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadingFinished();
                        if (response.contains("\"retStatus\":200")) {
                            parserAuditSuccess(response, action);
                        } else {
                            showNoAudit();
                        }
                    }
                });

    }

    private void initSelectedStatus() {
        selectTags.setVisibility(View.VISIBLE);
        selectedTagsAdapter=new SelectedTagsAdapter(selectStatus,getContext());
        selectedTagsAdapter.setHasStableIds(true);
        rvSelectTag.setAdapter(selectedTagsAdapter);
        rvSelectTag.setLayoutManager(new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL,false));
        selectedTagsAdapter.setListener(new SelectedTagsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                selectStatus.remove(position);
                selectStatusId.remove(position);
                if (selectStatus.size()==0||selectStatusId.size()==0){
                    selectTags.setVisibility(View.GONE);
                }
                if (selectStatusId.size()>0){
                    if (selectStatusId.size()==1){
                        status=selectStatusId.get(0);
                    }else {
                        for (int i = 0; i < selectStatusId.size() ; i++) {
                            status=status+selectStatusId.get(i);
                            if (i!=selectStatusId.size()-1){
                                status+=",";
                            }
                        }
                    }
                }else {
                    status="";
                }
                initData(0,false);
                selectedTagsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event.getAction()==MessageAction.ACTION_SELECT_STATUS){
            selectStatus= (ArrayList<String>) event.getValue();
        }else if (event.getAction()==MessageAction.ACTION_SELECT_STATUS_ID){
            selectStatusId= (ArrayList<String>) event.getValue();
        }else if (event.getAction()==MessageAction.REGRESH_EXHIBITOR_AUDIT){
            new WeakHandler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    page=1;
                    loadExhibitionFromServer(ACTION_REFRESH, "1,2", false);
                    initAuditData(ACTION_REFRESH);
                }
            }, 1000);
        }
        if (selectStatus.size()>0&&selectStatusId.size()>0){
            initSelectedStatus();
        }
    }

    private void initView() {
        tvTitle.setText(R.string.text_exhibitor_manager);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        etSearch.addTextChangedListener(this);
        selectTags.setVisibility(View.GONE);
    }

    public void initData(int i,boolean isSearch) {
        type=i;
        page = 1;
        loadExhibitionFromServer(ACTION_LAOD, "1,2", isSearch);
    }

    private void loadExhibitionFromServer(final byte action, String audit, final boolean isSearch) {
        if (!NetUtil.isConnected(getActivity())) {
            TosUtil.show(getString(R.string.check_network1));
            return;
        }

        OkHttpUtil.Get(getActivity())
                .url(Constants.NEW_URL + Constants.QUERY_EXHIBITORS)
                .addParams("eventId", eventId + "")
                .addParams("pagingPage", page + "")
                .addParams("audit", audit)
                .addParams("condition",type==0?status:((AcManager) getActivity()).getSelectStatus())
                .addParams("startTime", startTime)
                .addParams("search", result)
                .tag("ExhibitorManageQuery")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("queryExhibitor请求取消");
                            return;
                        }else {
                            showNoData();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadingFinished();
                        if (response.contains("\"retStatus\":200")) {
                            parserSuccess(response, action, isSearch);
                        } else {
                           showNoData();
                        }
                    }
                });

    }

    private void parserAuditSuccess(String response, byte action) {
        switch (action) {
            case ACTION_LAOD:
                parserLoadAuditData(response);
                break;
            case ACTION_REFRESH:
                parserRefreshAuditData(response);
                break;
        }
    }

    private void parserRefreshAuditData(String response) {
        ArrayList<ExhibitorData.ExhibitorList> exhibitorData = getListData(response);
        refreshLayout.setRefreshing(false);
        if (exhibitorData.size()>0){
            tvAudit.setVisibility(View.VISIBLE);
            tvAudit.setText(getString(R.string.audit_exhibitor) + String.valueOf(exhibitorData.size()));
        }else {
            tvAudit.setVisibility(View.GONE);
        }
    }

    private void parserLoadAuditData(String response) {
        ArrayList<ExhibitorData.ExhibitorList> exhibitorData = getListData(response);
        int attendeeSize = exhibitorData.size();
        if (attendeeSize>0){
            tvAudit.setVisibility(View.VISIBLE);
            tvAudit.setText(getString(R.string.audit_exhibitor) + String.valueOf(attendeeSize));
        }else {
            tvAudit.setVisibility(View.GONE);
        }
    }

    private void showNoAudit() {
        tvAudit.setVisibility(View.GONE);
    }

    private void parserSuccess(String response, byte action, boolean isSearch) {
        switch (action) {
            case ACTION_LAOD:
                parserLoadData(response, isSearch);
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
            }else {
                initRecyclerView(searchFData);
            }
        } else {
            ArrayList<ExhibitorData.ExhibitorList> exhibitorData = getListData(response);
            int attendeeSize = exhibitorData.size();
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
        if (exhibitorData == null) {
            showNoData();
        } else {
            page++;
        }
    }

    private void initRecyclerView(final ArrayList<ExhibitorData.ExhibitorList> exhibitorData) {
        if (isSearch) {
            rvSearchExhibitor.setVisibility(View.VISIBLE);
            vTransparent.setBackgroundColor(Color.parseColor("#ffffff"));
            exhibitorManagerAdapter = new ExhibitorManagerAdapter(exhibitorData);
            exhibitorManagerAdapter.setHasStableIds(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rvSearchExhibitor.setLayoutManager(linearLayoutManager);
//            exhibitorManagerAdapter.setOnItemClickListener(this);
            exhibitorManagerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                    intentDetail(position);
                    ExhibitorData.ExhibitorList exhibitorList = exhibitorManagerAdapter.getData().get(position);
//                    int exhibitor_id=exhibitorData.get(position).getExhibitor_id();
                    int exhibitor_id=exhibitorList.getExhibitor_id();
                    int audit=exhibitorData.get(position).getAudit();
                    Intent intent = new Intent(getActivity(), ExhibitorDetailActivity.class);
                    intent.putExtra("eventId", eventId);
                    intent.putExtra("exhibitorId", exhibitor_id);
                    intent.putExtra("audit",-1);
                    startActivity(intent);
                }
            });
            rvSearchExhibitor.setAdapter(exhibitorManagerAdapter);
        } else if (!isSearch) {
            rvExhibitor.setVisibility(View.VISIBLE);
            exhibitorManagerAdapter = new ExhibitorManagerAdapter(exhibitorData);
            exhibitorManagerAdapter.setHasStableIds(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rvExhibitor.setLayoutManager(linearLayoutManager);
//            exhibitorManagerAdapter.setOnItemClickListener(this);
            rvExhibitor.setAdapter(exhibitorManagerAdapter);
            exhibitorManagerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                    int exhibitor_id=exhibitorData.get(position).getExhibitor_id();
//                    int audit=exhibitorData.get(position).getAudit();
                    ExhibitorData.ExhibitorList exhibitorList = exhibitorManagerAdapter.getData().get(position);
                    int exhibitor_id=exhibitorList.getExhibitor_id();
                    int audit=exhibitorList.getAudit();
                    Intent intent = new Intent(getActivity(), ExhibitorDetailActivity.class);
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
                    loadExhibitionFromServer(ACTION_REFRESH, "1,2", false);
                    initAuditData(ACTION_REFRESH);
                }
            });

            exhibitorManagerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    loadExhibitionFromServer(ACTION_LOADMORE, "1,2", false);
                }
            }, rvExhibitor);
        }

    }

//    private ArrayList<ExhibitorData.ExhibitorList> getListData(String response) {
//        CrashReport.postCatchedException(new Throwable("eventId:" + eventId + "pagingPage:"
//                + String.valueOf(page) + "audit:" + "0" + "startTime:" + startTime + "search:" + result + "结果" + response));
//        showExhibitorList.clear();
//
//        ExhibitorData exhibitorData =
//                new ExhibitorData(new JsonParser().parse(response).getAsJsonObject());
//
//        ArrayList<ExhibitorData.ExhibitorList> exhibitorList = exhibitorData.getRespObject().getExhibitorList();
//        if (exhibitorData.getRespObject() == null || exhibitorList == null) {
//            return new ArrayList<>();
//        } else {
//            return exhibitorList;
//        }
//    }

    private ArrayList<ExhibitorData.ExhibitorList> getListData(String response) {
        showExhibitorList.clear();
        ExhibitorData exhibitorData =
                new ExhibitorData(new JsonParser().parse(response).getAsJsonObject());
        if (null!=exhibitorData){
            if (null!=exhibitorData.getRespObject()){
                ArrayList<ExhibitorData.ExhibitorList> exhibitorList = exhibitorData.getRespObject().getExhibitorList();
                if (null!=exhibitorList){
                    return exhibitorList;
                }
            }else {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    private void showNoData() {
        if (exhibitorManagerAdapter != null) {
            if (exhibitorManagerAdapter.getData() != null) {
                exhibitorManagerAdapter.getData().clear();
                exhibitorManagerAdapter.notifyDataSetChanged();
            }
            noDataViewBind = new NoDataViewBind(LayoutInflater.from(getContext()).inflate(R.layout.layout_no_attendee, null));
            if (isSearch) {
                vTransparent.setVisibility(View.VISIBLE);
                llSearchFilter.setVisibility(View.VISIBLE);
                vTransparent.setBackgroundColor(Color.parseColor("#50000000"));
                noDataViewBind.ivPageStatus.setVisibility(View.GONE);
                noDataViewBind.tvPageStatus.setVisibility(View.VISIBLE);
                noDataViewBind.tvPageStatus.setText(R.string.no_result);
            } else {
                noDataViewBind.tvPageStatus.setText(R.string.no_exhibitor);
            }
            exhibitorManagerAdapter.setEmptyView(noDataViewBind.itemView);
        }
    }

    private void setListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (!TextUtils.isEmpty(etSearch.getText().toString())) {
                    searchData();
                }
                return true;
            }
        });
    }

    private void searchData() {
        page = 1;
        if (!NetUtil.isConnected(getContext())) {
            TosUtil.show(getString(R.string.check_network1));
            return;
        }
        isSearch = true;
        result = etSearch.getText().toString().trim();
        loadExhibitionFromServer(ACTION_LAOD, "0,1,2",true);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View view) {

    }

    private void loadingFinished() {
        llLoading.setVisibility(View.GONE);
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onBackPressed() {
        return false;
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
//        initentDetail(position);
    }

    private void intentDetail(int position) {
        ExhibitorData.ExhibitorList exhibitorList = exhibitorManagerAdapter.getData().get(position);
        int exhibitor_id = exhibitorList.getExhibitor_id();
        Intent intent = new Intent(getActivity(), ExhibitorDetailActivity.class);
        intent.putExtra("eventId", eventId);
        intent.putExtra("exhibitorId", exhibitor_id);
        intent.putExtra("audit",exhibitorList.getAudit());
        startActivity(intent);
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
    public void onDestroyView() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        OkHttpUtils.getInstance().cancelTag("ExhibitorManageQuery");
        super.onDestroyView();
    }

}
