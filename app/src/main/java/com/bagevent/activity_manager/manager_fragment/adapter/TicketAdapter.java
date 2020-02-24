package com.bagevent.activity_manager.manager_fragment.adapter;

import android.support.annotation.Nullable;

import com.bagevent.R;
import com.bagevent.db.EventTicket;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zwj on 2016/6/2.
 */
public class TicketAdapter extends BaseQuickAdapter<EventTicket,BaseViewHolder> {

    private List<EventTicket> tickets;
    public TicketAdapter(@Nullable List<EventTicket> data) {
        super(R.layout.ticket_item, data);
        this.tickets = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, EventTicket item) {

//        if(helper.getAdapterPosition() == tickets.size() - 1) {
//            helper.setGone(R.id.v_line1,false);
//        }else {
//            helper.setGone(R.id.v_line1,true);
//        }

        String text = Integer.toString(item.selledCounts - item.checkinCounts);
        helper.setText(R.id.tv_ticket_name,item.ticketNames)
                .setText(R.id.tv_ticket_selled,Integer.toString(item.selledCounts))
                .setText(R.id.tv_ticket_count,Integer.toString(item.ticketCounts))
                .setText(R.id.tv_have_checkin,Integer.toString(item.checkinCounts))
                .setText(R.id.tv_none_checkin,text)
                .addOnClickListener(R.id.ll_have_checkin)
                .addOnClickListener(R.id.ll_no_checkin);

        if(item.selledCounts != 0) {
//            String a = ((float)item.selledCounts / item.ticketCounts) + "";
//            float progress = new BigDecimal(a).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
//            progress = progress * 100f;
//            helper.setProgress(R.id.ticket_progress,(int)progress,100);
//
            float a = item.selledCounts * 1.0f/ item.ticketCounts;
            double v = new BigDecimal(new Float(a).toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            double progress = v * 100;
            helper.setProgress(R.id.ticket_progress,(int)progress,100);
        }else {
            helper.setProgress(R.id.ticket_progress,0,100);
        }

    }
}
