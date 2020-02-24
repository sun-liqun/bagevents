package com.bagevent.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.activity_manager.AcManager;
import com.bagevent.activity_manager.detail.CollectionBarcode;
import com.bagevent.activity_manager.detail.CollectionDetail;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.db.EventList;
import com.bagevent.db.EventList_Table;
import com.bagevent.home.adapter.ExercisingAdapter;
import com.bagevent.home.data.ExercisingData;
import com.bagevent.home.home_interface.presenter.GetExercisingPresenter;
import com.bagevent.home.home_interface.view.GetExercisingView;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.dbutil.SyncEventUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zwj on 2016/5/24.
 */
public class ExercisingFragment extends Fragment implements GetExercisingView, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_exercising)
    RecyclerView rvExercising;
    @BindView(R.id.swipe_exercising)
    SwipeRefreshLayout swipeExercising;
    Unbinder unbinder;
    @BindView(R.id.iv_page_status)
    ImageView ivPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    private View view;
    private String userId;
    private List<EventList> eventLists = new ArrayList<EventList>();
    private ExercisingAdapter exercisingAdapter;
    private GetExercisingPresenter getExPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.exercising, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        isLoading();
        getUserInfo();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void getEventList() {
        loadFinished();
        eventLists.clear();
        List<EventList> queryEventList = new Select().from(EventList.class)
                .where(EventList_Table.userId.is(Integer.parseInt(userId)))
                .and(OperatorGroup.clause().and(EventList_Table.status.is(1))
                        .or(EventList_Table.status.is(2))
                        .or(EventList_Table.status.is(4)))
                .and(EventList_Table.mark.is("event")).queryList();
        eventLists.addAll(queryEventList);
        if (exercisingAdapter == null) {
            initAdapter();
        } else {
            exercisingAdapter.setNewData(eventLists);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.contains("newEventList")) {
            getEventList();
        }
    }

    private void getUserInfo() {
        if (NetUtil.isConnected(getActivity())) {
            getExPresenter = new GetExercisingPresenter(this);
            getExPresenter.getExercise();
        } else {
            getEventList();
        }

    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        swipeExercising.setVisibility(View.GONE);
    }

    private void loadFinished() {
        llLoading.setVisibility(View.GONE);
        swipeExercising.setVisibility(View.VISIBLE);
    }

    private void initAdapter() {
        if (eventLists.size() > 0) {
            llPageStatus.setVisibility(View.GONE);
            swipeExercising.setVisibility(View.VISIBLE);
            exercisingAdapter = new ExercisingAdapter(eventLists);
            exercisingAdapter.openLoadAnimation();
            exercisingAdapter.setOnItemClickListener(this);
            rvExercising.setAdapter(exercisingAdapter);
        } else {
            llPageStatus.setVisibility(View.VISIBLE);
            swipeExercising.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
            tvPageStatus.setText("暂无进行中的活动");
        }
    }

    private void initView() {
        userId = SharedPreferencesUtil.get(getActivity(), "userId", "");
        swipeExercising.setOnRefreshListener(this);
        rvExercising.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public int getPage() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return 0;
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public int getShowType() {
        return 0;
    }


    @Override
    public void showSuccess(ExercisingData data) {
        SyncEventUtil util = new SyncEventUtil(getActivity(), Integer.parseInt(userId), data);
        util.startSyncEventUtil();
    }

    @Override
    public void showFailed(String errInfo) {
        // Toast.makeText(getActivity(), errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHideView() {

    }

    @Override
    public void hideView() {

    }

    @Override
    public void onRefresh() {
        getUserInfo();
        swipeExercising.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        EventList eventList = eventLists.get(position);

        if (eventList.mark.contains("event")) {
            Intent intent = new Intent(getActivity(), AcManager.class);
            Bundle bundle = new Bundle();
            bundle.putString("eventName", eventList.eventName);
            bundle.putInt("eventId", eventList.eventId);
            bundle.putInt("stType", eventList.sType);
            bundle.putString(Common.BARCODE_LOGIN, "not");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (eventLists.get(position).mark.contains("collect")) {
            if (eventList.export == 1) {
                Intent intent1 = new Intent(getActivity(), CollectionDetail.class);
                intent1.putExtra("eventId", eventList.eventId);
                intent1.putExtra("collectionId", eventList.collectPointId);
                intent1.putExtra("collectionName", eventList.collectionName);
                intent1.putExtra(Common.COLLECT, Common.COLLECT_CHILD);//用于判断是否是采集点管理页面进入详情页
                startActivity(intent1);
            } else {
                Intent intent2 = new Intent(getActivity(), CollectionBarcode.class);
                intent2.putExtra("collectionId", eventList.collectPointId);
                intent2.putExtra("eventId", eventList.eventId);
                intent2.putExtra(Common.COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_HOMEPAGE);
                startActivity(intent2);
            }
        }

    }

    @OnClick(R.id.ll_page_status)
    public void onViewClicked() {
        isLoading();
        getUserInfo();
    }
}
