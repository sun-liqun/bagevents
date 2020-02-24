package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.bagevent.R;
import com.bagevent.activity_manager.detail.CollectionBarcode;
//import com.bagevent.activity_manager.manager_fragment.ExhibitorManageFragment;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.new_home.ReleaseExDynamicActivity;
import com.bagevent.new_home.adapter.ExhibitionDynamicAdapter;
import com.bagevent.new_home.data.DynamicListData;
import com.bagevent.new_home.data.ExhibitionListData;
import com.bagevent.new_home.data.ExhibitorDynamicData;
import com.bagevent.new_home.fragment.EventPandectFragment;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.gudong.loading.LoadingView;
import okhttp3.Call;
import okhttp3.Response;

import static com.bagevent.util.AppUtils.getContext;

public class NewExhibitionActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.refresh_exhibition)
    SwipeRefreshLayout refreshExhibition;
    @BindView(R.id.rv_dynamic)
    RecyclerView rvDynamic;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.loading)
    LoadingView loading;
    @BindView(R.id.iv_barcode_checkin)
    ImageView iv_barcode_checkin;
    @BindView(R.id.iv_release)
    ImageView iv_release;
    @BindView(R.id.fl_container)
    FrameLayout fl_container;

    private final byte ACTION_LAOD = 0;
    private final byte ACTION_REFRESH = 1;
    private View headerView;
    private View footerView;
    private HeaderViewBind headerViewBind;
    private FooterViewBind footerViewBind;

    private NoDataViewBind noDataViewBind;

    private ExhibitionDynamicAdapter dynamicAdapter;

    private int eventId;
    private String company;
    private String boothNo;
    private String boothHall;
    private String logo;
    private int exhibitorId;
    private int viewCount;
    private int favoriteCount;
    private int rank;
    private int attendeeCount;
    private int collectPointCount;
    private String collectionName;
    private int topCount;
    private int export;
    private int collectionId;
    private int sumNumber;
    private int sendNumber;
    private int collectPoint;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_new_exhibition);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        getHeaderView();
        getFooterView();
        headerViewBind = new HeaderViewBind(headerView);
        footerViewBind = new FooterViewBind(footerView);
        isLoading();
        getIntentValue();
        getExhibitor();
        setListener();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals("refresh_exbition_dynamic")) {
          getDynamic(ACTION_REFRESH);
        }
    }
    private void setListener() {
        refreshExhibition.setOnRefreshListener(this);
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId",0);
        exhibitorId = intent.getIntExtra("exhibitorId",0);
    }

    private void getExhibitor() {
        if (NetUtil.isConnected(getBaseContext())){
            getExhibitorInfo();
            getDynamic(ACTION_LAOD);
        }else {
            TosUtil.show(getString(R.string.check_your_net));
        }
    }

    private void getDynamic(final byte action) {
        OkHttpUtil.Post(this).url(Constants.NEW_URL+Constants.EXHIBITORCOMMENTLIST)
                .addParams("eventId", eventId+"")
                .addParams("exhibitorId",exhibitorId+"")
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
                        loadFinish();
                        if (response.contains("\"retStatus\":200")) {
                            parserSuccess(response,action);
                        } else {
                            showNoData();
                        }
                    }
                });
    }

    private void showNoData() {
        if (dynamicAdapter != null) {
            if (dynamicAdapter.getData() != null) {
                dynamicAdapter.getData().clear();
                dynamicAdapter.notifyDataSetChanged();
            }
            noDataViewBind = new NoDataViewBind(LayoutInflater.from(getContext()).inflate(R.layout.layout_no_attendee, null));
            noDataViewBind.tvPageStatus.setVisibility(View.GONE);
            noDataViewBind.ivPageStatus.setVisibility(View.VISIBLE);
            noDataViewBind.ivPageStatus.setImageResource(R.drawable.no_bg);
            dynamicAdapter.setEmptyView(noDataViewBind.itemView);
        }
    }

    private void parserSuccess(String response,byte action) {
        switch (action) {
            case ACTION_LAOD:
                parserLoadData(response);
                break;
            case ACTION_REFRESH:
                parserRefreshData(response);
                break;
        }

    }

    private void parserLoadData(String response) {
        ArrayList<ExhibitorDynamicData.DynamicList> dynamicLists=getListData(response);
        initRecyclerView(dynamicLists);
    }
    private void parserRefreshData(String response) {
        ArrayList<ExhibitorDynamicData.DynamicList> dynamicLists=getListData(response);
        refreshExhibition.setRefreshing(false);
        dynamicAdapter.setNewData(dynamicLists);
        if (dynamicLists!=null||dynamicLists.size()>0){
            footerViewBind.llPageStatus.setVisibility(View.GONE);
        }
    }


    private void initRecyclerView(ArrayList<ExhibitorDynamicData.DynamicList> dynamicLists) {
        if (dynamicLists.size()>0){
            footerViewBind.llPageStatus.setVisibility(View.GONE);
        }else {
            Glide.with(this).load(R.drawable.no_bg).into(footerViewBind.ivPageStatus);
            footerViewBind.llPageStatus.setVisibility(View.VISIBLE);
            footerViewBind.tvPageStatus.setVisibility(View.GONE);
        }
        dynamicAdapter=new ExhibitionDynamicAdapter(dynamicLists);
        dynamicAdapter.addHeaderView(headerView);
        dynamicAdapter.addFooterView(footerView);
        rvDynamic.setAdapter(dynamicAdapter);
        dynamicAdapter.setOnItemClickListener(this);
        rvDynamic.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rvDynamic.setHasFixedSize(true);
    }

    private ArrayList<ExhibitorDynamicData.DynamicList> getListData(String response) {
        ExhibitorDynamicData exhibitionListData = new ExhibitorDynamicData(new JsonParser().parse(response).getAsJsonObject());
        if (exhibitionListData.getRespObject() == null || exhibitionListData.getRespObject().getDynamicLists() == null) {
            return new ArrayList<>();
        } else {
            return exhibitionListData.getRespObject().getDynamicLists();
        }
    }

    private void getExhibitorInfo() {
        OkHttpUtil.Post(getBaseContext()).url(Constants.NEW_URL + Constants.EXHIBITORINFO)
                .addParams("eventId", eventId+"")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.show(getString(R.string.error_gain_data));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadFinish();
                        if (response.contains("\"retStatus\":200")) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boothNo = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getString("boothNo");
                                boothHall = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getString("boothHall");
                                favoriteCount = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getInt("favouriteNum");
                                viewCount = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getInt("viewCount");
                                favoriteCount = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getInt("favouriteNum");
                                rank = jsonObject.getJSONObject("respObject").getInt("rank");
                                sumNumber = jsonObject.getJSONObject("respObject").getInt("sumNumber");
                                sendNumber = jsonObject.getJSONObject("respObject").getInt("sendNumber");
                                attendeeCount = jsonObject.getJSONObject("respObject").getInt("collectAttendeeCount");
                                collectPointCount = jsonObject.getJSONObject("respObject").getJSONArray("collectPointList").length();
                                if (collectPointCount>0){
                                    export=jsonObject.getJSONObject("respObject").getJSONArray("collectPointList").getJSONObject(0).getInt("export");
                                    collectionId=jsonObject.getJSONObject("respObject").getJSONArray("collectPointList").getJSONObject(0).getInt("collectPointId");
                                    collectionName=jsonObject.getJSONObject("respObject").getJSONArray("collectPointList").getJSONObject(0).getString("collectionName");
                                }
                                company = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getJSONObject("exExhibitorInfoDTO").getString("company");
                                logo = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getJSONObject("exExhibitorInfoDTO").getString("logo");
                                setExhibitionData();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            TosUtil.show(getString(R.string.error_gain_data));
                        }
                    }
                });
    }

    private void setExhibitionData() {
        RequestOptions options=new RequestOptions().
                placeholder(R.drawable.img_loading).error(R.drawable.img_failed);
        headerViewBind.tv_company.setText(company);
        if (TextUtils.isEmpty(boothHall) && TextUtils.isEmpty(boothNo)) {
            headerViewBind.tv_boothHall.setText(R.string.no_booth_hall);
            headerViewBind.tv_boothNo.setVisibility(View.GONE);
        } else {
            headerViewBind.tv_boothHall.setText(boothHall);
            headerViewBind.tv_boothNo.setText(boothNo);
        }
        if (collectPointCount > 0) {
            headerViewBind.rl_haveAttendee.setVisibility(View.VISIBLE);
            headerViewBind.view_divide.setVisibility(View.VISIBLE);
            headerViewBind.tv_attendeeCount.setText(export + "");
            iv_barcode_checkin.setVisibility(View.VISIBLE);
        } else {
            headerViewBind.tv_my_invide.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(logo)) {
            headerViewBind.iv_company_logo.setVisibility(View.VISIBLE);
//            Glide.with(getApplication()).load("https://www.bagevent.com"+logo).apply(options).into(headerViewBind.iv_company_logo);
            Glide.with(getApplication()).load("https://img.bagevent.com"+logo).apply(options).into(headerViewBind.iv_company_logo);
        }
        headerViewBind.tv_viewCount.setText(viewCount + "");
        headerViewBind.tv_favouriteNum.setText(favoriteCount + "");
        headerViewBind.tv_rank.setText(rank + "");
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        loading.start();
        refreshExhibition.setVisibility(View.GONE);
    }

    private void loadFinish(){
        llLoading.setVisibility(View.GONE);
        loading.stop();
        refreshExhibition.setVisibility(View.VISIBLE);
    }
    private void getHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.exhibition_info, (ViewGroup) rvDynamic.getParent(), false);
    }

    private void getFooterView() {
        footerView = getLayoutInflater().inflate(R.layout.layout_page_status, (ViewGroup) rvDynamic.getParent(), false);
    }

    @Override
    public void onRefresh() {
//        getDynamic();
        getExhibitorInfo();
        getDynamic(ACTION_REFRESH);
        refreshExhibition.setRefreshing(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ExhibitorDynamicData.DynamicList dynamicList=dynamicAdapter.getData().get(position);
        if (dynamicList.getType() != 4) {
            Intent intent = new Intent(NewExhibitionActivity.this, DynamicDetailActivity.class);
            if (dynamicList.getType() == 2 || dynamicList.getType() == 1 || dynamicList.getType() == 5) {
                intent.putExtra(KeyUtil.KEY_EVENT_ID, dynamicList.getEventId());
                intent.putExtra(KeyUtil.KEY_COMMENT_ID, dynamicList.getCommentId());
                intent.putExtra(KeyUtil.KEY_TYPE,1);
            } else if (dynamicList.getType() == 3) {
//                DynamicListData.CommentList parentComment = dynamicList.getParentComment();
//                intent.putExtra(KeyUtil.KEY_EVENT_ID, parentComment.getEventId());
//                intent.putExtra(KeyUtil.KEY_COMMENT_ID, parentComment.getCommentId());
            }
           startActivity(intent);
        }
    }

    @OnClick({R.id.iv_release,R.id.iv_barcode_checkin})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_release:
                Intent intent=new Intent(NewExhibitionActivity.this,ReleaseExDynamicActivity.class);
                intent.putExtra("topCount",sumNumber-sendNumber);
                intent.putExtra("eventId",eventId);
                intent.putExtra("exhibitionId",exhibitorId);
                startActivity(intent);
                break;
            case R.id.iv_barcode_checkin:
                Intent toBarcodeCollect = new Intent(this, CollectionBarcode.class);
                toBarcodeCollect.putExtra("collectionId",collectionId);
                toBarcodeCollect.putExtra("eventId",eventId);
                toBarcodeCollect.putExtra("export",export);
//                toBarcodeCollect.putExtra("collectionName",collectionName);
                toBarcodeCollect.putExtra(Common.COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_MANAGER);
                startActivity(toBarcodeCollect);
                break;
        }
    }

    class HeaderViewBind{
        @BindView(R.id.iv_back)
        ImageView iv_back;
        @BindView(R.id.ll_view)
        AutoLinearLayout ll_view;
        @BindView(R.id.tv_exhibition_company)
        TextView tv_company;
        @BindView(R.id.tv_boothHall)
        TextView tv_boothHall;
        @BindView(R.id.tv_boothNo)
        TextView tv_boothNo;
        @BindView(R.id.tv_viewCount)
        TextView tv_viewCount;
        @BindView(R.id.tv_favouriteNum)
        TextView tv_favouriteNum;
        @BindView(R.id.tv_rank)
        TextView tv_rank;
        @BindView(R.id.view_divide)
        View view_divide;
        @BindView(R.id.rl_haveAttendee)
        RelativeLayout rl_haveAttendee;
        @BindView(R.id.tv_attendeeCount)
        TextView tv_attendeeCount;
        @BindView(R.id.tv_toCollectionPage)
        TextView tv_toCollectionPage;
        @BindView(R.id.tv_my_invide)
        TextView tv_my_invide;
        @BindView(R.id.company_logo)
        ImageView iv_company_logo;
        @BindView(R.id.btn_share)
        Button btn_share;
        View itemView;

        public HeaderViewBind(View headerView) {
            ButterKnife.bind(this, headerView);
            itemView=headerView;
        }

        @OnClick({R.id.iv_back,R.id.tv_toCollectionPage,R.id.btn_share})
        public void onViewClicked(View view) {
            switch (view.getId()){
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.btn_share:
                    Intent intent=new Intent(NewExhibitionActivity.this,CreateShareImageActivity.class);
                    intent.putExtra("eventId",eventId);
                    intent.putExtra("exhibitorId",exhibitorId);
                    startActivity(intent);
                    break;
                case R.id.tv_toCollectionPage:
                    Intent intent1=new Intent(NewExhibitionActivity.this,CollectionAttendeeActivity.class);
                    intent1.putExtra("eventId",eventId);
                    intent1.putExtra("exhibitorId",exhibitorId);
                    startActivity(intent1);
                    break;

            }
        }
    }

    class FooterViewBind{
        @BindView(R.id.iv_page_status)
        ImageView ivPageStatus;
        @BindView(R.id.tv_page_status)
        TextView tvPageStatus;
        @BindView(R.id.tv_retry)
        TextView tvRetry;
        @BindView(R.id.ll_page_status)
        AutoLinearLayout llPageStatus;
        View itemView;

        public FooterViewBind(View footerView) {
            ButterKnife.bind(this, footerView);
            itemView=footerView;
        }

        @OnClick(R.id.ll_page_status)
        public void onViewClicked() {

        }
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
