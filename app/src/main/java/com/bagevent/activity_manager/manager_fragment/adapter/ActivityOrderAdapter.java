package com.bagevent.activity_manager.manager_fragment.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.OrderInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by zwj on 2017/10/24.
 */

public class ActivityOrderAdapter extends BaseQuickAdapter<OrderInfo.RespObjectBean.ObjectsBean,BaseViewHolder> {

    //paystatus:0:未支付,1:已完成,2\:超时未支付,3\:支付金额不一致,4\:正在付款,5\:已取消 6 线下支付订单 10 退款
    private List<OrderInfo.RespObjectBean.ObjectsBean> datas;
    public ActivityOrderAdapter(@LayoutRes int layoutResId, @Nullable List<OrderInfo.RespObjectBean.ObjectsBean> data) {
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
                        .setTextColor(R.id.tv_order_status, 0xff898989);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 3:
                helper.setText(R.id.tv_order_status,R.string.pay_atypism)
                        .setTextColor(R.id.tv_order_status, 0xff898989);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 4:
                helper.setText(R.id.tv_order_status,R.string.pay_doing)
                        .setTextColor(R.id.tv_order_status, 0xff898989);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 5:
                helper.setText(R.id.tv_order_status,R.string.cancelPaid)
                        .setTextColor(R.id.tv_order_status, 0xff898989);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 6:
                helper.setText(R.id.tv_order_status,R.string.offline_order)
                        .setTextColor(R.id.tv_order_status, 0xff898989);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 10:
                helper.setText(R.id.tv_order_status,R.string.back_order_already)
                        .setTextColor(R.id.tv_order_status, 0xff898989);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 12:
                helper.setText(R.id.tv_order_status,R.string.order_wait)
                        .setTextColor(R.id.tv_order_status, 0xff898989);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 13:
                helper.setText(R.id.tv_order_status,R.string.audit_refuse)
                        .setTextColor(R.id.tv_order_status, 0xff898989);
                helper.setGone(R.id.v_mark_status, false);
                break;
            case 14:
                helper.setText(R.id.tv_order_status,R.string.success_wait_confirm)
                        .setTextColor(R.id.tv_order_status, 0xff898989);
                helper.setGone(R.id.v_mark_status, false);
                break;
            default:
                helper.setText(R.id.tv_order_status,R.string.unknown_state)
                        .setTextColor(R.id.tv_order_status, 0xff898989);
                helper.setGone(R.id.v_mark_status, false);
                break;

        }

//        if (item.getPayWay() == 0) {
//            helper.setText(R.id.tv_order_status,"未支付")
//                    .setTextColor(R.id.tv_order_status, Color.RED);
//            helper.setGone(R.id.v_mark_status, false);
//        }

//        if(item.getAudit() == 3) {
//            helper.setText(R.id.tv_order_status,"审核拒绝")
//                    .setTextColor(R.id.tv_order_status, Color.RED);
//            helper.setGone(R.id.v_mark_status, false);
//        }

        DecimalFormat format = new DecimalFormat( "0.00 ");

        if(item.getOrderNumber().contains("890418")) {
            Log.e("OrderAdapter",item.getPayStatus() + " " + item.getAudit());
        }

        helper.setText(R.id.tv_order,item.getOrderNumber())
                .setText(R.id.tv_order_name,item.getBuyerName())
                .setText(R.id.tv_order_time,item.getOrderTime())
                .setText(R.id.tv_order_pay,format.format(item.getTicketOrderTotalPrice()));
    }
}
