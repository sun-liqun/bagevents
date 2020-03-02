package com.bagevent.new_home.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.new_home.data.MsgRecordData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by zwj on 2017/10/23.
 */

public class MsgRecordAdapter extends BaseQuickAdapter<MsgRecordData.RespObjectBean.ObjectsBean,BaseViewHolder> {

    private DecimalFormat format;
    public MsgRecordAdapter(@LayoutRes int layoutResId, @Nullable List<MsgRecordData.RespObjectBean.ObjectsBean> data) {
        super(layoutResId, data);
        format = new DecimalFormat( "0.00 ");
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgRecordData.RespObjectBean.ObjectsBean item) {
        Log.e("fasd",item.getPay_way()+"");
        if(item.getPay_way() == 0) {
            helper.setText(R.id.tv_pay_style,R.string.alipay);
        }else if(item.getPay_way() == 1) {
            helper.setText(R.id.tv_pay_style,R.string.wechat);
        }else if(item.getPay_way() == 25) {
            helper.setText(R.id.tv_pay_style,R.string.have_money);
        }

        if(item.getPay_status() == 0) {
            helper.setText(R.id.tv_pay_status,R.string.unPaid)
            .setTextColor(R.id.tv_pay_status, Color.RED);
        }else if(item.getPay_status() == 1) {
            helper.setText(R.id.tv_pay_status,R.string.paid)
            .setTextColor(R.id.tv_pay_status,0xFF2BB600);
        }
        helper.setText(R.id.tv_order_num,item.getOrder_number())
                .setText(R.id.tv_pay_time,item.getCreate_time());
        helper.setText(R.id.tv_pay_fee,format.format(item.getFee()));



    }
}
