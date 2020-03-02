package com.bagevent.new_home.new_activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.db.EventList;
//import com.bagevent.db.EventList_Table;
import com.bagevent.db.EventList_Table;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.new_activity.adapter.ActivityAdapter;
import com.bagevent.util.AppManager;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectActivityOfInform extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.tv_selected_count)
    TextView tvTitle;
    @BindView(R.id.tv_select_event)
    TextView tv_select_event;
    @BindView(R.id.rv)
    RecyclerView rv;


    @OnClick(R.id.iv_back)
    public void backClick() {
        AppManager.getAppManager().finishActivity();
    }

    @OnClick(R.id.tv_sure)
    public void sureClick() {
        if (pos.size() == 0) {
            Toast.makeText(this, R.string.select_activity, Toast.LENGTH_SHORT).show();
        } else {
//            if (names.size()==1){
//                EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_EVENT, names.get(0)));
//            }else {
//                EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_EVENT, names.get(0) + " 等" + i + "个活动"));
//            }
//            EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_EVENTS, names));
//            if (id.size()==1){
//                EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_EVENT, id));
//            }else {
//
//            }
            EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_EVENT, id));
            EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_EVENTS, id));
            EventBus.getDefault().post(new MessageEvent(MessageAction.ACTION_SELECT_POSITION,pos));
            finish();
        }
    }

    @OnClick(R.id.tv_cancel)
    public void cancelClick() {
        finish();
    }

    private ActivityAdapter activityAdapter;
    private List<EventList> eventLists = new ArrayList<EventList>();
    ArrayList<String> pos;
    ArrayList<Integer> id;

    String userId;
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_of_inform);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        userId=SharedPreferencesUtil.get(getApplication(),"userId","");
        pos = getIntent().getStringArrayListExtra(KeyUtil.KEY_SELECT_EVENTS);
        id=getIntent().getIntegerArrayListExtra(KeyUtil.KEY_TRANSLATE_EVENTS);
        if (pos == null) {
            pos = new ArrayList<>();
        }else {
            tvTitle.setText("("+pos.size()+")");
        }

        if (id==null){
            id=new ArrayList<>();
        }

        List<EventList> queryEventList = new Select().from(EventList.class)
                .where(EventList_Table.userId.is(Integer.parseInt(userId)))
                .and(OperatorGroup.clause().and(EventList_Table.status.is(1))
                        .or(EventList_Table.status.is(2))
                        .or(EventList_Table.status.is(4)))
                .and(EventList_Table.mark.is("event")).queryList();

        eventLists.addAll(queryEventList);
        activityAdapter = new ActivityAdapter(eventLists,pos);
        activityAdapter.setOnItemClickListener(this);
        rv.setAdapter(activityAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplication()));

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        EventList eventList = eventLists.get(position);
//        String selectEventName = eventList.eventName;

        int selectEventId=eventList.eventId;

        if(pos.contains(String.valueOf(position))){
            pos.remove(String.valueOf(position));
        }else {
            pos.add(String.valueOf(position));
        }

        if (id.contains(selectEventId)){
            id.remove((Integer)selectEventId);
        }else {
            id.add((Integer)selectEventId);
        }

        if (activityAdapter.isItemChecked(position)) {
            activityAdapter.setItemChecked(position, false);
            view.findViewById(R.id.iv_check).setVisibility(View.GONE);

        } else {
            activityAdapter.setItemChecked(position, true);
            view.findViewById(R.id.iv_check).setVisibility(View.VISIBLE);

        }
        if (pos.size() > 0) {
            tv_select_event.setText(R.string.already_select_activity);
        } else {
            tv_select_event.setText(R.string.select_event);
        }
        tvTitle.setText("(" + pos.size() + ")");

        adapter.notifyItemChanged(position);

    }
}
