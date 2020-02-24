package com.bagevent.new_home.new_activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.db.EventList;
//import com.bagevent.db.EventList_Table;
import com.bagevent.db.EventList_Table;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.new_activity.adapter.ActivityAdapter;;
import com.bagevent.util.AppManager;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private ArrayList<String> pos = new ArrayList<>(1);
    int selectedItemCount=0;
    int eventId;
    String userId;

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    @OnClick(R.id.iv_title_back)
    public void backClick(){
        AppManager.getAppManager().finishActivity();
    }
    @OnClick(R.id.tv_sure)
    public void sureClick(){
        if (selectedItemCount>0){
            EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_EVENT_ID,eventId));
            AppManager.getAppManager().finishActivity();
        }else {
            TosUtil.show(getString(R.string.select_activity));
        }
    }
    @OnClick(R.id.tv_cancel)
    public void cancelClick(){
        finish();
    }

    private ActivityAdapter activityAdapter;
    private List<EventList> eventLists = new ArrayList<EventList>();

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.select_event);
        userId = SharedPreferencesUtil.get(getApplication(), "userId", "");

        List<EventList> queryEventList = new Select().from(EventList.class)
                .where(EventList_Table.userId.is(Integer.parseInt(userId)))
                .and(OperatorGroup.clause().and(EventList_Table.status.is(1))
                        .or(EventList_Table.status.is(2))
                        .or(EventList_Table.status.is(4)))
                .and(EventList_Table.mark.is("event")).queryList();

        eventLists.addAll(queryEventList);
        activityAdapter=new ActivityAdapter(eventLists, pos);
        activityAdapter.setOnItemClickListener(this);
        rv.setAdapter(activityAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplication()));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        EventList eventList = eventLists.get(position);
        eventId=eventList.eventId;

        activityAdapter.setPosition(position);
        activityAdapter.notifyDataSetChanged();

        if (pos.size()==0){
            pos.add(String.valueOf(position));
            selectedItemCount=pos.size();
        }else {
            pos.add(String.valueOf(position));
            pos.remove(pos.get(0));
        }
    }

}
