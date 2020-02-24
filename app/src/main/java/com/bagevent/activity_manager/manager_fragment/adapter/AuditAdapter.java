package com.bagevent.activity_manager.manager_fragment.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bagevent.R;
import com.bagevent.db.Attends;
import com.bagevent.db.EventTicket;
//import com.bagevent.db.EventTicket_Table;
import com.bagevent.db.EventTicket_Table;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by zwj on 2016/7/7.
 */
public class AuditAdapter extends BaseQuickAdapter<Attends,BaseViewHolder>{


    private List<Attends> mData;

    public AuditAdapter(@Nullable List<Attends> data) {
        super(R.layout.meeting_person_item, data);
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, Attends item) {

        String currentLetter = mData.get(helper.getAdapterPosition()).strSort;
        String previousLetter = helper.getAdapterPosition() >= 1 ? mData.get(helper.getAdapterPosition() - 1).strSort : "";
        if (!TextUtils.equals(currentLetter, previousLetter)) {
            helper.setGone(R.id.tv_meeting_letter,true)
                    .setText(R.id.tv_meeting_letter,currentLetter);
        } else {
            helper.setGone(R.id.tv_meeting_letter,false);
        }

        if(item.checkins == 1) {
            helper.setGone(R.id.v_meeting_checkin,true);
        }else {
            helper.setGone(R.id.v_meeting_checkin,false);
        }
        helper.setText(R.id.tv_meeting_name,item.names);
        EventTicket ticket = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(item.eventId)).and(EventTicket_Table.ticketIds.is(item.ticketIds)).querySingle();
        if(ticket != null) {
            helper.setText(R.id.tv_meeting_ticket,ticket.ticketNames);
        }
    }

    public void setAttendList(List<Attends> attendList) {
        this.mData = attendList;
    }

}
