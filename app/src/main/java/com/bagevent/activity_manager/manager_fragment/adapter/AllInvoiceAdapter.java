package com.bagevent.activity_manager.manager_fragment.adapter;

import android.support.annotation.Nullable;

import com.bagevent.R;
import com.bagevent.db.Invoice;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ZWJ on 2017/12/27 0027.
 */

public class AllInvoiceAdapter extends BaseQuickAdapter<Invoice,BaseViewHolder> {

    public AllInvoiceAdapter(@Nullable List<Invoice> data) {
        super(R.layout.activity_order_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Invoice item) {
        helper.setText(R.id.tv_order_name,item.buyerName)
                .setText(R.id.tv_order,item.orderNumber)
                .setText(R.id.tv_order_pay,item.payTotalPrice+"");

        if(item.obtaied == 0) {
            helper.setText(R.id.tv_order_status,R.string.no_open_bill);
        }else if(item.obtaied == 1) {
            helper.setText(R.id.tv_order_status,R.string.open_collected);
        }else if(item.obtaied == 2) {
            helper.setText(R.id.tv_order_status,R.string.already_express);
        }else if(item.obtaied == 3) {
            helper.setText(R.id.tv_order_status,R.string.rb_obtainway_scene);
        }else if(item.obtaied == 4) {
            helper.setText(R.id.tv_order_status,R.string.ticket_self_collect);
        }

        if(item.obtainWay == 0){
            helper.setText(R.id.tv_order_time,R.string.rb_obtainway_scene);
        }else if(item.obtainWay == 1) {
            helper.setText(R.id.tv_order_time,R.string.express1);
        }else if(item.obtainWay == 4) {
            helper.setText(R.id.tv_order_time,R.string.download_ticket);
        }
    }
}
