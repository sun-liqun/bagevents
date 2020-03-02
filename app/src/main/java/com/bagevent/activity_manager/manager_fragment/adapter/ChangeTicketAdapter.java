package com.bagevent.activity_manager.manager_fragment.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.db.EventTicket;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by WenJie on 2017/11/13.
 */

public class ChangeTicketAdapter extends BaseQuickAdapter<EventTicket, BaseViewHolder> {

    private int selectPosition = -1;

    public ChangeTicketAdapter(@Nullable List<EventTicket> data) {
        super(R.layout.layout_change_ticket_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventTicket item) {
        if (selectPosition == helper.getAdapterPosition()) {
            helper.setChecked(R.id.rb_ticket_checked, true);
        } else {
            helper.setChecked(R.id.rb_ticket_checked, false);
        }

        helper.setText(R.id.tv_ticket_name, item.ticketNames);
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
    }
}
