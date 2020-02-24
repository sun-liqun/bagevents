package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.new_home.adapter.AttendeeRemoveAdapter;
import com.bagevent.new_home.data.CollectionAttendeeData;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
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

public class SetTag extends BaseActivity implements AttendeeRemoveAdapter.OnItemRemoveClickListener {

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
    @BindView(R.id.et_tag_name)
    EditText etTagName;
    @BindView(R.id.rv_attendee_of_tag)
    RecyclerView rvAttendeeOfTag;


    private String userId;
    private int exhibitorId;
    private int eventId;
    private int tagId;
    private String tagName;
    private AttendeeRemoveAdapter attendeeAdapter;
    private ArrayList<CollectionAttendeeData.CollectionList> attendeeData=new ArrayList<>();
    private final byte ACTION_LAOD = 0;
    private final byte ACTION_REFRESH = 1;

    private int removePosition;
    private int removeId;

    @OnClick({R.id.tv_add_attendee,R.id.ll_right_click,R.id.iv_delete,R.id.ll_title_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_add_attendee:
                Intent intent=new Intent(this,AddAttendeeForTag.class);
                intent.putExtra("exhibitorId",exhibitorId);
                intent.putExtra("eventId",eventId);
                intent.putExtra("tagId",tagId);
                startActivity(intent);
                break;
            case R.id.ll_right_click:
                if (!TextUtils.isEmpty(etTagName.getText().toString().trim())){
                    upDateTagName();
                    finish();
                }else {
                    Toast.makeText(this, R.string.change_failed, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_delete:
                etTagName.setText("");
                break;
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set_tag);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        getIntentValue();
        getAttendeeOfTag(ACTION_LAOD);
    }


    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.set_tag);
        tvRightTitle.setText(R.string.complete1);
    }

    private void getIntentValue() {
        Intent intent=getIntent();
        userId=SharedPreferencesUtil.get(this,"userId","");
        eventId=intent.getIntExtra("eventId",0);
        exhibitorId=intent.getIntExtra("exhibitorId",0);
        tagId=intent.getIntExtra("tagId",0);
        tagName=intent.getStringExtra("tagName");
        etTagName.setText(tagName);
        etTagName.setSelection(tagName.length());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MsgEvent messageEvent) {
        if (messageEvent.mMsg.equals("refresh_attendee_of_tag")) {
            getAttendeeOfTag(ACTION_REFRESH);
        }
    }
    private void getAttendeeOfTag(final byte action) {
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Post(getBaseContext())
                .url(Constants.NEW_URL+Constants.ATTENDEE_OF_TAG)
                .addParams("userId",userId)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .addParams("tagId",tagId+"")
                .addParams("page","1")
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
        attendeeData=getListData(response);
        initRecyclerView(attendeeData);
        int attendeeSize=attendeeData.size();
//        if (attendeeSize == 0) {
////            showNoData();
//        } else {
//            page++;
//            for (int i = 0; i < attendeeSize ; i++) {
//                if (attendeeSize==1){
//                    exhibitorAttendeeId.add(attendeeData.get(0).getExhibitorAttendeeId());
//                }else {
//                    exhibitorAttendeeId.add(attendeeData.get(i).getExhibitorAttendeeId());
//                }
//
//            }
//        }
    }

    private void parserRefreshData(String response) {
        attendeeData=getListData(response);
        if (attendeeData==null||attendeeData.size()==0){
           rvAttendeeOfTag.setVisibility(View.GONE);
        }
        attendeeAdapter.setNewData(attendeeData);
}

    private void initRecyclerView(ArrayList<CollectionAttendeeData.CollectionList> attendeeData) {
        attendeeAdapter=new AttendeeRemoveAdapter(attendeeData);
        attendeeAdapter.setHasStableIds(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerViewNoBugLinearLayoutManager linearLayoutManager =
                new RecyclerViewNoBugLinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvAttendeeOfTag.setLayoutManager(linearLayoutManager);
        rvAttendeeOfTag.setAdapter(attendeeAdapter);
        attendeeAdapter.setItemRemoveListener(this);
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

    private void upDateTagName() {
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Post(getBaseContext())
                .url(Constants.NEW_URL+Constants.ADD_OR_UPDATETAG)
                .addParams("userId",userId)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .addParams("tagId",tagId+"")
                .addParams("tagName",etTagName.getText().toString())
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
                        Toast.makeText(SetTag.this, R.string.error_change_tag, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            EventBus.getDefault().post(new MsgEvent("refresh_tag"));
                            EventBus.getDefault().post(new MsgEvent("refresh_attendee"));
                        } else {
                            Toast.makeText(SetTag.this,  R.string.error_change_tag, Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    @Override
    public void onItemRemoveClick(final int position) {
        CollectionAttendeeData.CollectionList collectionList=attendeeAdapter.getData().get(position);
        int exhibitorAttendeeId=collectionList.getExhibitorAttendeeId();
        removePosition = position;
        removeId = exhibitorAttendeeId;
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }
        OkHttpUtil.Post(this).url(Constants.NEW_URL+Constants.DELETE_TAG_FOR_ATTENDEE)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .addParams("tagId",tagId+"")
                .addParams("exhibitorAttendeeId",removeId+"")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        String result = response.body().string();
                        return result;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(SetTag.this, R.string.error_delete_tag, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            attendeeData.remove(removePosition);
                            attendeeAdapter.notifyItemRemoved(removePosition);
                            EventBus.getDefault().post(new MsgEvent("refresh_tag"));
                            EventBus.getDefault().post(new MsgEvent("refresh_attendee"));
                        } else {
                            Toast.makeText(SetTag.this, R.string.error_delete_tag, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


}
