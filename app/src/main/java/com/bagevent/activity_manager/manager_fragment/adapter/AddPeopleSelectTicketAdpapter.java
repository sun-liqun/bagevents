package com.bagevent.activity_manager.manager_fragment.adapter;

import android.support.annotation.Nullable;

import com.bagevent.R;
import com.bagevent.db.EventTicket;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by ZWJ on 2017/12/22 0022.
 */

public class AddPeopleSelectTicketAdpapter extends BaseQuickAdapter<EventTicket, BaseViewHolder> {

    private int selectCount = 0;
    private int position = 0;

    public AddPeopleSelectTicketAdpapter(@Nullable List<EventTicket> data) {
        super(R.layout.activity_add_people_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventTicket item) {
        DecimalFormat format = new DecimalFormat("0.00 ");
        helper.setText(R.id.tv_add_ticket_name, item.showTicketNames)
                .setText(R.id.tv_residue, item.limitCounts + "")
                .addOnClickListener(R.id.ll_reduce)
                .addOnClickListener(R.id.ll_plus);

        if (helper.getAdapterPosition() == position) {
            helper.setText(R.id.tv_add_ticketnum, selectCount + "")
                    .setTag(R.id.tv_add_ticketnum, position);
        }

        if (item.ticketPrices == 0) {
            helper.setGone(R.id.tv_a_money, false)
                    .setText(R.id.tv_ticket_type, "Free");
        } else {
            String price = format.format(item.ticketPrices) + "";
            helper.setGone(R.id.tv_a_money, true)
                    .setText(R.id.tv_ticket_type, price);
        }
    }


    public void setSelectCount(int count, int position) {
        this.selectCount = count;
        this.position = position;
    }
}
