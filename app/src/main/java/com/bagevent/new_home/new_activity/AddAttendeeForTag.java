package com.bagevent.new_home.new_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.new_home.adapter.SelectAttendeeAdapter;
import com.bagevent.new_home.data.CollectionAttendeeData;
import com.bagevent.util.AppManager;
import com.bagevent.util.InputMethodUtil;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.bagevent.util.AppUtils.getContext;

public class AddAttendeeForTag extends BaseActivity implements BaseQuickAdapter.OnItemClickListener{

    @BindView(R.id.et_search)
    EditText etSearch;
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
    @BindView(R.id.btn_search_cancle)
    Button btn_search_cancle;
    @BindView(R.id.ll_loading)
    AutoLinearLayout loading;
    private int type;
    private NoDataViewBind noDataViewBind;

    private String userId;
    private int exhibitorId;
    private int eventId;
    private int tagId;
    private int page;
    private SelectAttendeeAdapter attendeeAdapter;
    private ArrayList<String> pos = new ArrayList<>();
    private ArrayList<String> exhibitorAttendeeIds=new ArrayList<>();

    private String selectedAttendeeId;

    @OnClick({R.id.iv_title_back,R.id.ll_right_click,R.id.et_search})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_right_click:
                 selectedAttendeeId="";
                if (exhibitorAttendeeIds==null||exhibitorAttendeeIds.size()==0){
                    Toast.makeText(this, R.string.please_select_people, Toast.LENGTH_SHORT).show();
                }else {
                    addAttendeeForTag();
                }
                break;
//            case R.id.et_search:
//                InputMethodUtil.showOrHide(this);
//                break;
        }
    }

    private void addAttendeeForTag() {
        if (exhibitorAttendeeIds.size()==1){
            selectedAttendeeId=exhibitorAttendeeIds.get(0);
        }else {
            for (int i = 0; i < exhibitorAttendeeIds.size() ; i++) {
                String id=exhibitorAttendeeIds.get(i);
                selectedAttendeeId=selectedAttendeeId+id;
                if (i!=exhibitorAttendeeIds.size()-1){
                    selectedAttendeeId+=",";
                }
            }
        }

        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Post(this).url(Constants.NEW_URL+Constants.ADD_ATTENDEE_OF_TAG)
                .addParams("userId",userId)
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("eventId",eventId+"")
                .addParams("exhibitorAttendeeIds",selectedAttendeeId)
                .addParams("tagId",tagId+"")
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
                        if (response.contains("\"retStatus\":200")) {
                            EventBus.getDefault().post(new MsgEvent("refresh_attendee_of_tag"));
                            EventBus.getDefault().post(new MsgEvent("refresh_tag"));
                            EventBus.getDefault().post(new MsgEvent("refresh_attendee"));
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_attendee_for_tag);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        initView();
        getIntentValue();
        getAttendeeData();
        searchListener();
    }

    private void initView() {
        loading.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.select_people);
        tvRightTitle.setText(R.string.complete1);
        btn_search_cancle.setVisibility(View.GONE);
    }
    private void searchListener() {

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchData();
                loading.setVisibility(View.VISIBLE);
                InputMethodUtil.showOrHide(getBaseContext());
                return true;
            }
        });
    }

    private void searchData() {
        page=1;
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
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
                            type=1;
                            parserSuccess(response);
                        } else {
//                            parserError(action);
                        }
                    }
                });
    }
    private void getIntentValue() {
        userId=SharedPreferencesUtil.get(this,"userId","");
        Intent intent=getIntent();
        exhibitorId=intent.getIntExtra("exhibitorId",0);
        eventId=intent.getIntExtra("eventId",0);
        tagId=intent.getIntExtra("tagId",0);
    }
    private void getAttendeeData() {
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }

        OkHttpUtil.Post(getBaseContext())
                .url(Constants.NEW_URL+Constants.ATTENDEE_LIST)
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
                            type=0;
                            parserSuccess(response);
                        } else {
//                            parserError(action);
                        }

                    }
                });
    }

    private void parserSuccess(String response) {
        loading.setVisibility(View.GONE);
        ArrayList<CollectionAttendeeData.CollectionList> attendeeData=getListData(response);
        initRecyclerView(attendeeData);
        int attendeeSize=attendeeData.size();
        if (attendeeSize == 0) {
            showNoData();
        } else {
            page++;
//            for (int i = 0; i < attendeeSize ; i++) {
//                if (attendeeSize==1){
//                    exhibitorAttendeeId.add(attendeeData.get(0).getExhibitorAttendeeId());
//                }else {
//                    exhibitorAttendeeId.add(attendeeData.get(i).getExhibitorAttendeeId());
//                }
//
//            }
        }
    }

    private void initRecyclerView(ArrayList<CollectionAttendeeData.CollectionList> attendeeData) {
        attendeeAdapter=new SelectAttendeeAdapter(attendeeData,pos);
        attendeeAdapter.setHasStableIds(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvAttendee.setLayoutManager(linearLayoutManager);
        rvAttendee.setAdapter(attendeeAdapter);
        attendeeAdapter.setOnItemClickListener(this);
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

    private void showNoData() {
        if (attendeeAdapter != null) {
            if (attendeeAdapter.getData() != null) {
                attendeeAdapter.getData().clear();
                attendeeAdapter.notifyDataSetChanged();
            }
            noDataViewBind = new NoDataViewBind(LayoutInflater.from(getContext()).inflate(R.layout.layout_no_attendee, null));
            attendeeAdapter.setEmptyView(noDataViewBind.itemView);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CollectionAttendeeData.CollectionList collectionList=attendeeAdapter.getData().get(position);
        if (pos.contains(String.valueOf(position))){
            pos.remove(String.valueOf(position));
        } else {
            pos.add(String.valueOf(position));
        }
        attendeeAdapter.notifyDataSetChanged();

        if (exhibitorAttendeeIds.contains(String.valueOf(collectionList.getExhibitorAttendeeId()))){
            exhibitorAttendeeIds.remove(String.valueOf(collectionList.getExhibitorAttendeeId()));
        }else {
            exhibitorAttendeeIds.add(String.valueOf(collectionList.getExhibitorAttendeeId()));
        }

        tvTitle.setText( Html.fromHtml(String.format("<font color='#010101'>选择人员</font><font color='#FF9000'>%s</font>",
                 "("+exhibitorAttendeeIds.size()+")")));


    }

    class NoDataViewBind {
        @BindView(R.id.iv_page_status)
        ImageView ivPageStatus;
        @BindView(R.id.tv_page_status)
        TextView tvPageStatus;
        View itemView;

        public NoDataViewBind(View view) {
            ButterKnife.bind(this, view);
            if (type==1){
                tvPageStatus.setText(R.string.no_fit_people);
            }else {
                tvPageStatus.setText(R.string.no_add_people);
            }

            itemView = view;
        }
    }

}
