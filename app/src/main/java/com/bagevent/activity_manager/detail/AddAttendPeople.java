package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.adapter.AddPeopleAdapter;
import com.bagevent.activity_manager.manager_fragment.data.SerializableMap;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetTicketInfoView;
import com.bagevent.common.Constants;
import com.bagevent.db.EventTicket;
//import com.bagevent.db.EventTicket_Table;
import com.bagevent.db.EventTicket_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.dbutil.SyncTicketUtil;
import com.bagevent.view.mydialog.CommonPopupWindow;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wevey.selector.dialog.DialogOnItemClickListener;
import com.wevey.selector.dialog.NormalSelectionDialog;
import com.zhy.autolayout.AutoLinearLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zwj on 2016/7/4.
 */
public class AddAttendPeople extends BaseActivity implements CommonPopupWindow.ConfirmListener, GetTicketInfoView {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_addpeople_ticket)
    ListView lvAddpeopleTicket;
    @BindView(R.id.tv_ticket_num)
    TextView tvTicketNum;
    @BindView(R.id.tv_add_money)
    TextView tvAddMoney;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_reminder)
    TextView tvReminder;
    @BindView(R.id.ll_ticket)
    AutoLinearLayout llTicket;
    private List<EventTicket> mAllTickets;
    private AddPeopleAdapter adapter = null;
    private GetTicketPresenter getTicketPresenter;
    private int eventId = -1;
    private int stType = 0;
    private SerializableMap serMap = new SerializableMap();
    private int buyWay = -1;
    private int num = -1;//是否有门票被选中
    private double price = 0.0;//根据价格判断是否显示现场付费
   // private CommonPopupWindow commonPopupWindow;
    private NormalSelectionDialog selectionDialog;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_people);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), Constants.STATUS_ALPHA);
        initView();
        initData();
        selectPayType();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initData() {
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", 0);
        stType = intent.getIntExtra("stType", 0);
        mAllTickets = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(eventId)).queryList();
        adapter = new AddPeopleAdapter(mAllTickets, this);
        lvAddpeopleTicket.setAdapter(adapter);

        adapter.setAddNumListener(new AddPeopleAdapter.AddNumListener() {
            @Override
            public void add(int count, SerializableMap map, double price1) {
                DecimalFormat format = new DecimalFormat("0.00 ");
                num = count;
                price = price1;
                serMap = map;
                tvTicketNum.setText(num + R.string.ticket_num);
                tvAddMoney.setText(format.format(price) + "");
                tvReminder.setVisibility(View.GONE);
                llTicket.setVisibility(View.VISIBLE);
            }
        });
        adapter.setReduceLisetner(new AddPeopleAdapter.ReduceLisetner() {
            @Override
            public void reduce(int count, SerializableMap map, double price1) {
                DecimalFormat format = new DecimalFormat("0.00 ");
                if (count == 0) {
                    num = count;
                    serMap = map;
                }
                tvTicketNum.setText(count + R.string.ticket_num);
                price = price1;
                tvAddMoney.setText(format.format(price) + "");
                if(count == 0) {
                    tvReminder.setVisibility(View.VISIBLE);
                    llTicket.setVisibility(View.GONE);
                }

            }
        });


    }

    private void initView() {
        tvTitle.setText(R.string.choose_ticket);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
    }


    private void toNextPage(int buyWay) {
        // tvAddMoney.setText(price + "");
        if (num > 0) {
            if (selectionDialog != null) {
                if (selectionDialog.isShowing()) {
                    selectionDialog.dismiss();
                }
            }

            Intent intent = new Intent(AddAttendPeople.this, AddAttendPeopleNext.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("map", serMap);
            bundle.putInt("eventId", eventId);
            bundle.putInt("buyWay", buyWay);
            bundle.putInt("stType", stType);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.select_ticket, Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick({R.id.ll_title_back, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_next:
                if (price == 0) {
                    toNextPage(2);
                } else {
                    selectionDialog.show();
                }
                break;
        }
    }

    private void selectPayType() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(getString(R.string.free_give));
        list.add(getString(R.string.spot_payment));
        selectionDialog = new NormalSelectionDialog.Builder(this)
                .setlTitleVisible(true)   //设置是否显示标题
                .setTitleHeight(50)   //设置标题高度
                .setTitleTextSize(14) //设置标题字体大小 sp
                .setTitleTextColor(R.color.black) //设置标题文本颜色
                .setItemHeight(45)  //设置item的高度
                .setItemWidth(0.9f)  //屏幕宽度*0.9
                .setItemTextColor(R.color.colorPrimaryDark)  //设置item字体颜色
                .setItemTextSize(16)  //设置item字体大小
                .setCancleButtonText(getString(R.string.cancel))  //设置最底部“取消”按钮文本
                .setOnItemListener(new DialogOnItemClickListener() {  //监听item点击事件
                    @Override
                    public void onItemClick(Button button, int position) {
                        if(position == 0) {
                            toNextPage(2);
                        }else {
                            toNextPage(3);

                        }
                    }
                })
                .setCanceledOnTouchOutside(false)  //设置是否可点击其他地方取消dialog
                .build();
        selectionDialog.setDataList(list);
    }

    @Override
    public void freeSend() {
        toNextPage(2);
    }

    @Override
    public void scenePay() {
        toNextPage(3);
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public String getEventId() {
        return eventId + "";
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showTicketInfo(TicketInfo ticketInfo) {
        SyncTicketUtil util = new SyncTicketUtil(this, eventId, ticketInfo, true);
        util.syncTicket();
    }

    @Override
    public void showErrInfo(String errInfo) {

    }

}
