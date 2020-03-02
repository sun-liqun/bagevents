package com.bagevent.new_home.adapter;

import android.support.annotation.Nullable;

import com.bagevent.R;
import com.bagevent.new_home.data.WithdrawRecordData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public class WithdrawRecordAdapter extends BaseQuickAdapter<WithdrawRecordData.RespObjectBean.ObjectsBean, BaseViewHolder> {

    public WithdrawRecordAdapter(@Nullable List<WithdrawRecordData.RespObjectBean.ObjectsBean> data) {
        super(R.layout.recharge_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawRecordData.RespObjectBean.ObjectsBean item) {
//        if (item.getType() == 1) {
//            helper.setText(R.id.tv_pay_style, "支付宝");
//        } else {
//            helper.setText(R.id.tv_pay_style, item.getPay_type_desc());
//        }

        helper.setText(R.id.tv_pay_style, item.getType_desc() == null ? item.getPay_type_desc() : item.getType_desc());
        if (item.getOutcome_type() == 0) {
            helper.setText(R.id.tv_pay_status, R.string.is_apply);
        } else if (item.getOutcome_type() == 1) {
            helper.setText(R.id.tv_pay_status, R.string.complete);
        } else if (item.getOutcome_type() == 2) {
            helper.setText(R.id.tv_pay_status, R.string.apply_refused);
        }

        helper.setText(R.id.tv_pay_fee, item.getTotal_amount() + "")
                .setText(R.id.tv_pay_time, item.getOutcome_time());

        helper.setGone(R.id.tv_order_num,false);
        helper.setGone(R.id.tv_account_mark,false);
        helper.setGone(R.id.tv_account_mark2,false);
    }

}
