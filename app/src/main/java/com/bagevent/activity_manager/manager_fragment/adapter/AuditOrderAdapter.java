package com.bagevent.activity_manager.manager_fragment.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by WenJie on 2017/11/3.
 */

public class AuditOrderAdapter extends BaseQuickAdapter<OrderInfo.RespObjectBean.ObjectsBean,BaseViewHolder> {

    public AuditOrderAdapter(int layoutResId, @Nullable List<OrderInfo.RespObjectBean.ObjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo.RespObjectBean.ObjectsBean item) {
        switch (item.getPayStatus()) {
            case 0:
                helper.setText(R.id.tv_order_status,R.string.pay_wait1)
                        .setTextColor(R.id.tv_order_status, Color.RED);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 1:
                helper.setText(R.id.tv_order_status,R.string.complete)
                        .setTextColor(R.id.tv_order_status,0xff00bb12);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 2:
                helper.setText(R.id.tv_order_status,R.string.overtime)
                        .setTextColor(R.id.tv_order_status, Color.RED);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 3:
                helper.setText(R.id.tv_order_status,R.string.pay_atypism)
                        .setTextColor(R.id.tv_order_status, Color.RED);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 4:
                helper.setText(R.id.tv_order_status,R.string.pay_doing)
                        .setTextColor(R.id.tv_order_status, Color.RED);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 5:
                helper.setText(R.id.tv_order_status,R.string.cancelPaid)
                        .setTextColor(R.id.tv_order_status, Color.RED);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 6:
                helper.setText(R.id.tv_order_status,R.string.offline_order)
                        .setTextColor(R.id.tv_order_status, Color.RED);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 10:
                helper.setText(R.id.tv_order_status,R.string.back_order_already)
                        .setTextColor(R.id.tv_order_status, Color.RED);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 12:
                helper.setText(R.id.tv_order_status,R.string.order_wait)
                        .setTextColor(R.id.tv_order_status, Color.RED);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 13:
                helper.setText(R.id.tv_order_status,R.string.audit_refuse)
                        .setTextColor(R.id.tv_order_status, Color.RED);
                helper.setGone(R.id.v_mark_status, false);
                break;
        }

//        if(item.getAudit() == 1) {
//            helper.setText(R.id.tv_order_status,"等待审核")
//                    .setTextColor(R.id.tv_order_status, 0xff00bb12);
//            helper.setGone(R.id.v_mark_status, false);
//        }
        DecimalFormat format = new DecimalFormat( "0.00 ");

        helper.setText(R.id.tv_order,item.getOrderNumber())
                .setText(R.id.tv_order_name,item.getBuyerName())
                .setText(R.id.tv_order_time,item.getOrderTime())
                .setText(R.id.tv_order_pay,format.format(item.getTicketOrderTotalPrice()));
    }

}
