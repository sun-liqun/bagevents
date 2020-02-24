package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetTicketInfoView;
import com.bagevent.new_home.new_interface.new_view.DeleteTicketView;
import com.bagevent.new_home.new_interface.presenter.DeleteTicketPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.wevey.selector.dialog.NormalSelectionDialog;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by zwj on 2016/9/19.
 */
public class ReleaseEventTicket extends BaseActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener,GetTicketInfoView,DeleteTicketView {

    private AutoLinearLayout eventTicketBack;
    private AutoLinearLayout eventTicketConfirm;
    private ListView lvTicket;
    private SwipeRefreshLayout swipeTicket;
    private GetTicketPresenter getTicketPresenter;

    private int eventId = -1;
    private int ticketIds = -1;
    private String userId = "";
    private int tempPositon = -1;//记录删除的位置
    private String ticketName = "";
    private DeleteTicketPresenter deleteTicketPresenter;
    private ReleaseEventTicketAdapter adapter;
    private ArrayList<TicketInfo.RespObjectBean> ticketInfos = new ArrayList<TicketInfo.RespObjectBean>();
    private NormalAlertDialog deleteTicketDialog;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event_ticket);
//        userId = SharedPreferencesUtil.get(this,"userId","");
//        Intent intent = getIntent();
//        eventId = intent.getIntExtra("eventId",-1);
//        initView();
//        initData();
//        setListener();
//       // initDialog();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event_ticket);
        userId = SharedPreferencesUtil.get(this,"userId","");
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId",-1);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if(event.mMsg.equals("fromReleaseEventAddTicket")) {
            initData();
        }
    }


    private void initData() {
        if(NetUtil.isConnected(this)) {
            getTicketPresenter = new GetTicketPresenter(this);
            getTicketPresenter.getTicket();
        }else {
            setToast(getString(R.string.net_err));
        }
    }

    private void setToast(String msg) {
        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private void initDialog() {
        deleteTicketDialog = new NormalAlertDialog.Builder(ReleaseEventTicket.this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black)
                .setContentText(getString(R.string.delete_ticket) + ticketName)
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.a009feb)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.a009feb)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        deleteTicketDialog.dismiss();
                    }
                    @Override
                    public void clickRightButton(View view) {
                        deleteTicketPresenter = new DeleteTicketPresenter(ReleaseEventTicket.this);
                        deleteTicketPresenter.delete();
                    }
                })
                .build();
        deleteTicketDialog.show();

    }

    private void setListener() {
        eventTicketBack.setOnClickListener(this);
        eventTicketConfirm.setOnClickListener(this);
        swipeTicket.setOnRefreshListener(this);
        lvTicket.setOnItemClickListener(this);
    }

    private void initView() {
        eventTicketBack = (AutoLinearLayout)findViewById(R.id.ll_event_ticket_back);
        eventTicketConfirm = (AutoLinearLayout)findViewById(R.id.ll_event_ticket_confirm);
        lvTicket = (ListView)findViewById(R.id.lv_event_ticket);
        swipeTicket = (SwipeRefreshLayout)findViewById(R.id.ticketSwipe);
        View footer = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.release_event_ticket_footer,null,false);
        AutoLinearLayout tv_event_ticket_footer = (AutoLinearLayout) footer.findViewById(R.id.ll_event_ticket_footer);
        tv_event_ticket_footer.setOnClickListener(this);
        lvTicket.addFooterView(footer);
        adapter = new ReleaseEventTicketAdapter(ticketInfos,this);
        lvTicket.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_event_ticket_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_event_ticket_confirm:
                EventBus.getDefault().postSticky(new MsgEvent("fromChildPage"));
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_event_ticket_footer:
                Intent intent = new Intent(this,ReleaseEventAddTicket.class);
                intent.putExtra("type","add");
                intent.putExtra("eventId",eventId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        initData();
        swipeTicket.setRefreshing(false);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public String getEventId() {
        return eventId+"";
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showTicketInfo(TicketInfo ticketInfo) {
        ticketInfos.clear();
        tempPositon = -1;
        ticketIds = -1;
        for (int i = 0; i < ticketInfo.getRespObject().size(); i++) {
            TicketInfo.RespObjectBean temp = new TicketInfo.RespObjectBean();
            temp.setTicketName(ticketInfo.getRespObject().get(i).getTicketName());
            temp.setTicketPrice(ticketInfo.getRespObject().get(i).getTicketPrice());
            temp.setTicketCount(ticketInfo.getRespObject().get(i).getTicketCount());
            temp.setAudit(ticketInfo.getRespObject().get(i).getAudit());
            temp.setAuditTicket(ticketInfo.getRespObject().get(i).isAuditTicket());
            temp.setAuditFeeTicket(ticketInfo.getRespObject().get(i).isAuditFeeTicket());
            temp.setTicketId(ticketInfo.getRespObject().get(i).getTicketId());
            ticketInfos.add(temp);
        }
        adapter.notifyDataSetChanged();

        adapter.setOnDeleteTicketClickListener(new ReleaseEventTicketAdapter.OnDeleteTicketClickListener() {
            @Override
            public void OnDeleteClick(int position) {
                if(ticketInfos.size() > 1) {
                    if(NetUtil.isConnected(ReleaseEventTicket.this)) {
                        tempPositon = position;
                        ticketIds = ticketInfos.get(position).getTicketId();
                        ticketName = ticketInfos.get(position).getTicketName();
                        initDialog();
                    }else {
                        setToast(getString(R.string.net_err));
                    }

                }else {
                    setToast(getString(R.string.retail_one));
                }
            }
        });
    }

    @Override
    public void showErrInfo(String errInfo) {
        setToast(errInfo);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this,ReleaseEventAddTicket.class);
        intent.putExtra("type","set");
        intent.putExtra("ticketName",ticketInfos.get(position).getTicketName());
        intent.putExtra("ticketPrice",ticketInfos.get(position).getTicketPrice());
        intent.putExtra("ticketCount",ticketInfos.get(position).getTicketCount());
        intent.putExtra("ticketAudit",ticketInfos.get(position).getAudit());
        intent.putExtra("ticketId",ticketInfos.get(position).getTicketId());
        intent.putExtra("eventId",eventId);
        startActivity(intent);
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public int ticketId() {
        return ticketIds;
    }

    @Override
    public void deleteSuccess() {
        ticketInfos.remove(tempPositon);
        adapter.notifyDataSetChanged();
        deleteTicketDialog.dismiss();
    }

    @Override
    public void deleteFailed(String errInfo) {

    }
}
