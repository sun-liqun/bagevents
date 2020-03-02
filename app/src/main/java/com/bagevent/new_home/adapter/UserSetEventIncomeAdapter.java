package com.bagevent.new_home.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bagevent.R;
import com.bagevent.new_home.data.EventTotalIncome;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/10/11.
 */
public class UserSetEventIncomeAdapter extends BaseQuickAdapter<EventTotalIncome.RespObjectBean.EventListBean,BaseViewHolder> {

    public UserSetEventIncomeAdapter(@Nullable List<EventTotalIncome.RespObjectBean.EventListBean> data) {
        super(R.layout.user_set_event_income_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventTotalIncome.RespObjectBean.EventListBean item) {
        DecimalFormat format = new DecimalFormat( "0.00 ");
        helper.setText(R.id.tv_event_name,item.getEventName())
                .setText(R.id.tv_online_income,item.getCurrencySign() + format.format(item.getOnlineIncome()))
                .setText(R.id.tv_offline_income,item.getCurrencySign() + format.format(item.getOfflineIncome()))
                .setText(R.id.quit_ticket_income,item.getCurrencySign() + format.format(item.getRefundPrice()));

        if(item.getStatus() == 1) {
            helper.setText(R.id.tv_eventstatus,R.string.sign_up_doing)
                    .setTag(R.id.tv_eventstatus,helper.getAdapterPosition());
            helper.getView(R.id.tv_eventstatus).setBackgroundResource(R.drawable.baomingzhong);
        }else if(item.getStatus() == 2) {
            helper.setText(R.id.tv_eventstatus,R.string.doing)
                    .setTag(R.id.tv_eventstatus,helper.getAdapterPosition());
            helper.getView(R.id.tv_eventstatus).setBackgroundResource(R.drawable.zhengzaijinxing);
        }else if(item.getStatus() == 3) {
            helper.setText(R.id.tv_eventstatus,R.string.stop)
                    .setTag(R.id.tv_eventstatus,helper.getAdapterPosition());
            helper.getView(R.id.tv_eventstatus).setBackgroundResource(R.drawable.yijieshu);
        }else if(item.getStatus() == 4) {
            helper.setText(R.id.tv_eventstatus,R.string.stop_sale_ticket)
                    .setTag(R.id.tv_eventstatus,helper.getAdapterPosition());
            helper.getView(R.id.tv_eventstatus).setBackgroundResource(R.drawable.baomingzhong);
        }else if(item.getStatus() == 5) {
            helper.setText(R.id.tv_eventstatus,R.string.audit_wait1)
                    .setTag(R.id.tv_eventstatus,helper.getAdapterPosition());
            helper.getView(R.id.tv_eventstatus).setBackgroundResource(R.drawable.baomingzhong);
        }else if(item.getStatus() == 10) {
            helper.setText(R.id.tv_eventstatus,R.string.cancel_release)
                    .setTag(R.id.tv_eventstatus,helper.getAdapterPosition());
            helper.getView(R.id.tv_eventstatus).setBackgroundResource(R.drawable.yijieshu);
        }
    }
}
