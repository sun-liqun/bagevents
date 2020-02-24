package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.new_home.adapter.SelectBindTagsAdapter;
import com.bagevent.new_home.adapter.SelectedTagsAdapter;
import com.bagevent.new_home.data.TagsData;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.formatter.IFillFormatter;
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

public class AddTagsForAttendee extends BaseActivity {

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
    @BindView(R.id.rv_tags)
    RecyclerView rvTags;
//    @BindView(R.id.ll_page_status)
//    AutoLinearLayout llPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.rl_state)
    AutoFrameLayout rlPageStatus;

    private SelectBindTagsAdapter bindTagsAdapter;
    private int exhibitorId;
    private int eventId;
    private int exhibitorAttendeeId;
    private String tagIds;
    private ArrayList<Integer> bindTags=new ArrayList<>();
    private ArrayList<String> bindTgaNames=new ArrayList<>();
    private ArrayList<Integer> unbindTags=new ArrayList<>();
    private ArrayList<Integer> allTgaIds=new ArrayList<>();
    private ArrayList<String> allTgaNames=new ArrayList<>();
    private ArrayList<String> pos=new ArrayList<>();
    private boolean isFromExhibitor=false;

    @OnClick({R.id.iv_title_back, R.id.ll_right_click,R.id.btn_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_right_click:
                Intent intentToAdd=new Intent(this,AddTag.class);
                intentToAdd.putExtra("exhibitorId",exhibitorId);
                intentToAdd.putExtra("eventId",eventId);
                startActivity(intentToAdd);
                break;
            case R.id.btn_sure:
                tagIds="";
                if (unbindTags.size()>0){
                    bindTagsForAttendee();
                    if (isFromExhibitor){
                        Intent intent=new Intent(this,AttendeeDetailActivity.class);
                        intent.putExtra("exhibitorId",exhibitorId);
                        intent.putExtra("eventId",eventId);
                        intent.putExtra("exhibitorAttendeeId",exhibitorAttendeeId);
                        startActivity(intent);
                    }else {
                        AppManager.getAppManager().finishActivity();
                    }

                }else {
                    tagIds="";
                    Toast.makeText(this, R.string.please_select_tag, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void bindTagsForAttendee() {
        if (unbindTags.size()==1){
            tagIds=unbindTags.get(0).toString().trim();
        }else {
            for (int i = 0; i < unbindTags.size() ; i++) {
                String tags=unbindTags.get(i).toString().trim();
                tagIds=tagIds+tags;
                if (i!=unbindTags.size()-1){
                    tagIds+=",";
                }
            }
        }

        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Post(getBaseContext())
                .url(Constants.NEW_URL+Constants.ADD_TAG_FOR_ATTENDEE)
                .addParams("eventId",eventId+"")
                .addParams("tagIds",tagIds)
                .addParams("exhibitorAttendeeId",exhibitorAttendeeId+"")
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
                        Toast.makeText(AddTagsForAttendee.this, R.string.error_add_tag, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            if (response.contains("\"retStatus\":200")) {
                                EventBus.getDefault().post(new MsgEvent("refresh_attendee"));
                                finish();
                            }
                        } else {
                            Toast.makeText(AddTagsForAttendee.this, R.string.error_add_tag, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_tags_for_attendee);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        getIntentValue();
        getAllTagsData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MsgEvent messageEvent) {
        if (messageEvent.mMsg.equals("refresh_tag")){
            getAllTagsData();
            allTgaIds.clear();
            allTgaNames.clear();
        }
    }
    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.select_tag);
        tvRightTitle.setText(R.string.add_tag);
    }

    private void getIntentValue(){
        Intent intent = getIntent();
        exhibitorId = intent.getIntExtra("exhibitorId", 0);
        eventId = intent.getIntExtra("eventId", 0);
        bindTags = intent.getIntegerArrayListExtra("tagIds");
        bindTgaNames=intent.getStringArrayListExtra("tagNames");
        exhibitorAttendeeId=intent.getIntExtra("exhibitorAttendeeId",0);
        isFromExhibitor = intent.getBooleanExtra("isFromExhibitor", false);
    }

    private void getAllTagsData() {
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL+Constants.EXHIBITORTAGS)
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
//                        parserError(action);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            parserSuccess(response);
                        } else {
//                            parserError();
                        }

                    }
                });
    }

    private void parserSuccess(String response) {
        ArrayList<TagsData.TagDataList> tagDataLists= getListData(response);
        int tagCount=tagDataLists.size();
        if (tagCount>0){
            if (tagCount==1){
                 allTgaNames.add(tagDataLists.get(0).getName());
                 allTgaIds.add(tagDataLists.get(0).getTagId());
            }else {
                for (int i = 0; i < tagCount ; i++) {
                    allTgaNames.add(tagDataLists.get(i).getName());
                    allTgaIds.add(tagDataLists.get(i).getTagId());
                }
            }
            if (allTgaNames.containsAll(bindTgaNames)){
                allTgaNames.removeAll(bindTgaNames);
            }
            if (allTgaIds.containsAll(bindTags)){
                allTgaIds.removeAll(bindTags);
            }
        }
        initRecyclerView();
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

    private void initRecyclerView() {
        if (allTgaNames.size()>0){
            rvTags.setVisibility(View.VISIBLE);
            bindTagsAdapter=new SelectBindTagsAdapter(allTgaNames,pos);
            bindTagsAdapter.setHasStableIds(true);
            bindTagsAdapter.setListener(new SelectBindTagsAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    if (pos.contains(String.valueOf(position))){
                        pos.remove(String.valueOf(position));
                    } else {
                        pos.add(String.valueOf(position));
                    }
                    if (unbindTags.contains(allTgaIds.get(position))){
                        unbindTags.remove(allTgaIds.get(position));
                    }else {
                        unbindTags.add(allTgaIds.get(position));
                    }
                    bindTagsAdapter.notifyDataSetChanged();
                }
            });
            rvTags.setLayoutManager(new LinearLayoutManager(
                    this,LinearLayoutManager.VERTICAL,false
            ));
            rvTags.setAdapter(bindTagsAdapter);
            rvTags.setHasFixedSize(true);
            rlPageStatus.setVisibility(View.GONE);
        }else {
            tvPageStatus.setText(R.string.no_tag);
            rlPageStatus.setVisibility(View.VISIBLE);
            rvTags.setVisibility(View.GONE);
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
