package com.bagevent.new_home.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bagevent.R;
import com.bagevent.new_home.data.WithDrawAccountData;
import com.bagevent.util.CompareRex;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ZWJ on 2017/12/5 0005.
 */

public class WithDrawAccountAdapter extends BaseQuickAdapter<WithDrawAccountData.RespObjectBean.AccountBean, BaseViewHolder> {

    private int selectPosition;

    public WithDrawAccountAdapter(@Nullable List<WithDrawAccountData.RespObjectBean.AccountBean> data) {
        super(R.layout.layout_withdraw_account_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithDrawAccountData.RespObjectBean.AccountBean item) {
        if (selectPosition == helper.getAdapterPosition()) {
            helper.setGone(R.id.iv_select, true);
        } else {
            helper.setGone(R.id.iv_select, false);
        }
//
        if (item.getType() == 1) {
            Glide.with(mContext).load(R.drawable.ic_alipay).into((ImageView) helper.getView(R.id.iv_bank_icon));
            helper.setText(R.id.tv_account_type, R.string.alipay);
        } else {
            Glide.with(mContext).load(item.getBankIcon()).into((ImageView) helper.getView(R.id.iv_bank_icon));
            helper.setText(R.id.tv_account_type, item.getBankName());
        }

        if (item.getHasSubmittedValidationInfo() == 0) {
            helper.setTextColor(R.id.tv_account_type, Color.parseColor("#FF898989"));
            helper.setTextColor(R.id.tv_account, Color.parseColor("#FF898989"));
        } else {
            helper.setTextColor(R.id.tv_account_type, Color.BLACK);
            helper.setTextColor(R.id.tv_account, Color.BLACK);
        }
//        if(CompareRex.isCellPhone(item.getAccount())) {
//            helper.setText(R.id.tv_account,item.getAccount());
//        }else if(CompareRex.checkEmail(item.getAccount())) {
//            helper.setText(R.id.tv_account,item.getAccount());
//        }else {
        helper.setText(R.id.tv_account, item.getAccount());
//        }
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
    }
}
