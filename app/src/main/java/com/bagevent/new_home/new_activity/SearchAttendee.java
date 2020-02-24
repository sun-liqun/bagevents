package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.new_home.adapter.AttendeeAdapter;
import com.bagevent.new_home.adapter.AttendeeTagAdapter;
import com.bagevent.new_home.data.CollectionAttendeeData;
import com.bagevent.util.AppManager;
import com.bagevent.util.InputMethodUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonParser;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class SearchAttendee extends BaseActivity implements AttendeeAdapter.OnItemClickListener {


    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.rv_attendee)
    RecyclerView rvAttendee;
    @BindView(R.id.ll_loading)
    AutoLinearLayout loading;
    private AttendeeAdapter attendeeAdapter;
    private String userId;
    private int  exhibitorId;
    private int eventId;
    private int page;
    private String search;
    private List<Integer> exhibitorAttendeeId=new ArrayList<>();

    private final byte ACTION_LAOD = 0;
    private final byte ACTION_REFRESH = 1;
    private final byte ACTION_LOADMORE = 2;

    @OnClick({R.id.btn_search_cancle,R.id.et_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_search_cancle:
                AppManager.getAppManager().finishActivity();
                break;
//            case R.id.et_search:
//
//                break;
        }
    }
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_attendee);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        intView();
        getIntentValue();
        searchListener();
    }

    private void intView() {
        tvPageStatus.setText(getString(R.string.no_search_attendee));
        loading.setVisibility(View.GONE);
    }


    private void getIntentValue() {
        userId=SharedPreferencesUtil.get(this,"userId","");
        Intent intent=getIntent();
        exhibitorId=intent.getIntExtra("exhibitorId",0);
        eventId=intent.getIntExtra("eventId",0);
    }
    private void searchListener() {
        page=1;
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchData(ACTION_LAOD);
                loading.setVisibility(View.VISIBLE);
                llPageStatus.setVisibility(View.GONE);
                InputMethodUtil.showOrHide(getBaseContext());
                return true;
            }
        });
    }

    private void searchData(final byte action) {
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_network));
            return;
        }

        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL+Constants.ATTENDEELIST)
                .addParams("userId",userId)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .addParams("page",page+"")
                .addParams("search",etSearch.getText().toString().trim())
                .build()
                .writeTimeOut(5000)
                .connTimeOut(5000)
                .readTimeOut(5000)
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        String result = response.body().string();
                        return result;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        parserError(action);
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if (response.contains("\"retStatus\":200")) {
                            parserSuccess(response, action);
                        } else {
//                            parserError(action);
                        }
                    }
                });
    }

    private void parserSuccess(String response, byte action) {
        loading.setVisibility(View.GONE);

        switch (action) {
            case ACTION_LAOD:
                parserLoadData(response);
                break;
            case ACTION_LOADMORE:
//                exhibitorAttendeeId.removeAll(exhibitorAttendeeId);
//                parserLoadMoreData(response);
                break;
            case ACTION_REFRESH:
//                exhibitorAttendeeId.removeAll(exhibitorAttendeeId);
//                parserRefreshData(response);
                break;
        }
    }

    private void parserLoadData(String response) {
        ArrayList<CollectionAttendeeData.CollectionList> attendeeData=getListData(response);
        initRecyclerview(attendeeData);
        int attendeeSize=attendeeData.size();
        if (attendeeSize == 0) {
           llPageStatus.setVisibility(View.VISIBLE);
        } else {
            page++;
            for (int i = 0; i < attendeeSize ; i++) {
                if (attendeeSize==1){
                    exhibitorAttendeeId.add(attendeeData.get(0).getExhibitorAttendeeId());
                }else {
                    exhibitorAttendeeId.add(attendeeData.get(i).getExhibitorAttendeeId());
                }
            }
        }
    }
    private void initRecyclerview(ArrayList<CollectionAttendeeData.CollectionList> attendeeData) {

        rvAttendee.setVisibility(View.VISIBLE);
        attendeeAdapter=new AttendeeAdapter(attendeeData);
        attendeeAdapter.setHasStableIds(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvAttendee.setLayoutManager(linearLayoutManager);
        rvAttendee.setAdapter(attendeeAdapter);
        attendeeAdapter.setOnItemClickListener(this);

        if (attendeeData.size()<20){
            attendeeAdapter.loadMoreEnd();
        }

//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                page=1;
//                loadAttendeeFromServer(ACTION_REFRESH);
//            }
//        });

//        attendeeTagAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                loadAttendeeFromServer(ACTION_LOADMORE);
//            }
//        },rvAttendee);
    }

    private ArrayList<CollectionAttendeeData.CollectionList> getListData(String response) {
        CollectionAttendeeData attendeeData=
                new CollectionAttendeeData(new JsonParser().parse(response).getAsJsonObject());
        if (attendeeData.getRespObject()==null||attendeeData.getRespObject().getCollectionLists()==null){
            return new ArrayList<>();
        }else {

            return attendeeData.getRespObject().getCollectionLists();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent=new Intent(this,AttendeeDetailActivity.class);
        intent.putExtra("exhibitorId",exhibitorId);
        intent.putExtra("eventId",eventId);
        intent.putExtra("exhibitorAttendeeId",exhibitorAttendeeId.get(position));
        startActivity(intent);
    }
}
