package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.new_home.adapter.TagOfManageAdapter;
import com.bagevent.new_home.data.TagsData;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonParser;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.bagevent.util.AppUtils.getContext;

public class TagManager extends BaseActivity implements TagOfManageAdapter.OnItemClickListener,
        TagOfManageAdapter.OnItemRemoveClickListener {

    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightTitle;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.rv_tags)
    RecyclerView rvTags;
    @BindView(R.id.sfl_tag)
    SwipeRefreshLayout refreshLayout;

    private String userId;
    private int exhibitorId;
    private int eventId;
    private final byte ACTION_LAOD = 0;
    private final byte ACTION_REFRESH = 1;
    private TagOfManageAdapter tagAdapter;
    private NoDataViewBind noDataViewBind;
    private ArrayList<TagsData.TagDataList> tagDataLists;

    private boolean isClear=false;
    private int removePosition;
    private int removeTagId;

    @OnClick({R.id.ll_title_back,R.id.ll_right_click})
    public void click(View view){
        switch (view.getId()){
            case R.id.ll_title_back:
                finish();
                break;
            case R.id.ll_right_click:
                Intent intent=new Intent(this,AddTag.class);
                intent.putExtra("exhibitorId",exhibitorId);
                intent.putExtra("eventId",eventId);
                startActivity(intent);
                break;
        }
    }
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tag_manager);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        getIntentValue();
        loadTagData(ACTION_LAOD);
    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.tag_manage);
        tvRightTitle.setText(R.string.add_tag);
    }

    private void getIntentValue() {
        Intent intent=getIntent();
        userId=SharedPreferencesUtil.get(this,"userId","");
        eventId=intent.getIntExtra("eventId",0);
        exhibitorId=intent.getIntExtra("exhibitorId",0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MsgEvent messageEvent) {
        if (messageEvent.mMsg.equals("refresh_tag")) {
           loadTagData(ACTION_REFRESH);
        }
    }
    private void loadTagData(final byte action) {
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Post(getBaseContext())
                .url(Constants.NEW_URL+Constants.EXHIBITORTAGS)
                .addParams("userId",userId)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
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
                        parserError(action);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            parserSuccess(response, action);
                        } else {
                            parserError(action);
                        }
                    }
                });
    }

    private void parserSuccess(String response, byte action) {
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
        tagDataLists= getListData(response);
        initRecyclerView(tagDataLists);
        if (tagDataLists==null||tagDataLists.size()==0){
            showNoData();
        }
    }

    private ArrayList<TagsData.TagDataList> getListData(String response) {
        TagsData tagsData=
                new TagsData(new JsonParser().parse(response).getAsJsonObject());
        if (tagsData.getRespObject()==null||tagsData.getRespObject().getTagDataLists()==null){
            return new ArrayList<>();
        }else {
            return tagsData.getRespObject().getTagDataLists();
        }
    }

    private void initRecyclerView(ArrayList<TagsData.TagDataList> tagDataLists) {
        tagAdapter=new TagOfManageAdapter(tagDataLists);
        tagAdapter.setHasStableIds(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager linearLayoutManager =
                new RecyclerViewNoBugLinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvTags.setLayoutManager(linearLayoutManager);
        rvTags.setAdapter(tagAdapter);
        tagAdapter.setItemClickListener(this);
        tagAdapter.setItemRemoveListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTagData(ACTION_REFRESH);
            }
        });
    }

    private void parserRefreshData(String response) {
        tagDataLists= getListData(response);
        refreshLayout.setRefreshing(false);
        if (tagDataLists==null||tagDataLists.size()==0){
            showNoData();
        }
        tagAdapter.setNewData(tagDataLists);
    }



    private void parserError(byte action) {
         showNoData();
    }

    @Override
    public void onItemClick(View view, int position) {
        TagsData.TagDataList tagDataList= tagAdapter.getData().get(position);
        Intent intent =new Intent(TagManager.this,SetTag.class);
        intent.putExtra("exhibitorId",exhibitorId);
        intent.putExtra("eventId",eventId);
        intent.putExtra("tagId",tagDataList.getTagId());
        intent.putExtra("tagName",tagDataList.getName());
        startActivity(intent);
    }

    @Override
    public void onItemRemoveClick(final int position) {
        int tagId = tagAdapter.getData().get(position).getTagId();

        removePosition = position;
        removeTagId = tagId;

        if (!NetUtil.isConnected(this)){
            return;
        }

        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL+Constants.DELETE_TAG_AND_ATTENDEE)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .addParams("tagId",removeTagId+"")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(TagManager.this, R.string.error_delete_tag, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")){
                            tagDataLists.remove(removePosition);
                            tagAdapter.notifyItemRemoved(removePosition);
//                            loadTagData(ACTION_REFRESH);

                            EventBus.getDefault().post(new MsgEvent("refresh_tag"));
                            EventBus.getDefault().post(new MsgEvent("refresh_attendee"));
                        }else {
                            Toast.makeText(TagManager.this, R.string.error_delete_tag, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    class NoDataViewBind {
        @BindView(R.id.iv_page_status)
        ImageView ivPageStatus;
        @BindView(R.id.tv_page_status)
        TextView tvPageStatus;
        View itemView;

        public NoDataViewBind(View view) {
            ButterKnife.bind(this, view);
            itemView = view;
            tvPageStatus.setText(R.string.no_tag);
        }
    }
    private void showNoData() {
        if (tagAdapter != null) {
            if (tagAdapter.getData() != null) {
                tagAdapter.getData().clear();
                tagAdapter.notifyDataSetChanged();
            }
            noDataViewBind = new NoDataViewBind(LayoutInflater.from(getContext()).inflate(R.layout.layout_no_attendee, null));
            tagAdapter.setEmptyView(noDataViewBind.itemView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
