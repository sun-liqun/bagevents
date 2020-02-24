package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.new_home.adapter.UserSetEventIncomeAdapter;
import com.bagevent.new_home.data.EventTotalIncome;
import com.bagevent.new_home.new_interface.new_view.GetEventTotalIncomeView;
import com.bagevent.new_home.new_interface.presenter.GetEventTotalIncomePresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zwj on 2016/10/11.
 */
public class UserSetEventIncome extends BaseActivity implements GetEventTotalIncomeView {
    @BindView(R.id.rv_income)
    RecyclerView rvIncome;
    @BindView(R.id.tv_total_income)
    TextView tvTotalIncome;
    @BindView(R.id.tv_total_online_income)
    TextView tvTotalOnlineIncome;
    @BindView(R.id.tv_total_offline_income)
    TextView tvTotalOfflineIncome;
    @BindView(R.id.iv_page_status)
    ImageView ivPageStatus;
    @BindView(R.id.tv_page_status)
    TextView tvPageStatus;
    @BindView(R.id.ll_page_status)
    AutoLinearLayout llPageStatus;

    private List<EventTotalIncome.RespObjectBean.EventListBean> mTotalIncome = new ArrayList<EventTotalIncome.RespObjectBean.EventListBean>();
    private UserSetEventIncomeAdapter adapter;
    private GetEventTotalIncomePresenter getEventTotalIncomePresenter;

    private String userId = "";

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.user_set_event_income);
        StatusBarUtil.setTranslucent(this);
        ButterKnife.bind(this);
        initView();
        getIncome();
    }


    private void getIncome() {
        if (NetUtil.isConnected(this)) {
            getEventTotalIncomePresenter = new GetEventTotalIncomePresenter(this);
            getEventTotalIncomePresenter.eventTotalIncome();
        } else {
            Toast.makeText(this, R.string.net_err, Toast.LENGTH_SHORT).show();
        }
    }

    private void initAdapter() {
        if (mTotalIncome.size() > 0) {
            adapter = new UserSetEventIncomeAdapter(mTotalIncome);
            adapter.openLoadAnimation();
            rvIncome.setAdapter(adapter);
        } else {
            rvIncome.setVisibility(View.GONE);
            llPageStatus.setVisibility(View.VISIBLE);
            if(!UserSetEventIncome.this.isFinishing()){
                Glide.with(this).load(R.drawable.no_record).into(ivPageStatus);
            }
            tvPageStatus.setText(R.string.no_activity_income);
        }
    }


    private void initView() {
        userId = SharedPreferencesUtil.get(this, "userId", "");
        rvIncome.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public void getEventTotalIncomeSuccess(EventTotalIncome response) {
        for (int i = 0; i < response.getRespObject().getEventList().size(); i++) {
            EventTotalIncome.RespObjectBean.EventListBean bean = response.getRespObject().getEventList().get(i);
            EventTotalIncome.RespObjectBean.EventListBean data = new EventTotalIncome.RespObjectBean.EventListBean();
            if (bean.getStatus() > 0 && bean.getStatus() < 10) {
                data.setCurrencySign(bean.getCurrencySign());
                data.setEventName(bean.getEventName()
                        .replaceAll("&#40;", "(")
                        .replaceAll("&#41;", ")")
                        .replaceAll("&gt;", ">")
                        .replaceAll("&lt;", "<")
                        .replaceAll("&amp;","&")
                        .replaceAll("&quot;","\"")
                        .replaceAll("&apos;","'")
                        .replaceAll("&nbsp;"," "));
                data.setOfflineIncome(convert(bean.getOfflineIncome()));
                data.setOnlineIncome(convert(bean.getOnlineIncome()));
                data.setRefundPrice(convert(bean.getRefundPrice()));
                data.setStatus(bean.getStatus());
                mTotalIncome.add(data);
            }
        }
        Collections.sort(mTotalIncome, new Comparator<EventTotalIncome.RespObjectBean.EventListBean>() {
            @Override
            public int compare(EventTotalIncome.RespObjectBean.EventListBean o1, EventTotalIncome.RespObjectBean.EventListBean o2) {
                int i = o1.getStatus() - o2.getStatus();
                if (i == 0) {
                    return o1.getStatus() - o2.getStatus();
                }
                return i;
            }
        });
        if (adapter == null) {
            initAdapter();
        } else {
            adapter.setNewData(mTotalIncome);
        }
        DecimalFormat format = new DecimalFormat("0.00 ");
        double onLineIncome = convert(response.getRespObject().getTotalOnline());
        double onLineDollarIncome = convert(response.getRespObject().getTotalOnlineDollar());
        double offLineIncome = convert(response.getRespObject().getTotalOffline());
        double offLineDollarIncome = convert(response.getRespObject().getTotalOfflineDollar());
        double total = onLineIncome + offLineIncome;
        double totalDollar = onLineDollarIncome + offLineDollarIncome;
        String sOnlineIncome = "￥" + format.format(onLineIncome);
        String sDOnlineDollarIncome = "$" + format.format(onLineDollarIncome);
        String sOfflineIncome = "￥" + format.format(offLineIncome);
        String sDOffLineDollarIncome = "$" + format.format(offLineDollarIncome);
        String sTotal = "￥" + format.format(convert(total));
        String sDTotal = "$" + format.format(convert(totalDollar));

        tvTotalIncome.setText(sTotal);
        tvTotalOnlineIncome.setText(sOnlineIncome);
        tvTotalOfflineIncome.setText(sOfflineIncome);

    }

    @Override
    public void getEventTotalIncomeFailed(String errInfo) {

    }

    private double convert(double value) {
        long l1 = Math.round(value * 100);
        double ret = l1 / 100.00;
        return ret;
    }


    @OnClick(R.id.ll_title_back)
    public void onViewClicked() {
        AppManager.getAppManager().finishActivity();
    }
}
