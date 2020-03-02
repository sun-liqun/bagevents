package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.new_home.adapter.AttendeeAdapter;
import com.bagevent.new_home.adapter.AttendeeDetailAdapter;
import com.bagevent.new_home.adapter.CommentAdapter;
import com.bagevent.new_home.data.AttendeeInfo;
import com.bagevent.new_home.data.AttendeeInfoEntity;
import com.bagevent.new_home.data.CommentEntity;
import com.bagevent.new_home.data.DynamicListData;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.view.CircleTextView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

public class AttendeeDetailActivity extends BaseActivity implements AttendeeDetailAdapter.OnItemRemoveClickListener{


    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_circle_avatar)
    CircleTextView tvCircleAvatar;
    @BindView(R.id.iv_circle_avatar)
    CircleImageView ivCircleAvatar;
    @BindView(R.id.rv_attendee_detail)
    RecyclerView rvAttendeeDetail;

    private String userId;
    private int exhibitorId;
    private int eventId;
    private int exhibitorAttendeeId;

    private AttendeeDetailAdapter detailAdapter;
    private  ArrayList<AttendeeInfo.TagList> tagLists;
    private int removePosition;
    private int removeId;
    @OnClick({ R.id.iv_title_back})
    public void click(){
        finish();
    }
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_attendee_detail);
        ButterKnife.bind(this);
        initView();
        getIntentValue();
        loadData();
    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.attendee_detail);
    }

    private void getIntentValue() {
        userId = SharedPreferencesUtil.get(getApplication(), "userId", "");
        Intent intent=getIntent();
        exhibitorId = intent.getIntExtra("exhibitorId", 0);
        eventId=intent.getIntExtra("eventId",0);
        exhibitorAttendeeId=intent.getIntExtra("exhibitorAttendeeId",0);
    }

    private void loadData() {
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }
        OkHttpUtil.Post(this).url(Constants.NEW_URL+Constants.EXHIBITITOEATTENDEEDETAIL)
                .addParams("userId",userId)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .addParams("exhibitorAttendeeId",exhibitorAttendeeId+"")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")){
//                            Log.i("---------",value+"");
                            parserDataList(response);
                        }
                    }
                });
    }

    private void parserDataList(String response) {

//        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();

//        JsonObject joAttendMap =jsonObject.getAsJsonObject("respObject")
//                .getAsJsonObject("apiAttendee").getAsJsonObject("attendeeMap");
//        JsonArray fromFieldLists = jsonObject.getAsJsonObject("respObject")
//                .getAsJsonArray("formFieldList");


        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonObject joAttendMap =jsonObject.getAsJsonObject("respObject")
                .getAsJsonObject("apiAttendee").getAsJsonObject("attendeeMap");
        JsonArray fromFieldList = jsonObject.getAsJsonObject("respObject")
                .getAsJsonArray("formFieldList");

        AttendeeInfo.AttendeeMap attendMap = new AttendeeInfo.AttendeeMap(joAttendMap,fromFieldList);
        ArrayList<String> value = attendMap.getValue();
        tvName.setText(value.get(0));

//        jsonObject.getAsJsonObject("respObject").getAsJsonObject()

        AttendeeInfo attendeeInfo=new AttendeeInfo(new JsonParser().parse(response).getAsJsonObject());
        String avater=attendeeInfo.getRespObject().getApiAttendee().getAvatar();

        AttendeeInfo.RespObjectData respObject = attendeeInfo.getRespObject();
       tagLists = attendeeInfo.getRespObject().getTagLists();

      ArrayList<AttendeeInfo.FromFieldList> fromFieldLists = attendeeInfo.getRespObject().getFromFieldLists();

        if (!TextUtils.isEmpty(avater)){
            ivCircleAvatar.setVisibility(View.VISIBLE);
            tvCircleAvatar.setVisibility(View.GONE);
            Glide.with(this).load(avater).into(ivCircleAvatar);
        }else {
            ivCircleAvatar.setVisibility(View.GONE);
            tvCircleAvatar.setVisibility(View.VISIBLE);
            tvCircleAvatar.setText(value.get(0).substring(0,1));
        }

        ArrayList<AttendeeInfoEntity> attendeeInfoEntities = new ArrayList<>();

        attendeeInfoEntities.add(new AttendeeInfoEntity(AttendeeInfoEntity.TYPE_NAME,"TA的标签"));
        if (tagLists!=null){
            for (int i = 0; i < tagLists.size() ; i++) {
                attendeeInfoEntities.add(new AttendeeInfoEntity(AttendeeInfoEntity.TYPE_TAG,tagLists.get(i)));
            }
        }else {
            attendeeInfoEntities.add(new AttendeeInfoEntity(AttendeeInfoEntity.TYPE_NO_TAG,"暂无标签"));
        }

        if (fromFieldLists!=null){
            attendeeInfoEntities.add(new AttendeeInfoEntity(AttendeeInfoEntity.TYPE_NAME,"基本信息"));
            for (int i = 0; i < fromFieldLists.size() ; i++) {
                if (fromFieldLists.get(i).getExDisplay()==1){
                    attendeeInfoEntities.add(new AttendeeInfoEntity(AttendeeInfoEntity.TYPE_INFO,fromFieldLists.get(i),value.get(i)));
                }
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        detailAdapter = new AttendeeDetailAdapter(attendeeInfoEntities);
        rvAttendeeDetail.setLayoutManager(linearLayoutManager);
        rvAttendeeDetail.setAdapter(detailAdapter);
        detailAdapter.setItemRemoveListener(this);


//        String json=response;
//        String regex="\"(\\d{6})\":\\s+\"([\\w\\d@\\s_\\.]*)\",";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(json);
//        List<String> listKey=new ArrayList<>();
//        List<String> listValue=new ArrayList<>();
//
//        while (matcher.find()){
//            listKey.add(matcher.group(1));
//            listValue.add(matcher.group(2));
//        }
//
//        Log.i("-------",listKey.toString());
//        Log.i("-------",listValue.toString());

    }

    @Override
    public void onItemRemoveClick(int position) {
         AttendeeInfoEntity attendeeInfoEntities=detailAdapter.getData().get(position);
         String name = attendeeInfoEntities.getTagList().getName();
         int tagId = attendeeInfoEntities.getTagList().getTagId();
         removePosition = position-1;
         removeId = tagId;
//
        if (!NetUtil.isConnected(this)){
            return;
        }

        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL+Constants.DELETE_TAG_FOR_ATTENDEE)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .addParams("tagId",removeId+"")
                .addParams("exhibitorAttendeeId",exhibitorAttendeeId+"")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(AttendeeDetailActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")){
                            detailAdapter.remove(removePosition);
                            detailAdapter.notifyItemRemoved(removePosition);
                            loadData();
//                            loadTagData(ACTION_REFRESH);

                            EventBus.getDefault().post(new MsgEvent("refresh_tag"));
                            EventBus.getDefault().post(new MsgEvent("refresh_attendee"));
                        }
                    }
                });
        Log.i("------------"+position,name+"");
    }

//    @Override
//    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//        switch (view.getId()){
//            case R.id.rl_remove:
//                Toast.makeText(this, adapter.getData().get(position)+"111", Toast.LENGTH_SHORT).show();
//                 break;
//        }
//    }
}
