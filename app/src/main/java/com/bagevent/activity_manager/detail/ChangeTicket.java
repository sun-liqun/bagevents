package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.adapter.ChangeTicketAdapter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ChangeTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.QuitTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ChangeTicketView;
import com.bagevent.common.Common;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.EventTicket;
//import com.bagevent.db.EventTicket_Table;
import com.bagevent.db.EventTicket_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.view.listener.CancelListener;
import com.bagevent.view.listener.ConfirmListener;
import com.bagevent.view.mydialog.PointDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WenJie on 2017/11/13.
 */

public class ChangeTicket extends BaseActivity implements BaseQuickAdapter.OnItemClickListener,ChangeTicketView {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_change_ticket)
    TextView tvChangeTicket;
    @BindView(R.id.rv_change_ticket)
    RecyclerView rvChangeTicket;

    private List<EventTicket> ticketList = new ArrayList<EventTicket>();
    private int ticketId;
    private int eventId;
    private int attendeeId;
    private int position;
    private int notice;
    private ChangeTicketAdapter changeTicketAdapter;
    private ChangeTicketPresenter changeTicketPresenter;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_change_ticket);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId",0);
        ticketId = intent.getIntExtra("ticketId",0);
        attendeeId = intent.getIntExtra("attendeeId",0);
        position = intent.getIntExtra("position",0);
   //     Log.e("attend",ticketId+"");

        ticketList = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(eventId)).and(EventTicket_Table.ticketIds.isNot(ticketId)).queryList();
        if(changeTicketAdapter == null) {
            initAdapter();
        }
    }

    private void initAdapter() {
        if(ticketList.size() > 0) {
            changeTicketAdapter = new ChangeTicketAdapter(ticketList);
            changeTicketAdapter.openLoadAnimation();
            changeTicketAdapter.setOnItemClickListener(this);
            rvChangeTicket.setAdapter(changeTicketAdapter);
        }
    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.change_ticket);
        rvChangeTicket.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showTipsDialog() {
        final PointDialog dialog = new PointDialog(this);
        dialog.setText(getString(R.string.change_ticket_remind));
        dialog.setCancelText(getString(R.string.cancel));
        dialog.setConfirmText(getString(R.string.confirm));
        dialog.setConfirmListener(new ConfirmListener() {
            @Override
            public void confirm(View v) {
                if (NetUtil.isConnected(ChangeTicket.this)) {
                    dialog.dismiss();
                    showChangeTicketDialog();
                } else {
                    Toast.makeText(ChangeTicket.this, R.string.check_network1, Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.setCancelListener(new CancelListener() {
            @Override
            public void cancel(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showChangeTicketDialog() {
        final PointDialog dialog = new PointDialog(this);
        dialog.setText(getString(R.string.weather_send_bank_ticket));
        dialog.setCancelText(getString(R.string.no_send));
        dialog.setConfirmText(getString(R.string.send));
        dialog.setConfirmListener(new ConfirmListener() {
            @Override
            public void confirm(View v) {
                notice = 1;
                if (NetUtil.isConnected(ChangeTicket.this)) {
                    if(NetUtil.isConnected(ChangeTicket.this)) {
                        changeTicketPresenter = new ChangeTicketPresenter(ChangeTicket.this);
                        changeTicketPresenter.changeTicket();
                    }else {
                        Toast.makeText(ChangeTicket.this, R.string.check_network3, Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(ChangeTicket.this, R.string.check_network1, Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.setCancelListener(new CancelListener() {
            @Override
            public void cancel(View v) {
                notice = 0;
                if (NetUtil.isConnected(ChangeTicket.this)) {
                    if(NetUtil.isConnected(ChangeTicket.this)) {
                        changeTicketPresenter = new ChangeTicketPresenter(ChangeTicket.this);
                        changeTicketPresenter.changeTicket();
                    }else {
                        Toast.makeText(ChangeTicket.this, R.string.check_network3, Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(ChangeTicket.this, R.string.check_network1, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    @OnClick({R.id.ll_title_back, R.id.tv_change_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_change_ticket:
                showTipsDialog();
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ticketId = ticketList.get(position).ticketIds;
        changeTicketAdapter.setSelectPosition(position);
        changeTicketAdapter.notifyDataSetChanged();
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public int userId() {
        return Integer.parseInt(SharedPreferencesUtil.get(this,"userId",""));
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public int attendeeId() {
        return attendeeId;
    }

    @Override
    public int ticketId() {
        return ticketId;
    }

    @Override
    public int notice() {
        return notice;
    }

    @Override
    public void changeTicketSuccess() {
        SQLite.update(Attends.class).set(Attends_Table.ticketIds.is(ticketId)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.attendId.is(attendeeId)).execute();
        EventBus.getDefault().postSticky(new MsgEvent(position, ticketId,Common.CHANGE_TICKET));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void changeTicketFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }
}
