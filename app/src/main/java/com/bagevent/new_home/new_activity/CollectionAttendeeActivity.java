package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.new_home.adapter.AttendeeTagAdapter;
import com.bagevent.new_home.adapter.SelectedTagsAdapter;
import com.bagevent.new_home.adapter.TagsAdapter;
import com.bagevent.new_home.data.CollectionAttendeeData;
import com.bagevent.new_home.data.TagsData;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.JsonParser;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.bagevent.util.AppUtils.getContext;

public class CollectionAttendeeActivity extends BaseActivity implements
        AttendeeTagAdapter.OnItemClickListener,AttendeeTagAdapter.OnTagClickListener{

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightTitle;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.rv_attendee)
    RecyclerView rvAttendee;
    @BindView(R.id.spl_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.rl_my_tag)
    AutoRelativeLayout rlMyTag;
    @BindView(R.id.tag_filter)
    ImageView tagFilter;
    @BindView(R.id.ll_search)
    AutoLinearLayout llSearch;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.select_tags)
    AutoLinearLayout selectTags;
    @BindView(R.id.rv_select_tag)
    RecyclerView rvSelectTag;
    @BindView(R.id.rv_drawer)
    RecyclerView rvDrawer;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout ll_page_status;
    @BindView(R.id.ll_right_drawer)
    AutoLinearLayout llRightDrawer;
    private int eventId;
    private int exhibitorId;
    private int page;
    private String tagsId="";
    private int pageSize=20;

    private final byte ACTION_LAOD = 0;
    private final byte ACTION_REFRESH = 1;
    private final byte ACTION_LOADMORE = 2;

    private AttendeeTagAdapter attendeeTagAdapter;
    private NoDataViewBind noDataViewBind;
    private TagsAdapter tagsAdapter;
    private SelectedTagsAdapter selectedTagsAdapter;
    private ArrayList<String> selectTagId=new ArrayList<>();
    private ArrayList<String> selectTagName=new ArrayList<>();
    private ArrayList<String> pos = new ArrayList<>();

    private ArrayList<Integer> attendeeTags=new ArrayList<>();
    private ArrayList<String> attendeeTagsName=new ArrayList<>();

    private ArrayList<TagsData.TagDataList> tagData=new ArrayList<>();


    @OnClick({R.id.ll_right_click,R.id.tag_filter,R.id.rl_my_tag,R.id.ll_search,R.id.tv_ok,R.id.tv_cancel,R.id.ll_title_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_right_click:
                Intent intentToAdd=new Intent(CollectionAttendeeActivity.this,AddTag.class);
                intentToAdd.putExtra("exhibitorId",exhibitorId);
                intentToAdd.putExtra("eventId",eventId);
                startActivity(intentToAdd);
                break;
            case R.id.tag_filter:
                ShowFilterDrawer();
                selectTagId.removeAll(selectTagId);
                pos.removeAll(pos);
                selectTagName.removeAll(selectTagName);
                tagsId="";
                tagsAdapter.notifyDataSetChanged();
                break;
            case R.id.rl_my_tag:
                Intent intentToManager=new Intent(CollectionAttendeeActivity.this,TagManager.class);
                intentToManager.putExtra("exhibitorId",exhibitorId);
                intentToManager.putExtra("eventId",eventId);
                startActivity(intentToManager);
                break;
            case R.id.ll_search:
                Intent intentToSearch=new Intent(CollectionAttendeeActivity.this,SearchAttendee.class);
                intentToSearch.putExtra("exhibitorId",exhibitorId);
                intentToSearch.putExtra("eventId",eventId);
                startActivity(intentToSearch);
                break;
            case R.id.tv_ok:
                getTagsId();
                lazyLoad();
                drawerLayout.closeDrawer(Gravity.RIGHT);
                if (selectTagId.size()>0){
                    selectTags.setVisibility(View.VISIBLE);
                    showRvSelectedTags();
                }
                break;
            case R.id.tv_cancel:
                if (selectTagId.size()>0){
                    selectTagId.removeAll(selectTagId);
                    pos.removeAll(pos);
                    selectTagName.removeAll(selectTagName);
                    tagsAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.ll_title_back:
                finish();
                break;
        }
    }

    private void showRvSelectedTags() {
        selectedTagsAdapter=new SelectedTagsAdapter(selectTagName,this);
        selectedTagsAdapter.setHasStableIds(true);
        rvSelectTag.setAdapter(selectedTagsAdapter);
        rvSelectTag.setLayoutManager(new LinearLayoutManager(
                this,LinearLayoutManager.HORIZONTAL,false));
        selectedTagsAdapter.setListener(new SelectedTagsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
               selectTagName.remove(position);
               selectTagId.remove(position);
               if (selectTagName.size()==0){
                    selectTags.setVisibility(View.GONE);
                }
                getTagsId();
                lazyLoad();
                selectedTagsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getTagsId() {
        if (selectTagId.size()>0){
            if (selectTagId.size()==1){
                tagsId=selectTagId.get(0);
            }else {
                for (int i = 0; i < selectTagId.size() ; i++) {
                    String tagId=selectTagId.get(i);
                    tagsId=tagsId+tagId;
                    if (i!=selectTagId.size()-1){
                        tagsId+=",";
                    }
                }
            }
        }else {
            tagsId="";
        }
    }

    private void ShowFilterDrawer() {
        if (!drawerLayout.isDrawerOpen(Gravity.RIGHT)){
            drawerLayout.openDrawer(Gravity.RIGHT);
        }else {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        }

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_collection_attendee);
//        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
//        initView();
//        isLoading();
//        getIntentValue();
//        lazyLoad();
//        initRightDrawer();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collection_attendee);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        isLoading();
        getIntentValue();
        lazyLoad();
        initRightDrawer();
    }


    private void isLoading() {
       llLoading.setVisibility(View.VISIBLE);
    }

    private void loadingFinish(){
        llLoading.setVisibility(View.GONE);
    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.collect_people);
        tvRightTitle.setText(R.string.add_tag);
    }

    private void getIntentValue() {
        Intent intent=getIntent();
        eventId = intent.getIntExtra("eventId", 0);
        exhibitorId=intent.getIntExtra("exhibitorId",0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MsgEvent messageEvent) {
        if (messageEvent.mMsg.equals("refresh_attendee")) {
            page=1;
            loadAttendeeFromServer(ACTION_REFRESH);
            initRightDrawer();
        }else if (messageEvent.mMsg.equals("refresh_tag")){
            initRightDrawer();
        }
    }
    private void lazyLoad() {
        page=1;
        loadAttendeeFromServer(ACTION_LAOD);
    }

    private void loadAttendeeFromServer(final byte action) {

        if (!NetUtil.isConnected(this)) {
            TosUtil.show(getString(R.string.check_network));
            return;
        }

        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL+Constants.ATTENDEELIST)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .addParams("tagIds",tagsId)
                .addParams("page",page+"")
                .addParams("search","")
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
            case ACTION_LOADMORE:
//                exhibitorAttendeeId.removeAll(exhibitorAttendeeId);
                parserLoadMoreData(response);
                break;
            case ACTION_REFRESH:
//                exhibitorAttendeeId.removeAll(exhibitorAttendeeId);
                parserRefreshData(response);
                break;
        }
    }

    private void parserLoadData(String response) {
       ArrayList<CollectionAttendeeData.CollectionList> attendeeData=getListData(response);
       initRecyclerView(attendeeData);
       int attendeeSize=attendeeData.size();
        if (attendeeSize == 0) {
            showNoData();
        } else {
            page++;
        }
    }

    private void parserLoadMoreData(String response) {
        ArrayList<CollectionAttendeeData.CollectionList> attendeeData=getListData(response);
        if (attendeeData == null) {
            attendeeTagAdapter.loadMoreFail();
            return;
        } else {
            page++;
        }

        if (attendeeData.size() < pageSize) {
            attendeeTagAdapter.loadMoreComplete();
            attendeeTagAdapter.loadMoreEnd();
        } else {
            attendeeTagAdapter.loadMoreComplete();
        }
        attendeeTagAdapter.addData(attendeeData);
    }

    private void parserRefreshData(String response) {
        ArrayList<CollectionAttendeeData.CollectionList> attendeeData=getListData(response);
        refreshLayout.setRefreshing(false);
        attendeeTagAdapter.setNewData(attendeeData);
        if (attendeeData == null ||attendeeData.size()==0) {
           showNoData();
        } else {
            page++;
        }
    }

    private void initRecyclerView(ArrayList<CollectionAttendeeData.CollectionList> attendeeData) {
        loadingFinish();
        rvAttendee.setVisibility(View.VISIBLE);
        attendeeTagAdapter =new AttendeeTagAdapter(attendeeData);
        attendeeTagAdapter.setHasStableIds(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvAttendee.setLayoutManager(linearLayoutManager);
        rvAttendee.setAdapter(attendeeTagAdapter);
        attendeeTagAdapter.setItemClickListener(this);
        attendeeTagAdapter.setTagClickListener(this);

        if (attendeeData.size()<pageSize){
            attendeeTagAdapter.loadMoreEnd();
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                loadAttendeeFromServer(ACTION_REFRESH);
            }
        });

        attendeeTagAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadAttendeeFromServer(ACTION_LOADMORE);
            }
        },rvAttendee);
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

    private void parserError(byte action) {
        showNoData();
    }

    private void showNoData() {
        if (attendeeTagAdapter != null) {
            if (attendeeTagAdapter.getData() != null) {
                attendeeTagAdapter.getData().clear();
                attendeeTagAdapter.notifyDataSetChanged();
            }
        }
        noDataViewBind = new NoDataViewBind(LayoutInflater.from(getContext()).inflate(R.layout.layout_no_attendee, null));
        attendeeTagAdapter.setEmptyView(noDataViewBind.itemView);
    }

    @Override
    public void toSelectTag(int position, ArrayList<CollectionAttendeeData.TagList> tagList) {
        if (tagList !=null){
            for (int i = 0; i < tagList.size() ; i++) {
                if (tagList.size()==1){
                    attendeeTags.add(tagList.get(0).getTagId());
                    attendeeTagsName.add(tagList.get(0).getName());
                }else {
                    attendeeTags.add(tagList.get(i).getTagId());
                    attendeeTagsName.add(tagList.get(i).getName());
                }
            }
        }else {
            attendeeTags=new ArrayList<>();
            attendeeTagsName=new ArrayList<>();
        }
        CollectionAttendeeData.CollectionList collectionList=attendeeTagAdapter.getData().get(position);
        Intent intent=new Intent(CollectionAttendeeActivity.this,AddTagsForAttendee.class);
        intent.putExtra("eventId",eventId);
        intent.putExtra("tagIds",attendeeTags);
        intent.putExtra("tagNames",attendeeTagsName);
        intent.putExtra("exhibitorId",exhibitorId);
        intent.putExtra("exhibitorAttendeeId",collectionList.getExhibitorAttendeeId());
        intent.putExtra("isFromExhibitor",false);
        startActivity(intent);
    }


    @Override
    public void onItemClick(View view, int position) {
        CollectionAttendeeData.CollectionList collectionList=attendeeTagAdapter.getData().get(position);
        Intent intent=new Intent(CollectionAttendeeActivity.this,AttendeeDetailActivity.class);
        intent.putExtra("exhibitorId",exhibitorId);
        intent.putExtra("eventId",eventId);
        intent.putExtra("exhibitorAttendeeId",collectionList.getExhibitorAttendeeId());
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
            lazyLoad();
        }
    }

    private void initRightDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        OkHttpUtil.Post(this).url(Constants.NEW_URL+Constants.EXHIBITORTAGS)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showNoTagData();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")){
                            parserDataList(response);
                        }else {
                            showNoTagData();
                        }
                    }
                });
    }

    private void parserDataList(String response) {

        tagData=getTagListData(response);
        if (tagData==null||tagData.size()==0){
            showNoTagData();
        }else {
            ll_page_status.setVisibility(View.GONE);
            rvDrawer.setVisibility(View.VISIBLE);
        }
        tagsAdapter=new TagsAdapter(tagData,pos);
        tagsAdapter.notifyDataSetChanged();
        tagsAdapter.setHasStableIds(true);
        rvDrawer.setAdapter(tagsAdapter);
        rvDrawer.setLayoutManager(new LinearLayoutManager
                (this,LinearLayoutManager.VERTICAL,false));
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this);
        rvDrawer.setLayoutManager(manager);

        tagsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (pos.contains(String.valueOf(position))){
                    pos.remove(String.valueOf(position));
                } else {
                    pos.add(String.valueOf(position));
                }

                if (selectTagName.contains(tagData.get(position).getName())){
                    selectTagName.remove(tagData.get(position).getName());
                }else {
                    selectTagName.add(tagData.get(position).getName());
                }

                if (selectTagId.contains(tagData.get(position).getTagId())){
                    selectTagId.remove(String.valueOf(tagData.get(position).getTagId()));
                }else {
                    selectTagId.add(String.valueOf(tagData.get(position).getTagId()));
                }
                tagsAdapter.notifyDataSetChanged();

            }
        });
    }


    private void showNoTagData() {
        ll_page_status.setVisibility(View.VISIBLE);
        rvDrawer.setVisibility(View.GONE);
    }

    private ArrayList<TagsData.TagDataList> getTagListData(String response){
        TagsData tagsDataData=
                new TagsData(new JsonParser().parse(response).getAsJsonObject());
        if (tagsDataData.getRespObject()==null||tagsDataData.getRespObject().getTagDataLists()==null){
            return new ArrayList<>();
        }else {
            return tagsDataData.getRespObject().getTagDataLists();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if(event.getAction() == KeyEvent.ACTION_DOWN){
                closeDrawer();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
    private void closeDrawer() {
        if (drawerLayout!=null&&drawerLayout.isDrawerOpen(Gravity.RIGHT)){
            drawerLayout.closeDrawer(Gravity.RIGHT);
            return;
        }
        AppManager.getAppManager().finishActivity(this);
    }
}
